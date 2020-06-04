package ua.polina.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="renouncement")
public class Renouncement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;
    @Column(name="date")
    private LocalDate date;
    @Column(name="reason")
    private String reason;
    @Column(name="report_id")
    private Long reportId;

}
