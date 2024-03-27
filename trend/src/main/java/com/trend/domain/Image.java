package com.trend.domain;

import lombok.*;
import org.apache.ibatis.type.Alias;

@ToString
@Builder
@Alias("im")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    private int postingId;

    private String fileName;

    private int ord;

}
