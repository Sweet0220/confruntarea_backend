package com.mirceanealcos.confruntarea.excel.importer;

import com.mirceanealcos.confruntarea.entity.Ability;
import com.mirceanealcos.confruntarea.entity.Champion;
import com.mirceanealcos.confruntarea.entity.Monster;
import com.mirceanealcos.confruntarea.entity.enums.AbilityType;
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

public class ImportMonsterExcelUtil {
    public static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String SHEET = "Monsters";

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Monster> excelToMonsters(InputStream inputStream) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet(SHEET);

            Iterator<Row> rows = sheet.iterator();

            List<Monster> monsters = new ArrayList<>();

            int rowNumber = 0;
            while(rows.hasNext()) {
                Row currentRow = rows.next();
                if(rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Monster monster = new Monster();
                int cellIdx = 0;
                while(cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch(cellIdx) {
                        case 0:
                            monster.setName(currentCell.getStringCellValue());
                            break;
                        case 1:
                            Integer level = Math.toIntExact(Math.round(currentCell.getNumericCellValue()));
                            monster.setLevel(level);
                            break;
                        case 2:
                            Integer hp = Math.toIntExact(Math.round(currentCell.getNumericCellValue()));
                            monster.setHp(hp);
                            break;
                        case 3:
                            Integer damage = Math.toIntExact(Math.round(currentCell.getNumericCellValue()));
                            monster.setBaseDamage(damage);
                            break;
                        case 4:
                            Integer moneyReward = Math.toIntExact(Math.round(currentCell.getNumericCellValue()));
                            monster.setMoneyReward(moneyReward);
                            break;
                        case 5:
                            Integer expReward = Math.toIntExact(Math.round(currentCell.getNumericCellValue()));
                            monster.setExpReward(expReward);
                            break;
                        case 6:
                            monster.setPicture(currentCell.getStringCellValue());
                            break;
                        case 7:
                            monster.setNameColor(currentCell.getStringCellValue());
                            break;
                    }
                    cellIdx++;
                }
                monsters.add(monster);
            }
            workbook.close();
            return monsters;
        }catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }
    }
}
