package ua.polina.report_renoucement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiReport {
    ReportDto reportDto;
    Long clientId;
}
