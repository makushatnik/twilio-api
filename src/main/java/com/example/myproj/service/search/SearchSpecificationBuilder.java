package com.example.myproj.service.search;

import com.google.common.base.Preconditions;
import com.example.myproj.model.Location;
import com.example.myproj.model.post.Post;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class SearchSpecificationBuilder<E, S> {

    public static final String LOCATION_ID = Post.FN_LOCATION + "." + Location.FN_ID;
    public static final String LOCATION_ZIPCODE = Post.FN_LOCATION + "." + Location.FN_ZIPCODE;


    protected static final String SPECIFICATIONS_EQUAL_NULL_ERROR = "specifications param must be provided";
    protected static final String FIELD_NAME_IS_BLANK_ERROR = "fieldName param must be provided";
    protected static final String USER_ID_EQUAL_NULL_ERROR = "userId param must be provided";

    public Optional<Specification<E>> getSearchSpecification(S filterDto, Long userId) {
        Specification<E> searchSpec;
        List<Specification<E>> specifications = getSpecifications(filterDto, userId);

        if (specifications.isEmpty()) {
            return Optional.empty();
        }

        if (specifications.size() == 1) {
            Specification<E> spec = specifications.get(0);
            searchSpec = spec == null ? null : Specification.where(spec);
            return Optional.ofNullable(searchSpec);
        }

        searchSpec = specifications.get(0);
        for (int i = 1; i < specifications.size(); i++) {
            if (searchSpec == null) {
                searchSpec = specifications.get(i);
                continue;
            }
            searchSpec = Specification.where(searchSpec.and(specifications.get(i)));
        }
        return Optional.ofNullable(searchSpec);
    }

    protected void addEqualSpecification(List<Specification<E>> specifications, String fieldName, Object searchValue) {
        Preconditions.checkArgument(specifications != null, SPECIFICATIONS_EQUAL_NULL_ERROR);
        Preconditions.checkArgument(StringUtils.isNotBlank(fieldName), FIELD_NAME_IS_BLANK_ERROR);

        if (searchValue != null) {
            if (LOCATION_ID.equals(fieldName)) {
                specifications.add((root, query, cb) -> cb.equal(root.get(Post.FN_LOCATION).get(Location.FN_ID), searchValue));
            } else if (LOCATION_ZIPCODE.equals(fieldName)) {

            } else {
                specifications.add((root, query, cb) -> cb.equal(root.get(fieldName), searchValue));
            }
        }
    }

    protected void addInSpecification(List<Specification<E>> specifications, String fieldName, Object searchValue) {
        Preconditions.checkArgument(specifications != null, SPECIFICATIONS_EQUAL_NULL_ERROR);
        Preconditions.checkArgument(StringUtils.isNotBlank(fieldName), FIELD_NAME_IS_BLANK_ERROR);

        if (searchValue != null) {
            if ((searchValue instanceof Collection<?>) && ((Collection<?>) searchValue).size() != 0) {
                specifications.add((root, query, cb) -> cb.isTrue(root.get(fieldName).in((Collection<?>) searchValue)));
            } else {
                specifications.add((root, query, cb) -> cb.isTrue(root.get(fieldName).in(searchValue)));
            }
        }
    }

    protected void addNotInSpecification(List<Specification<E>> specifications, String fieldName, Object searchValue) {
        Preconditions.checkArgument(specifications != null, SPECIFICATIONS_EQUAL_NULL_ERROR);
        Preconditions.checkArgument(StringUtils.isNotBlank(fieldName), FIELD_NAME_IS_BLANK_ERROR);

        if (searchValue != null) {
            if ((searchValue instanceof Collection<?>) && ((Collection<?>) searchValue).size() != 0) {
                specifications.add((root, query, cb) -> cb.isFalse(root.get(fieldName).in((Collection<?>) searchValue)));
            } else {
                specifications.add((root, query, cb) -> cb.isFalse(root.get(fieldName).in(searchValue)));
            }
        }
    }

    protected abstract List<Specification<E>> getSpecifications(S filterDto, Long userId);

}
