package com.kunthea.phoneshop.controller;


import com.kunthea.phoneshop.Mapper.BrandMapper;
import com.kunthea.phoneshop.Mapper.ColorsMapper;
import com.kunthea.phoneshop.dto.ColorDTO;
import com.kunthea.phoneshop.entity.Colors;
import com.kunthea.phoneshop.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("colors")
@RequiredArgsConstructor
public class ColorsController {
    private final ColorsMapper colorsEntityMapper;
    private final ColorService colorService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createColor(@RequestBody ColorDTO colorsDTO) {
        Colors colors = colorsEntityMapper.toColor(colorsDTO);
        colors = colorService.create(colors);
        return ResponseEntity.ok(colorsEntityMapper.toDTO(colors));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getColorById(@PathVariable Integer id) {
        Colors colors = colorService.getColorId(id);
        return ResponseEntity.ok(colorsEntityMapper.toDTO(colors));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateColor(@PathVariable Integer id, @RequestBody ColorDTO colorsDTO) {
        Colors Updatecolor = colorsEntityMapper.toColor(colorsDTO);
        Updatecolor=colorService.update(Updatecolor, id);
        return ResponseEntity.ok(colorsEntityMapper.toDTO(Updatecolor));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteColor(@PathVariable("id") Integer id) {
        Colors colors = colorService.deleteById(id);
        ColorDTO colorDTO = colorsEntityMapper.toDTO(colors);
        return  ResponseEntity.ok(colorDTO);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllColors() {
        List<ColorDTO> getAllColors= colorService.getAllColor();
        return ResponseEntity.ok(getAllColors);
    }
}
