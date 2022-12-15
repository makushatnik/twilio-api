package com.example.myproj.service.search;

import com.google.common.base.Preconditions;
import com.example.myproj.dto.FilterDto;
import com.example.myproj.model.User;
import com.example.myproj.model.post.Post;
import com.example.myproj.model.post.PostLike;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PostSearchSpecificationBuilder extends SearchSpecificationBuilder<Post, FilterDto> {

    private static final String ALL = "ALL";

    @Override
    protected List<Specification<Post>> getSpecifications(FilterDto filterDto, Long userId) {
        if (filterDto == null) {
            return Collections.emptyList();
        }
        List<Specification<Post>> result = new LinkedList<>();
        //filters that might change
        addEqualSpecification(result, Post.FN_ID, filterDto.getId());
        addEqualSpecification(result, Post.FN_CONDITION, filterDto.getCondition());
        addEqualSpecification(result, LOCATION_ID, filterDto.getLocationId());
        addEqualSpecification(result, LOCATION_ZIPCODE, filterDto.getZipCode());
        addEqualSpecification(result, Post.FN_STATE, filterDto.getState());
        List<String> categories = filterDto.getCategories();
        if (!CollectionUtils.isEmpty(categories)) {
            if (categories.size() == 1 && ALL.equals(categories.get(0))) {
                //nothing to do
            } else {
                categories.forEach(category -> addInSpecification(result, Post.FN_CATEGORY, category));
            }
        }

        //hard filters
        addLikeNotInSpecification(result, Post.FN_ID, userId);
        addRenewAtDescSort(result, Post.FN_RENEW_AT);
        return result;
    }

    private void addRenewAtDescSort(List<Specification<Post>> specs, String fieldName) {
        Preconditions.checkArgument(specs != null, SPECIFICATIONS_EQUAL_NULL_ERROR);
        Preconditions.checkArgument(StringUtils.isNotBlank(fieldName), FIELD_NAME_IS_BLANK_ERROR);

        specs.add((root, query, cb) -> {
            query.orderBy(cb.desc(root.get(fieldName)));
            return cb.conjunction();
        });
    }

    private void addLikeNotInSpecification(List<Specification<Post>> specs, String fieldName, Long userId) {
        Preconditions.checkArgument(specs != null, SPECIFICATIONS_EQUAL_NULL_ERROR);
        Preconditions.checkArgument(StringUtils.isNotBlank(fieldName), FIELD_NAME_IS_BLANK_ERROR);
        Preconditions.checkArgument(userId != null, USER_ID_EQUAL_NULL_ERROR);

        specs.add((root, query, cb) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<PostLike> subRoot = subquery.from(PostLike.class);
            subquery.select(subRoot.get(PostLike.FN_POST).get(Post.FN_ID))
                    .where(cb.equal(subRoot.get(PostLike.FN_USER).get(User.FN_ID), userId));

            return cb.isTrue(cb.not(root.get(Post.FN_ID).in(subquery)));
        });
    }
}
