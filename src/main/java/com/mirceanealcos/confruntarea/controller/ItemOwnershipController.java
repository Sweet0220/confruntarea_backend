package com.mirceanealcos.confruntarea.controller;

import com.mirceanealcos.confruntarea.dto.ItemOwnershipDTO;
import com.mirceanealcos.confruntarea.service.ItemOwnershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Link table (items-users) controller with suggestive endpoints
 */
@RestController
@RequestMapping(path = "/api/item-ownerships")
public class ItemOwnershipController {

    private final ItemOwnershipService itemOwnershipService;

    @Autowired
    public ItemOwnershipController(ItemOwnershipService itemOwnershipService) {
        this.itemOwnershipService = itemOwnershipService;
    }

    @GetMapping
    public ResponseEntity<List<ItemOwnershipDTO>> getAllItemOwnerships() {
        try {
            List<ItemOwnershipDTO> itemOwnershipDTOS = itemOwnershipService.getAllItemOwnerships();
            if(itemOwnershipDTOS.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(itemOwnershipDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/username/{username}")
    public ResponseEntity<List<ItemOwnershipDTO>> getItemOwnershipsByUsername(@PathVariable("username") String username) {
        try {
            List<ItemOwnershipDTO> itemOwnershipDTOS = itemOwnershipService.getItemOwnershipsByUsername(username);
            if(itemOwnershipDTOS.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(itemOwnershipDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/item-name/{name}")
    public ResponseEntity<List<ItemOwnershipDTO>> getItemOwnershipsByItemName(@PathVariable("name") String name) {
        try {
            List<ItemOwnershipDTO> itemOwnershipDTOS = itemOwnershipService.getItemOwnershipsByItemName(name);
            if(itemOwnershipDTOS.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(itemOwnershipDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/username/{username}/item-name/{item-name}")
    public ResponseEntity<ItemOwnershipDTO> getItemOwnershipByUsernameAndItemName(@PathVariable("username") String username,
                                                                                  @PathVariable("item-name") String itemName) {
        try {
            ItemOwnershipDTO ownershipDTO = this.itemOwnershipService.getItemOwnershipByUsernameAndItemName(username, itemName);
            if(ownershipDTO == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(ownershipDTO, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/link-user/{username}/to-item/{item_name}")
    public ResponseEntity<String> addItemOwnership(@PathVariable("username") String username,
                                                   @PathVariable("item_name") String itemName,
                                                   @RequestBody ItemOwnershipDTO itemOwnershipDTO) {
        try {
            itemOwnershipService.addItemOwnership(username, itemName, itemOwnershipDTO);
            return new ResponseEntity<>("Succesfully linked item to user!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<String> updateItemOwnership(@PathVariable("id") Long id, @RequestBody ItemOwnershipDTO itemOwnershipDTO) {
        try {
            itemOwnershipService.updateItemOwnership(id, itemOwnershipDTO);
            return new ResponseEntity<>("Successfully updated link!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteItemOwnership(@PathVariable("id") Long id) {
        try {
            itemOwnershipService.deleteItemOwnership(id);
            return new ResponseEntity<>("Successfully deleted link!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
