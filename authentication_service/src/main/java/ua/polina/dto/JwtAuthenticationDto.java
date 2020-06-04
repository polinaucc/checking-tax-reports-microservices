package ua.polina.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class JwtAuthenticationDto {
    @NonNull
    private String accessToken;
    private String tokenType = "Bearer";
}
