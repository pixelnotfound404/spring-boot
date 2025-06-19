package com.kunthea.phoneshop.repository;

import com.kunthea.phoneshop.entity.Brand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class BrandRepositoryTest {

    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void findByNameContaining() {
        //Given
        Brand brand = new Brand();
        brand.setName("Nokia");

        brandRepository.save(brand);

        //when
        List<Brand> brands = brandRepository.findByNameContaining("N");

        //then
        assertEquals(1, brands.size());
        assertEquals("Nokia", brands.get(0).getName());
    }
}
