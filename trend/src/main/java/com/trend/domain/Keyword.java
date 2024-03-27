package com.trend.domain;

import lombok.*;
import org.apache.ibatis.type.Alias;


import java.sql.Date;


@Alias("kw")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Keyword {
    private String keyword;
    private Date regDate;
}
