package com.gussoft.motoservice.service.impl;

import com.gussoft.motoservice.models.Moto;
import com.gussoft.motoservice.repository.MotoRepository;
import com.gussoft.motoservice.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoServiceImpl implements MotoService {

    @Autowired
    private MotoRepository repo;

    @Override
    public List<Moto> getAll() {
        return repo.findAll();
    }

    @Override
    public Moto getMotoById(int id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Moto saved(Moto obj) {
        Moto data = repo.save(obj);
        return data;
    }

    @Override
    public List<Moto> byUsuarioId(int usuarioId) {
        return repo.findByUsuarioId(usuarioId);
    }
}
