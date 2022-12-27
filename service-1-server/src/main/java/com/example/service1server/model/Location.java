package com.example.service1server.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(of = {"id", "x", "y", "z"})
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "location")
public class Location implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "x", nullable = false, updatable = false)
    private int x;

    @Column(name = "y", nullable = false, updatable = false)
    private Double y; //Поле не может быть null

    @Column(name = "z", nullable = false, updatable = false)
    private double z;

}
