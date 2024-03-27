package com.trend.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.ibatis.type.Alias;
import org.springframework.web.util.UriComponentsBuilder;


@Data

@NoArgsConstructor
@Alias("PageRequest")
public class PageRequest {


    private int page = 1;

    private int pageSize = 10;

    private String keyword = "";

    private String categoryKeyword;

    public PageRequest(int page, int pageSize, String keyword, String categoryKeyword) {
        this.page = page;
        this.pageSize = pageSize;
        this.keyword = keyword;
        this.categoryKeyword = categoryKeyword != null ? categoryKeyword : "";
    }

    public int getOffset() {
        return (page-1) * pageSize;
    }





}
