package com.trend.mapper;

import com.trend.domain.CursorRequest;
import com.trend.domain.PageRequest;
import com.trend.domain.Posting;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostingMapper {
    void insertPosting(Posting posting);


    List<Posting> selectFirstPage(CursorRequest cursorRequest);
    List<Posting> selectNextPage(CursorRequest cursorRequest);

    void insertLike(Map map);

    List<Posting> selectAllMyPosting(Map map);
    Integer searchResultCnt(Map map);
}
