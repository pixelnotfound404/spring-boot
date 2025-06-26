package com.kunthea.phoneshop.service;

import com.kunthea.phoneshop.dto.BrandDTO;
import com.kunthea.phoneshop.entity.Brand;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface BrandService {
    Brand create(Brand brand);
    @Named("getBrandId")
    Brand getBrandId(Integer brandId);
    Brand update(Brand BrandUpdate, Integer id);
    Brand DeleteById(Integer id);
    List<BrandDTO> GetAllBrands();
    Page<Brand> getBrands(Map<String, String> params);

}
