package com.trend.domain;

import lombok.*;
import org.apache.ibatis.type.Alias;

@ToString
@Alias("Category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Integer categoryId;
    private String name;
    private Integer parentCategoryId;
}
