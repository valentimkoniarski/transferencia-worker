//package com.example.transferencia.streams;
//
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.kstream.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//
//@Service
//public class PixAggregator {
//
//    @Autowired
//    public void aggregator(StreamsBuilder streamsBuilder) {
//        KStream<UUID, Double> stream = streamsBuilder.stream("pix-topic", Consumed.with(Serdes.UUID(), Serdes.Double()));
//
//        // Definir uma janela de 24 horas (um dia) e agregar as transferências por dia
//        KTable<Windowed<UUID>, Double> dailySum = stream
//                .groupBy((key, value) -> key) // Use a chave correta
//                .windowedBy(TimeWindows.of(24 * 60 * 60 * 1000)) // Janela de 24 horas
//                .aggregate(
//                        () -> 0.0,
//                        (key, value, aggregate) -> (Double.sum(aggregate, value)),
//                        Materialized.with(Serdes.UUID(), Serdes.Double())
//                );
//
//        // Filtrar as somas diárias superiores a 5.000
//        dailySum.toStream()
//                .filter((key, value) -> value > 5000.0)
//                .to("kafka-streams-demo-6");
//
//        // Impressão dos resultados
//        dailySum.toStream().print(Printed.toSysOut());
//    }
//}
