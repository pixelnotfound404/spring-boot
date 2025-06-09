package com.kunthea.phoneshop.controller;

import com.kunthea.phoneshop.Mapper.BrandMapper;
import com.kunthea.phoneshop.dto.BrandDTO;
import com.kunthea.phoneshop.entity.Brand;
import com.kunthea.phoneshop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("brands")
public class BrandController {
    @Autowired
    private BrandService brandService;
    @Autowired
    private BrandMapper brandMapper;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody BrandDTO brandDTO){
        Brand brand = brandMapper.toBrand(brandDTO);
        brand = brandService.create(brand);
        return ResponseEntity.ok(brandMapper.toBrandDTO(brand));
    }


    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody BrandDTO brandDTO){
        Brand updateBrand = brandMapper.toBrand(brandDTO);
        updateBrand = brandService.update(updateBrand, id);
        return ResponseEntity.ok(brandMapper.toBrandDTO(updateBrand));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        Brand deleteBrand = brandService.DeleteById(id);
        BrandDTO responseDTO = brandMapper.toBrandDTO(deleteBrand);
        return ResponseEntity.ok(responseDTO);
    }

//    @GetMapping("")
//    public ResponseEntity<?> getBrands(){
//        List<BrandDTO> brands = brandService.GetAllBrands();
//        return ResponseEntity.ok(brands);
//    }

    @GetMapping
    public ResponseEntity<?> getBrandByName(@RequestParam Map<String, String> params){
        if(params.isEmpty()){
            List<BrandDTO> brands = brandService.GetAllBrands();
            return ResponseEntity.ok(brands);
        }
        List<Brand> brands = brandService.getBrands(params);

        if (!brands.isEmpty()) {
            // Return only the first brand
            BrandDTO brandDTO = brandMapper.toBrandDTO(brands.get(0));
            return ResponseEntity.ok(brandDTO);
        }

        return ResponseEntity.notFound().build();
    }
}