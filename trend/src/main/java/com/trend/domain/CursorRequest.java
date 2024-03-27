package com.trend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Alias("req")
public class CursorRequest {

    private Integer postingId;
    private int size;
    public static final Long NONE_postingId = -1L;

    /*
    * 커서 키(PK)가 있을 때에는 인덱스 필수, 중복 존재 X
    *
    */

    public Boolean hasPostingId() {
        return postingId != null && !postingId.equals(NONE_postingId);
    }

    public  CursorRequest next(Integer postingId) {
        return new CursorRequest(postingId, size);
    }

}
