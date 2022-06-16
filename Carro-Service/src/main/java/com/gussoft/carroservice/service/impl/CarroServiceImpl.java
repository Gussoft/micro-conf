package com.gussoft.carroservice.service.impl;

import com.gussoft.carroservice.models.Carro;
import com.gussoft.carroservice.repository.CarroRepository;
import com.gussoft.carroservice.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroServiceImpl implements CarroService {

    @Autowired
    private CarroRepository repo;

    @Override
    public List<Carro> getAll() {
        return repo.findAll();
    }

    @Override
    public Carro getCarroById(int id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Carro saved(Carro obj) {
        Carro data = repo.save(obj);
        return data;
    }

    @Override
    public List<Carro> byUsuarioId(int usuarioId) {
        return repo.findByUsuarioId(usuarioId);
    }
}
