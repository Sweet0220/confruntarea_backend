package com.mirceanealcos.confruntarea.service;

import com.mirceanealcos.confruntarea.dto.ItemOwnershipDTO;
import com.mirceanealcos.confruntarea.entity.Item;
import com.mirceanealcos.confruntarea.entity.ItemOwnership;
import com.mirceanealcos.confruntarea.entity.User;
import com.mirceanealcos.confruntarea.repository.ItemOwnershipRepository;
import com.mirceanealcos.confruntarea.repository.ItemRepository;
import com.mirceanealcos.confruntarea.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemOwnershipService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemOwnershipRepository itemOwnershipRepository;

    @Autowired
    public ItemOwnershipService(ItemRepository itemRepository, UserRepository userRepository, ItemOwnershipRepository itemOwnershipRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.itemOwnershipRepository = itemOwnershipRepository;
    }

    public List<ItemOwnershipDTO> getAllItemOwnerships() {

        List<ItemOwnership> itemOwnerships = itemOwnershipRepository.findAll();
        List<ItemOwnershipDTO> itemOwnershipDTOS = new ArrayList<>();

        itemOwnerships.forEach(itemOwnership -> itemOwnershipDTOS.add(toItemOwnershipDTO(itemOwnership)));

        return itemOwnershipDTOS;
    }

    public List<ItemOwnershipDTO> getItemOwnershipsByUsername(String username) {

        List<ItemOwnership> itemOwnerships = itemOwnershipRepository.findByUserUsername(username);
        List<ItemOwnershipDTO> itemOwnershipDTOS = new ArrayList<>();

        itemOwnerships.forEach(itemOwnership -> itemOwnershipDTOS.add(toItemOwnershipDTO(itemOwnership)));

        return itemOwnershipDTOS;
    }

    public List<ItemOwnershipDTO> getItemOwnershipsByItemName(String name) {

        List<ItemOwnership> itemOwnerships = itemOwnershipRepository.findByItemName(name);
        List<ItemOwnershipDTO> itemOwnershipDTOS = new ArrayList<>();

        itemOwnerships.forEach(itemOwnership -> itemOwnershipDTOS.add(toItemOwnershipDTO(itemOwnership)));

        return itemOwnershipDTOS;
    }

    public ItemOwnershipDTO getItemOwnershipByUsernameAndItemName(String username, String itemName) {
        List<ItemOwnership> itemNameLinks = itemOwnershipRepository.findByItemName(itemName);
        for (ItemOwnership itemNameLink : itemNameLinks) {
            if (itemNameLink.getUser().getUsername().equals(username) && itemNameLink.getItem().getName().equals(itemName)) {
                return toItemOwnershipDTO(itemNameLink);
            }
        }
        return null;
    }

    public void addItemOwnership(String username, String itemName, ItemOwnershipDTO itemOwnershipDTO) throws Exception {

        User user = userRepository.findByUsername(username);
        Item item = itemRepository.findByName(itemName);

        if(user == null) {
            throw new Exception("User with username " + username + " does not exist!");
        }
        if(item == null) {
            throw new Exception("Item with name " + itemName + " does not exist!");
        }
        if(itemOwnershipDTO.getItemCount() == null) {
            throw new Exception("Missing item count!");
        }

        ItemOwnership itemOwnership = new ItemOwnership();
        itemOwnership.setItemCount(itemOwnershipDTO.getItemCount());
        itemOwnership.setItem(item);
        itemOwnership.setUser(user);

        itemOwnershipRepository.save(itemOwnership);

    }

    public void updateItemOwnership(Long id, ItemOwnershipDTO itemOwnershipDTO) throws Exception {

        ItemOwnership itemOwnership = itemOwnershipRepository.findById(id).orElse(null);
        if(itemOwnership == null) {
            throw new Exception("Link with id " + id + " does not exist!");
        }

        if(itemOwnershipDTO.getItemCount() == null) {
            throw new Exception("Missing item count!");
        }

        itemOwnership.setItemCount(itemOwnershipDTO.getItemCount());

        itemOwnershipRepository.save(itemOwnership);

    }

    public void deleteItemOwnership(Long id) {
        itemOwnershipRepository.deleteById(id);
    }

    public void increaseCount(Long id) throws Exception {
        ItemOwnership ownership = itemOwnershipRepository.findById(id).orElse(null);
        if(ownership == null) {
            throw new Exception("Link does not exist!");
        }
        ownership.setItemCount(ownership.getItemCount() + 1);
        itemOwnershipRepository.save(ownership);
    }

    public void decreaseCount(Long id) throws Exception {
        ItemOwnership ownership = itemOwnershipRepository.findById(id).orElse(null);
        if(ownership == null) {
            throw new Exception("Link does not exist!");
        }
        if(ownership.getItemCount() > 0) {
            ownership.setItemCount(ownership.getItemCount() - 1);
            itemOwnershipRepository.save(ownership);
        }
    }

    private ItemOwnershipDTO toItemOwnershipDTO(ItemOwnership itemOwnership) {
        return new ItemOwnershipDTO(
                itemOwnership.getId(),
                itemOwnership.getItemCount(),
                itemOwnership.getItem(),
                itemOwnership.getUser()
        );
    }

}
