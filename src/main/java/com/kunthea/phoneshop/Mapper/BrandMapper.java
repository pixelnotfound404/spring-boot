package com.kunthea.phoneshop.Mapper;

import com.kunthea.phoneshop.dto.BrandDTO;
import com.kunthea.phoneshop.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface BrandMapper {


    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);

    @Mapping(source = "branId", target = "id")
    @Mapping(source = "brandName", target = "name")
    Brand toBrand(BrandDTO dto);

    @Mapping(source = "id", target = "branId")
    @Mapping(source = "name", target = "brandName")
    BrandDTO toBrand(Brand brand);


}
