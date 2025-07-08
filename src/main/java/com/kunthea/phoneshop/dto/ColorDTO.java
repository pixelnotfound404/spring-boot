package com.kunthea.phoneshop.dto;

import com.kunthea.phoneshop.entity.Brand;
import lombok.Data;

@Data
public class ColorDTO {
    private String colorName;
    private Integer brandId;
    private BrandDTO brand;
}
