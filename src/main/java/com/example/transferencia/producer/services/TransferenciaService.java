package com.example.transferencia.producer.services;

import dev.valentim.transferencia.Transferencia;
import dev.valentim.transferencia.dto.TransferenciaDto;
import dev.valentim.transferencia.repository.TransferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransferenciaService {

    private final TransferenciaRepository transferenciaRepository;

    private final KafkaTemplate<String, TransferenciaDto>  kafkaTemplate;

    @Autowired
    public TransferenciaService(TransferenciaRepository transferenciaRepository,
                                KafkaTemplate<String, TransferenciaDto> kafkaTemplate) {
        this.transferenciaRepository = transferenciaRepository;
        this.kafkaTemplate = kafkaTemplate;
    }


    public TransferenciaDto salvarPix(TransferenciaDto transferenciaDto) {
        Transferencia transferencia = Transferencia.toEntity(transferenciaDto);
        transferenciaRepository.save(transferencia);
        kafkaTemplate.send("pix-topic", String.valueOf(transferenciaDto.getIdentifier()), transferenciaDto);
        return transferenciaDto;
    }

}
