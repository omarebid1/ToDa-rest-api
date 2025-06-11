package com.userservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Standard structure for API error responses")
public class ErrorResponse {

    @Schema(description = "Short error title or code", example = "BAD_REQUEST")
    private String error;

    @Schema(description = "Detailed error message", example = "Email format is invalid")
    private String message;

    @Schema(
            description = "Time the error occurred",
            example = "11-06-2025 15:42",
            type = "string",
            pattern = "dd-MM-yyyy HH:mm"
    )
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime timestamp;
}