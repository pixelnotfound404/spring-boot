package com.kunthea.phoneshop.controller;

import com.kunthea.phoneshop.Mapper.ModelsMapper;
import com.kunthea.phoneshop.dto.ModelDTO;
import com.kunthea.phoneshop.entity.Model;
import com.kunthea.phoneshop.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("models")
public class ModelController {

    private  final ModelService modelService;
    private  final ModelsMapper modelEntityMapper;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createModel(@RequestBody ModelDTO modelDTO) {
        Model model = modelEntityMapper.toModel(modelDTO);
        model = modelService.save(model);
        return ResponseEntity.ok().body(model);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getModel(@PathVariable Integer id){
        Model model = modelService.getModelById(id);
        return ResponseEntity.ok(modelEntityMapper.toModelDTO(model));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateModel(@PathVariable("id") Integer id, @RequestBody ModelDTO modelDTO){
        Model updateModel = modelEntityMapper.toModel(modelDTO);
        updateModel = modelService.update(updateModel, id);
        return ResponseEntity.ok(modelEntityMapper.toModelDTO(updateModel));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteModel(@PathVariable("id") Integer id){
        Model DeleteModel = modelService.deleteById(id);
        ModelDTO modelDTO = modelEntityMapper.toModelDTO(DeleteModel);
        return ResponseEntity.ok(modelDTO);
    }

    @GetMapping
    public ResponseEntity<?> getAllModels(){
        List<ModelDTO> getAllModels = modelService.getAllModels();
        return ResponseEntity.ok().body(getAllModels);
    }
}
