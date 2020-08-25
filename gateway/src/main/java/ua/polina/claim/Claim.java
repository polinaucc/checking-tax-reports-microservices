package ua.polina.claim;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Claim {
    private Long id;
    private String reason;
    private Status status;
    private Long clientId;
    private Long inspectorId;

}
