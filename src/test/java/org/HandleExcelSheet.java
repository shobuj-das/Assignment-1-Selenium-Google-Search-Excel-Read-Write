package org;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.checkerframework.checker.units.qual.K;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HandleExcelSheet {

    public String getDays(){
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        String day = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
       // System.out.println(day);
        return day;
    }


    public List<String> readExcelSheet(String sheetName) throws Exception{
        //String sheetName = getDays(); // temp
        List<String> keyWords = new ArrayList<>();

        try{
            File filePath = new File("excelSheet.xlsx");
            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);

            for(Row row : sheet){
                Cell cell = row.getCell(2);// 2 for the certain column
                if(cell != null){
                    keyWords.add(cell.getStringCellValue());
                }
            }
            fis.close();
            workbook.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        return keyWords;

//
//        for(String s : keyWords){
//            System.out.println(s);
//        }
//        System.out.println(keyWords.size());

    }

    @Test
    public void writeToExcelSheet(String sheetName, int rowIndex, String longestSuggestion, String shortestSuggestion){
       // String sheetName = "Monday";

        try{
            File filePath = new File("excelSheet.xlsx");
            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);

            //int rowIndex = 2; // get it as argument
            Row row = sheet.getRow(rowIndex);

            row.createCell(4).setCellValue(longestSuggestion);
            row.createCell(5).setCellValue(shortestSuggestion);

            fis.close();

            // now save the file
            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();
            workbook.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}
