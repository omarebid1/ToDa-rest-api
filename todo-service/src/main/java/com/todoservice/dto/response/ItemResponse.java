package com.todoservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {

    private String message;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime currentTime;

}
