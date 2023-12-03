package com.example.demo.service;

import com.example.demo.dto.*;
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

    public Double saldo(SaldoDTO saldoDTO){
        var agencia = agenciaService.readById(saldoDTO.numAgencia());

        if(agencia.isPresent()){
            try{
                return agenciaService.saldo(saldoDTO);
            }catch(Exception e){
                try {
                    throw new Exception("Erro ao verificar saldo");
                } catch (Exception ex) {
                    throw new RuntimeException();
                }

            }
        }

        try {
            throw new Exception("Agencia " + saldoDTO.numAgencia() + " não encontrada");
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void saque(SaqueDTO saqueDTO) {
        var agencia = agenciaService.readById(saqueDTO.numAgencia());

        agencia.ifPresentOrElse((present) -> {
            try {
                agenciaService.saque(saqueDTO);
            } catch (Exception e) {
                try {
                    throw new Exception("Erro ao realizar saque");
                } catch (Exception ex) {
                    throw new RuntimeException();
                }
            }
        }, () -> {
            try {
                throw new Exception("Agencia " + saqueDTO.numAgencia() + " não encontrada");
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
    }

    public Double taxaJuros(TaxaJurosDTO taxaJurosDTO){
        var agencia = agenciaService.readById(taxaJurosDTO.numAgencia());

        if(agencia.isPresent()){
            try{
                return agenciaService.taxaJuros(taxaJurosDTO);
            }catch(Exception e){
                try {
                    throw new Exception("Erro ao verificar taxa");
                } catch (Exception ex) {
                    throw new RuntimeException();
                }

            }
        }

        try {
            throw new Exception("Agencia " + taxaJurosDTO.numAgencia() + " não encontrada");
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public Double calcularJuros(CalcularJurosDTO calcularJurosDTO) throws Exception {
        var agencia = agenciaService.readById(calcularJurosDTO.numAgencia());

        if (agencia.isPresent()) {
            try {
                return agenciaService.calcularJuros(calcularJurosDTO);
            } catch (Exception e) {
                try {
                    throw new Exception("Erro ao calcular taxa de juros");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else {
            throw new Exception("Agência com o id %d não encontrada".formatted(calcularJurosDTO.numAgencia()));
        }
    }

    public Double transferencia(TransferenciaDTO transferenciaDTO) throws Exception {
        var agenciaOrigem = agenciaService.readById(transferenciaDTO.numAgenciaOrigem());
        var agenciaDestino = agenciaService.readById(transferenciaDTO.numAgenciaDestino());

        if (agenciaOrigem.isPresent() && agenciaDestino.isPresent()) {
            var saque = new SaqueDTO(transferenciaDTO.numAgenciaOrigem(), transferenciaDTO.numContaOrigem(), transferenciaDTO.valor());
            var deposito = new DepositoDTO(transferenciaDTO.numAgenciaDestino(), transferenciaDTO.numContaDestino(), transferenciaDTO.valor());

            agenciaService.saque(saque);
            agenciaService.deposito(deposito);

            return transferenciaDTO.valor();
        } else {
            throw new Exception("Uma das agências não pode ser encontrada");
        }
    }
}
