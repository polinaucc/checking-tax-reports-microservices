package ua.polina.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="individual")
public class Individual {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "second_name")
    private String middleName;
    @Column(name = "surname")
    private String lastName;
    @Column(name = "address")
    private String address;
    @Column(name = "passport")
    private String passport;
    @Column(name = "identification_code")
    private String identCode;
    @Column(name = "client_fk")
    private Long clientId;
}
