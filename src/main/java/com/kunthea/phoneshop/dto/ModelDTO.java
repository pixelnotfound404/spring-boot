package com.kunthea.phoneshop.dto;

import lombok.Data;

@Data
public class ModelDTO {
    private String modelName;
    private Integer branId;
    private BrandDTO brand;
}
