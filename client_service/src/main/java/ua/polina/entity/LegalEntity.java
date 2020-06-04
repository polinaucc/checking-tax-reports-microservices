package ua.polina.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LegalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String edrpou;
    private String mfo;
    private String address;
    @Column(name = "client_fk")
    private Long clientId;
}
