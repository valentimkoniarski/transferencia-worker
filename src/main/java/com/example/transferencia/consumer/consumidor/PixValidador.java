package com.example.transferencia.consumer.consumidor;

import dev.valentim.key.Key;
import dev.valentim.transferencia.Transferencia;
import dev.valentim.transferencia.dto.TransferenciaDto;
import dev.valentim.transferencia.enuns.TransferenciaStatus;
import dev.valentim.key.repository.KeyRepository;
import dev.valentim.transferencia.repository.TransferenciaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PixValidador {

    private final KeyRepository keyRepository;

    private final TransferenciaRepository transferenciaRepository;

    @Autowired
    public PixValidador(KeyRepository keyRepository, TransferenciaRepository transferenciaRepository) {
        this.keyRepository = keyRepository;
        this.transferenciaRepository = transferenciaRepository;
    }

    private static final Logger logger = LogManager.getLogger(PixValidador.class);

    @KafkaListener(topics = "pix-topic")
    //@RetryableTopic(
    //        backoff = @Backoff(value = 3000L),
    //        attempts = "2",
    //        autoCreateTopics = "true",
    //        // Qual as exceptions que devem ser retentativas
    //        include = KeyNotFoundException.class
    //)
    public void processaPix(TransferenciaDto transferenciaDto) {
        logger.info("Transferencia recebida: %s", transferenciaDto.getIdentifier());

        Transferencia transferencia = transferenciaRepository.findByIdentifier(transferenciaDto.getIdentifier());
        Key origem = keyRepository.findByChave(transferenciaDto.getChaveOrigem());
        Key destino = keyRepository.findByChave(transferenciaDto.getChaveDestino());

        if (origem == null || destino == null) {
            transferencia.setStatus(TransferenciaStatus.ERRO);
        } else {
            transferencia.setStatus(TransferenciaStatus.PROCESSADO);
        }
        transferenciaRepository.save(transferencia);
    }
}
