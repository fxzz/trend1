package com.trend.mapper;

import com.trend.domain.Image;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {

    void insertImage(Image image);
}
