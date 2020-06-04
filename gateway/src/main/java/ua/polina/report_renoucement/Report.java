package ua.polina.report_renoucement;

import lombok.Data;

import java.time.LocalDate;
@Data
public class Report {
    private Long id;
    private Long clientId;
    private String comment;
    private Status status;
    private LocalDate date;
}
