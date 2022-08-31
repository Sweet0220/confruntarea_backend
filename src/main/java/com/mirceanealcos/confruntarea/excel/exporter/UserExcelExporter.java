package com.mirceanealcos.confruntarea.excel.exporter;

import com.mirceanealcos.confruntarea.entity.User;
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

public class UserExcelExporter{

    private XSSFWorkbook workbook;
    private XSSFSheet userSheet;
    private List<User> users;


    public UserExcelExporter(List<User> users) {
        this.users = users;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        userSheet = workbook.createSheet("Users");

        Row row = userSheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);

        createCell(userSheet, row, 0, "User ID", style);
        createCell(userSheet, row, 1, "Username", style);
        createCell(userSheet, row, 2, "Email", style);
        createCell(userSheet, row, 3, "Role", style);
        createCell(userSheet, row, 4, "Funds", style);
        createCell(userSheet, row, 5, "Picture", style);
        createCell(userSheet, row, 6, "Level", style);
        createCell(userSheet, row, 7, "XP", style);
        createCell(userSheet, row, 8, "Activated", style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style= workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(16);
        style.setFont(font);

        for(User user : users) {
            Row row = userSheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(userSheet, row, columnCount++, user.getId(), style);
            createCell(userSheet, row, columnCount++, user.getUsername(), style);
            createCell(userSheet, row, columnCount++, user.getEmail(), style);
            createCell(userSheet, row, columnCount++, user.getRole(), style);
            createCell(userSheet, row, columnCount++, user.getFunds(), style);
            createCell(userSheet, row, columnCount++, user.getPicture(), style);
            createCell(userSheet, row, columnCount++, user.getLevel(), style);
            createCell(userSheet, row, columnCount++, user.getExp(), style);
            createCell(userSheet, row, columnCount, user.isEnabled(), style);
        }
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

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }
}
