package com.userservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Generic response for authentication-related operations")
public class AuthResponse {

    @Schema(description = "Response message", example = "Password reset successful")
    private String message;

    @Schema(
            description = "Timestamp of the response",
            example = "11-06-2025 14:35",
            type = "string",
            pattern = "dd-MM-yyyy HH:mm"
    )
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime timestamp;

    public AuthResponse(String message) {
        this.message = message;
    }
}
