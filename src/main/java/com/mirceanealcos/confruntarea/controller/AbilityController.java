package com.mirceanealcos.confruntarea.controller;

import com.mirceanealcos.confruntarea.dto.AbilityDTO;
import com.mirceanealcos.confruntarea.service.AbilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Ability controller with suggestive endpoints
 */
@RestController
@RequestMapping(path = "/api/abilities")
public class AbilityController {

    private final AbilityService abilityService;

    @Autowired
    public AbilityController(AbilityService abilityService) {
        this.abilityService = abilityService;
    }

    @GetMapping
    public ResponseEntity<List<AbilityDTO>> getAllAbilities() {
        try {
            List<AbilityDTO> abilityDTOS = abilityService.getAllAbilities();
            if(abilityDTOS.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(abilityDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/champion_name/{name}")
    public ResponseEntity<List<AbilityDTO>> getAbilitiesByChampionName(@PathVariable("name") String name) {
        try {
            List<AbilityDTO> abilityDTOS = abilityService.getAbilitiesByChampionName(name);
            if(abilityDTOS.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(abilityDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<AbilityDTO> getAbilityById(@PathVariable("id") Long id) {
        try {
            AbilityDTO abilityDTO = abilityService.findById(id);
            if(abilityDTO == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(abilityDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/name/{name}")
    public ResponseEntity<AbilityDTO> getAbilityByName(@PathVariable("name") String name) {
        try {
            AbilityDTO abilityDTO = abilityService.findByName(name);
            if(abilityDTO == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(abilityDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/add/to-champion/{name}")
    public ResponseEntity<String> addAbilityToChampion(@PathVariable("name") String name, @RequestBody AbilityDTO abilityDTO) {
        try {
            abilityService.addAbilityToChampion(name, abilityDTO);
            return new ResponseEntity<>("Ability added successsfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/update/{name}")
    public ResponseEntity<String> updateAbility(@PathVariable("name") String name, @RequestBody AbilityDTO abilityDTO) {
        try {
            abilityService.updateAbility(name, abilityDTO);
            return new ResponseEntity<>("Ability updated successsfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/delete/{name}")
    public ResponseEntity<String> deleteAbility(@PathVariable("name") String name) {
        try {
            abilityService.deleteAbility(name);
            return new ResponseEntity<>("Ability deleted successsfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
