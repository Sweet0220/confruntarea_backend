package com.mirceanealcos.confruntarea.controller;

import com.mirceanealcos.confruntarea.dto.ItemDTO;
import com.mirceanealcos.confruntarea.entity.enums.ItemType;
import com.mirceanealcos.confruntarea.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Item controller with suggestive endpoints
 */
@RestController
@RequestMapping("/api/items")
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        try {
            List<ItemDTO> itemDTOS = itemService.getAllItems();
            if(itemDTOS.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(itemDTOS, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/page/{page}")
    public ResponseEntity<List<ItemDTO>> getItemPage(@PathVariable("page") Integer page) {
        try {
            List<ItemDTO> dtos = itemService.getItemPage(page);
            if(dtos.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/type/{type}")
    public ResponseEntity<List<ItemDTO>> getItemsByType(@PathVariable("type") ItemType type) {
        try {
            List<ItemDTO> itemDTOS = itemService.getItemsByType(type);
            if(itemDTOS.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(itemDTOS, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/user-id/{id}")
    public ResponseEntity<List<ItemDTO>> getItemsByUserId(@PathVariable("id") Long user_id) {
        try {
            List<ItemDTO> itemDTOS = itemService.getItemsByUserId(user_id);
            if(itemDTOS.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(itemDTOS, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable("id") Long id) {
        try {
            ItemDTO itemDTO = itemService.getItemById(id);
            if(itemDTO == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(itemDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/name/{name}")
    public ResponseEntity<ItemDTO> getItemByName(@PathVariable("name") String name) {
        try {
            ItemDTO itemDTO = itemService.getItemByName(name);
            if(itemDTO == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(itemDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<String> addItem(@RequestBody ItemDTO itemDTO) {
        try {
            itemService.addItem(itemDTO);
            return new ResponseEntity<>("Item added successfully!", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/update/{name}")
    public ResponseEntity<String> updateItem(@PathVariable("name") String name, @RequestBody ItemDTO itemDTO) {
        try {
            itemService.updateItem(name, itemDTO);
            return new ResponseEntity<>("Item updated successfully!", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/delete/{name}")
    public ResponseEntity<String> deleteItemByName(@PathVariable("name") String name) {
        try {
            itemService.deleteItemByName(name);
            return new ResponseEntity<>("Item deleted successfully!", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
