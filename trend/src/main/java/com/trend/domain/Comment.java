package com.trend.domain;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@ToString
@Builder
@Alias("co")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Integer commentId;
    private Integer accountId;
    private Integer postingId;
    private String content;
    private Date createdAt;


}
