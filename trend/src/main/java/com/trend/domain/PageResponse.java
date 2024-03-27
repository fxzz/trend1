package com.trend.domain;


import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponse<E> {

    private List<E> dtoList;

    private List<Integer> pageNumList;

    private PageRequest pageRequest;

    private int naviSize;

    private boolean prev, next;

    private int totalCount, prevPage, nextPage, totalPage, current;

    public PageResponse(List<E> dtoList, PageRequest pageRequest, int totalCount) {
        this.dtoList = dtoList;
        this.pageRequest = pageRequest;
        this.totalCount = totalCount;

        // 페이지 네비 이전 1 2 3 4 5 다음 에서 1 2 3 4 5 몇개 보여줄지
        naviSize = pageRequest.getPageSize();

        // 전체 페이지 수 계산
        totalPage = (int) Math.ceil(totalCount / (double) naviSize);

        // 현재 페이지 설정
        current = pageRequest.getPage();

        // 시작 페이지 계산
        int start = (pageRequest.getPage()-1) / naviSize * naviSize +1;

        // 끝 페이지 계산
        int end = Math.min(start + naviSize -1, totalPage);

        // 페이지 번호 목록 생성
        pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

        // 이전 페이지 버튼 활성화 여부 설정
        prev = current > 1;

        // 다음 페이지 버튼 활성화 여부 설정
        next = current < totalPage;

        // 이전 페이지 번호 설정
        prevPage = prev ? current - 1 : 0;

       // 다음 페이지 번호 설정
        nextPage = next ? current + 1 : 0;
    }
}