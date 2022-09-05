package com.mirceanealcos.confruntarea.controller;

import com.mirceanealcos.confruntarea.entity.*;
import com.mirceanealcos.confruntarea.pdf.*;
import com.mirceanealcos.confruntarea.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(path = "/export/pdf")
public class PdfController {

    private final UserService userService;
    private final ChampionService championService;
    private final ItemService itemService;
    private final AbilityService abilityService;
    private final MonsterService monsterService;

    @Autowired
    public PdfController(UserService userService, ChampionService championService, ItemService itemService, AbilityService abilityService, MonsterService monsterService) {
        this.userService = userService;
        this.championService = championService;
        this.itemService = itemService;
        this.abilityService = abilityService;
        this.monsterService = monsterService;
    }

    @GetMapping(path = "/users")
    public void exportUsersToPDF(HttpServletResponse response) throws Exception{
        response.setContentType("application/pdf");
        List<User> users = userService.getAllUsersAsEntity();

        UserPDFExporter exporter = new UserPDFExporter(users);
        exporter.export(response);
    }

    @GetMapping(path = "/champions")
    public void exportChampionsToPDF(HttpServletResponse response) throws Exception{
        response.setContentType("application/pdf");
        List<Champion> champions = championService.getAllChampionsAsEntity();

        ChampionPDFExporter exporter = new ChampionPDFExporter(champions);
        exporter.export(response);
    }

    @GetMapping(path = "/items")
    public void exportItemsToPDF(HttpServletResponse response) throws Exception{
        response.setContentType("application/pdf");
        List<Item> items = itemService.getAllItemsAsEntity();

        ItemPDFExporter exporter = new ItemPDFExporter(items);
        exporter.export(response);
    }

    @GetMapping(path = "/abilities")
    public void exportAbilitiesToPDF(HttpServletResponse response) throws Exception{
        response.setContentType("application/pdf");
        List<Ability> abilities = abilityService.getAllAbilitiesAsEntity();

        AbilityPDFExporter exporter = new AbilityPDFExporter(abilities);
        exporter.export(response);
    }

    @GetMapping(path = "/monsters")
    public void exportMonstersToPDF(HttpServletResponse response) throws Exception{
        response.setContentType("application/pdf");
        List<Monster> monsters = monsterService.getAllMonstersAsEntity();

        MonsterPDFExporter exporter = new MonsterPDFExporter(monsters);
        exporter.export(response);
    }
}
