package com.gussoft.usuarioservice.controller;

import com.gussoft.usuarioservice.models.Usuario;
import com.gussoft.usuarioservice.models.dto.Carro;
import com.gussoft.usuarioservice.models.dto.Moto;
import com.gussoft.usuarioservice.service.impl.UsuarioServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl service;

    @GetMapping
    public ResponseEntity<List<Usuario>> listUsers(){
        List<Usuario> list = service.getAll();
        if (list.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUser(@PathVariable("id") int id){
        Usuario obj = service.getUserById(id);
        if (obj == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<Usuario> savedUser(@RequestBody Usuario obj){
        Usuario data = service.saved(obj);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(data.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    //comunicacion de MicroServicios para enlazar las rutas
    //Implementando Circuit Breaker
    @CircuitBreaker(name = "carrosCB", fallbackMethod = "fallbackGetCars")
    @GetMapping("/carros/{usuarioId}")
    public ResponseEntity<List<Carro>> listCars(@PathVariable("usuarioId") int id){
        Usuario obj = service.getUserById(id);
        if (obj == null)
            return ResponseEntity.notFound().build();
        List<Carro> data = service.getCars(id);
        return ResponseEntity.ok(data);
    }

    //Implementando Circuit Breaker
    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallbackGetBikes")
    @GetMapping("/motos/{usuarioId}")
    public ResponseEntity<List<Moto>> listBikes(@PathVariable("usuarioId") int id){
        Usuario obj = service.getUserById(id);
        if (obj == null)
            return ResponseEntity.notFound().build();
        List<Moto> data = service.getBikes(id);
        return ResponseEntity.ok(data);
    }

    //comunicacion de MicroServicios con OpenFeign
    //Implementando Circuit Breaker
    @CircuitBreaker(name = "carrosCB", fallbackMethod = "fallbackSaveCars")
    @PostMapping("/carro/{usuarioId}")
    public ResponseEntity<Carro> guardarCarro(@PathVariable("usuarioId") int usuarioId, @RequestBody Carro obj) {
        Carro car = service.saveCar(usuarioId, obj);
        return ResponseEntity.ok(car);
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallbackSaveBikes")
    @PostMapping("/moto/{usuarioId}")
    public ResponseEntity<Moto> guardarMoto(@PathVariable("usuarioId") int usuarioId, @RequestBody Moto obj) {
        Moto bike = service.saveBike(usuarioId, obj);
        return ResponseEntity.ok(bike);
    }

    @CircuitBreaker(name = "todosCB", fallbackMethod = "fallbackGetTodos")
    @GetMapping("/all/{usuarioId}")
    public ResponseEntity<Map<String, Object>> listAllVehicles(@PathVariable("usuarioId") int usuarioId) {
        Map<String, Object> vehicles = service.getUserAndVehicles(usuarioId);
        return ResponseEntity.ok(vehicles);
    }

    private ResponseEntity<List<Carro>> fallbackGetCars(@PathVariable("usuarioId") int id, RuntimeException ex) {
        return new ResponseEntity("El usuario " + id + " Tiene los carros en el taller", HttpStatus.OK);
    }

    private ResponseEntity<List<Carro>> fallbackSaveCars(@PathVariable("usuarioId") int id,@RequestBody Carro obj, RuntimeException ex) {
        return new ResponseEntity("El usuario " + id + " No puede pagar el Carro", HttpStatus.OK);
    }

    private ResponseEntity<List<Moto>> fallbackGetBikes(@PathVariable("usuarioId") int id, RuntimeException ex) {
        return new ResponseEntity("El usuario " + id + " Tiene las Motos en el taller", HttpStatus.OK);
    }

    private ResponseEntity<List<Moto>> fallbackSaveBikes(@PathVariable("usuarioId") int id,@RequestBody Moto obj, RuntimeException ex) {
        return new ResponseEntity("El usuario " + id + " No puede pagar la Moto", HttpStatus.OK);
    }

    private ResponseEntity<List<Moto>> fallbackGetTodos(@PathVariable("usuarioId") int id, RuntimeException ex) {
        return new ResponseEntity("El usuario " + id + " Tiene los Vehiculos en el taller", HttpStatus.OK);
    }
}
