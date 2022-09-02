package com.mirceanealcos.confruntarea.controller;

import com.mirceanealcos.confruntarea.entity.Ability;
import com.mirceanealcos.confruntarea.entity.Champion;
import com.mirceanealcos.confruntarea.entity.Item;
import com.mirceanealcos.confruntarea.entity.Monster;
import com.mirceanealcos.confruntarea.excel.exporter.AbilityExcelExporter;
import com.mirceanealcos.confruntarea.excel.exporter.ChampionExcelExporter;
import com.mirceanealcos.confruntarea.excel.exporter.ItemExcelExporter;
import com.mirceanealcos.confruntarea.excel.exporter.MonsterExcelExporter;
import com.mirceanealcos.confruntarea.excel.importer.ImportAbilityExcelUtil;
import com.mirceanealcos.confruntarea.excel.importer.ImportChampionExcelUtil;
import com.mirceanealcos.confruntarea.excel.importer.ImportItemExcelUtil;
import com.mirceanealcos.confruntarea.excel.importer.ImportMonsterExcelUtil;
import com.mirceanealcos.confruntarea.excel.service.ExcelService;
import com.mirceanealcos.confruntarea.repository.AbilityRepository;
import com.mirceanealcos.confruntarea.repository.ChampionRepository;
import com.mirceanealcos.confruntarea.repository.ItemRepository;
import com.mirceanealcos.confruntarea.repository.MonsterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "import/excel")
@Slf4j
public class ExcelImportController {

    private final ExcelService excelService;
    private List<Champion> failedChampions = new ArrayList<>();
    private List<Item> failedItems = new ArrayList<>();
    private List<Ability> failedAbilities = new ArrayList<>();
    private List<Monster> failedMonsters = new ArrayList<>();
    private final ChampionRepository championRepository;
    private final ItemRepository itemRepository;
    private final AbilityRepository abilityRepository;
    private final MonsterRepository monsterRepository;

    @Autowired
    public ExcelImportController(ExcelService excelService, ChampionRepository championRepository, ItemRepository itemRepository, AbilityRepository abilityRepository, MonsterRepository monsterRepository) {
        this.excelService = excelService;
        this.championRepository = championRepository;
        this.itemRepository = itemRepository;
        this.abilityRepository = abilityRepository;
        this.monsterRepository = monsterRepository;
    }

    @PostMapping(path = "/champions")
    public ResponseEntity<String> importChampions(@RequestParam("file")MultipartFile file) {
        String message = "";
        if(ImportChampionExcelUtil.hasExcelFormat(file)) {
            try {
                List<Champion> allChampions = excelService.getChampionListFromExcelFile(file);
                int success = excelService.saveChampions(file, failedChampions);
                if(success == allChampions.size()) {
                    message = "Successfully imported all rows from " + file.getOriginalFilename() + "!";
                    return new ResponseEntity<>(message, HttpStatus.OK);
                }
                if(success == 0) {
                    message = "No champions were imported..";
                    return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
                }
                message = "Successfully added " + success + " champions out of " + allChampions.size();
                return new ResponseEntity<>(message, HttpStatus.PARTIAL_CONTENT);
            }catch (Exception e) {
                return new ResponseEntity<>("Invalid or empty file..", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        message = "Please upload an excel file!";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/champions/failed")
    public void exportFailedChampions(HttpServletResponse response) {
        response.setContentType("application/octet-stream");

        List<Champion> failedChampionsWithId = new ArrayList<>();

        failedChampions.forEach(champion -> {
            failedChampionsWithId.add(championRepository.findByName(champion.getName()));
        });

        ChampionExcelExporter excelExporter = new ChampionExcelExporter(failedChampionsWithId);

        try {
            excelExporter.export(response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @PostMapping(path = "/items")
    public ResponseEntity<String> importItems(@RequestParam("file")MultipartFile file) {
        String message = "";
        if(ImportItemExcelUtil.hasExcelFormat(file)) {
            try {
                List<Item> allItems = excelService.getItemListFromExcelFile(file);
                int success = excelService.saveItems(file, failedItems);
                if(success == allItems.size()) {
                    message = "Successfully imported all rows from " + file.getOriginalFilename() + "!";
                    return new ResponseEntity<>(message, HttpStatus.OK);
                }
                if(success == 0) {
                    message = "No items were imported..";
                    return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
                }
                message = "Successfully added " + success + " items out of " + allItems.size();
                return new ResponseEntity<>(message, HttpStatus.PARTIAL_CONTENT);
            }catch (Exception e) {
                return new ResponseEntity<>("Invalid or empty file..", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        message = "Please upload an excel file!";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/items/failed")
    public void exportFailedItems(HttpServletResponse response) {
        response.setContentType("application/octet-stream");

        List<Item> failedItemsWithId = new ArrayList<>();

        failedItems.forEach(item -> {
            failedItemsWithId.add(itemRepository.findByName(item.getName()));
        });

        ItemExcelExporter excelExporter = new ItemExcelExporter(failedItemsWithId);

        try {
            excelExporter.export(response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @PostMapping(path = "/abilities")
    public ResponseEntity<String> importAbilities(@RequestParam("file")MultipartFile file) {
        String message = "";
        ImportAbilityExcelUtil excelUtil = new ImportAbilityExcelUtil(championRepository);
        if(ImportAbilityExcelUtil.hasExcelFormat(file)) {
            try {
                List<Ability> allAbilities = excelService.getAbilityListFromExcelFile(file);
                int success = excelService.saveAbilities(file, failedAbilities);
                if(success == allAbilities.size()) {
                    message = "Successfully imported all rows from " + file.getOriginalFilename() + "!";
                    return new ResponseEntity<>(message, HttpStatus.OK);
                }
                if(success == 0) {
                    message = "No abilities were imported..";
                    return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
                }
                message = "Successfully added " + success + " abilities out of " + allAbilities.size();
                return new ResponseEntity<>(message, HttpStatus.PARTIAL_CONTENT);
            }catch (Exception e) {
                return new ResponseEntity<>("Invalid or empty file..", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        message = "Please upload an excel file!";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/abilities/failed")
    public void exportFailedAbilities(HttpServletResponse response) {
        response.setContentType("application/octet-stream");

        List<Ability> failedAbilitiesWithId = new ArrayList<>();

        failedAbilities.forEach(ability -> {
            failedAbilitiesWithId.add(abilityRepository.findByName(ability.getName()));
        });

        AbilityExcelExporter excelExporter = new AbilityExcelExporter(failedAbilitiesWithId);

        try {
            excelExporter.export(response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @PostMapping(path = "/monsters")
    public ResponseEntity<String> importMonsters(@RequestParam("file")MultipartFile file) {
        String message = "";
        if(ImportMonsterExcelUtil.hasExcelFormat(file)) {
            try {
                List<Monster> allMonsters = excelService.getMonsterListFromExcelFile(file);
                int success = excelService.saveMonsters(file, failedMonsters);
                if(success == allMonsters.size()) {
                    message = "Successfully imported all rows from " + file.getOriginalFilename() + "!";
                    return new ResponseEntity<>(message, HttpStatus.OK);
                }
                if(success == 0) {
                    message = "No monsters were imported..";
                    return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
                }
                message = "Successfully added " + success + " monsters out of " + allMonsters.size();
                return new ResponseEntity<>(message, HttpStatus.PARTIAL_CONTENT);
            }catch (Exception e) {
                return new ResponseEntity<>("Invalid or empty file..", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        message = "Please upload an excel file!";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/monsters/failed")
    public void exportFailedMonsters(HttpServletResponse response) {
        response.setContentType("application/octet-stream");

        List<Monster> failedMonstersWithId = new ArrayList<>();

        failedMonsters.forEach(monster -> {
            failedMonstersWithId.add(monsterRepository.findByName(monster.getName()));
        });

        MonsterExcelExporter excelExporter = new MonsterExcelExporter(failedMonstersWithId);

        try {
            excelExporter.export(response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
