package com.mirceanealcos.confruntarea.excel.exporter;

import com.mirceanealcos.confruntarea.entity.Ability;
import com.mirceanealcos.confruntarea.entity.Monster;
import com.mirceanealcos.confruntarea.entity.enums.AbilityType;
import com.mirceanealcos.confruntarea.entity.enums.ItemType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MonsterExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet monsterSheet;
    private List<Monster> monsters;

    public MonsterExcelExporter(List<Monster> monsters) {
        this.monsters = monsters;
        this.workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        monsterSheet = workbook.createSheet("Monsters");

        Row row  = monsterSheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);

        createCell(monsterSheet, row, 0, "Monster ID", style);
        createCell(monsterSheet, row, 1, "Name", style);
        createCell(monsterSheet, row, 2, "Level", style);
        createCell(monsterSheet, row, 3, "HP", style);
        createCell(monsterSheet, row, 4, "Base Damage", style);
        createCell(monsterSheet, row, 5, "Money Reward", style);
        createCell(monsterSheet, row, 6, "Exp Reward", style);
        createCell(monsterSheet, row, 7, "Picture", style);
        createCell(monsterSheet, row, 8, "Name Color", style);
    }

    private void createCell(XSSFSheet sheet, Row row, int columnCount, Object value, CellStyle style) {

        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);

        if(value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if(value instanceof Integer){
            cell.setCellValue((Integer) value);
        } else if(value instanceof ItemType || value instanceof AbilityType) {
            cell.setCellValue(value.toString());
        } else {
            cell.setCellValue((String) value);
        }

        cell.setCellStyle(style);

    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(16);
        style.setFont(font);

        for(Monster monster : monsters) {
            Row row = monsterSheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(monsterSheet, row, columnCount++, monster.getId(),style);
            createCell(monsterSheet, row, columnCount++, monster.getName(),style);
            createCell(monsterSheet, row, columnCount++, monster.getLevel(),style);
            createCell(monsterSheet, row, columnCount++, monster.getHp(),style);
            createCell(monsterSheet, row, columnCount++, monster.getBaseDamage(),style);
            createCell(monsterSheet, row, columnCount++, monster.getMoneyReward(),style);
            createCell(monsterSheet, row, columnCount++, monster.getExpReward(),style);
            createCell(monsterSheet, row, columnCount++, monster.getPicture(),style);
            createCell(monsterSheet, row, columnCount, monster.getNameColor(),style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }
}
