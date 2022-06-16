package com.gussoft.usuarioservice.feignclients;

import com.gussoft.usuarioservice.models.dto.Moto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "moto-service", url = "http://localhost:9093")
@RequestMapping("/motos")
public interface BikeFeignClient {

    @PostMapping
    Moto saveBike(@RequestBody Moto moto);

    @GetMapping("/usuarios/{usuarioId}")
    List<Moto> getMotos(@PathVariable("usuarioId") int userId);
}
