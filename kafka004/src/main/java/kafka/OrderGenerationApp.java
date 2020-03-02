package kafka;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;

import kafka.constants.IKafkaConstants;
import kafka.consumer.OrderConsumer;
import kafka.consumer.OrderStatusConsumer;
import kafka.pojo.CustomObject;
import kafka.producer.OrderProducer;

public class OrderGenerationApp {
	public static void main(String[] args) throws FileNotFoundException {
		runProducer();
//		runConsumer();
	}

	static void getStatus() {
		Consumer<Long, String> consumer = OrderStatusConsumer.createConsumer();

		int noMessageToFetch = 0;

		while (true) {
			final ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);
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
			});
			consumer.commitAsync();
		}
		consumer.close();
	}

	static void runProducer() throws FileNotFoundException {
		Producer<Long, CustomObject> producer = OrderProducer.createProducer();
		
		CSVReader reader = new CSVReader(new FileReader(IKafkaConstants.INPUT_ORDER_CSV_PATH));
	      CsvToBean<CustomObject> csv = new CsvToBean<CustomObject>();
	      csv.setCsvReader(reader);
	      csv.setMappingStrategy(setColumMapping());
	      List<CustomObject> list = csv.parse();
	      int index=0;
	      //Read CSV line by line and use the string array as you want
	      for (CustomObject object : list) {
	          System.out.println(object);
	          final ProducerRecord<Long, CustomObject> record = new ProducerRecord<Long, CustomObject>(IKafkaConstants.TOPIC_NAME,
						createObject(index));
	          try {
					RecordMetadata metadata = producer.send(record).get();
					System.out.println("Record sent with key " + index + " to partition " + metadata.partition()
							+ " with offset " + metadata.offset());
				} catch (ExecutionException e) {
					System.out.println("Error in sending record");
					System.out.println(e);
				} catch (InterruptedException e) {
					System.out.println("Error in sending record");
					System.out.println(e);
				}
	          index++;
	      }
	      
	      getStatus();

	}
	
	
	static CustomObject createObject(int index) {
		CustomObject obj = new CustomObject();
		obj.setId(""+ index);
		obj.setName("Order " + index);
		return obj;
		
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	   private static ColumnPositionMappingStrategy setColumMapping()
	   {
	      ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
	      strategy.setType(CustomObject.class);
	      String[] columns = new String[] {"id", "name", "type"}; 
	      strategy.setColumnMapping(columns);
	      return strategy;
	   }
}
