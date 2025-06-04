package com.kunthea.phoneshop.util;

import com.kunthea.phoneshop.dto.BrandDTO;
import com.kunthea.phoneshop.entity.Brand;

public class Mapper {
    public static Brand toEntity(BrandDTO dto){
        Brand brand = new Brand();
        brand.setId(dto.getBranId());
        brand.setName(dto.getBrandName());
        return brand;
    }

    public static BrandDTO toDTO(Brand brand) {
        BrandDTO dto = new BrandDTO();
        dto.setBranId(brand.getId());
        dto.setBrandName(brand.getName());
        return dto;
    }
}
