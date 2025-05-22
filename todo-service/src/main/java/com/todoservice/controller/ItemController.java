package com.todoservice.controller;

import com.todoservice.dto.request.ItemRequest;
import com.todoservice.dto.response.ItemResponse;
import com.todoservice.entity.Item;
import com.todoservice.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
@Tag(name = "Items", description = "CRUD operations for managing items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Operation(summary = "Add a new item")
    @ApiResponse(responseCode = "201", description = "Item successfully added")
    @PostMapping("/add-new-item")
    public ResponseEntity<ItemResponse> addNewItem(@RequestBody ItemRequest itemRequest) {
        itemService.save(itemRequest);

        ItemResponse itemResponse = new ItemResponse(
                "New item added successfully!",
                LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an item by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item updated successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @PutMapping("/update-item")
    public ResponseEntity<ItemResponse> updateItem(
            @Parameter(description = "ID of the item to be updated") @RequestParam Long id,
            @RequestBody ItemRequest itemRequest) {

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

    @Operation(summary = "Get an item by title")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item found"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @GetMapping("/get-item")
    public ResponseEntity<ItemResponse> getItemByTitle(
            @Parameter(description = "Title of the item to search for") @RequestParam String title) {

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

    @Operation(summary = "Delete an item by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @DeleteMapping("/delete-item")
    public ResponseEntity<ItemResponse> deleteItem(
            @Parameter(description = "ID of the item to be deleted") @RequestParam Long id) {

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
