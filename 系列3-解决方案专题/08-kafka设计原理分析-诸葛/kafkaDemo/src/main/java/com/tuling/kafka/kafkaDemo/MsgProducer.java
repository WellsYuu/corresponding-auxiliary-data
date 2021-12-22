package com.tuling.kafka.kafkaDemo;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class MsgProducer {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.0.60:9092,192.168.0.60:9093");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<>(props);
		for (int i = 0; i < 5; i++) {
			//同步方式发送消息
			ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("test", 0, Integer.toString(i), Integer.toString(i));
			/*Future<RecordMetadata> result = producer.send(producerRecord);
			//等待消息发送成功的同步阻塞方法
			RecordMetadata metadata = result.get();
			System.out.println("同步方式发送消息结果：" + "topic-" + metadata.topic() + "|partition-"
			        + metadata.partition() + "|offset-" + metadata.offset());*/

			//异步方式发送消息
			producer.send(producerRecord, new Callback() {
				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					if (exception != null) {
						System.err.println("发送消息失败：" + exception.getStackTrace());
					}
					if (metadata != null) {
						System.out.println("异步方式发送消息结果：" + "topic-" + metadata.topic() + "|partition-"
						        + metadata.partition() + "|offset-" + metadata.offset());
					}
				}
			});
		}

		producer.close();
	}
}
