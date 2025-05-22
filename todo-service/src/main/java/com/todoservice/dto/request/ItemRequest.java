package com.todoservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ItemRequest {

    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "User Id is required")
    private Long userId;
    @NotBlank(message = "Item Details is required")
    private ItemDetailsRequest itemDetails;

}
