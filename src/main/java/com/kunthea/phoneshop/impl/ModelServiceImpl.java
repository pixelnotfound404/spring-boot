package com.kunthea.phoneshop.impl;

import com.kunthea.phoneshop.Mapper.ModelsMapper;
import com.kunthea.phoneshop.dto.ModelDTO;
import com.kunthea.phoneshop.entity.Model;
import com.kunthea.phoneshop.exception.ApiException;
import com.kunthea.phoneshop.repository.ModelRepository;
import com.kunthea.phoneshop.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {
    private final ModelRepository modelRepository;
    private final ModelsMapper modelEntityMapper;


    @Override
    public Model save(Model model) {
        return modelRepository.save(model);
    }

    @Override
    public Model getModelById(Integer id) {
        return modelRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Model not found with ID: " + id));
    }

    @Override
    public Model update(Model modelUpdate, Integer id) {
        Model model = getModelById(id);
        model.setName(modelUpdate.getName());
        return modelRepository.save(model);
    }

    @Override
    public Model deleteById(Integer id) {
        Model model = getModelById(id);
        modelRepository.delete(model);
        return model;
    }

    @Override
    public List<ModelDTO> getAllModels() {
        List<Model> models = modelRepository.findAll();
        return models.stream()
                .map(modelEntityMapper::toModelDTO)
                .collect(Collectors.toList());
    }
}
