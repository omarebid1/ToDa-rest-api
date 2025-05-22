package com.todoservice.dto.request;

import com.todoservice.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ItemDetailsRequest {

    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Status is required")
    private Boolean status;
    @NotBlank(message = "Priority is required")
    private Priority priority;

}
