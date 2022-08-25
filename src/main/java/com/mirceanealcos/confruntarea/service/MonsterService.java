package com.mirceanealcos.confruntarea.service;

import com.mirceanealcos.confruntarea.dto.MonsterDTO;
import com.mirceanealcos.confruntarea.entity.Monster;
import com.mirceanealcos.confruntarea.repository.MonsterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MonsterService {

    private final MonsterRepository monsterRepository;

    @Autowired
    public MonsterService(MonsterRepository monsterRepository) {
        this.monsterRepository = monsterRepository;
    }

    public List<MonsterDTO> getAllMonsters() {

        List<Monster> monsters = monsterRepository.findAll();
        List<MonsterDTO> monsterDTOS = new ArrayList<>();

        monsters.forEach(monster -> monsterDTOS.add(toMonsterDTO(monster)));

        return monsterDTOS;

    }

    public MonsterDTO getMonsterById(Long id) throws Exception {
        Monster monster = monsterRepository.findById(id).orElse(null);
        if(monster == null) {
            throw new Exception("Monster with id " + id + " does not exist!");
        }
        return toMonsterDTO(monster);
    }

    public MonsterDTO getMonsterByName(String name) throws Exception {
        Monster monster = monsterRepository.findByName(name);
        if(monster == null) {
            throw new Exception("Monster with name " + name + " does not exist!");
        }
        return toMonsterDTO(monster);
    }

    public List<MonsterDTO> getMonstersByLevel(Integer level) {
        List<Monster> monsters = monsterRepository.findByLevel(level);
        List<MonsterDTO> monsterDTOS = new ArrayList<>();

        monsters.forEach(monster -> monsterDTOS.add(toMonsterDTO(monster)));
        return monsterDTOS;
    }

    public void addMonster(MonsterDTO monsterDTO) throws Exception{
        if(monsterDTO == null) {
            throw new Exception("Empty body..");
        }

        if(monsterDTO.hasEmptyFields()) {
            throw new Exception("Missing fields!");
        }

        Monster monster = toEntity(monsterDTO);
        monsterRepository.save(monster);
    }

    public void updateMonster(String name, MonsterDTO monsterDTO) throws Exception{

        Monster monster = monsterRepository.findByName(name);

        if(monster == null) {
            throw new Exception("Monster with name " + name + " does not exist!");
        }

        if(monsterDTO.isEmpty()) {
            throw new Exception("No changes present..");
        }

        if(monsterDTO.getName() != null) {
            monster.setName(monsterDTO.getName());
        }

        if(monsterDTO.getLevel() != null) {
            monster.setLevel(monsterDTO.getLevel());
        }

        if(monsterDTO.getHp() != null) {
            monster.setHp(monsterDTO.getHp());
        }

        if(monsterDTO.getBaseDamage() != null) {
            monster.setBaseDamage(monsterDTO.getBaseDamage());
        }

        if(monsterDTO.getMoneyReward() != null) {
            monster.setMoneyReward(monsterDTO.getMoneyReward());
        }

        if(monsterDTO.getExpReward() != null) {
            monster.setExpReward(monsterDTO.getExpReward());
        }

        if(monsterDTO.getPicture() != null) {
            monster.setPicture(monsterDTO.getPicture());
        }

        if(monsterDTO.getNameColor() != null) {
            monster.setNameColor(monsterDTO.getNameColor());
        }

        monsterRepository.save(monster);

    }

    public void deleteMonsterByName(String name) throws Exception {
        Monster monster = monsterRepository.findByName(name);
        if(monster == null) {
            throw new Exception("Monster with name " + name + " does not exist!");
        }
        monsterRepository.delete(monster);
    }

    private MonsterDTO toMonsterDTO(Monster monster) {
        return new MonsterDTO(
                monster.getName(),
                monster.getLevel(),
                monster.getHp(),
                monster.getBaseDamage(),
                monster.getMoneyReward(),
                monster.getExpReward(),
                monster.getPicture(),
                monster.getNameColor()
        );
    }

    private Monster toEntity(MonsterDTO monsterDTO) {
        Monster monster = new Monster();
        monster.setName(monsterDTO.getName());
        monster.setLevel(monsterDTO.getLevel());
        monster.setHp(monsterDTO.getHp());
        monster.setBaseDamage(monsterDTO.getBaseDamage());
        monster.setMoneyReward(monsterDTO.getMoneyReward());
        monster.setExpReward(monsterDTO.getExpReward());
        monster.setPicture(monsterDTO.getPicture());
        monster.setNameColor(monsterDTO.getNameColor());
        return monster;
    }

}
