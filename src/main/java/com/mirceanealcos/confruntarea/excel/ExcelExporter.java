package com.mirceanealcos.confruntarea.excel;

import com.mirceanealcos.confruntarea.entity.*;
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

public class ExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet championSheet;
    private XSSFSheet userSheet;
    private XSSFSheet itemSheet;
    private XSSFSheet monsterSheet;
    private XSSFSheet abilitySheet;
    private List<Champion> champions;
    private List<User> users;
    private List<Item> items;
    private List<Monster> monsters;
    private List<Ability> abilities;

    public ExcelExporter(List<Champion> champions, List<User> users, List<Item> items, List<Monster> monsters, List<Ability> abilities) {
        this.champions = champions;
        this.users = users;
        this.items = items;
        this.monsters = monsters;
        this.abilities = abilities;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        initChampionSheet();
        initUserSheet();
        initItemSheet();
        initMonsterSheet();
        initAbilitySheet();
    }

    private void initUserSheet() {
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

    private void initChampionSheet() {
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

    private void initItemSheet() {
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

    private void initMonsterSheet() {
        monsterSheet = workbook.createSheet("Monsters");

        Row row = monsterSheet.createRow(0);

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

    private void initAbilitySheet() {
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
        } else {
            cell.setCellValue((String) value);
        }

        cell.setCellStyle(style);

    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style= workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(16);
        style.setFont(font);

        for(Champion champion : champions) {
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

        rowCount = 1;
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

        rowCount = 1;
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

        rowCount = 1;
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

        rowCount = 1;
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
            createCell(abilitySheet, row, columnCount++, ability.getChampion().getName(), style);
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
