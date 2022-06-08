package ru.ystu.cmis.domain;

import lombok.Data;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;


import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "ticket")
@Data
public class Ticket {
    @Id
    @Generated(value = GenerationTime.INSERT)
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column
    private LocalDateTime dtime;

    @Column
    private String name;


}
