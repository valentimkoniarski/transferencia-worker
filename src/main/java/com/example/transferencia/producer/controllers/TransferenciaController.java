package com.example.transferencia.producer.controllers;

import com.example.transferencia.producer.services.TransferenciaService;
import dev.valentim.transferencia.dto.TransferenciaDto;
import dev.valentim.transferencia.enuns.TransferenciaStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/pix")
@RequiredArgsConstructor
public class TransferenciaController {

    private final TransferenciaService pixService;

    @PostMapping
    public TransferenciaDto salvarPix(@RequestBody TransferenciaDto transferenciaDto) {

        transferenciaDto.setIdentifier(UUID.randomUUID().toString());
        transferenciaDto.setDataTransferencia(LocalDateTime.now());
        transferenciaDto.setStatus(TransferenciaStatus.EM_PROCESSAMENTO);

        return pixService.salvarPix(transferenciaDto);
    }
}
