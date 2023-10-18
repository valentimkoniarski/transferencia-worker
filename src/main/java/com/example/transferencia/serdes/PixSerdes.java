package com.example.transferencia.serdes;


import dev.valentim.transferencia.dto.TransferenciaDto;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class PixSerdes extends Serdes.WrapperSerde<TransferenciaDto> {

    public PixSerdes() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(TransferenciaDto.class));
    }

    public static Serde<TransferenciaDto> serdes() {
        JsonSerializer<TransferenciaDto> serializer = new JsonSerializer<>();
        JsonDeserializer<TransferenciaDto> deserializer = new JsonDeserializer<>(TransferenciaDto.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

}
