package com.todoservice.dto.request;

import com.todoservice.enums.Priority;
import lombok.Data;

@Data
public class ItemDetailsRequest {

    private String description;
    private Boolean status;
    private Priority priority;

}
