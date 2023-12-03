package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.Agencia;
import com.example.demo.entity.Conta;
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

    public Double saldo(SaldoDTO saldoDTO) {
        var conta = contaService.readById(saldoDTO.numConta());

        if(conta.isPresent()){
            try{
                return conta.get().getSaldo();
            }catch (Exception e) {
                try {
                    throw new Exception("Conta com o id %d não encontrada".formatted(saldoDTO.numConta()));
                } catch (Exception ex) {
                    throw new RuntimeException();
                }
            }
        }

        try {
            throw new Exception("Conta " + saldoDTO.numConta() + " não encontrada");
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void saque(SaqueDTO saqueDTO) {
        var conta = contaService.readById(saqueDTO.numConta());

        conta.ifPresentOrElse((present) -> {
            try {
                Double valorSacado = present.getSaldo() - saqueDTO.valor();
                if (valorSacado >= 0) {
                    present.setSaldo(valorSacado);
                    contaService.update(present.getNumeroConta(), present);
                } else {
                    throw new Exception("Valor insuficiente");
                }
            } catch (Exception e) {
                try {
                    throw new Exception("Conta com o id %d não encontrada".formatted(saqueDTO.numConta()));
                } catch (Exception ex) {
                    throw new RuntimeException();
                }
            }
        }, () -> {
            try {
                throw new Exception("Conta " + saqueDTO.numConta() + " não encontrada");
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
    }

    public Double taxaJuros(TaxaJurosDTO taxaJurosDTO) {
        var conta = contaService.readById(taxaJurosDTO.numConta());

        if(conta.isPresent()){
            try{
                return conta.get().getTaxaJuros();
            }catch (Exception e) {
                try {
                    throw new Exception("Conta com o id %d não encontrada".formatted(taxaJurosDTO.numConta()));
                } catch (Exception ex) {
                    throw new RuntimeException();
                }
            }
        }

        try {
            throw new Exception("Conta " + taxaJurosDTO.numConta() + " não encontrada");
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public Double calcularJuros(CalcularJurosDTO calcularJurosDTO) throws Exception {
        var conta = contaService.readById(calcularJurosDTO.numConta());

        if (conta.isPresent()) {
            Double valorAtualizado = conta.get().getSaldo() * (1 + conta.get().getTaxaJuros()/100);
            var c = new Conta(conta.get());
            c.setSaldo(valorAtualizado);

            contaService.update(c.getNumeroConta(), c);
            return conta.get().getSaldo();
        } else {
            throw new Exception("Conta não encontrada");
        }
    }

    public void transferencia(TransferenciaDTO transferenciaDTO) {
        var contaOrigem = contaService.readById(transferenciaDTO.numContaOrigem());
        var contaDestino = contaService.readById(transferenciaDTO.numContaDestino());

        contaOrigem.ifPresentOrElse((presentOrigin) -> {
            try {
                contaDestino.ifPresentOrElse((presentDestiny) -> {
                    try {

                        presentOrigin.setSaldo(presentOrigin.getSaldo() - transferenciaDTO.valor());
                        presentDestiny.setSaldo(presentDestiny.getSaldo() + transferenciaDTO.valor());

                        contaService.update(transferenciaDTO.numContaOrigem(), presentOrigin);
                        contaService.update(transferenciaDTO.numContaDestino(), presentDestiny);
                    } catch (Exception e) {
                        try {
                            throw new Exception("Conta com o id %d não encontrada".formatted(transferenciaDTO.numContaDestino()));
                        } catch (Exception ex) {
                            throw new RuntimeException();
                        }
                    }
                }, () -> {
                    try {
                        throw new Exception("Conta " + transferenciaDTO.numContaDestino() + " não encontrada");
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                });

            } catch (Exception e) {
                try {
                    throw new Exception("Conta com o id %d não encontrada".formatted(transferenciaDTO.numContaOrigem()));
                } catch (Exception ex) {
                    throw new RuntimeException();
                }
            }
        }, () -> {
            try {
                throw new Exception("Conta " + transferenciaDTO.numContaDestino() + " não encontrada");
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
    }
}
