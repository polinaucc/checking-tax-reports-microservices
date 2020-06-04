package ua.polina.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;
    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private ClientType clientType;
    @Column(name="inspector_fk")
    private Long inspectorId;
    @Column(name="user_fk")
    private Long userId;
}
