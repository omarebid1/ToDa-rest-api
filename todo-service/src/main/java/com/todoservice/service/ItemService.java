package com.todoservice.service;

import com.todoservice.dto.request.ItemRequest;
import com.todoservice.entity.Item;
import com.todoservice.entity.ItemDetails;
import com.todoservice.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    //  Save a new item
    public void save(ItemRequest item) {
        Item itemEntity = new Item(
                item.getTitle(),
                item.getUserId(),
                new ItemDetails(
                        item.getItemDetails().getDescription(),
                        item.getItemDetails().getStatus(),
                        item.getItemDetails().getPriority(),
                        LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter)
                ));
        itemRepository.save(itemEntity);
    }

    // Search for task by Title
    public Optional<Item> getItemByTitle(String title) {
        return itemRepository.findByTitle(title);
    }

    // get item by id to use it for deleting item
    public Optional<Item> getItemById(long id) {
        return itemRepository.findById(id);
    }

    // delete item by Id
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    // update item by id
    public void updateItem(Long id, ItemRequest item) {
        Item existingItem = itemRepository.findById(id).get();

        // Update the existing item with new values
        existingItem.setTitle(item.getTitle());
        existingItem.setUserId(item.getUserId());
        existingItem.getItemDetails().setDescription(
                item.getItemDetails().getDescription());
        existingItem.getItemDetails().setStatus(
                item.getItemDetails().getStatus());
        existingItem.getItemDetails().setPriority(
                item.getItemDetails().getPriority());
        existingItem.getItemDetails().setCreatedAt(
                LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter)
        );
        itemRepository.save(existingItem);
    }
}
