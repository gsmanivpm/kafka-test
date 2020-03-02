package kafka;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import kafka.constants.IKafkaConstants;
import kafka.consumer.OrderConsumer;
import kafka.pojo.CustomObject;
import kafka.producer.OrderProducer;
import kafka.producer.OrderStatusProducer;

public class OrderFulfilmentApp {
	public static void main(String[] args) {
//		runProducer();
		runConsumer();
	}

	static void runConsumer() {
		Consumer<Long, CustomObject> consumer = OrderConsumer.createConsumer();

		int noMessageToFetch = 0;

		while (true) {
			final ConsumerRecords<Long, CustomObject> consumerRecords = consumer.poll(1000);
			if (consumerRecords.count() == 0) {
				noMessageToFetch++;
				if (noMessageToFetch > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
					break;
				else
					continue;
			}

			consumerRecords.forEach(record -> {
				System.out.println("Record Key " + record.key());
				System.out.println("Record value " + record.value());
				System.out.println("Record partition " + record.partition());
				System.out.println("Record offset " + record.offset());
				
				System.out.println("Changing record Status to NEW");
				setStatus( record.value().getId(), "NEW");
				
				System.out.println("Changing record Status to DELIVERED");
				setStatus( record.value().getId(), "DELIVERED");
				
			});
			consumer.commitAsync();
		}
		consumer.close();
	}

	static void setStatus(String orderId, String status) {
		Producer<Long, String> producer = OrderStatusProducer.createProducer();

			final ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(IKafkaConstants.STATUS_TOPIC_NAME,
					"Status of order " + orderId + " is :>>"+ status);
			try {
				RecordMetadata metadata = producer.send(record).get();
			} catch (ExecutionException e) {
				System.out.println("Error in sending record");
				System.out.println(e);
			} catch (InterruptedException e) {
				System.out.println("Error in sending record");
				System.out.println(e);
			}
	}
	
	
	static CustomObject createObject(int index) {
		CustomObject obj = new CustomObject();
		obj.setId(""+ index);
		obj.setName("Order " + index);
		return obj;
		
	}
}
