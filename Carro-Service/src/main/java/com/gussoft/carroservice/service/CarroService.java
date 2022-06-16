package com.gussoft.carroservice.service;

import com.gussoft.carroservice.models.Carro;

import java.util.List;

public interface CarroService {

    List<Carro> getAll();

    Carro getCarroById(int id);

    Carro saved(Carro obj);

    List<Carro> byUsuarioId(int usuarioId);

}
