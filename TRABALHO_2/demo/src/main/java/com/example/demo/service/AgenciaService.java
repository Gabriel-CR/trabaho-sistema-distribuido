package com.example.demo.service;

import com.example.demo.entity.Agencia;
import com.example.demo.repository.AgenciaRepository;
import com.example.demo.repository.BancoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgenciaService {
    private AgenciaRepository agenciaRepository;
    private BancoRepository bancoRepository;

    public AgenciaService(AgenciaRepository agenciaRepository, BancoRepository bancoRepository) {
        this.agenciaRepository = agenciaRepository;
        this.bancoRepository = bancoRepository;
    }

    public Agencia create(Agencia agencia) throws Exception {
        try {
            try {
                var banco = bancoRepository.findById(agencia.getBanco().getId());
                if (banco.isPresent()) {
                    agenciaRepository.save(agencia);
                    return agencia;
                }
            } catch (Exception e) {
                throw new Exception("Banco não encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Agencia já existente");
        }

        return agencia;
    }

    public List<Agencia> read() {
        return agenciaRepository.findAll();
    }
}
