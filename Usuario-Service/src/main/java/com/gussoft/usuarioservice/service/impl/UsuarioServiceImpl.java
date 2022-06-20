package com.gussoft.usuarioservice.service.impl;

import com.gussoft.usuarioservice.feignclients.BikeFeignClient;
import com.gussoft.usuarioservice.feignclients.CarFeignClient;
import com.gussoft.usuarioservice.models.Usuario;
import com.gussoft.usuarioservice.models.dto.Carro;
import com.gussoft.usuarioservice.models.dto.Moto;
import com.gussoft.usuarioservice.repository.UsuarioRepository;
import com.gussoft.usuarioservice.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private RestTemplate template;

    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private CarFeignClient cfClient;

    @Autowired
    private BikeFeignClient bfClient;

    @Override
    public List<Usuario> getAll() {
        return repo.findAll();
    }

    @Override
    public Usuario getUserById(int id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Usuario saved(Usuario obj) {
        Usuario user = repo.save(obj);
        return user;
    }

    //Core de Microservicos
    @Override
    public List<Carro> getCars(int usuarioId) {
        List<Carro> obj = template.getForObject("http://carro-service/carros/usuarios/" + usuarioId, List.class); //localhost:9092
        return obj;
    }

    @Override
    public List<Moto> getBikes(int usuarioId) {
        List<Moto> obj = template.getForObject("http://moto-service/motos/usuarios/" + usuarioId, List.class); //localhost:9093
        return obj;
    }

    @Override
    public Carro saveCar(int userId, Carro obj) {
        obj.setUsuarioId(userId);
        Carro car = cfClient.saveCar(obj);
        return car;
    }

    @Override
    public Moto saveBike(int userId, Moto obj) {
        obj.setUsuarioId(userId);
        Moto bike = bfClient.saveBike(obj);
        return bike;
    }

    @Override
    public Map<String, Object> getUserAndVehicles(int userId) {
        Map<String, Object> result = new HashMap<>();
        Usuario usuario = repo.findById(userId).orElse(null);
        if (usuario == null) {
            result.put("Message", "El usuario no existe!");
            return result;
        }
        result.put("Usuario", usuario);
        List<Carro> carros = cfClient.getCarros(userId);
        if (carros == null) {
            result.put("Carros", "El usuario no tiene carros.");
        } else {
            result.put("Carros", carros);
        }
        List<Moto> motos = bfClient.getMotos(userId);
        if (motos == null) {
            result.put("Motos", "El usuario no tiene Motos.");
        } else {
            result.put("Motos", motos);
        }
        return result;
    }
}
