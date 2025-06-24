package com.kunthea.phoneshop.services;

import com.kunthea.phoneshop.Mapper.BrandMapper;
import com.kunthea.phoneshop.Spec.BrandSpec;
import com.kunthea.phoneshop.dto.BrandDTO;
import com.kunthea.phoneshop.entity.Brand;
import com.kunthea.phoneshop.impl.BrandServiceImpl;
import com.kunthea.phoneshop.repository.BrandRepository;
import com.kunthea.phoneshop.service.BrandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BrandServices {
    @Mock
    private BrandRepository brandRepository;

    @Mock
    private BrandMapper brandMapper;


    @InjectMocks
    private BrandServiceImpl brandService;

    @Captor
    private ArgumentCaptor<BrandSpec> specCaptor;

    @Captor
    private ArgumentCaptor<Pageable> pageableCaptor;

    @Test
    public void testCreate(){
        Brand brand = new Brand();
        brand.setName("Huawei");
        brand.setId(1);

        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        Brand brandReturn = brandService.create(new Brand());

        assertEquals(1, brandReturn.getId());
        assertEquals("Huawei", brandReturn.getName());
    }

    @Test
    public void getId(){
        Brand brand = new Brand();
        brand.setName("Huawei");
        brand.setId(1);

        when(brandRepository.findById(any(Integer.class))).thenReturn(Optional.of(brand));
        Brand result = brandService.getBrandId(1);

        assertEquals("Huawei", result.getName());
    }

    @Test
    public void testUpdate(){
        Brand brand = new Brand();
        brand.setName("Huawei");
        brand.setId(1);

        Brand BrandUpdate = new Brand();
        BrandUpdate.setName("Apple");
        BrandUpdate.setId(1);

        when(brandRepository.findById(any(Integer.class))).thenReturn(Optional.of(brand));

        when(brandRepository.save(any(Brand.class))).thenReturn(BrandUpdate);

        Brand result = brandService.update(BrandUpdate,1);
        assertEquals("Apple",result.getName());

        verify(brandRepository).save(brand);
    }

    @Test
    public void testDeleteById(){
        Brand brand = new Brand();
        brand.setName("Huawei");
        brand.setId(1);


        when(brandRepository.findById(any(Integer.class))).thenReturn(Optional.of(brand));


        Brand result=brandService.DeleteById(1);
        assertEquals("Huawei",result.getName());
        verify(brandRepository).delete(brand);
    }

    @Test
    public void testGetAllBrand() {
        // Arrange
        Brand brand = new Brand();
        brand.setName("Huawei");
        brand.setId(1);

        Brand brand2 = new Brand();
        brand2.setName("Apple");
        brand2.setId(2);

        List<Brand> brandList = List.of(brand, brand2);

        BrandDTO dto1 = new BrandDTO();
        dto1.setBranId(1);
        dto1.setBrandName("Huawei");

        BrandDTO dto2 = new BrandDTO();
        dto2.setBranId(2);
        dto2.setBrandName("Apple");

        when(brandRepository.findAll()).thenReturn(brandList);
        when(brandMapper.toBrandDTO(brand)).thenReturn(dto1);
        when(brandMapper.toBrandDTO(brand2)).thenReturn(dto2);


        List<BrandDTO> result = brandService.GetAllBrands();


        assertEquals(2, result.size());


        assertEquals(1, result.get(0).getBranId()); // Fixed: changed from getBranId to getBrandId
        assertEquals("Huawei", result.get(0).getBrandName());

        assertEquals(2, result.get(1).getBranId()); // Fixed: changed from getBranId to getBrandId
        assertEquals("Apple", result.get(1).getBrandName());

        verify(brandRepository).findAll();
        verify(brandMapper).toBrandDTO(brand);
        verify(brandMapper).toBrandDTO(brand2);
    }

    @Test
    void testGetBrands_WithValidParameters() {
        // Arrange
        Map<String, String> params = new HashMap<>();
        params.put("name", "Apple");
        params.put("id", "1");
        params.put("pageLimit", "5");
        params.put("pageNumber", "0");

        Brand brand = new Brand();
        brand.setId(1);
        brand.setName("Apple");

        Page<Brand> expectedPage = new PageImpl<>(List.of(brand), PageRequest.of(0, 5), 1);

        when(brandRepository.findAll(any(BrandSpec.class), any(Pageable.class)))
                .thenReturn(expectedPage);

        // Act
        Page<Brand> result = brandService.getBrands(params);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(5, result.getSize());
        assertEquals(0, result.getNumber());
        assertEquals("Apple", result.getContent().get(0).getName());

        // Verify repository was called with correct parameters
        verify(brandRepository).findAll(specCaptor.capture(), pageableCaptor.capture());

        Pageable capturedPageable = pageableCaptor.getValue();
        assertEquals(0, capturedPageable.getPageNumber());
        assertEquals(10, capturedPageable.getPageSize());
    }

    @Test
    void testGetBrands_WithDefaultPagination() {
        // Arrange
        Map<String, String> params = new HashMap<>();
        params.put("name", "Samsung");

        Brand brand = new Brand();
        brand.setName("Samsung");

        Page<Brand> expectedPage = new PageImpl<>(List.of(brand), PageRequest.of(0, 10), 1);

        when(brandRepository.findAll(any(BrandSpec.class), any(Pageable.class)))
                .thenReturn(expectedPage);

        // Act
        Page<Brand> result = brandService.getBrands(params);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());

        verify(brandRepository).findAll(any(BrandSpec.class), pageableCaptor.capture());

        Pageable capturedPageable = pageableCaptor.getValue();
        assertEquals(0, capturedPageable.getPageNumber()); // Default page number
        assertEquals(10, capturedPageable.getPageSize()); // Default page limit
    }

    @Test
    void testGetBrands_WithEmptyParameters() {
        // Arrange
        Map<String, String> params = new HashMap<>();

        Page<Brand> expectedPage = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);

        when(brandRepository.findAll(any(BrandSpec.class), any(Pageable.class)))
                .thenReturn(expectedPage);

        // Act
        Page<Brand> result = brandService.getBrands(params);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());

        verify(brandRepository).findAll(any(BrandSpec.class), any(Pageable.class));
    }

    @Test
    void testGetBrands_WithWhitespaceParameters() {
        // Arrange
        Map<String, String> params = new HashMap<>();
        params.put("name", "   "); // Only whitespace
        params.put("id", "  "); // Only whitespace
        params.put("pageLimit", "10");

        Page<Brand> expectedPage = new PageImpl<>(List.of(), PageRequest.of(0, 15), 0);

        when(brandRepository.findAll(any(BrandSpec.class), any(Pageable.class)))
                .thenReturn(expectedPage);

        // Act
        Page<Brand> result = brandService.getBrands(params);

        // Assert
        assertNotNull(result);
        verify(brandRepository).findAll(any(BrandSpec.class), pageableCaptor.capture());

        Pageable capturedPageable = pageableCaptor.getValue();
        assertEquals(10, capturedPageable.getPageSize());
    }

    @Test
    void testGetBrands_WithInvalidIdParameter() {
        // Arrange
        Map<String, String> params = new HashMap<>();
        params.put("id", "invalid_number");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> brandService.getBrands(params)
        );

        assertEquals("Invalid numeric parameter provided", exception.getMessage());
        assertNotNull(exception.getCause());
        assertTrue(exception.getCause() instanceof NumberFormatException);
    }


    @Test
    void testGetBrands_WithNullParameterMap() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            brandService.getBrands(null);
        });
    }

    @Test
    void testGetBrands_RepositoryException() {
        // Arrange
        Map<String, String> params = new HashMap<>();
        params.put("name", "Test");

        when(brandRepository.findAll(any(BrandSpec.class), any(Pageable.class)))
                .thenThrow(new RuntimeException("Database connection failed"));

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> brandService.getBrands(params)
        );

        assertEquals("Database connection failed", exception.getMessage());
    }
}
