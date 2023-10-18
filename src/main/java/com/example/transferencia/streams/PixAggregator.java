package com.example.transferencia.streams;

import com.example.transferencia.serdes.PixSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PixAggregator {

    @Autowired
    public void aggregator(StreamsBuilder streamsBuilder) {
        KTable<String, Double> messageStream = streamsBuilder
                .stream("pix-topic", Consumed.with(Serdes.String(), PixSerdes.serdes()))
                .peek((key, value) -> System.out.println("Key: " + key + " Value: " + value))
                .groupBy((key, value) -> value.getChaveOrigem())
                .aggregate(
                        () -> 0.0,
                        (key, value, aggregate) -> (aggregate + value.getValor()),
                        Materialized.with(Serdes.String(), Serdes.Double())
                );

        isMaxValorDiario(messageStream);

        messageStream.toStream().print(Printed.toSysOut());
        messageStream.toStream().to("pix-topic-agregacao", Produced.with(Serdes.String(), Serdes.Double()));
    }

    // TODO criar uma exception
    private static void isMaxValorDiario(KTable<String, Double> messageStream) {
        messageStream.toStream().foreach((key, value) -> {
            if (value > 5000.0) {
                throw new RuntimeException("Valor maior que 5.000 em um único dia: " + value);
            }
        });
    }

}
