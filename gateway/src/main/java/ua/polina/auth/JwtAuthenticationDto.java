package ua.polina.auth;

import lombok.Data;
import lombok.NonNull;

@Data
public class JwtAuthenticationDto {
    private String accessToken;
    private String tokenType = "Bearer";
}
