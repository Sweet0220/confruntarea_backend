package com.mirceanealcos.confruntarea.excel.importer;

import com.mirceanealcos.confruntarea.entity.Ability;
import com.mirceanealcos.confruntarea.entity.Champion;
import com.mirceanealcos.confruntarea.entity.enums.AbilityType;
import com.mirceanealcos.confruntarea.repository.ChampionRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ImportAbilityExcelUtil {
    public static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String SHEET = "Abilities";
    private final ChampionRepository championRepository;

    @Autowired
    public ImportAbilityExcelUtil(ChampionRepository championRepository) {
        this.championRepository = championRepository;
    }

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public List<Ability> excelToAbilities(InputStream inputStream) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet(SHEET);

            Iterator<Row> rows = sheet.iterator();

            List<Ability> abilities = new ArrayList<>();

            int rowNumber = 0;
            while(rows.hasNext()) {
                Row currentRow = rows.next();
                if(rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Ability ability = new Ability();
                int cellIdx = 0;
                while(cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch(cellIdx) {
                        case 0:
                            ability.setName(currentCell.getStringCellValue());
                            break;
                        case 1:
                            String type = currentCell.getStringCellValue();
                            ability.setType(AbilityType.valueOf(type));
                            break;
                        case 2:
                            Integer healing = Math.toIntExact(Math.round(currentCell.getNumericCellValue()));
                            ability.setHealing(healing);
                            break;
                        case 3:
                            Integer damage = Math.toIntExact(Math.round(currentCell.getNumericCellValue()));
                            ability.setDamage(damage);
                            break;
                        case 4:
                            String picture = currentCell.getStringCellValue();
                            ability.setPicture(picture);
                            break;
                        case 5:
                            Integer manaCost = Math.toIntExact(Math.round(currentCell.getNumericCellValue()));
                            ability.setManaCost(manaCost);
                            break;
                        case 6:
                            String championName = currentCell.getStringCellValue();
                            Champion champion = championRepository.findByName(championName);
                            ability.setChampion(champion);
                            break;
                    }
                    cellIdx++;
                }
                abilities.add(ability);
            }
            workbook.close();
            return abilities;
        }catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }
    }

}
