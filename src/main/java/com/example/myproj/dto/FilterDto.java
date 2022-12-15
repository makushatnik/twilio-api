package com.example.myproj.dto;

import com.example.myproj.enums.Condition;
import com.example.myproj.enums.PostState;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@ApiModel
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class FilterDto extends SearchRequest {

    private Long id;
    private Long locationId;
    private String zipCode;
    private Condition condition;
    private PostState state;
    private List<String> categories;
}
