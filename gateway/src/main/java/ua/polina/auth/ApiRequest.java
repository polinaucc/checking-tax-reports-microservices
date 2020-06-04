package ua.polina.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiRequest<T> {
    Long userId;
    T dto;
}
