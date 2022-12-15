package com.example.myproj.controller;

import static com.example.myproj.util.CommonUtils.*;
import static com.example.myproj.util.FileUtils.getResourceLength;
import static org.springframework.http.ResponseEntity.ok;

import com.example.myproj.controller.api.ImageControllerAPI;
import com.example.myproj.dto.ImageDto;
import com.example.myproj.service.ImageService;
import com.example.myproj.util.MediaTypeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * ImageController.
 *
 * @author Evgeny_Ageev
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageController implements ImageControllerAPI {

    private final ImageService service;
    private final ServletContext servletContext;

    @Override
    public ResponseEntity<List<ImageDto>> handleGetAll(@PathVariable("postId") Long postId) {
        checkIfLongIdIsPositive(postId);
        return ok(service.getAllByPostId(postId));
    }

    @Override
    public ResponseEntity<ImageDto> handleGet(@PathVariable("postId") Long postId,
                                              @PathVariable("fileName") String fileName) {
        checkIfLongIdIsPositive(postId);
        checkIfStringIsBlank(fileName);
        return ok(service.getImageByPostIdAndUrl(postId, fileName));
    }

    @Override
    public ResponseEntity<Resource> handleDownloadFile(@PathVariable("postId") Long postId,
                                                       @PathVariable("fileName") String fileName) {
        checkIfLongIdIsPositive(postId);
        checkIfStringIsBlank(fileName);

        Resource resource = service.loadAsResource(postId, fileName);
        return ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaTypeUtils.getMediaTypeForFileName(servletContext, fileName))
                .contentLength(getResourceLength(resource, log))
                .body(resource);
    }

//    @Override
//    public ResponseEntity<ImageDto> handleFileUpload(@PathVariable("postId") Long postId,
//                                                     @RequestParam("file") MultipartFile file) {
//        checkIfLongIdIsPositive(postId);
//        checkIfFileIsEmpty(file);
//        return ok(service.uploadFile(postId, file));
//    }
//
//    @Override
//    public ResponseEntity<List<ImageDto>> handleMultiUpload(@PathVariable("postId") Long postId,
//                                                          @RequestParam("files") MultipartFile[] files) {
//        checkIfLongIdIsPositive(postId);
//        checkIfArrayOfFilesIsEmpty(files);
//        return ok(service.multiUpload(postId, files));
//    }

    @Override
    public ResponseEntity<ImageDto> handleTempFileUpload(@RequestParam("file") MultipartFile file) {
        checkIfFileIsEmpty(file);
        return ok(service.uploadTempFile(file));
    }

    @Override
    public ResponseEntity<List<ImageDto>> handleTempMultiUpload(@RequestParam("files") MultipartFile[] files) {
        checkIfArrayOfFilesIsEmpty(files);
        return ok(service.tempMultiUpload(files));
    }

    @Override
    public ResponseEntity<ImageDto> handleTempFileUploadBase64(@RequestBody String file) {
        checkIfStringIsBlank(file);
        return ok(service.uploadTempFileBase64(file));
    }
}
