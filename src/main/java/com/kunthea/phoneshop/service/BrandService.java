package com.kunthea.phoneshop.service;

import com.kunthea.phoneshop.dto.BrandDTO;
import com.kunthea.phoneshop.entity.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {
    Brand create(Brand brand);
    Brand getBrandId(Integer brandId);
    Brand update(Brand BrandUpdate, Integer id);
    Brand DeleteById(Integer id);
    List<BrandDTO> GetAllBrands();
    List<Brand> getBrands(Map<String, String> params);
}
