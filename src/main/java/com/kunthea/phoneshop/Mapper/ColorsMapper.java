package com.kunthea.phoneshop.Mapper;

import com.kunthea.phoneshop.dto.ColorDTO;
import com.kunthea.phoneshop.entity.Colors;
import com.kunthea.phoneshop.service.BrandService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BrandMapper.class, BrandService.class})
public interface ColorsMapper {

    @Mapping(source = "colorName", target = "name")
    @Mapping(source = "brandId", target = "brand", qualifiedByName = "getBrandId")
    Colors toColor(ColorDTO dto);

    @Mapping(source = "name", target = "colorName")
    @Mapping(source = "brand.id", target = "brandId")
    ColorDTO toDTO(Colors entity);
}
