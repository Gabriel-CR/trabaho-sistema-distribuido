package com.example.demo.service;

import com.example.demo.entity.Conta;
import com.example.demo.repository.AgenciaRepository;
import com.example.demo.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {
    private ContaRepository contaRepository;
    private AgenciaRepository agenciaRepository;

    public ContaService(ContaRepository contaRepository, AgenciaRepository agenciaRepository) {
        this.contaRepository = contaRepository;
        this.agenciaRepository = agenciaRepository;
    }

    public Conta create(Conta conta) throws Exception {
        try {
            var agencia = agenciaRepository.findById(conta.getIdAgencia());
            if (agencia.isPresent()) {
                contaRepository.save(conta);
                return conta;
            } else {
                throw new Exception("Agencia não encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Conta já existente");
        }
    }

    public List<Conta> read() {
        return contaRepository.findAll();
    }

    public Optional<Conta> readById(Integer id) {
        return contaRepository.findById(id);
    }

    public Conta update(Integer numeroConta, Conta conta) {
        contaRepository.findById(numeroConta).ifPresentOrElse((existingConta) -> {
            agenciaRepository.findById(conta.getIdAgencia()).ifPresentOrElse((existingAgencia) -> {
                conta.setNumeroConta(numeroConta);
                contaRepository.save(conta);
            }, () -> {
                try {
                    throw new Exception("Agencia com o id %d não existe".formatted(conta.getIdAgencia()));
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            });
        }, () -> {
            try {
                throw new Exception("Conta com o número %d não existe".formatted(numeroConta));
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });

        return conta;
    }

    public void delete(Integer numero) {
        contaRepository.findById(numero).ifPresentOrElse((existingBanco) -> {
            contaRepository.deleteById(numero);
        }, () -> {
            try {
                throw new Exception("Conta com o número %d não encontrado".formatted(numero));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
