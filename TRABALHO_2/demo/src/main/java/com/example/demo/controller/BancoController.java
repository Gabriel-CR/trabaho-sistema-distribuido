package com.example.demo.controller;

import com.example.demo.dto.*;
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


    @PostMapping("saldo/{id}")
    public ResponseEntity saldo (@PathVariable Integer id, @RequestBody SaldoDTO saldoDTO){
        var banco = bancoService.readById(id);

        if(banco.isPresent()){
            try{
                var response = bancoService.saldo(saldoDTO);
                return ResponseEntity.ok().body(response);

            }catch (Exception e) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Erro ao verificar saldo\n" + e.getMessage());
            }
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Banco com o id %d não encontrado".formatted(id));
    }

    @PostMapping("saque/{id}")
    public ResponseEntity saque(@PathVariable Integer id, @RequestBody SaqueDTO saqueDTO){
        var banco = bancoService.readById(id);

        if(banco.isPresent()){
            try{
                bancoService.saque(saqueDTO);
                return ResponseEntity.ok().body("Saque realizado com sucesso");

            }catch (Exception e) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Erro ao realizar saque\n" + e.getMessage());
            }
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Banco com o id %d não encontrado".formatted(id));
    }


    @PostMapping("taxajuros/{id}")
    public ResponseEntity taxaJuros (@PathVariable Integer id, @RequestBody TaxaJurosDTO taxaJurosDTO){
        var banco = bancoService.readById(id);

        if(banco.isPresent()){
            try{
                var response = bancoService.taxaJuros(taxaJurosDTO);
                return ResponseEntity.ok().body(response);

            }catch (Exception e) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Erro ao verificar taxa\n" + e.getMessage());
            }
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Banco com o id %d não encontrado".formatted(id));
    }


    @PostMapping("calcularjuros/{id}")
    public ResponseEntity calcularJuros(@PathVariable Integer id, @RequestBody CalcularJurosDTO calcularJurosDTO){
        var banco = bancoService.readById(id);

        if(banco.isPresent()){
            try{
                bancoService.calcularJuros(calcularJurosDTO);
                return ResponseEntity.ok().body("Calculo dos juros realizado com sucesso");

            }catch (Exception e) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Erro ao realizar calculo dos juros\n" + e.getMessage());
            }
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Banco com o id %d não encontrado".formatted(id));
    }

    @PostMapping("transferir/{id}")
    public ResponseEntity transferencia(@PathVariable Integer id, @RequestBody TransferenciaDTO transferenciaDTO){
        var banco = bancoService.readById(id);

        if(banco.isPresent()){
            try{
                bancoService.transferencia(transferenciaDTO);
                return ResponseEntity.ok().body("Transferencia realizada com sucesso");

            }catch (Exception e) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Erro ao realizar transferencia\n" + e.getMessage());
            }
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Banco com o id %d não encontrado".formatted(id));
    }


}
