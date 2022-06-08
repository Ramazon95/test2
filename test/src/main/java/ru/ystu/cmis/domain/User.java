package ru.ystu.cmis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Generated(value = GenerationTime.INSERT)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", updatable = false, nullable = false)
    private Long id;
    @Column
    private String login;
    @Column
    private String password;
    @Column
    private String role;
    @Column
    private String isAdmin;
}
