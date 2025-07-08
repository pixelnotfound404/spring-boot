package com.kunthea.phoneshop.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "colors")
public class Colors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name="brandid")
    private Brand brand;
}
