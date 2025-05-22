package com.todoservice.dto.request;

import com.todoservice.enums.Priority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Details about the item such as description, status, and priority")
public class ItemDetailsRequest {

    @NotBlank(message = "Description is required")
    @Schema(description = "Detailed description of the item", example = "Complete the Java assignment")
    private String description;

    @NotNull(message = "Status is required")
    @Schema(description = "Status of the item Done/Not", example = "true")
    private Boolean status;

    @NotNull(message = "Priority is required")
    @Schema(
            description = "Priority of the item",
            example = "HIGH"
    )
    private Priority priority;
}

