package com.example.demo.service;

import com.example.demo.dto.DepositoDTO;
import com.example.demo.entity.Agencia;
import com.example.demo.repository.AgenciaRepository;
import com.example.demo.repository.BancoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgenciaService {
    private AgenciaRepository agenciaRepository;
    private BancoRepository bancoRepository;
    private ContaService contaService;

    public AgenciaService(AgenciaRepository agenciaRepository, BancoRepository bancoRepository, ContaService contaService) {
        this.agenciaRepository = agenciaRepository;
        this.bancoRepository = bancoRepository;
        this.contaService = contaService;
    }

    public Agencia create(Agencia agencia) throws Exception {
        try {
            var banco = bancoRepository.findById(agencia.getIdBanco());
            if (banco.isPresent()) {
                agenciaRepository.save(agencia);
                return agencia;
            } else {
                throw new Exception("Banco não encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Agencia já existente");
        }
    }

    public List<Agencia> read() {
        return agenciaRepository.findAll();
    }

    public Optional<Agencia> readById(Integer id) {
        return agenciaRepository.findById(id);
    }

    public Agencia update(Integer id, Agencia agencia) {
        agenciaRepository.findById(id).ifPresentOrElse((existingAgencia) -> {
            bancoRepository.findById(agencia.getIdBanco()).ifPresentOrElse((existingBanco) -> {
                agencia.setId(id);
                agenciaRepository.save(agencia);
            }, () -> {
                try {
                    throw new Exception("Banco com o id %d não existe".formatted(agencia.getIdBanco()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }, () -> {
            try {
                throw new Exception("Banco com o id %d não existe".formatted(id));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return agencia;
    }

    public void delete(Integer id) {
        agenciaRepository.findById(id).ifPresentOrElse((existingBanco) -> {
            agenciaRepository.deleteById(id);
        }, () -> {
            try {
                throw new Exception("Agência com o id %d não encontrado".formatted(id));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deposito(DepositoDTO depositoDTO) {
        var conta = contaService.readById(depositoDTO.numConta());

        conta.ifPresentOrElse((present) -> {
            try {
                present.setSaldo(present.getSaldo() + depositoDTO.valor());
                contaService.update(present.getNumeroConta(), present);
            } catch (Exception e) {
                try {
                    throw new Exception("Conta com o id %d não encontrada".formatted(depositoDTO.numConta()));
                } catch (Exception ex) {
                    throw new RuntimeException();
                }
            }
        }, () -> {
            try {
                throw new Exception("Conta " + depositoDTO.numConta() + " não encontrada");
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
    }
}
