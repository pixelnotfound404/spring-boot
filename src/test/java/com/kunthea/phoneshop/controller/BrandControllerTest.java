package com.kunthea.phoneshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kunthea.phoneshop.Mapper.BrandMapper;
import com.kunthea.phoneshop.dto.BrandDTO;
import com.kunthea.phoneshop.entity.Brand;
import com.kunthea.phoneshop.service.BrandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
public class BrandControllerTest {
    @Mock
    private BrandService brandService;

    @Mock
    private BrandMapper brandMapper;

    @InjectMocks
    private BrandController brandController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(brandController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateBrand() throws Exception {
        // Given
        BrandDTO inputBrandDTO = new BrandDTO();
        inputBrandDTO.setBrandName("Nokia");
        inputBrandDTO.setBranId(1);

        Brand brand = new Brand();
        brand.setName("Nokia");
        brand.setId(1);

        BrandDTO outputBrandDTO = new BrandDTO();
        outputBrandDTO.setBrandName("Nokia");
        outputBrandDTO.setBranId(1);

        // Mock behavior
        when(brandMapper.toBrand(any(BrandDTO.class))).thenReturn(brand);
        when(brandService.create(any(Brand.class))).thenReturn(brand);
        when(brandMapper.toBrandDTO(any(Brand.class))).thenReturn(outputBrandDTO);

        // When & Then
        mockMvc.perform(post("/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputBrandDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.branId").value(1))
                .andExpect(jsonPath("$.brandName").value("Nokia"));
    }

    @Test
    public void testUpdateBrand() throws Exception {
        Integer brandId = 1;
        BrandDTO inputBrandDTO = new BrandDTO();
        inputBrandDTO.setBrandName("Nokia");
        inputBrandDTO.setBranId(1);

        Brand mappedBrand = new Brand();
        mappedBrand.setName("Nokia");
        mappedBrand.setId(1);

        Brand updatedBrand = new Brand();
        updatedBrand.setName("Nokia");
        updatedBrand.setId(1);

        BrandDTO outputBrandDTO = new BrandDTO();
        outputBrandDTO.setBrandName("Nokia");
        outputBrandDTO.setBranId(1);

        // Mock behavior - Note: Based on your original method signature, it should be update(Brand, Integer)
        when(brandMapper.toBrand(any(BrandDTO.class))).thenReturn(mappedBrand);
        when(brandService.update(any(Brand.class), eq(brandId))).thenReturn(updatedBrand);
        when(brandMapper.toBrandDTO(any(Brand.class))).thenReturn(outputBrandDTO);

        // When & Then
        mockMvc.perform(put("/brands/{id}", brandId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputBrandDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.branId").value(1))
                .andExpect(jsonPath("$.brandName").value("Nokia"));

        // Verify interactions
        verify(brandMapper).toBrand(any(BrandDTO.class));
        verify(brandService).update(any(Brand.class), eq(brandId));
        verify(brandMapper).toBrandDTO(any(Brand.class));

    }


    @Test
    public void testDeleteBrand() throws Exception {
        Integer brandId = 1;


        Brand deleteBrand = new Brand();
        deleteBrand.setName("Nokia");
        deleteBrand.setId(1);

        BrandDTO outputBrandDTO = new BrandDTO();
        outputBrandDTO.setBrandName("Nokia");
        outputBrandDTO.setBranId(1);

        when(brandService.DeleteById(eq(brandId))).thenReturn(deleteBrand);
        when(brandMapper.toBrandDTO(any(Brand.class))).thenReturn(outputBrandDTO);

        mockMvc.perform(delete("/brands/{id}", brandId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.branId").value(1))
                .andExpect(jsonPath("$.brandName").value("Nokia"));

        verify(brandService).DeleteById(brandId);
        verify(brandMapper).toBrandDTO(any(Brand.class));
    }

    @Test
    public void testGetBrands_EmptyParams_ReturnsAllBrands() throws Exception {

        List<BrandDTO> allBrands = Arrays.asList(
                createBrandDTO(1, "Nokia"),
                createBrandDTO(2, "Samsung"),
                createBrandDTO(3, "Apple")
        );


        when(brandService.GetAllBrands()).thenReturn(allBrands);


        mockMvc.perform(get("/brands"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].branId").value(1))
                .andExpect(jsonPath("$[0].brandName").value("Nokia"))
                .andExpect(jsonPath("$[1].branId").value(2))
                .andExpect(jsonPath("$[1].brandName").value("Samsung"))
                .andExpect(jsonPath("$[2].branId").value(3))
                .andExpect(jsonPath("$[2].brandName").value("Apple"));

        verify(brandService).GetAllBrands();
    }

    @Test
    public void testGetBrands_WithParams_ReturnsPagedResults() throws Exception {
        // Given
        Brand brand1 = new Brand();
        brand1.setId(1);
        brand1.setName("Nokia");

        Brand brand2 = new Brand();
        brand2.setId(2);
        brand2.setName("Samsung");

        List<Brand> brandList = Arrays.asList(brand1, brand2);
        Page<Brand> brandPage = new PageImpl<>(brandList);

        BrandDTO brandDTO1 = createBrandDTO(1, "Nokia");
        BrandDTO brandDTO2 = createBrandDTO(2, "Samsung");


        when(brandService.getBrands(any(Map.class))).thenReturn(brandPage);
        when(brandMapper.toBrandDTO(brand1)).thenReturn(brandDTO1);
        when(brandMapper.toBrandDTO(brand2)).thenReturn(brandDTO2);


        mockMvc.perform(get("/brands")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].branId").value(1))
                .andExpect(jsonPath("$[0].brandName").value("Nokia"))
                .andExpect(jsonPath("$[1].branId").value(2))
                .andExpect(jsonPath("$[1].brandName").value("Samsung"));

        verify(brandService).getBrands(any(Map.class));
        verify(brandMapper).toBrandDTO(brand1);
        verify(brandMapper).toBrandDTO(brand2);
    }

    @Test
    public void testGetBrands_WithParams_EmptyResults_ReturnsNotFound() throws Exception {

        Page<Brand> emptyPage = Page.empty();


        when(brandService.getBrands(any(Map.class))).thenReturn(emptyPage);


        mockMvc.perform(get("/brands")
                        .param("name", "NonExistentBrand"))
                .andExpect(status().isNotFound());

        verify(brandService).getBrands(any(Map.class));
    }

    @Test
    public void testGetBrands_WithMultipleParams() throws Exception {
        // Given
        Brand brand = new Brand();
        brand.setId(1);
        brand.setName("iPhone");

        List<Brand> brandList = Arrays.asList(brand);
        Page<Brand> brandPage = new PageImpl<>(brandList);

        BrandDTO brandDTO = createBrandDTO(1, "iPhone");

        // Mock behavior
        when(brandService.getBrands(any(Map.class))).thenReturn(brandPage);
        when(brandMapper.toBrandDTO(brand)).thenReturn(brandDTO);

        // When & Then
        mockMvc.perform(get("/brands")
                        .param("name", "iPhone")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "name"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].branId").value(1))
                .andExpect(jsonPath("$[0].brandName").value("iPhone"));

        verify(brandService).getBrands(any(Map.class));
        verify(brandMapper).toBrandDTO(brand);
    }

    @Test
    public void testGetBrands_EmptyParams_EmptyList() throws Exception {
        // Given
        List<BrandDTO> emptyList = new ArrayList<>();

        // Mock behavior
        when(brandService.GetAllBrands()).thenReturn(emptyList);

        // When & Then
        mockMvc.perform(get("/brands"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

        verify(brandService).GetAllBrands();
    }


    private BrandDTO createBrandDTO(Integer id, String name) {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setBranId(id);
        brandDTO.setBrandName(name);
        return brandDTO;
    }
}