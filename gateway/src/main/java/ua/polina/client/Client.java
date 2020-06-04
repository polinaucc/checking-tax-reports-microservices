package ua.polina.client;

import lombok.Data;

@Data
public class Client {
    private Long id;
    private ClientType clientType;
    private Long inspectorId;
    private Long userId;
}
