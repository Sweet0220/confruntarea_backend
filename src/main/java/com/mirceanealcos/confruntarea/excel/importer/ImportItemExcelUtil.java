package com.mirceanealcos.confruntarea.excel.importer;

import com.mirceanealcos.confruntarea.entity.Item;
import com.mirceanealcos.confruntarea.entity.enums.ItemType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImportItemExcelUtil {
    public static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String SHEET = "Items";

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Item> excelToItems(InputStream inputStream) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet(SHEET);

            Iterator<Row> rows = sheet.iterator();

            List<Item> items = new ArrayList<>();

            int rowNumber = 0;
            while(rows.hasNext()) {
                Row currentRow = rows.next();
                if(rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Item item = new Item();
                int cellIdx = 0;
                while(cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch(cellIdx) {
                        case 0:
                            item.setName(currentCell.getStringCellValue());
                            break;
                        case 1:
                            Integer bonusDamage = Math.toIntExact(Math.round(currentCell.getNumericCellValue()));
                            item.setBonusDamage(bonusDamage);
                            break;
                        case 2:
                            Integer hp = Math.toIntExact(Math.round(currentCell.getNumericCellValue()));
                            item.setBonusHp(hp);
                            break;
                        case 3:
                            Integer price = Math.toIntExact(Math.round(currentCell.getNumericCellValue()));
                            item.setPrice(price);
                            break;
                        case 4:
                            String type = currentCell.getStringCellValue();
                            item.setType(ItemType.valueOf(type));
                            break;
                        case 5:
                            item.setPicture(currentCell.getStringCellValue());
                            break;
                        case 6:
                            item.setNameColor(currentCell.getStringCellValue());
                            break;
                    }
                    cellIdx++;
                }
                items.add(item);
            }
            workbook.close();
            return items;
        }catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }
    }
}
