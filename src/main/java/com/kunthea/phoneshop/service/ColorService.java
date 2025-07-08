package com.kunthea.phoneshop.service;


import com.kunthea.phoneshop.dto.ColorDTO;
import com.kunthea.phoneshop.entity.Brand;
import com.kunthea.phoneshop.entity.Colors;

import java.awt.*;
import java.util.List;

public interface ColorService {
    Colors create(Colors color);
    Colors getColorId(Integer id);
    Colors update(Colors colorUpdate, Integer id);
    Colors deleteById(Integer id);
    List<ColorDTO> getAllColor();
}
