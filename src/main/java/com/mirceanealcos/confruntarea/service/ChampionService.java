package com.mirceanealcos.confruntarea.service;

import com.mirceanealcos.confruntarea.dto.ChampionDTO;
import com.mirceanealcos.confruntarea.entity.Champion;
import com.mirceanealcos.confruntarea.repository.ChampionOwnershipRepository;
import com.mirceanealcos.confruntarea.repository.ChampionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChampionService {

    private final ChampionRepository championRepository;
    private final ChampionOwnershipRepository championOwnershipRepository;

    @Autowired
    public ChampionService(ChampionRepository championRepository, ChampionOwnershipRepository championOwnershipRepository) {
        this.championRepository = championRepository;
        this.championOwnershipRepository = championOwnershipRepository;
    }

    public List<ChampionDTO> getAllChampions() {

        List<Champion> champions = championRepository.findAll();
        List<ChampionDTO> championDTOS = new ArrayList<>();

        champions.forEach(champion -> championDTOS.add(toChampionDTO(champion)));

        return championDTOS;
    }

    public List<ChampionDTO> getChampionPage(Integer page) {
        Pageable pageToLoad = PageRequest.of(page,2, Sort.by("name"));
        List<Champion> champions = championRepository.findByOrderByNameAsc(pageToLoad);
        List<ChampionDTO> dtos = new ArrayList<>();

        champions.forEach(champion -> dtos.add(toChampionDTO(champion)));
        return dtos;
    }

    public List<Champion> getAllChampionsAsEntity() {
        return this.championRepository.findAll();
    }

    public List<ChampionDTO> getChampionsByUserName(String username) {

        List<Champion> champions = championRepository.findByOwnershipsUserUsername(username);
        List<ChampionDTO> championDTOS = new ArrayList<>();

        champions.forEach(champion -> championDTOS.add(toChampionDTO(champion)));

        return championDTOS;
    }

    public ChampionDTO getChampionById(Long id) throws Exception{

        Champion champion = championRepository.findById(id).orElse(null);

        if(champion == null) {
            throw new Exception("Champion with id " + id + " does not exist!");
        }

        return toChampionDTO(champion);
    }

    public ChampionDTO getChampionByName(String name) throws Exception{

        Champion champion = championRepository.findByName(name);

        if(champion == null) {
            throw new Exception("Champion with name " + name + " does not exist!");
        }

        return toChampionDTO(champion);
    }

    public void addChampion(ChampionDTO championDTO) throws Exception {

        if(championDTO.hasEmptyFields()) {
            throw new Exception("Missing fields!");
        }

        Champion champion = toEntity(championDTO);

        championRepository.save(champion);
    }

    public void updateChampion(String name, ChampionDTO championDTO) throws Exception {

        Champion champion = championRepository.findByName(name);

        if(champion == null) {
            throw new Exception("Champion with name " + name + " does not exist!");
        }

        if(championDTO.isEmpty()) {
            throw new Exception("No changes present..");
        }

        if(championDTO.getName() != null) {
            champion.setName(championDTO.getName());
        }

        if(championDTO.getHp() != null) {
            champion.setHp(championDTO.getHp());
        }

        if(championDTO.getBaseDamage() != null) {
            champion.setBaseDamage(championDTO.getBaseDamage());
        }

        if(championDTO.getPrice() != null) {
            champion.setPrice(championDTO.getPrice());
        }

        if(championDTO.getMana() != null) {
            champion.setMana(championDTO.getMana());
        }

        if(championDTO.getPicture() != null) {
            champion.setPicture(championDTO.getPicture());
        }

        if(championDTO.getNameColor() != null) {
            champion.setNameColor(championDTO.getNameColor());
        }

        championRepository.save(champion);
    }

    public void deleteChampion(String name) throws Exception{

        Champion champion = championRepository.findByName(name);
        if(champion == null) {
            throw new Exception("Champion with name " + name + " does not exist!");
        }

        championOwnershipRepository.deleteByChampionName(name);
        championRepository.delete(champion);
    }

    private ChampionDTO toChampionDTO(Champion champion) {
        return new ChampionDTO(
                champion.getName(),
                champion.getHp(),
                champion.getBaseDamage(),
                champion.getPrice(),
                champion.getMana(),
                champion.getPicture(),
                champion.getNameColor()
        );
    }

    private Champion toEntity(ChampionDTO championDTO) throws Exception{
        Champion champion = new Champion();
        champion.setName(championDTO.getName());
        champion.setHp(championDTO.getHp());
        champion.setBaseDamage(championDTO.getBaseDamage());
        champion.setMana(championDTO.getMana());
        champion.setPrice(championDTO.getPrice());
        champion.setPicture(championDTO.getPicture());
        champion.setNameColor(championDTO.getNameColor());

        return champion;
    }

}
