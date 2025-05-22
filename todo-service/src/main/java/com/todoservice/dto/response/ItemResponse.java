package com.todoservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response message with a timestamp")
public class ItemResponse {

    @Schema(description = "Message describing the outcome of the request", example = "Item added successfully")
    private String message;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @Schema(description = "Timestamp when the response was generated", example = "22-05-2025 15:30")
    private LocalDateTime currentTime;
}

