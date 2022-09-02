package com.mirceanealcos.confruntarea.excel.service;

import com.mirceanealcos.confruntarea.entity.Ability;
import com.mirceanealcos.confruntarea.entity.Champion;
import com.mirceanealcos.confruntarea.entity.Item;
import com.mirceanealcos.confruntarea.entity.Monster;
import com.mirceanealcos.confruntarea.excel.importer.ImportAbilityExcelUtil;
import com.mirceanealcos.confruntarea.excel.importer.ImportChampionExcelUtil;
import com.mirceanealcos.confruntarea.excel.importer.ImportItemExcelUtil;
import com.mirceanealcos.confruntarea.excel.importer.ImportMonsterExcelUtil;
import com.mirceanealcos.confruntarea.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ExcelService {

    private final ChampionRepository championRepository;
    private final ItemRepository itemRepository;
    private final AbilityRepository abilityRepository;
    private final MonsterRepository monsterRepository;

    @Autowired
    public ExcelService(ChampionRepository championRepository, ItemRepository itemRepository, AbilityRepository abilityRepository, MonsterRepository monsterRepository) {
        this.championRepository = championRepository;
        this.itemRepository = itemRepository;
        this.abilityRepository = abilityRepository;
        this.monsterRepository = monsterRepository;
    }

    public int saveChampions(MultipartFile file, List<Champion> failedChampions) throws Exception {
        List<Champion> champions;
        int success = 0;
        failedChampions.clear();

        champions = ImportChampionExcelUtil.excelToChampions(file.getInputStream());

        if(champions.isEmpty()) {
            throw new Exception("Excel file provided is empty..");
        }

        for(Champion champion : champions) {
            try {
                championRepository.save(champion);
                success++;
            }catch (Exception e) {
                log.error("Failed to add champion " + champion.getName() + ": " + e.getMessage());
                failedChampions.add(champion);
            }
        }

        return success;
    }

    public List<Champion> getChampionListFromExcelFile(MultipartFile file) throws IOException{
        return ImportChampionExcelUtil.excelToChampions(file.getInputStream());
    }

    public int saveItems(MultipartFile file, List<Item> failedItems) throws Exception {
        List<Item> items;
        int success = 0;
        failedItems.clear();

        items = ImportItemExcelUtil.excelToItems(file.getInputStream());

        if(items.isEmpty()) {
            throw new Exception("Excel file provided is empty..");
        }

        for(Item item : items) {
            try {
                itemRepository.save(item);
                success++;
            }catch (Exception e) {
                log.error("Failed to add item " + item.getName() + ": " + e.getMessage());
                failedItems.add(item);
            }
        }

        return success;
    }

    public List<Item> getItemListFromExcelFile(MultipartFile file) throws IOException {
        return ImportItemExcelUtil.excelToItems(file.getInputStream());
    }

    public int saveAbilities(MultipartFile file, List<Ability> failedAbilities) throws Exception {
        List<Ability> abilities;
        int success = 0;
        failedAbilities.clear();

        ImportAbilityExcelUtil excelUtil = new ImportAbilityExcelUtil(championRepository);
        abilities = excelUtil.excelToAbilities(file.getInputStream());

        if(abilities.isEmpty()) {
            throw new Exception("Excel file provided is empty..");
        }

        for(Ability ability : abilities) {
            try {
                abilityRepository.save(ability);
                success++;
            }catch (Exception e) {
                log.error("Failed to add ability " + ability.getName() + ": " + e.getMessage());
                failedAbilities.add(ability);
            }
        }

        return success;
    }

    public List<Ability> getAbilityListFromExcelFile(MultipartFile file) throws IOException {
        ImportAbilityExcelUtil excelUtil = new ImportAbilityExcelUtil(championRepository);
        return excelUtil.excelToAbilities(file.getInputStream());
    }

    public int saveMonsters(MultipartFile file, List<Monster> failedMonsters) throws Exception {
        List<Monster> monsters;
        int success = 0;
        failedMonsters.clear();

        monsters = ImportMonsterExcelUtil.excelToMonsters(file.getInputStream());

        if(monsters.isEmpty()) {
            throw new Exception("Excel file provided is empty..");
        }

        for(Monster monster : monsters) {
            try {
                monsterRepository.save(monster);
                success++;
            }catch (Exception e) {
                log.error("Failed to add monster " + monster.getName() + ": " + e.getMessage());
                failedMonsters.add(monster);
            }
        }

        return success;
    }

    public List<Monster> getMonsterListFromExcelFile(MultipartFile file) throws IOException {
        return ImportMonsterExcelUtil.excelToMonsters(file.getInputStream());
    }
}
