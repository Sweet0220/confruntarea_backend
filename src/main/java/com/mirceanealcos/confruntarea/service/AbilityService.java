package com.mirceanealcos.confruntarea.service;

import com.mirceanealcos.confruntarea.dto.AbilityDTO;
import com.mirceanealcos.confruntarea.entity.Ability;
import com.mirceanealcos.confruntarea.entity.Champion;
import com.mirceanealcos.confruntarea.repository.AbilityRepository;
import com.mirceanealcos.confruntarea.repository.ChampionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the ability entity service implementation
 */
@Service
public class AbilityService {

    private final AbilityRepository abilityRepository;
    private final ChampionRepository championRepository;

    @Autowired
    public AbilityService(AbilityRepository abilityRepository, ChampionRepository championRepository) {
        this.abilityRepository = abilityRepository;
        this.championRepository = championRepository;
    }

    /**
     * This function gets all abilities from the DB
     * @return a list of ability DTOs
     */
    public List<AbilityDTO> getAllAbilities() {
        List<Ability> abilities = abilityRepository.findAll();
        List<AbilityDTO> abilityDTOS = new ArrayList<>();

        abilities.forEach(ability -> abilityDTOS.add(toAbilityDTO(ability)));
        return abilityDTOS;
    }

    public List<Ability> getAllAbilitiesAsEntity() {
        return this.abilityRepository.findAll();
    }

    /**
     * This function gets all abilities that correspond to a certain champion from the DB
     * @param name represents the champion's name
     * @return a list of ability DTOs corresponding to the champion with the name sent via parameter
     */
    public List<AbilityDTO> getAbilitiesByChampionName(String name) {
        List<Ability> abilities = abilityRepository.findByChampionName(name);
        List<AbilityDTO> abilityDTOS = new ArrayList<>();

        abilities.forEach(ability -> abilityDTOS.add(toAbilityDTO(ability)));
        return abilityDTOS;
    }

    /**
     * Gets the ability from the DB with the id provided
     * @param id ability id
     * @return ability DTO corresponding to the entity with the id provided
     */
    public AbilityDTO findById(Long id) {
        Ability ability = abilityRepository.findById(id).orElse(null);
        return toAbilityDTO(ability);
    }


    /**
     * Gets the ability from the DB with the name provided
     * @param name ability name
     * @return ability DTO corresponding to the entity with the name provided
     */
    public AbilityDTO findByName(String name) {
        Ability ability = abilityRepository.findByName(name);
        return toAbilityDTO(ability);
    }

    /**
     * Adds the ability provided via body in JSON format to the champion with the name provided
     * @param name champion's name
     * @param abilityDTO the request body containing an ability DTO
     */
    public void addAbilityToChampion(String name, AbilityDTO abilityDTO) throws Exception {

        Champion champion = championRepository.findByName(name);
        if(champion == null) {
            throw new Exception("Champion with name " + name + " does not exist!");
        }

        if(abilityDTO.hasEmptyFields()) {
            throw new Exception("Missing fields!");
        }

        Ability ability = toEntity(abilityDTO,champion);
        abilityRepository.save(ability);
    }

    /**
     * Updates the ability provided via name with the fields contained in the DTO
     * @param name ability name
     * @param abilityDTO the request body containing an ability DTO
     */
    public void updateAbility(String name, AbilityDTO abilityDTO) throws Exception {
        Ability ability = abilityRepository.findByName(name);
        if(ability == null) {
            throw new Exception("Ability with name " + name + " does not exist!");
        }

        if(abilityDTO.isEmpty()) {
            throw new Exception("No changes present..");
        }

        if(abilityDTO.getName() != null) {
            ability.setName(abilityDTO.getName());
        }

        if(abilityDTO.getType() != null) {
            ability.setType(abilityDTO.getType());
        }

        if(abilityDTO.getHealing() != null) {
            ability.setHealing(abilityDTO.getHealing());
        }

        if(abilityDTO.getDamage() != null) {
            ability.setDamage(abilityDTO.getDamage());
        }

        if(abilityDTO.getPicture() != null) {
            ability.setPicture(abilityDTO.getPicture());
        }

        if(abilityDTO.getManaCost() != null) {
            ability.setManaCost(abilityDTO.getManaCost());
        }

        abilityRepository.save(ability);
    }


    /**
     * Deletes the ability with the name provided from the DB
     * @param name ability name
     * @throws Exception if ability is not found
     */
    public void deleteAbility(String name) throws Exception{
        Ability ability = abilityRepository.findByName(name);
        if(ability == null) {
            throw new Exception("Ability with name " + name + " does not exist!");
        }
        abilityRepository.delete(ability);
    }


    /**
     * Converts from ability entity to ability DTO
     * @param ability the ability to convert
     * @return the ability DTO
     */
    private AbilityDTO toAbilityDTO(Ability ability) {
        if(ability == null) {
            return null;
        }
        return new AbilityDTO(
                ability.getName(),
                ability.getType(),
                ability.getHealing(),
                ability.getDamage(),
                ability.getPicture(),
                ability.getManaCost()
        );
    }


    /**
     * Converts from ability DTO to ability entity
     * @param abilityDTO the ability DTO to convert
     * @param champion the champion to associate the entity to
     * @return the ability entity
     */
    private Ability toEntity(AbilityDTO abilityDTO, Champion champion) {
        Ability ability = new Ability();

        ability.setName(abilityDTO.getName());
        ability.setType(abilityDTO.getType());
        ability.setPicture(abilityDTO.getPicture());
        ability.setDamage(abilityDTO.getDamage());
        ability.setHealing(abilityDTO.getHealing());
        ability.setManaCost(abilityDTO.getManaCost());
        ability.setChampion(champion);

        return ability;
    }

}
