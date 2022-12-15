package com.example.myproj.service;

import static com.example.myproj.util.FileUtils.checkFileExtensionAllowed;
import static com.example.myproj.util.FileUtils.cutExtensionFromBase64;

import com.example.myproj.dto.ImageDto;
import com.example.myproj.exception.StorageFileNotFoundException;
import com.example.myproj.mapper.ImageMapper;
import com.example.myproj.model.Image;
import com.example.myproj.model.post.Post;
import com.example.myproj.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ImageService.
 *
 * @author Evgeny_Ageev
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private static final String FILE_IS_NOT_READABLE = "Could not read file: {} %s";

    private final ImageMapper mapper;
    private final ImageRepository repository;

    @Value("${app.upload.path}")
    private String uploadPath;

    @Value("${app.upload.temp}")
    private String uploadTempPath;

    @Transactional(readOnly = true)
    public List<ImageDto> getAllByPostId(Long postId) {
        return repository.findAllByPostId(postId).stream()
                         .map(mapper::toDto)
                         .sorted()
                         .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ImageDto getImageByPostIdAndUrl(Long postId, String fileName) {
        Image image = repository.findByPostIdAndFileName(postId, fileName)
                                .orElseThrow(EntityNotFoundException::new);
        return mapper.toDto(image);
    }

    @Transactional(readOnly = true)
    public List<Image> findAllByIdList(List<Long> ids) {
        return repository.findAllByIdIn(ids).stream()
                .sorted()
                .collect(Collectors.toList());
    }

    @Transactional
    public Resource loadAsResource(Long postId, String fileName) {
        Image image = repository.findByPostIdAndFileName(postId, fileName)
                                .orElseThrow(EntityNotFoundException::new);

        Resource resource;
        try {
            Path directory = Paths.get(uploadPath, "" + postId);
            Path file = directory.resolve(fileName);
            resource = new UrlResource(file.toUri());
            if(resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                log.error(FILE_IS_NOT_READABLE, fileName);
                throw new StorageFileNotFoundException(String.format(FILE_IS_NOT_READABLE, fileName));
            }
        } catch (MalformedURLException e) {
            log.error(e.getMessage(), e);
            throw new StorageFileNotFoundException(String.format(FILE_IS_NOT_READABLE, fileName), e);
        }
    }

//    @Transactional
//    public ImageDto uploadFile(Long postId, MultipartFile file) {
////        String srcPath = ImageService.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//
//        Post post = postRepository.findById(postId)
//                                  .orElseThrow(EntityNotFoundException::new);
//
//        return uploadFileInner(post, file);
//    }
//
//    @Transactional
//    public List<ImageDto> multiUpload(Long postId, MultipartFile[] files) {
//        List<ImageDto> images = new ArrayList<>();
//        Arrays.asList(files)
//              .forEach(file -> images.add(uploadFile(postId, file)));
//        return images;
//    }

    /**
     * Uploading to a temporary store.
     * It's where images don't have urls.
     *
     * @param file  MultipartFile
     * @return ImageDto
     */
    @Transactional
    public ImageDto uploadTempFile(MultipartFile file) {
        String[] fileNameArr = checkAndGetFilename(file);
        doUploadTempFile(fileNameArr, file);

        Image image = new Image()
                .setFileName(fileNameArr[0] + "." + fileNameArr[1]);
        return mapper.toDto(repository.save(image));
    }

    @Transactional
    public List<ImageDto> tempMultiUpload(MultipartFile[] files) {
        List<ImageDto> images = new ArrayList<>();
        Arrays.asList(files)
              .forEach(file -> images.add(uploadTempFile(file)));
        return images.stream()
                     .sorted()
                     .collect(Collectors.toList());
    }

    @Transactional
    public ImageDto uploadTempFileBase64(String file) {
        String[] parts = file.split(",");
        String extension = cutExtensionFromBase64(parts[0]);
        checkFileExtensionAllowed(log, extension);
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "." + extension;
        try {
            byte[] fileByte = Base64.getMimeDecoder().decode(parts[1]);
            new FileOutputStream(uploadTempPath + File.separator + fileName).write(fileByte);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        Image image = new Image()
                .setFileName(fileName);
        return mapper.toDto(repository.save(image));
    }

//    public List<ImageDto> multiUploadFromPost(Post post, MultipartFile[] files) {
//        List<ImageDto> images = new ArrayList<>();
//        Arrays.asList(files)
//              .forEach(file -> images.add(uploadFileInner(post, file)));
//        return images;
//    }

    @Transactional
    public void removeImages(Post post, List<Image> images) {
        Long postId = post.getId();
        createDirectoryIfNeeded(postId);
        images.forEach(image -> {
            removeFileByImage(postId, image);
            image.setPost(post);
        });
        repository.saveAll(images);
    }

    private void removeFileByImage(Long postId, Image image) {
        String fileName = image.getFileName();
        try {
            Files.move(Paths.get(uploadTempPath, fileName).toAbsolutePath(),
                    Paths.get(uploadPath, "" + postId, fileName).toAbsolutePath());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

//    private ImageDto uploadFileInner(Post post, MultipartFile file) {
//        String[] fileNameArr = checkAndGetFilename(file);
//        createDirectoryIfNeeded(post.getId());
//        doUploadFile(post.getId(), fileNameArr, file);
//
//        Image image = new Image()
//                .setPost(post)
//                .setFileName(fileNameArr[0] + "." + fileNameArr[1]);
//        return mapper.toDto(repository.save(image));
//    }

    private String[] checkAndGetFilename(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        checkFileExtensionAllowed(log, extension);

        String name = FilenameUtils.getBaseName(file.getOriginalFilename());
        return new String[] { name, extension };
    }

    private void createDirectoryIfNeeded(Long postId) {
        try {
            Path directory = Paths.get(uploadPath, "" + postId);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

//    private void doUploadFile(Long postId, String[] fileNameArr, MultipartFile file) {
//        String uuid = UUID.randomUUID().toString();
//        fileNameArr[0] = uuid;
//        try {
//            File template = Paths.get(uploadPath, "" + postId, uuid + "." + fileNameArr[1])
//                                 .toAbsolutePath().toFile();
//            file.transferTo(template);
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        }
//    }

    private void doUploadTempFile(String[] fileNameArr, MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        fileNameArr[0] = uuid;
        try {
            File template = Paths.get(uploadTempPath, uuid + "." + fileNameArr[1])
                    .toAbsolutePath().toFile();
            file.transferTo(template);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
