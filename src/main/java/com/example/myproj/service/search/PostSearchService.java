package com.example.myproj.service.search;

import com.example.myproj.dto.FilterDto;
import com.example.myproj.dto.post.PostResponse;
import com.example.myproj.mapper.PostMapper;
import com.example.myproj.model.post.Post;
import com.example.myproj.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostSearchService {

    private final PostMapper mapper;
    private final PostRepository repository;

    @Value("${app.pageSize}")
    private Integer maxPageSize;

    @Transactional(readOnly = true)
    public Page<PostResponse> find(FilterDto searchRequest, Long userId) {
        Integer page = searchRequest.getPageNumber();
        Integer pageSize = searchRequest.getPageSize();
        PageRequest pageRequest = PageRequest.of(page != null ? page : 0, pageSize != null ? pageSize : maxPageSize);

        return find(searchRequest, pageRequest, userId);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> find(FilterDto searchRequest, Sort sort, Long userId) {
        Integer page = searchRequest.getPageNumber();
        Integer pageSize = searchRequest.getPageSize();
        PageRequest pageRequest = PageRequest.of(page != null ? page : 0, pageSize != null ? pageSize : maxPageSize,
                sort);

        return find(searchRequest, pageRequest, userId);
    }

    private Page<PostResponse> find(FilterDto searchRequest, PageRequest pageRequest, Long userId) {
        Specification<Post> specification = getSearchSpecificationsBuilder()
                .getSearchSpecification(searchRequest, userId).orElse(null);

        Page<Post> result = repository.findAll(specification, pageRequest);

        return convertToDto(result);
    }

    private SearchSpecificationBuilder<Post, FilterDto> getSearchSpecificationsBuilder() {
        return new PostSearchSpecificationBuilder();
    }

    private Page<PostResponse> convertToDto(Page<Post> content) {
        return content.map(mapper::toResponse);
    }
}
