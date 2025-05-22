package com.todoservice.dto.request;

import lombok.Data;

@Data
public class ItemRequest {

    private String title;
    private Long userId;
    private ItemDetailsRequest itemDetails;

}
