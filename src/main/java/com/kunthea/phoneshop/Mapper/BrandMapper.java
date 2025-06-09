package com.kunthea.phoneshop.Mapper;

import com.kunthea.phoneshop.dto.BrandDTO;
import com.kunthea.phoneshop.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    @Mapping(source = "branId", target = "id")
    @Mapping(source = "brandName", target = "name")
    Brand toBrand(BrandDTO dto);

    @Mapping(source = "id", target = "branId")
    @Mapping(source = "name", target = "brandName")
    BrandDTO toBrandDTO(Brand brand);
}