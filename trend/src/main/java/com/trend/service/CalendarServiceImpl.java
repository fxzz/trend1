package com.trend.service;

import com.trend.domain.Keyword;
import com.trend.mapper.CalendarMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final CalendarMapper calendarMapper;

    @Override
    public List<String> getMainTodayData(String date) {
        return calendarMapper.getMainTodayData(date);
    }

    @Override
    public List<Keyword> search(Map<String, Object> params) {
        return calendarMapper.search(params);
    }

    @Override
    public void generateExcel(Map<String, Object> params, HttpServletResponse response) {
        List<Keyword> keywordList = calendarMapper.search(params);

        //엑셀
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Keyword");
        HSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("keyword");
        //row.createCell(1).setCellValue("created_at");

        int dataRowIndex = 1;

        for (Keyword keyword : keywordList) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(keyword.getKeyword());
           // dataRow.createCell(1).setCellValue(keyword.getRegDate());
            dataRowIndex ++;
        }


        try {
            ServletOutputStream ops = response.getOutputStream();
            workbook.write(ops);
            workbook.close();
            ops.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

