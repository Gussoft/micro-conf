package com.gussoft.carroservice.controller;

import com.gussoft.carroservice.models.Carro;
import com.gussoft.carroservice.service.CarroService;
import com.gussoft.carroservice.service.impl.CarroServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/carros")
public class CarroController {

    @Autowired
    private CarroServiceImpl service;

    @GetMapping
    public ResponseEntity<List<Carro>> listCars(){
        List<Carro> list = service.getAll();
        if (list.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carro> getCars(@PathVariable("id") int id){
        Carro obj = service.getCarroById(id);
        if (obj == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<Carro> savedCar(@RequestBody Carro obj){
        Carro data = service.saved(obj);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(data.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<List<Carro>> listCarsForUsers(@PathVariable ("usuarioId") int id){

        List<Carro> obj = service.byUsuarioId(id);
        if (obj.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(obj);
    }
}
