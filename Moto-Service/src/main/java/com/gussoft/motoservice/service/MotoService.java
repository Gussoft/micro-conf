package com.gussoft.motoservice.service;

import com.gussoft.motoservice.models.Moto;

import java.util.List;

public interface MotoService {

    List<Moto> getAll();

    Moto getMotoById(int id);

    Moto saved(Moto obj);

    List<Moto> byUsuarioId(int usuarioId);

}
