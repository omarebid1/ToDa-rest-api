package com.todoservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request body for creating or updating an item")
public class ItemRequest {

    @NotBlank(message = "Title is required")
    @Schema(description = "Title of the item", example = "Do laundry")
    private String title;

    @NotNull(message = "User ID is required")
    @Schema(description = "ID of the user who owns this item", example = "101")
    private Long userId;

    @NotNull(message = "Item Details are required")
    @Schema(description = "Detailed properties of the item")
    private ItemDetailsRequest itemDetails;
}
