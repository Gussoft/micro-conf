package com.gussoft.usuarioservice.service;

import com.gussoft.usuarioservice.models.Usuario;
import com.gussoft.usuarioservice.models.dto.Carro;
import com.gussoft.usuarioservice.models.dto.Moto;

import java.util.List;
import java.util.Map;

public interface UsuarioService {

    List<Usuario> getAll();

    Usuario getUserById(int id);

    Usuario saved(Usuario obj);

    List<Carro> getCars(int usuarioId);

    List<Moto> getBikes(int usuarioId);

    //With OpenFeign
    Carro saveCar(int userId, Carro obj);

    Moto saveBike(int userId, Moto obj);

    Map<String, Object> getUserAndVehicles(int userId);
}
