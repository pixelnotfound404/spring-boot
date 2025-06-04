package com.kunthea.phoneshop.controller;

import com.kunthea.phoneshop.util.Mapper;
import com.kunthea.phoneshop.dto.BrandDTO;
import com.kunthea.phoneshop.entity.Brand;
import com.kunthea.phoneshop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brands")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody BrandDTO brandDTO){
        Brand brand = Mapper.toEntity(brandDTO);
        brand = brandService.create(brand);
        BrandDTO reponseDTO = Mapper.toDTO(brand);
        return ResponseEntity.ok(reponseDTO);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getBrand(@PathVariable("id") Integer id){
        Brand brand = brandService.getBrandId(id);
        BrandDTO reponseDTO = Mapper.toDTO(brand);
        return ResponseEntity.ok(reponseDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody BrandDTO brandDTO){
        Brand UpdateBrand = Mapper.toEntity(brandDTO);
        UpdateBrand = brandService.update(UpdateBrand, id);
        BrandDTO reponseDTO = Mapper.toDTO(UpdateBrand);
        return ResponseEntity.ok(reponseDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        Brand DeleteBrand=brandService.DeleteById(id);
        BrandDTO reponseDTO = Mapper.toDTO(DeleteBrand);
        return ResponseEntity.ok(reponseDTO);
    }

    @GetMapping("")
    public ResponseEntity<?> getBrands(){
        List<BrandDTO> brands = brandService.GetAllBrands();
        return ResponseEntity.ok(brands);
    }
}
