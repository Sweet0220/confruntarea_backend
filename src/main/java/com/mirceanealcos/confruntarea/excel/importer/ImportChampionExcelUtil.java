package com.mirceanealcos.confruntarea.excel.importer;

import com.mirceanealcos.confruntarea.entity.Champion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImportChampionExcelUtil {

    public static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String SHEET = "Champions";

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Champion> excelToChampions(InputStream inputStream) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet(SHEET);

            Iterator<Row> rows = sheet.iterator();

            List<Champion> champions = new ArrayList<>();

            int rowNumber = 0;
            while(rows.hasNext()) {
                Row currentRow = rows.next();
                if(rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Champion champion = new Champion();
                int cellIdx = 0;
                while(cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch(cellIdx) {
                        case 0:
                            champion.setName(currentCell.getStringCellValue());
                            break;
                        case 1:
                            Integer hp = Math.toIntExact(Math.round(currentCell.getNumericCellValue()));
                            champion.setHp(hp);
                            break;
                        case 2:
                            Integer damage = Math.toIntExact(Math.round(currentCell.getNumericCellValue()));
                            champion.setBaseDamage(damage);
                            break;
                        case 3:
                            Integer price = Math.toIntExact(Math.round(currentCell.getNumericCellValue()));
                            champion.setPrice(price);
                            break;
                        case 4:
                            Integer mana = Math.toIntExact(Math.round(currentCell.getNumericCellValue()));
                            champion.setMana(mana);
                            break;
                        case 5:
                            champion.setPicture(currentCell.getStringCellValue());
                            break;
                        case 6:
                            champion.setNameColor(currentCell.getStringCellValue());
                            break;
                    }
                    cellIdx++;
                }
                champions.add(champion);
            }
            workbook.close();
            return champions;
        }catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }
    }
}
