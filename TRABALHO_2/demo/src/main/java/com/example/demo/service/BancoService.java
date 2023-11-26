package com.example.demo.service;

import com.example.demo.entity.Banco;
import com.example.demo.repository.BancoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BancoService {
    private BancoRepository bancoRepository;

    public BancoService(BancoRepository bancoRepository) {
        this.bancoRepository = bancoRepository;
    }

    public Banco create(Banco banco) {
        // TODO: buscar se já tem o nome antes de criar
        bancoRepository.save(banco);
        return banco;
    }

    public List<Banco> read() {
        return bancoRepository.findAll();
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
}
