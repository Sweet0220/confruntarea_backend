package com.mirceanealcos.confruntarea.service;

import com.mirceanealcos.confruntarea.dto.ItemDTO;
import com.mirceanealcos.confruntarea.entity.Item;
import com.mirceanealcos.confruntarea.repository.ItemOwnershipRepository;
import com.mirceanealcos.confruntarea.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemOwnershipRepository itemOwnershipRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, ItemOwnershipRepository itemOwnershipRepository) {
        this.itemRepository = itemRepository;
        this.itemOwnershipRepository = itemOwnershipRepository;
    }

    public List<ItemDTO> getAllItems() {

        List<Item> items = itemRepository.findAll();
        List<ItemDTO> itemDTOS = new ArrayList<>();

        items.forEach(item -> itemDTOS.add(toItemDTO(item)));

        return itemDTOS;
    }

    public List<Item> getAllItemsAsEntity() {
        return itemRepository.findAll();
    }

    public List<ItemDTO> getItemsByType(String type) {

        List<Item> items = itemRepository.findByType(type);
        List<ItemDTO> itemDTOS = new ArrayList<>();

        items.forEach(item -> itemDTOS.add(toItemDTO(item)));

        return itemDTOS;
    }

    public ItemDTO getItemByName(String name) {

        Item item = itemRepository.findByName(name);
        return toItemDTO(item);
    }

    public ItemDTO getItemById(Long id) {

        Item item = itemRepository.findById(id).orElse(null);
        return toItemDTO(item);
    }

    public List<ItemDTO> getItemsByUserId(Long user_id) {

        List<Item> items = itemRepository.findByOwnershipsUserId(user_id);
        List<ItemDTO> itemDTOS = new ArrayList<>();

        items.forEach(item -> itemDTOS.add(toItemDTO(item)));

        return itemDTOS;
    }

    public void addItem(ItemDTO itemDTO) throws Exception{
        if(itemDTO.hasEmptyFields()) {
            throw new Exception("Missing fields!");
        }

        Item item = toEntity(itemDTO);

        itemRepository.save(item);
    }

    public void updateItem(String name, ItemDTO itemDTO) throws Exception{
        if(!itemDTO.hasPopulatedFields()) {
            throw new Exception("No changes present..");
        }

        Item item = itemRepository.findByName(name);

        if(item == null) {
            throw new Exception("Item with name " + name + " does not exist!");
        }

        if(itemDTO.getName() != null) {
            item.setName(itemDTO.getName());
        }

        if(itemDTO.getBonusDamage() != null) {
            item.setBonusDamage(itemDTO.getBonusDamage());
        }

        if(itemDTO.getBonusHp() != null) {
            item.setBonusHp(itemDTO.getBonusHp());
        }

        if(itemDTO.getPrice() != null) {
            item.setPrice(itemDTO.getPrice());
        }

        if(itemDTO.getType() != null) {
            item.setType(itemDTO.getType());
        }

        if(itemDTO.getPicture() != null) {
            item.setPicture(itemDTO.getPicture());
        }

        if(itemDTO.getNameColor() != null) {
            item.setNameColor(itemDTO.getNameColor());
        }

        itemRepository.save(item);
    }

    public void deleteItemByName(String name) {
        Item item = itemRepository.findByName(name);
        itemOwnershipRepository.deleteByItemName(name);
        itemRepository.delete(item);
    }

    private ItemDTO toItemDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();

        itemDTO.setName(item.getName());
        itemDTO.setBonusDamage(item.getBonusDamage());
        itemDTO.setBonusHp(item.getBonusHp());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setPicture(item.getPicture());
        itemDTO.setType(item.getType());
        itemDTO.setNameColor(item.getNameColor());

        return itemDTO;
    }

    private Item toEntity(ItemDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setBonusDamage(itemDTO.getBonusDamage());
        item.setBonusHp(itemDTO.getBonusHp());
        item.setPrice(itemDTO.getPrice());
        item.setType(itemDTO.getType());
        item.setPicture(itemDTO.getPicture());
        item.setNameColor(itemDTO.getNameColor());
        return item;
    }

}
