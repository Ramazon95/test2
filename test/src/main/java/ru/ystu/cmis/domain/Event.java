package ru.ystu.cmis.domain;

import lombok.Data;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "event")
@Data
public class Event {
    @Id
    @Generated(value = GenerationTime.INSERT)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column
    private LocalDateTime dtime;

    @Column
    private String name;

    @Column
    private Integer tickets;

    @Column
    private Integer ticketFree;

    @Column
    private Integer price;

    @Column
    private String description;

    @Column
    private Integer count;
}
