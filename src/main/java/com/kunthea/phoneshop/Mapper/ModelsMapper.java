package com.kunthea.phoneshop.Mapper;
import com.kunthea.phoneshop.dto.ModelDTO;
import com.kunthea.phoneshop.entity.Model;
import com.kunthea.phoneshop.service.BrandService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {BrandService.class, BrandMapper.class})
public interface ModelsMapper {

    @Mapping(source = "modelName", target = "name")
    @Mapping(source = "branId", target = "brand", qualifiedByName = "getBrandId")
    Model toModel(ModelDTO dto);

    @Mapping(source = "name", target = "modelName")
    @Mapping(source = "brand.id", target = "branId")
    ModelDTO toModelDTO(Model model);
}