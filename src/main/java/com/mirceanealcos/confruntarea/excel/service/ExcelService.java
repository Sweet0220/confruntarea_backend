package com.mirceanealcos.confruntarea.excel.service;

import com.mirceanealcos.confruntarea.entity.Champion;
import com.mirceanealcos.confruntarea.excel.importer.ImportChampionExcelUtil;
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
        failedChampions = new ArrayList<>();

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
}
