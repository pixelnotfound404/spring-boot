package com.kunthea.phoneshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

@Data
@Entity
@Table(name="brands")
public class Brand {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Integer id;

    @Column(name = "brand_name")
    private String name;
}
