package com.example.transferencia.consumer;

import dev.valentim.transferencia.Transferencia;
import dev.valentim.transferencia.dto.TransferenciaDto;
import dev.valentim.transferencia.enuns.TransferenciaStatus;
import dev.valentim.transferencia.repository.TransferenciaRepository;
import dev.valentim.usuario.Usuario;
import dev.valentim.usuario.UsuarioRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PixValidador {

    private final TransferenciaRepository transferenciaRepository;

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public PixValidador(TransferenciaRepository transferenciaRepository, UsuarioRepository usuarioRepository) {
        this.transferenciaRepository = transferenciaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private static final Logger logger = LogManager.getLogger(PixValidador.class);

    @KafkaListener(topics = "pix-topic", groupId = "pix-group")
    @Transactional
    public void processaPix(TransferenciaDto transferenciaDto) {
        logger.info("Transferencia recebida: %s", transferenciaDto.getIdentifier());

        Transferencia transferencia = transferenciaRepository.findByIdentifier(transferenciaDto.getIdentifier());


        Optional<Usuario> origem = Optional.of(usuarioRepository.findByKey(transferenciaDto.getChaveOrigem()).get());
        Optional<Usuario> destino = Optional.of(usuarioRepository.findByKey(transferenciaDto.getChaveDestino()).get());

        if (origem == null || destino == null) {
            transferencia.setStatus(TransferenciaStatus.ERRO);
        } else {
            transferencia.setStatus(TransferenciaStatus.PROCESSADO);
        }

        atualizaSaldos(transferenciaDto);

        transferenciaRepository.save(transferencia);
    }

    private void atualizaSaldos(TransferenciaDto transferenciaDto) {
        Optional<Usuario> usuarioOrigemOptional = usuarioRepository.findByKey(transferenciaDto.getChaveOrigem());
        if (usuarioOrigemOptional.isPresent()) {
            Usuario usuarioOrigem = usuarioOrigemOptional.get();
            transferenciaRepository.updateSaldoClienteTransferidor(transferenciaDto.getValor(), usuarioOrigem.getId());
        } else {
            // TODO criar uma exception apropriada
            throw new IllegalStateException("Usuário de origem não encontrado");
        }

        Optional<Usuario> usuarioDestinoOptional = usuarioRepository.findByKey(transferenciaDto.getChaveDestino());
        if (usuarioDestinoOptional.isPresent()) {
            Usuario usuarioDestino = usuarioDestinoOptional.get();
            transferenciaRepository.updateSaldoClienteRecebedor(transferenciaDto.getValor(), usuarioDestino.getId());
        } else {
            // TODO criar uma exception apropriada
            throw new IllegalStateException("Usuário de destino não encontrado");
        }
    }
}
