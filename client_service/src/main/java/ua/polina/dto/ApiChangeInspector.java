package ua.polina.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.polina.entity.Client;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiChangeInspector {
    ChangeInspectorDto changeInspectorDto;
    Client client;
}
