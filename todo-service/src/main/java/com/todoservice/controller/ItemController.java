package com.todoservice.controller;

import com.todoservice.dto.request.ItemRequest;
import com.todoservice.dto.response.ItemResponse;
import com.todoservice.entity.Item;
import com.todoservice.exception.ItemNotFoundException;
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
            @ApiResponse(responseCode = "404", description = "Item not found")})
    @PutMapping("/update-item")
    public ResponseEntity<ItemResponse> updateItem(
            @Parameter(description = "ID of the item to be updated") @RequestParam Long id,
            @RequestBody ItemRequest itemRequest) {

        itemService.updateItemById(id, itemRequest)
                .orElseThrow(() -> new ItemNotFoundException("Try with another one"));

        ItemResponse itemResponse = new ItemResponse(
                "Item updated successfully!",
                LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));

        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get an item by title")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item found"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @GetMapping("/get-item")
    public ResponseEntity<Item> getItemByTitle(
            @Parameter(description = "Title of the item to search for") @RequestParam String title) {
        Item foundItem = itemService.getItemByTitle(title)
                .orElseThrow(() -> new ItemNotFoundException("Item not Found, Try searching for another one"));

        return new ResponseEntity<>(foundItem, HttpStatus.OK);
    }

    @Operation(summary = "Delete an item by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @DeleteMapping("/delete-item")
    public ResponseEntity<ItemResponse> deleteItem(
            @Parameter(description = "ID of the item to be deleted") @RequestParam Long id) {

        itemService.deleteItemById(id)
                .orElseThrow(() -> new ItemNotFoundException("try with another one"));

        ItemResponse itemResponse = new ItemResponse(
                "Item deleted successfully!",
                LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));
        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
    }
}
