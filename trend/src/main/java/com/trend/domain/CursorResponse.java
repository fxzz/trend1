package com.trend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CursorResponse<T> {
    private CursorRequest nextCursorRequest;
    List<T> content;
}
