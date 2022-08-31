package com.mirceanealcos.confruntarea.excel.exporter;

import com.mirceanealcos.confruntarea.entity.Champion;
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

public class ChampionExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet championSheet;
    private List<Champion> champions;

    public ChampionExcelExporter(List<Champion> champions) {
        this.champions = champions;
        this.workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        championSheet = workbook.createSheet("Champions");

        Row row = championSheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);

        createCell(championSheet, row, 0, "Champion ID", style);
        createCell(championSheet, row, 1, "Name", style);
        createCell(championSheet, row, 2, "HP", style);
        createCell(championSheet, row, 3, "Base Damage", style);
        createCell(championSheet, row, 4, "Price", style);
        createCell(championSheet, row, 5, "Mana", style);
        createCell(championSheet, row, 6, "Picture", style);
        createCell(championSheet, row, 7, "Name Color", style);
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

        for (Champion champion : champions) {
            Row row = championSheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(championSheet, row, columnCount++, champion.getId(), style);
            createCell(championSheet, row, columnCount++, champion.getName(), style);
            createCell(championSheet, row, columnCount++, champion.getHp(), style);
            createCell(championSheet, row, columnCount++, champion.getBaseDamage(), style);
            createCell(championSheet, row, columnCount++, champion.getPrice(), style);
            createCell(championSheet, row, columnCount++, champion.getMana(), style);
            createCell(championSheet, row, columnCount++, champion.getPicture(), style);
            createCell(championSheet, row, columnCount, champion.getNameColor(), style);
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
