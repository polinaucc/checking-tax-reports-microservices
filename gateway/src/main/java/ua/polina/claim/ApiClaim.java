package ua.polina.claim;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiClaim {
    ClaimDto claimDto;
    Long clientId;
    Long inspectorId;
}
