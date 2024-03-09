import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;

// source ./../bin/env.sh; mvn test -Dtest=JavaKafkaTopicSourceTest
// source ./../bin/env.sh; mvn clean compile exec:java -Dexec.mainClass="JavaKafkaTopicSourceTest"

public class JavaKafkaTopicSourceTest {

    private Locale defaultLocale;

    /**
     * In locales, where the decimal separator is not "." this rest would fail.
     * Therefore we ensure it's run in a pre-defined locale and we make sure it's
     * reset after the test.
     */
    @Before
    public void setupTest() {
        defaultLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);
        System.out.println(">>> Test SETUP()");
    }

    @After
    public void teardownTest() {
        System.out.println(">>> Test TEARDOWN()");
        Locale.setDefault(defaultLocale);
    }

    @Test
    public void testA() throws Exception {
        Assert.assertEquals(3, 3);
    }

    public static void main (String[] args) {

        JavaKafkaTopicSourceTest c = new JavaKafkaTopicSourceTest();
        c.testReadFromKafkaTopic();

    }


    @Test
    public void testReadFromKafkaTopic() {

        final String topicName1 = "banking-tx-small-csv";

        System.out.println("> 0 ... ");

        System.out.println( "*** [TOPIC-Name] " + topicName1 + " ***");

        System.out.println( ">   Read from topic ... ");

        System.out.println("> 1 ... ");

        Properties props = getDefaultProperties();

        System.out.println("> 2 ... ");

        props.list(System.out);

        System.out.println("> 3 ... ");

        KafkaConsumer consumer = new KafkaConsumer<String, String>(props);

        try {

            consumer.subscribe( Arrays.asList(topicName1) );

            int i=0;
            while (i < 4) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    processRecord(record);
                    i++;
                }
            }

            Assert.assertEquals(4, i);

            System.out.println("> 4 ... ");
        }
        catch (Exception ex) {

            ex.printStackTrace();

        }
    }

    private static void processRecord(ConsumerRecord<String, String> record) {
        // Implement your record processing logic here
        System.out.printf("key = %s, value = %s%n", record.key(), record.value());
    }

    public static Properties getDefaultProperties() {

        Properties props = new Properties();

        String BOOTSTRAP_SERVER = System.getenv("BOOTSTRAP_SERVER");
        String CLUSTER_API_KEY = System.getenv("CLUSTER_API_KEY");
        String CLUSTER_API_SECRET = System.getenv("CLUSTER_API_SECRET");
        String SR_ENDPOINT = System.getenv("SR_ENDPOINT");
        String SR_API_KEY = System.getenv("SR_API_KEY");
        String SR_API_SECRET = System.getenv("SR_API_SECRET");



        System.out.println( BOOTSTRAP_SERVER );
        System.out.println( CLUSTER_API_KEY );
        System.out.println( CLUSTER_API_SECRET );
        System.out.println( SR_ENDPOINT );
        System.out.println( SR_API_KEY );
        System.out.println( SR_API_SECRET );


        // Set additional properties if needed
        props.put("bootstrap.servers", BOOTSTRAP_SERVER );
        props.put("security.protocol", "SASL_SSL");
        props.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username='" + CLUSTER_API_KEY + "' password='" + CLUSTER_API_SECRET + "';");
        props.put("sasl.mechanism", "PLAIN");
        props.put("client.dns.lookup", "use_all_dns_ips");
        props.put("session.timeout.ms", "45000");
        props.put("acks", "all");
        props.put("schema.registry.url", SR_ENDPOINT);
        props.put("basic.auth.credentials.source", "USER_INFO");
        props.put("basic.auth.user.info", SR_API_KEY + ":" + SR_API_SECRET );

        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        props.put(ConsumerConfig.GROUP_ID_CONFIG, "wayang-kafka-java-source-client");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        props.list( System.out );

        return props;
    }

}
