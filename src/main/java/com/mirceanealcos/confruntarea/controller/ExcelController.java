package com.mirceanealcos.confruntarea.controller;

import com.mirceanealcos.confruntarea.entity.*;
import com.mirceanealcos.confruntarea.excel.ExcelExporter;
import com.mirceanealcos.confruntarea.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/export/excel")
public class ExcelController {

    private final ChampionService championService;
    private final UserService userService;
    private final ItemService itemService;
    private final MonsterService monsterService;
    private final AbilityService abilityService;

    @Autowired
    public ExcelController(ChampionService championService, UserService userService, ItemService itemService, MonsterService monsterService, AbilityService abilityService) {
        this.championService = championService;
        this.userService = userService;
        this.itemService = itemService;
        this.monsterService = monsterService;
        this.abilityService = abilityService;
    }

    @GetMapping
    public void exportChampionsToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = champions_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Champion> champions = championService.getAllChampionsAsEntity();
        List<User> users = userService.getAllUsersAsEntity();
        List<Item> items = itemService.getAllItemsAsEntity();
        List<Monster> monsters = monsterService.getAllMonstersAsEntity();
        List<Ability> abilities = abilityService.getAllAbilitiesAsEntity();

        ExcelExporter excelExporter = new ExcelExporter(champions, users, items, monsters, abilities);

        excelExporter.export(response);

    }
}
