package com.gussoft.motoservice.controller;

import com.gussoft.motoservice.models.Moto;
import com.gussoft.motoservice.service.impl.MotoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/motos")
public class MotoController {
    @Autowired
    private MotoServiceImpl service;

    @GetMapping
    public ResponseEntity<List<Moto>> listBikes(){
        List<Moto> list = service.getAll();
        if (list.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> getBike(@PathVariable("id") int id){
        Moto obj = service.getMotoById(id);
        if (obj == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<Moto> savedBike(@RequestBody Moto obj){
        Moto data = service.saved(obj);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(data.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<List<Moto>> listBikesForUsers(@PathVariable ("usuarioId") int id){

        List<Moto> obj = service.byUsuarioId(id);
        if (obj.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(obj);
    }
}
