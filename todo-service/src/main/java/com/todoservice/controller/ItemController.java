package com.todoservice.controller;

import com.todoservice.dto.request.ItemRequest;
import com.todoservice.dto.response.ItemResponse;
import com.todoservice.entity.Item;
import com.todoservice.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    // Add a new item
    @PostMapping("/add-new-item")
    public ResponseEntity<ItemResponse> addNewItem(@RequestBody ItemRequest itemRequest) {
        itemService.save(itemRequest);

        ItemResponse itemResponse = new ItemResponse(
                "New item added successfully!",
                LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    // update item by id
    @PutMapping("/update-item")
    public ResponseEntity<ItemResponse> updateItem(@RequestParam Long id, @RequestBody ItemRequest itemRequest) {

        Optional<Item> item = itemService.getItemById(id);
        if (item.isPresent()) {

            itemService.updateItem(id, itemRequest);

            ItemResponse itemResponse = new ItemResponse(
                    "Item updated successfully!",
                    LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));

            return new ResponseEntity<>(itemResponse, HttpStatus.OK);

        } else {

            ItemResponse itemResponse = new ItemResponse(
                    "Item not found!",
                    LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));

            return new ResponseEntity<>(itemResponse, HttpStatus.NOT_FOUND);
        }
    }

    // search for item by title
    @GetMapping("/get-item")
    public ResponseEntity<ItemResponse> getItemByTitle(@RequestParam String title) {

        Optional<Item> item = itemService.getItemByTitle(title);
        if (item.isPresent()) {

            ItemResponse itemResponse = new ItemResponse(
                    "Item found!",
                    LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));

            return new ResponseEntity<>(itemResponse, HttpStatus.OK);

        } else {

            ItemResponse itemResponse = new ItemResponse(
                    "Item not found!",
                    LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));

            return new ResponseEntity<>(itemResponse, HttpStatus.NOT_FOUND);
        }

    }

    // delete item by Id
    @DeleteMapping("/delete-item")
    public ResponseEntity<ItemResponse> deleteItem(@RequestParam Long id) {


        Optional<Item> item = itemService.getItemById(id);
        if (item.isPresent()) {

            itemService.deleteItem(id);

            ItemResponse itemResponse = new ItemResponse(
                    "Item deleted successfully!",
                    LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));

            return new ResponseEntity<>(itemResponse, HttpStatus.OK);

        } else {

            ItemResponse itemResponse = new ItemResponse(
                    "Item not found!",
                    LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));

            return new ResponseEntity<>(itemResponse, HttpStatus.NOT_FOUND);
        }
    }


}