package com.example.myproj.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class SearchRequest implements Serializable {

    @ApiModelProperty(value = "Страница")
    protected Integer pageNumber;
    @ApiModelProperty(value = "Размер страницы")
    protected Integer pageSize;

}
