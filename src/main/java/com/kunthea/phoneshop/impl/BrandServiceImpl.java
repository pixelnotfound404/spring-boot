package com.kunthea.phoneshop.impl;
import com.kunthea.phoneshop.Mapper.BrandMapper;
import com.kunthea.phoneshop.Spec.BrandFilter;
import com.kunthea.phoneshop.Spec.BrandSpec;
import com.kunthea.phoneshop.dto.BrandDTO;
import com.kunthea.phoneshop.entity.Brand;
import com.kunthea.phoneshop.exception.ApiException;
import com.kunthea.phoneshop.repository.BrandRepository;
import com.kunthea.phoneshop.service.BrandService;
import com.kunthea.phoneshop.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandMapper brandMapper;

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
                .map(brandMapper::toBrandDTO)
                .collect(Collectors.toList());

    }

//    @Override
//    public List<Brand> getBrands(Map<String, String> params) {
//        BrandFilter brandFilter = new BrandFilter();
//
//        if (params.containsKey("name")) {
//            String name=params.get("name");
//            brandFilter.setName(name);
//        }
//
//        if (params.containsKey("id")) {
//            String id =  params.get("id");
//            brandFilter.setId(Integer.parseInt(id));
//        }
//
//        BrandSpec brandSpec = new BrandSpec(brandFilter);
//
//
//        return brandRepository.findAll(brandSpec);
//    }

    @Override
    public Page<Brand> getBrands(Map<String, String> params) {
        try {
            BrandFilter brandFilter = buildBrandFilter(params);
            Pageable pageable = buildPageable(params);
            BrandSpec brandSpec = new BrandSpec(brandFilter);

            return brandRepository.findAll(brandSpec, pageable);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric parameter provided", e);
        }
    }

    private BrandFilter buildBrandFilter(Map<String, String> params) {
        BrandFilter brandFilter = new BrandFilter();

        Optional.ofNullable(params.get("name"))
                .filter(name -> !name.trim().isEmpty())
                .ifPresent(brandFilter::setName);

        Optional.ofNullable(params.get("id"))
                .filter(id -> !id.trim().isEmpty())
                .map(Integer::parseInt)
                .ifPresent(brandFilter::setId);

        return brandFilter;
    }

    private Pageable buildPageable(Map<String, String> params) {
        int pageLimit = parseIntParam(params, PageUtil.PAGE_LIMIT, 10);
        int pageNumber = parseIntParam(params, PageUtil.PAGE_NUMBER, 0);

        if (pageLimit <= 0 || pageLimit > 100) {
            throw new IllegalArgumentException("Page limit must be between 1 and 100");
        }
        if (pageNumber < 0) {
            throw new IllegalArgumentException("Page number must be non-negative");
        }

        return PageUtil.getPageable(pageNumber, pageLimit);
    }

    private int parseIntParam(Map<String, String> params, String key, int defaultValue) {
        return Optional.ofNullable(params.get(key))
                .filter(value -> !value.trim().isEmpty())
                .map(Integer::parseInt)
                .orElse(defaultValue);
    }



}
