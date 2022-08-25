package com.mirceanealcos.confruntarea.controller;


import com.mirceanealcos.confruntarea.dto.ChampionDTO;
import com.mirceanealcos.confruntarea.service.ChampionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Champion controller with suggestive endpoints
 */
@RestController
@RequestMapping(path = "/api/champions")
public class ChampionController {

    private final ChampionService championService;

    @Autowired
    public ChampionController(ChampionService championService) {
        this.championService = championService;
    }

    @GetMapping
    public ResponseEntity<List<ChampionDTO>> getAllChampions() {
        try {
            List<ChampionDTO> championDTOS = championService.getAllChampions();
            if(championDTOS.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(championDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<ChampionDTO> getChampionById(@PathVariable("id") Long id) {
        try {
            ChampionDTO championDTO = championService.getChampionById(id);
            return new ResponseEntity<>(championDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/name/{name}")
    public ResponseEntity<ChampionDTO> getChampionByName(@PathVariable("name") String name) {
        try {
            ChampionDTO championDTO = championService.getChampionByName(name);
            return new ResponseEntity<>(championDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/user-name/{username}")
    public ResponseEntity<List<ChampionDTO>> getChampionsByUserName(@PathVariable("username") String username) {
        try {
            List<ChampionDTO> championDTOS = championService.getChampionsByUserName(username);
            if(championDTOS.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(championDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<String> addChampion(@RequestBody ChampionDTO championDTO) {
        try {
            championService.addChampion(championDTO);
            return new ResponseEntity<>("Champion added successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/update/{name}")
    public ResponseEntity<String> updateChampion(@PathVariable("name") String name, @RequestBody ChampionDTO championDTO) {
        try {
            championService.updateChampion(name, championDTO);
            return new ResponseEntity<>("Champion updated successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/delete/{name}")
    public ResponseEntity<String> deleteChampion(@PathVariable("name") String name) {
        try {
            championService.deleteChampion(name);
            return new ResponseEntity<>("Champion deleted successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
