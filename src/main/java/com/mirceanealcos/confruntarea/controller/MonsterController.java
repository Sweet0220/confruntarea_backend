package com.mirceanealcos.confruntarea.controller;


import com.mirceanealcos.confruntarea.dto.MonsterDTO;
import com.mirceanealcos.confruntarea.service.MonsterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Monster controller with suggestive endpoints
 */
@RestController
@RequestMapping("/api/monsters")
@Slf4j
public class MonsterController {

    private final MonsterService monsterService;

    @Autowired
    public MonsterController(MonsterService monsterService) {
        this.monsterService = monsterService;
    }

    @GetMapping
    public ResponseEntity<List<MonsterDTO>> getAllMonsters() {
        try {
            List<MonsterDTO> monsterDTOS = monsterService.getAllMonsters();
            if(monsterDTOS.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(monsterDTOS, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<MonsterDTO> getMonsterById(@PathVariable("id") Long id) {
        try {
            MonsterDTO monsterDTO = monsterService.getMonsterById(id);
            return new ResponseEntity<>(monsterDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/name/{name}")
    public ResponseEntity<MonsterDTO> getMonsterByName(@PathVariable("name") String name) {
        try {
            MonsterDTO monsterDTO = monsterService.getMonsterByName(name);
            return new ResponseEntity<>(monsterDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/level/{level}")
    public ResponseEntity<List<MonsterDTO>> getMonstersByLevel(@PathVariable("level") Integer level) {
        try {
            List<MonsterDTO> monsters = monsterService.getMonstersByLevel(level);
            if(monsters.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(monsters, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<String> addMonster(@RequestBody MonsterDTO monsterDTO) {
        try {
            monsterService.addMonster(monsterDTO);
            return new ResponseEntity<>("Monster added successfully!", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/update/{name}")
    public ResponseEntity<String> updateMonster(@PathVariable("name") String name, @RequestBody MonsterDTO monsterDTO) {
        try {
            monsterService.updateMonster(name, monsterDTO);
            return new ResponseEntity<>("Monster updated successfully!", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/delete/{name}")
    public ResponseEntity<String> deleteMonsterById(@PathVariable("name") String name) {
        try {
            monsterService.deleteMonsterByName(name);
            return new ResponseEntity<>("Monster deleted successfully!", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
