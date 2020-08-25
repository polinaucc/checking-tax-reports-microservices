package ua.polina.claim;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.polina.client.Client;
import ua.polina.inspector.Inspector;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimOutputDto {
    Long claimId;
    String clientName;
    Inspector inspector;
    String reason;
    Status status;
}
