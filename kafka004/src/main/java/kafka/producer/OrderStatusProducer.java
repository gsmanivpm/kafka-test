package kafka.producer;

import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import kafka.constants.IKafkaConstants;
import kafka.pojo.CustomObject;

public class OrderStatusProducer {

	public static Producer<Long, String> createProducer() {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
		props.put(ProducerConfig.CLIENT_ID_CONFIG, IKafkaConstants.CLIENT_ID);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		
		//configure the following three settings for SSL Encryption
		props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
		props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, IKafkaConstants.FULL_ACL_CONSUMER_TS);
		props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG,  IKafkaConstants.PASSWORD);

		// configure the following three settings for SSL Authentication
		props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, IKafkaConstants.FULL_ACL_CONSUMER_KS);
		props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, IKafkaConstants.PASSWORD);
		props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, IKafkaConstants.PASSWORD);
		props.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
		
		
		return new KafkaProducer<>(props);
	}
}