package com.example.demo.service;

import com.example.demo.dto.DepositoDTO;
import com.example.demo.entity.Banco;
import com.example.demo.repository.BancoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BancoService {
    private BancoRepository bancoRepository;
    private AgenciaService agenciaService;

    public BancoService(BancoRepository bancoRepository, AgenciaService agenciaService) {
        this.bancoRepository = bancoRepository;
        this.agenciaService = agenciaService;
    }

    public Banco create(Banco banco) {
        // TODO: buscar se já tem o nome antes de criar
        bancoRepository.save(banco);
        return banco;
    }

    public List<Banco> read() {
        return bancoRepository.findAll();
    }

    public Optional<Banco> readById(Integer id) {
        return bancoRepository.findById(id);
    }

    public Banco update(Integer id, Banco banco) {
        bancoRepository.findById(id).ifPresentOrElse((existingBanco) -> {
            banco.setId(id);
            bancoRepository.save(banco);
        }, () -> {
            try {
                throw new Exception("Banco com o id %d não existe".formatted(id));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return banco;
    }

    public void delete(Integer id) {
        bancoRepository.findById(id).ifPresentOrElse((existingBanco) -> {
            bancoRepository.deleteById(id);
        }, () -> {
            try {
                throw new Exception("Banco com o id %d não encontrado".formatted(id));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deposito(DepositoDTO depositoDTO) {
        var agencia = agenciaService.readById(depositoDTO.numAgencia());

        agencia.ifPresentOrElse((present) -> {
            try {
                agenciaService.deposito(depositoDTO);
            } catch (Exception e) {
                try {
                    throw new Exception("Erro ao realizar depósito");
                } catch (Exception ex) {
                    throw new RuntimeException();
                }
            }
        }, () -> {
            try {
                throw new Exception("Agencia " + depositoDTO.numAgencia() + " não encontrada");
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
    }
}
