package ua.polina.report_renoucement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusApi {
    Status status;
    Long clientId;
}
