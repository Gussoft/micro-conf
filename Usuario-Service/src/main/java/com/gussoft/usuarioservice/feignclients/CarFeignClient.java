package com.gussoft.usuarioservice.feignclients;

import com.gussoft.usuarioservice.models.dto.Carro;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Carro-Service")//, url = "http://localhost:9092")
@RequestMapping("/carros")
public interface CarFeignClient {

    @PostMapping
    Carro saveCar(@RequestBody Carro carro);

    @GetMapping("/usuarios/{usuarioId}")
    List<Carro> getCarros(@PathVariable("usuarioId") int userId);

}
