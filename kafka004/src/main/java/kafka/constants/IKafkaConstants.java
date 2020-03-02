package kafka.constants;

public interface IKafkaConstants {
	public static String KAFKA_BROKERS = "localhost:9093";
	
	public static Integer MESSAGE_COUNT=2;
	
	public static String CLIENT_ID="client001";
	
//	public static String TOPIC_NAME="test";
//	order-status-topic
	public static String TOPIC_NAME="secure-topic1";	
	
	public static String STATUS_TOPIC_NAME="status-order-topic1";
	
	public static String GROUP_ID_CONFIG="consumerGroup001";
	
	public static Integer MAX_NO_MESSAGE_FOUND_COUNT=100;
	
	public static String OFFSET_RESET_LATEST="latest";
	
	public static String OFFSET_RESET_EARLIER="earliest";
	
	public static Integer MAX_POLL_RECORDS=1;
	
	public static String READ_ONLY_CONSUMER_KS="E:\\subbu\\dev\\eclipse-workspace\\001\\kafka004\\src\\main\\resources\\kafka.client2.keystore.jks";
	public static String READ_ONLY_CONSUMER_TS="E:\\subbu\\dev\\eclipse-workspace\\001\\kafka004\\src\\main\\resources\\kafka.client2.truststore.jks";
	
	
	public static String FULL_ACL_CONSUMER_KS="E:\\subbu\\dev\\eclipse-workspace\\001\\kafka004\\src\\main\\resources\\kafka.client1.keystore.jks";
	public static String FULL_ACL_CONSUMER_TS="E:\\subbu\\dev\\eclipse-workspace\\001\\kafka004\\src\\main\\resources\\kafka.client1.truststore.jks";
	
	public static String PASSWORD="order123";

	
	public static String INPUT_ORDER_CSV_PATH="E:\\subbu\\dev\\kafka-test\\file\\in\\orders.csv";
}
