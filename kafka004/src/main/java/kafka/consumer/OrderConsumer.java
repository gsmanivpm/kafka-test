package kafka.consumer;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.LongDeserializer;

import kafka.constants.IKafkaConstants;
import kafka.deserializer.CustomDeserializer;
import kafka.pojo.CustomObject;

public class OrderConsumer {

	public static Consumer<Long, CustomObject> createConsumer() {
		final Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, IKafkaConstants.GROUP_ID_CONFIG);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
//		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomDeserializer.class.getName());
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, IKafkaConstants.MAX_POLL_RECORDS);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, IKafkaConstants.OFFSET_RESET_EARLIER);

		
		//configure the following three settings for SSL Encryption
		props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
		props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, IKafkaConstants.READ_ONLY_CONSUMER_TS);
		props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG,  IKafkaConstants.PASSWORD);

		// configure the following three settings for SSL Authentication
		props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, IKafkaConstants.READ_ONLY_CONSUMER_KS);
		props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, IKafkaConstants.PASSWORD);
		props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, IKafkaConstants.PASSWORD);
		props.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
		
		final Consumer<Long, CustomObject> consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Collections.singletonList(IKafkaConstants.TOPIC_NAME));
		return consumer;
	}

}
