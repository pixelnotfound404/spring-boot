package com.kunthea.phoneshop.impl;
import com.kunthea.phoneshop.util.Mapper;
import com.kunthea.phoneshop.dto.BrandDTO;
import com.kunthea.phoneshop.entity.Brand;
import com.kunthea.phoneshop.exception.ApiException;
import com.kunthea.phoneshop.repository.BrandRepository;
import com.kunthea.phoneshop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Brand create(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand getBrandId(Integer brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Brand not found with ID: " + brandId));
    }

    @Override
    public Brand update(Brand BrandUpdate, Integer id) {
        Brand brand = getBrandId(id);
        brand.setName(BrandUpdate.getName());
        return brandRepository.save(brand);
    }

    @Override
    public Brand DeleteById(Integer id) {
        Brand brand = getBrandId(id);
        brandRepository.delete(brand);
        return brand;
    }

    @Override
    public List<BrandDTO> GetAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream()
                .map(Mapper::toDTO)
                .collect(Collectors.toList());

    }


}
