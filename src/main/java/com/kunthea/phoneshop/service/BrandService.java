package com.kunthea.phoneshop.service;

import com.kunthea.phoneshop.dto.BrandDTO;
import com.kunthea.phoneshop.entity.Brand;

import java.util.List;

public interface BrandService {
    Brand create(Brand brand);
    Brand getBrandId(Integer brandId);
    Brand update(Brand BrandUpdate, Integer id);
    Brand DeleteById(Integer id);
    List<BrandDTO> GetAllBrands();
}
