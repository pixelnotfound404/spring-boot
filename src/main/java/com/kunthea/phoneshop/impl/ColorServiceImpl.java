package com.kunthea.phoneshop.impl;

import com.kunthea.phoneshop.Mapper.ColorsMapper;
import com.kunthea.phoneshop.dto.ColorDTO;
import com.kunthea.phoneshop.entity.Colors;
import com.kunthea.phoneshop.exception.ApiException;
import com.kunthea.phoneshop.repository.ColorRepository;
import com.kunthea.phoneshop.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;
    private final ColorsMapper colorEntityMapper;


    @Override
    public Colors create(Colors color) {
        return colorRepository.save(color);
    }

    @Override
    public Colors getColorId(Integer id) {
        return  colorRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Color not found with ID: " + id));
    }

    @Override
    public Colors update(Colors colorUpdate, Integer id) {
        Colors color = getColorId(id);
        color.setName(colorUpdate.getName());
        return colorRepository.save(color);
    }

    @Override
    public Colors deleteById(Integer id) {
        Colors color = getColorId(id);
        colorRepository.deleteById(id);
        return color;
    }

    @Override
    public List<ColorDTO> getAllColor() {
        List<Colors> colors = colorRepository.findAll();
        return colors.stream()
                .map(colorEntityMapper::toDTO)
                .collect(Collectors.toList());
    }
}
