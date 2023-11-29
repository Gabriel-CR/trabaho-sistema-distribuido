package com.example.demo.controller;

import com.example.demo.dto.DepositoDTO;
import com.example.demo.entity.Banco;
import com.example.demo.service.BancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banco")
public class BancoController {
    @Autowired
    private BancoService bancoService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Banco banco) {
        try {
            var response = bancoService.create(banco);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/read")
    public ResponseEntity read() {
        var response = bancoService.read();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody Banco banco) {
        try {
            var response = bancoService.update(id, banco);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Banco com o id %d não encontrado".formatted(id));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        try {
            bancoService.delete(id);
            return ResponseEntity.ok().body("Banco removido com sucesso");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Banco com o id %d não encontrado".formatted(id));
        }
    }

    @PostMapping("/deposito/{id}")
    public ResponseEntity deposito(@PathVariable Integer id, @RequestBody DepositoDTO depositoDTO) {
        var banco = bancoService.readById(id);

        if (banco.isPresent()) {
            try {
                bancoService.deposito(depositoDTO);
                return ResponseEntity.ok().body("Depósito realizado com sucesso");
            } catch (Exception e) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Erro ao realizar depósito\n" + e.getMessage());
            }
        }

        return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Banco com o id %d não encontrado".formatted(id));
    }
}
