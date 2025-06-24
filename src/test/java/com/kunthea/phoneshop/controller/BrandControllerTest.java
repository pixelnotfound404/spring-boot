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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.hasSize;

import java.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

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
                .andExpect(jsonPath("$.brandName").value("Nokia"))
                .andDo(result -> {
                    // Additional MockMvc-based assertions if needed
                    String responseContent = result.getResponse().getContentAsString();
                    System.out.println("Response: " + responseContent);
                });
    }

    @Test
    public void testUpdateBrand() throws Exception {
        // Given
        Integer brandId = 1;
        BrandDTO inputBrandDTO = new BrandDTO();
        inputBrandDTO.setBrandName("Updated Nokia");
        inputBrandDTO.setBranId(1);

        Brand mappedBrand = new Brand();
        mappedBrand.setName("Updated Nokia");
        mappedBrand.setId(1);

        Brand updatedBrand = new Brand();
        updatedBrand.setName("Updated Nokia");
        updatedBrand.setId(1);

        BrandDTO outputBrandDTO = new BrandDTO();
        outputBrandDTO.setBrandName("Updated Nokia");
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
                .andExpect(jsonPath("$.brandName").value("Updated Nokia"));

        // Verify interactions
        verify(brandMapper).toBrand(any(BrandDTO.class));
        verify(brandService).update(any(Brand.class), eq(brandId));
        verify(brandMapper).toBrandDTO(any(Brand.class));
    }

    @Test
    public void testUpdateBrand_Success() throws Exception {
        // Given
        Integer brandId = 2;
        BrandDTO inputBrandDTO = new BrandDTO();
        inputBrandDTO.setBrandName("Samsung");

        Brand mappedBrand = new Brand();
        mappedBrand.setName("Samsung");

        Brand updatedBrand = new Brand();
        updatedBrand.setId(brandId);
        updatedBrand.setName("Samsung");

        BrandDTO responseBrandDTO = new BrandDTO();
        responseBrandDTO.setBranId(brandId);
        responseBrandDTO.setBrandName("Samsung");

        // When
        when(brandMapper.toBrand(inputBrandDTO)).thenReturn(mappedBrand);
        when(brandService.update(mappedBrand, brandId)).thenReturn(updatedBrand);
        when(brandMapper.toBrandDTO(updatedBrand)).thenReturn(responseBrandDTO);

        // Then
        mockMvc.perform(put("/brands/{id}", brandId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputBrandDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.branId").value(brandId))
                .andExpect(jsonPath("$.brandName").value("Samsung"));

        verify(brandMapper).toBrand(inputBrandDTO);
        verify(brandService).update(mappedBrand, brandId);
        verify(brandMapper).toBrandDTO(updatedBrand);
    }

    @Test
    public void testUpdateBrand_InvalidJson() throws Exception {
        // Given
        Integer brandId = 1;
        String invalidJson = "{ invalid json }";

        // When & Then
        mockMvc.perform(put("/brands/{id}", brandId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateBrand_EmptyBody() throws Exception {
        // Given
        Integer brandId = 1;

        // When & Then
        mockMvc.perform(put("/brands/{id}", brandId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk()); // Assuming empty body is handled gracefully
    }



    @Test
    public void testDeleteBrand() throws Exception {
        // Given
        Integer brandId = 1;

        Brand deletedBrand = new Brand();
        deletedBrand.setName("Nokia");
        deletedBrand.setId(1);

        BrandDTO outputBrandDTO = new BrandDTO();
        outputBrandDTO.setBrandName("Nokia");
        outputBrandDTO.setBranId(1);

        // Mock behavior - Only mock what's actually called
        when(brandService.DeleteById(eq(brandId))).thenReturn(deletedBrand);
        when(brandMapper.toBrandDTO(any(Brand.class))).thenReturn(outputBrandDTO);

        // When & Then
        mockMvc.perform(delete("/brands/{id}", brandId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.branId").value(1))
                .andExpect(jsonPath("$.brandName").value("Nokia"));

        // Verify only the methods that are actually called
        verify(brandService).DeleteById(brandId);
        verify(brandMapper).toBrandDTO(any(Brand.class));
        // Don't verify brandMapper.toBrand() since it's not called in delete operation
    }

    @Test
    public void testGetBrands_EmptyParams_ReturnsAllBrands() throws Exception {
        // Given
        List<BrandDTO> allBrands = Arrays.asList(
                createBrandDTO(1, "Nokia"),
                createBrandDTO(2, "Samsung"),
                createBrandDTO(3, "Apple")
        );

        // Mock behavior
        when(brandService.GetAllBrands()).thenReturn(allBrands);

        // When & Then
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

        // Mock behavior
        when(brandService.getBrands(any(Map.class))).thenReturn(brandPage);
        when(brandMapper.toBrandDTO(brand1)).thenReturn(brandDTO1);
        when(brandMapper.toBrandDTO(brand2)).thenReturn(brandDTO2);

        // When & Then
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
        // Given
        Page<Brand> emptyPage = Page.empty();

        // Mock behavior
        when(brandService.getBrands(any(Map.class))).thenReturn(emptyPage);

        // When & Then
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

    // Helper method to create BrandDTO
    private BrandDTO createBrandDTO(Integer id, String name) {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setBranId(id);
        brandDTO.setBrandName(name);
        return brandDTO;
    }

    @Test
    public void getBrandById() throws Exception {
        Brand brand = new Brand();
        brand.setId(1);
        brand.setName("iPhone");

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setBranId(1);
        brandDTO.setBrandName("iPhone");

        when(brandMapper.toBrandDTO(brand)).thenReturn(brandDTO);
        when(brandService.getBrandId(any(Integer.class))).thenReturn(brand);

        mockMvc.perform(get("/brands/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.branId").value(1))
                .andExpect(jsonPath("$.brandName").value("iPhone"));
        verify(brandService).getBrandId(any(Integer.class));
    }
}