package com.trend.domain;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@ToString
@Builder
@Alias("po")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Posting {
    private Integer postingId;
    private String content;
    private Integer accountId;
    private Date createdAt;
    private String nickname;
    private String imageNames;
    private String profileImage;


    private String commentContent;
    private String commentNickname;
    private String commentCreatedAt;
    private Integer commentCount;
    private String commentProfileImage;
    private Integer likeCount;
}


