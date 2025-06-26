package com.kunthea.phoneshop.service;

import com.kunthea.phoneshop.dto.ModelDTO;
import com.kunthea.phoneshop.entity.Model;

import java.util.List;

public interface ModelService {
    Model save(Model model);
    Model getModelById(Integer id);
    Model update(Model modelUpdate, Integer id);
    Model deleteById(Integer id);
    List<ModelDTO> getAllModels();
}
