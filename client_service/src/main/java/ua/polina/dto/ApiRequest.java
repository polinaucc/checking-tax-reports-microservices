package ua.polina.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiRequest<T> {
    Long userId;
    T dto;
}
