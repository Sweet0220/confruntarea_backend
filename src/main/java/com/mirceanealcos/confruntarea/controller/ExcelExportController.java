package com.mirceanealcos.confruntarea.controller;

import com.mirceanealcos.confruntarea.entity.*;
import com.mirceanealcos.confruntarea.excel.exporter.*;
import com.mirceanealcos.confruntarea.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/export/excel")
@Slf4j
public class ExcelExportController {

    private final ChampionService championService;
    private final UserService userService;
    private final ItemService itemService;
    private final MonsterService monsterService;
    private final AbilityService abilityService;

    @Autowired
    public ExcelExportController(ChampionService championService, UserService userService, ItemService itemService, MonsterService monsterService, AbilityService abilityService) {
        this.championService = championService;
        this.userService = userService;
        this.itemService = itemService;
        this.monsterService = monsterService;
        this.abilityService = abilityService;
    }

    @GetMapping
    public void exportAllEntitiesToExcel(HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = champions_" + currentDateTime + ".xlsx";

        ContentDisposition contentDisposition = ContentDisposition.builder("inline")
                .filename(headerValue)
                .build();

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());

        List<Champion> champions = championService.getAllChampionsAsEntity();
        List<User> users = userService.getAllUsersAsEntity();
        List<Item> items = itemService.getAllItemsAsEntity();
        List<Monster> monsters = monsterService.getAllMonstersAsEntity();
        List<Ability> abilities = abilityService.getAllAbilitiesAsEntity();

        ExcelExporter excelExporter = new ExcelExporter(champions, users, items, monsters, abilities);

        try {
            excelExporter.export(response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @GetMapping(path = "/users")
    public void exportUsersToExcel(HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        List<User> users = userService.getAllUsersAsEntity();

        UserExcelExporter excelExporter = new UserExcelExporter(users);

        try {
            excelExporter.export(response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @GetMapping(path = "/champions")
    public void exportChampionsToExcel(HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        List<Champion> champions = championService.getAllChampionsAsEntity();

        ChampionExcelExporter excelExporter = new ChampionExcelExporter(champions);

        try {
            excelExporter.export(response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @GetMapping(path = "/items")
    public void exportItemsToExcel(HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        List<Item> items = itemService.getAllItemsAsEntity();

        ItemExcelExporter excelExporter = new ItemExcelExporter(items);

        try {
            excelExporter.export(response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @GetMapping(path = "/abilities")
    public void exportAbilitiesToExcel(HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        List<Ability> abilities = abilityService.getAllAbilitiesAsEntity();

        AbilityExcelExporter excelExporter = new AbilityExcelExporter(abilities);

        try {
            excelExporter.export(response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @GetMapping(path = "/monsters")
    public void exportMonstersToExcel(HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        List<Monster> monsters = monsterService.getAllMonstersAsEntity();

        MonsterExcelExporter excelExporter = new MonsterExcelExporter(monsters);

        try {
            excelExporter.export(response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
