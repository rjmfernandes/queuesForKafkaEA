# Queues for Kafka - EA - Apache Kafka 4.0

Basic tests with Queues for Kafka with EA in Apache Kafka 4.0 

- [Queues for Kafka - EA - Apache Kafka 4.0](#queues-for-kafka---ea---apache-kafka-40)
  - [Disclaimer](#disclaimer)
  - [Setup](#setup)
    - [Start Docker Compose](#start-docker-compose)
    - [Check Control Center](#check-control-center)
  - [First test of Queues for Kafka](#first-test-of-queues-for-kafka)
  - [KafkaShareConsumer example](#kafkashareconsumer-example)
  - [Cleanup](#cleanup)

## Disclaimer

The code and/or instructions here available are **NOT** intended for production usage. 
It's only meant to serve as an example or reference and does not replace the need to follow actual and official documentation of referenced products.

## Setup

### Start Docker Compose

```bash
docker compose up -d
```

### Check Control Center

Open http://localhost:9021 and check cluster is healthy.

## First test of Queues for Kafka

We will be following the tests described here: https://cwiki.apache.org/confluence/display/KAFKA/Queues+for+Kafka+%28KIP-932%29+-+Early+Access+Release+Notes

Run for 4 separate shells:

```shell
docker compose exec -it broker bash
```

Once inside each shell execute:

```shell
cd /opt/kafka
```

In one execute:

```shell
bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic quickstart-events
bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic quickstart-events
```

You will be able to start producing in this shell.

In the other 2 terminals execute:

```shell
bin/kafka-console-share-consumer.sh --bootstrap-server localhost:9092 --topic quickstart-events
```

In the final shell execute (the kafka-console-share-consumer.sh  tool creates a consumer in a share group called "console-share-consumer" by default): 

```shell
bin/kafka-share-groups.sh --bootstrap-server localhost:9092 --list
bin/kafka-share-groups.sh --bootstrap-server localhost:9092 --describe --group console-share-consumer --state
 ```

 For some reason the events are not being printed out on the console consumers.

 Consume as usual with to see events being consumed:

 ```shell
 bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic quickstart-events --from-beginning
 ```

## KafkaShareConsumer example

You can check basic examples on following references:
- https://docs.confluent.io/platform/current/clients/javadocs/javadoc/org/apache/kafka/clients/consumer/KafkaShareConsumer.html
- https://www.morling.dev/blog/kip-932-queues-for-kafka/

Execute:

```shell
mvn exec:java -Dexec.mainClass="io.confluent.csta.KafkaShareConsumerApp"
```

Again for some reason nothing is consumed. 

## Cleanup

```bash
docker compose down -v
```