package com.mirceanealcos.confruntarea.service;

import com.mirceanealcos.confruntarea.dto.ChampionOwnershipDTO;
import com.mirceanealcos.confruntarea.entity.Champion;
import com.mirceanealcos.confruntarea.entity.ChampionOwnership;
import com.mirceanealcos.confruntarea.entity.User;
import com.mirceanealcos.confruntarea.repository.ChampionOwnershipRepository;
import com.mirceanealcos.confruntarea.repository.ChampionRepository;
import com.mirceanealcos.confruntarea.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChampionOwnershipService {

    private final ChampionOwnershipRepository championOwnershipRepository;
    private final UserRepository userRepository;
    private final ChampionRepository championRepository;

    @Autowired
    public ChampionOwnershipService(ChampionOwnershipRepository championOwnershipRepository, UserRepository userRepository, ChampionRepository championRepository) {
        this.championOwnershipRepository = championOwnershipRepository;
        this.userRepository = userRepository;
        this.championRepository = championRepository;
    }

    public List<ChampionOwnershipDTO> getAllChampionLinks() {
        List<ChampionOwnership> championOwnerships = championOwnershipRepository.findAll();
        List<ChampionOwnershipDTO> championOwnershipDTOS = new ArrayList<>();

        championOwnerships.forEach(championOwnership -> championOwnershipDTOS.add(toChampionOwnershipDTO(championOwnership)));

        return championOwnershipDTOS;
    }

    public List<ChampionOwnershipDTO> getChampionLinksByUserName(String username) {
        List<ChampionOwnership> championOwnerships = championOwnershipRepository.findByUserUsername(username);
        List<ChampionOwnershipDTO> championOwnershipDTOS = new ArrayList<>();

        championOwnerships.forEach(championOwnership -> championOwnershipDTOS.add(toChampionOwnershipDTO(championOwnership)));

        return championOwnershipDTOS;
    }

    public List<ChampionOwnershipDTO> getChampionLinksByChampionName(String name) {
        List<ChampionOwnership> championOwnerships = championOwnershipRepository.findByChampionName(name);
        List<ChampionOwnershipDTO> championOwnershipDTOS = new ArrayList<>();

        championOwnerships.forEach(championOwnership -> championOwnershipDTOS.add(toChampionOwnershipDTO(championOwnership)));

        return championOwnershipDTOS;
    }

    public void addOwnership(String username, String champion_name, ChampionOwnershipDTO championOwnershipDTO) throws Exception {
        User user = userRepository.findByUsername(username);
        Champion champion = championRepository.findByName(champion_name);

        if(user == null) {
            throw new Exception("User with username " + username + " does not exist!");
        }

        if(champion == null) {
            throw new Exception("Champion with name " + champion_name + " does not exist!");
        }

        if(championOwnershipDTO.getLevel() == null) {
            throw new Exception("Level not present!");
        }

        ChampionOwnership championOwnership = new ChampionOwnership();

        championOwnership.setLevel(championOwnershipDTO.getLevel());
        championOwnership.setChampion(champion);
        championOwnership.setUser(user);

        championOwnershipRepository.save(championOwnership);
    }

    public void updateOwnership(Long id, ChampionOwnershipDTO championOwnershipDTO) throws Exception {
        ChampionOwnership championOwnership = championOwnershipRepository.findById(id).orElse(null);
        if(championOwnership == null) {
            throw new Exception("Link with id " + id + " does not exist!");
        }
        if(championOwnershipDTO.getLevel() == null) {
            throw new Exception("Level not present!");
        }

        championOwnership.setLevel(championOwnershipDTO.getLevel());
        championOwnershipRepository.save(championOwnership);
    }

    public void deleteOwnership(Long id) throws Exception{
        ChampionOwnership championOwnership = championOwnershipRepository.findById(id).orElse(null);
        if(championOwnership == null) {
            throw new Exception("Link with id " + id + " does not exist!");
        }

        championOwnershipRepository.delete(championOwnership);
    }

    private ChampionOwnershipDTO toChampionOwnershipDTO(ChampionOwnership championOwnership) {
        return new ChampionOwnershipDTO(
                championOwnership.getId(),
                championOwnership.getLevel(),
                championOwnership.getChampion(),
                championOwnership.getUser()
        );
    }

}
