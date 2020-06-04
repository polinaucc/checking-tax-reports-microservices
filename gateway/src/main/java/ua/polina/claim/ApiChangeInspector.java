package ua.polina.claim;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.polina.client.Client;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiChangeInspector {
    ChangeInspectorDto changeInspectorDto;
    Client client;
}
