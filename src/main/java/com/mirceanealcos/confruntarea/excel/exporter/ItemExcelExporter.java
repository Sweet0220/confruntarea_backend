package com.mirceanealcos.confruntarea.excel.exporter;

import com.mirceanealcos.confruntarea.entity.Champion;
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

public class ItemExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet itemSheet;
    private List<Item> items;

    public ItemExcelExporter(List<Item> items) {
        this.items = items;
        this.workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        itemSheet = workbook.createSheet("Items");

        Row row = itemSheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);

        createCell(itemSheet, row, 0, "Item ID", style);
        createCell(itemSheet, row, 1, "Name", style);
        createCell(itemSheet, row, 2, "Bonus Damage", style);
        createCell(itemSheet, row, 3, "Bonus Hp", style);
        createCell(itemSheet, row, 4, "Price", style);
        createCell(itemSheet, row, 5, "Type", style);
        createCell(itemSheet, row, 6, "Picture", style);
        createCell(itemSheet, row, 7, "Name Color", style);
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

        for(Item item : items) {
            Row row = itemSheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(itemSheet, row, columnCount++, item.getId(), style);
            createCell(itemSheet, row, columnCount++, item.getName(), style);
            createCell(itemSheet, row, columnCount++, item.getBonusDamage(), style);
            createCell(itemSheet, row, columnCount++, item.getBonusHp(), style);
            createCell(itemSheet, row, columnCount++, item.getPrice(), style);
            createCell(itemSheet, row, columnCount++, item.getType(), style);
            createCell(itemSheet, row, columnCount++, item.getPicture(), style);
            createCell(itemSheet, row, columnCount, item.getNameColor(), style);
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
