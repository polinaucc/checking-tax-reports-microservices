package ua.polina.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inspector")
public class Inspector {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "second_name")
    private String middleName;
    @Column(name = "surname")
    private String lastName;
    @Column(name = "employment_date")
    private LocalDate employmentDate;
    @Column(name = "user_fk")
    private Long userId;
}
