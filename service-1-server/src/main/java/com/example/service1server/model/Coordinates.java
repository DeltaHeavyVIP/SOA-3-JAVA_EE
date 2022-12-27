package com.example.service1server.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(of = {"id", "x", "y"})
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "coordinates")
public class Coordinates implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Max(791)
    @Column(name = "x", nullable = false, updatable = false)
    private float x; //Максимальное значение поля: 791

    @Column(name = "y", nullable = false, updatable = false)
    private Float y; //Поле не может быть null

}
