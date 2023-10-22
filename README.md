##Projetos envolvidos
- api-transferencia: https://github.com/valentimkoniarski/api-transferencia
- transferencia-worker: https://github.com/valentimkoniarski/transferencia-worker
- transferencia-models: https://github.com/valentimkoniarski/transferencia-models


### O que são Streams?

- Processamento batch:
    - Dados são processados em blocos (batches)
    - Dados são lidos de uma fonte (arquivo, banco de dados, etc)
    - Dados são processados
    - Dados são escritos em uma fonte (arquivo, banco de dados, etc)

<br>

- Processamento stream: (netflix, youtube...)
    - dados são processados conforme são recebidos. Ex.: Buffer do youtube que carrega em pedaço
    - serviços de logs
    - usado como consumidor


**Processamento de Eventos em Tempo Real:** As streams no Kafka permitem o processamento de eventos à medida que eles 
chegam, em tempo real.

**Transformação de Dados:** As streams podem ser usadas para transformar os dados à medida que são consumidos do Kafka. 
Isso inclui filtragem, agregação, enriquecimento e outras operações de processamento de dados.

**Análise de Dados em Tempo Real:** Streams facilitam a realização de análises em tempo real sobre os dados que fluem 
pelo Kafka. Isso permite que você extraia informações valiosas à medida que os eventos são gerados.

**Integração de Sistemas:** As streams no Kafka são usadas para integrar sistemas e aplicativos diferentes. 
Elas permitem que dados sejam transmitidos entre sistemas de forma contínua e em tempo real.

**Fluxo de Dados de Alta Volume:** O Kafka é projetado para lidar com fluxos de dados de alto volume, e as streams 
ajudam a processar esses volumes de dados sem problemas, garantindo que nenhum dado seja perdido e que o processamento 
seja escalável.


