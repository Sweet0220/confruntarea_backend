package com.mirceanealcos.confruntarea.excel.exporter;

import com.mirceanealcos.confruntarea.entity.Ability;
import com.mirceanealcos.confruntarea.entity.Item;
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

public class AbilityExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet abilitySheet;
    private List<Ability> abilities;

    public AbilityExcelExporter(List<Ability> abilities) {
        this.abilities = abilities;
        this.workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        abilitySheet = workbook.createSheet("Abilities");

        Row row = abilitySheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);

        createCell(abilitySheet, row, 0, "Ability ID", style);
        createCell(abilitySheet, row, 1, "Name", style);
        createCell(abilitySheet, row, 2, "Type", style);
        createCell(abilitySheet, row, 3, "Healing", style);
        createCell(abilitySheet, row, 4, "Damage", style);
        createCell(abilitySheet, row, 5, "Picture", style);
        createCell(abilitySheet, row, 6, "Mana Cost", style);
        createCell(abilitySheet, row, 7, "For Champion", style);
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

        for(Ability ability : abilities) {
            Row row = abilitySheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(abilitySheet, row, columnCount++, ability.getId(), style);
            createCell(abilitySheet, row, columnCount++, ability.getName(), style);
            createCell(abilitySheet, row, columnCount++, ability.getType(), style);
            createCell(abilitySheet, row, columnCount++, ability.getHealing(), style);
            createCell(abilitySheet, row, columnCount++, ability.getDamage(), style);
            createCell(abilitySheet, row, columnCount++, ability.getPicture(), style);
            createCell(abilitySheet, row, columnCount++, ability.getManaCost(), style);
            createCell(abilitySheet, row, columnCount, ability.getChampion().getName(), style);
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
