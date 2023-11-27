package com.example.demo.controller;

import com.example.demo.entity.Agencia;
import com.example.demo.service.AgenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agencia")
public class AgenciaController {
    @Autowired
    private AgenciaService agenciaService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Agencia agencia) {
        try {
            var response = agenciaService.create(agencia);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/read")
    public ResponseEntity read() {
        var response = agenciaService.read();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody Agencia agencia) {
        try {
            var response = agenciaService.update(id, agencia);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        try {
            agenciaService.delete(id);
            return ResponseEntity.ok().body("Agência removida com sucesso");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Agencia com o id %d não encontrado".formatted(id));
        }
    }
}
