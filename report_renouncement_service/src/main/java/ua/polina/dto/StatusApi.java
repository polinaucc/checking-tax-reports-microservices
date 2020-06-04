package ua.polina.dto;

import lombok.Data;
import ua.polina.entity.Status;

@Data
public class StatusApi {
    Status status;
    Long clientId;
}
