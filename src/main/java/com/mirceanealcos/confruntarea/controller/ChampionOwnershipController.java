package com.mirceanealcos.confruntarea.controller;

import com.mirceanealcos.confruntarea.dto.ChampionOwnershipDTO;
import com.mirceanealcos.confruntarea.service.ChampionOwnershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Link table (champions-users) controller with suggestive endpoints
 */
@RestController
@RequestMapping("/api/champion-ownerships")
public class ChampionOwnershipController {

    private final ChampionOwnershipService championOwnershipService;

    @Autowired
    public ChampionOwnershipController(ChampionOwnershipService championOwnershipService) {
        this.championOwnershipService = championOwnershipService;
    }

    @GetMapping
    public ResponseEntity<List<ChampionOwnershipDTO>> getAllChampionOwnerships() {
        try {
            List<ChampionOwnershipDTO> championOwnershipDTOS = championOwnershipService.getAllChampionLinks();
            if(championOwnershipDTOS.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(championOwnershipDTOS,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/user-name/{name}")
    public ResponseEntity<List<ChampionOwnershipDTO>> getChampionOwnershipsByUserId(@PathVariable("name") String username) {
        try {
            List<ChampionOwnershipDTO> championOwnershipDTOS = championOwnershipService.getChampionLinksByUserName(username);
            if(championOwnershipDTOS.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(championOwnershipDTOS,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/champion-name/{name}")
    public ResponseEntity<List<ChampionOwnershipDTO>> getChampionOwnershipsByChampionId(@PathVariable("name") String name) {
        try {
            List<ChampionOwnershipDTO> championOwnershipDTOS = championOwnershipService.getChampionLinksByChampionName(name);
            if(championOwnershipDTOS.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(championOwnershipDTOS,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/link-user/{username}/to-champion/{champion_name}")
    public ResponseEntity<String> addOwnership(@PathVariable("username") String username, @PathVariable("champion_name") String champion_name, @RequestBody ChampionOwnershipDTO championOwnershipDTO) {
        try {
            championOwnershipService.addOwnership(username,champion_name,championOwnershipDTO);
            return new ResponseEntity<>("Link successfully made!",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<String> updateOwnership(@PathVariable("id") Long id, @RequestBody ChampionOwnershipDTO championOwnershipDTO) {
        try {
            championOwnershipService.updateOwnership(id,championOwnershipDTO);
            return new ResponseEntity<>("Link updated successfully!",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteOwnership(@PathVariable("id") Long id) {
        try {
            championOwnershipService.deleteOwnership(id);
            return new ResponseEntity<>("Link deleted successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
