package com.examination.service.impl;

import com.examination.bean.ScoreVM;
import com.examination.service.PoiService;
import com.examination.utils.LocalDateUtil;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @Author:晓风残月Lx
 * @Date: 2022/10/20 8:32
 */
@Service
public class PoiServiceImpl implements PoiService {

    /**
     * 导出成绩表
     */
    @Override
    public void downLoadExcel(List<ScoreVM> scoreVMList) throws IOException {
        String PATH ="D:\\";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
        //设置要导出的文件的名字 注意必须加上    .xls
        String fileName = PATH+"学生成绩表"+".xls";

        //新增数据行，并且设置单元格数据
        int rowNum = 1;

        String[] headers = { "排名", "用户名", "真实姓名", "学号", "分数" , "交卷时间"};

        HSSFRow row = sheet.createRow(0);

        //在excel表中添加表头
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //在表中存放查询到的数据放入对应的列

        for (ScoreVM scoreVM : scoreVMList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(scoreVM.getSId());
            row1.createCell(1).setCellValue(scoreVM.getUserName());
            row1.createCell(2).setCellValue(scoreVM.getRealName());
            row1.createCell(3).setCellValue(scoreVM.getUId());
            row1.createCell(4).setCellValue(scoreVM.getMark());
            row1.createCell(5).setCellValue(LocalDateUtil.localDateToString(scoreVM.getExamTime()));
            rowNum++;
        }
        //获取io流
        FileOutputStream fos = new FileOutputStream(fileName);
        //生成一张表
        workbook.write(fos);
        fos.close();
    }
}
