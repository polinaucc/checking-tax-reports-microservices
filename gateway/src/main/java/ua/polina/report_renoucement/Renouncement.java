package ua.polina.report_renoucement;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Renouncement {
    private Long id;
    private LocalDate date;
    private String reason;
    private Long reportId;
}
