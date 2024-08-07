import org.apache.wayang.api.JavaPlanBuilder;
import org.apache.wayang.basic.data.Tuple2;
import org.apache.wayang.core.api.Configuration;
import org.apache.wayang.core.api.WayangContext;
import org.apache.wayang.core.function.FunctionDescriptor;
import org.apache.wayang.core.optimizer.cardinality.DefaultCardinalityEstimator;
import org.apache.wayang.core.optimizer.costs.LoadProfileEstimator;
import org.apache.wayang.core.optimizer.costs.LoadProfileEstimators;
import org.apache.wayang.java.Java;

import java.util.Arrays;
import java.util.Collection;

// Import the Logger class
import org.apache.log4j.Logger;


public class KafkaTopicWordCount {


    // Create a logger instance
    private static final Logger logger = Logger.getLogger(KafkaTopicWordCount.class);

    // Define the lambda function for formatting the output
    private static final FunctionDescriptor.SerializableFunction<Tuple2<String, Integer>, String> udf = tuple -> {
        return tuple.getField0() + ": " + tuple.getField1();
    };

    public static void main(String[] args){

        System.out.println( ">>> Apache Wayang Test #01");
        System.out.println( "    We use a Kafka topic and a 'Java Context'.");
        logger.debug("GO: debugging...");
        logger.warn("GO: warning...");

        // Default topic name
        String topicName = "banking-tx-small-csv";

        // Check if at least one argument is provided
        if (args.length > 0) {
            // Assuming the first argument is the topic name
            topicName = args[0];

            int i = 0;
            for (String arg : args) {
                String line = String.format( "  %d    - %s", i,arg);
                System.out.println(line);
                i=i+1;
            }

        }
        else {
            System.out.println( "*** Use default topic name: " + topicName );
        }

        Configuration configuration = new Configuration();
        // Get a plan builder.
        WayangContext wayangContext = new WayangContext(configuration)
                .withPlugin(Java.basicPlugin());
        //        .withPlugin(Spark.basicPlugin());
        JavaPlanBuilder planBuilder = new JavaPlanBuilder(wayangContext)
                .withJobName(String.format("WordCount using Java on topic (%s)", topicName))
                .withUdfJarOf(KafkaTopicWordCount.class);
/*
        // Start building the WayangPlan.
        Collection<Tuple2<String, Integer>> wordcounts_collection = planBuilder
                // Read the text file.
                .readKafkaTopic(topicName).withName("Load data from topic")

                // Split each line by non-word characters.
                .flatMap(line -> Arrays.asList(line.split("\\W+")))
                .withSelectivity(10, 100, 0.9)
                .withName("Split words")

                // Filter empty tokens.
                .filter(token -> !token.isEmpty())
                .withSelectivity(0.99, 0.99, 0.99)
                .withName("Filter empty words")

                // Attach counter to each word.
                .map(word -> new Tuple2<>(word.toLowerCase(), 1)).withName("To lower case, add counter")

                // Sum up counters for every word.
                .reduceByKey(
                        Tuple2::getField0,
                        (t1, t2) -> new Tuple2<>(t1.getField0(), t1.getField1() + t2.getField1())
                )
                .withCardinalityEstimator(new DefaultCardinalityEstimator(0.9, 1, false, in -> Math.round(0.01 * in[0])))
                .withName("Add counters")

                // Execute the plan and collect the results.
                .collect();

        System.out.println( wordcounts_collection );
        System.out.println( "### Done. ###" );
     */

        // Start building the WayangPlan.
        planBuilder
                // Read the text file.
                .readKafkaTopic(topicName).withName("Load data from topic")

                // Split each line by non-word characters.
                .flatMap(line -> Arrays.asList(line.split("\\W+")))
                .withSelectivity(10, 100, 0.9)
                .withName("Split words")

                // Filter empty tokens.
                .filter(token -> !token.isEmpty())
                .withSelectivity(0.99, 0.99, 0.99)
                .withName("Filter empty words")

                // Attach counter to each word.
                .map(word -> new Tuple2<>(word.toLowerCase(), 1)).withName("To lower case, add counter")

                // Sum up counters for every word.
                .reduceByKey(
                        Tuple2::getField0,
                        (t1, t2) -> new Tuple2<>(t1.getField0(), t1.getField1() + t2.getField1())
                )
                .withCardinalityEstimator(new DefaultCardinalityEstimator(0.9, 1, false, in -> Math.round(0.01 * in[0])))
                .withName("Add counters")

                // Execute the plan and store the results in Kafka.
                //.writeKafkaTopic("file:///Users/mkaempf/GITHUB.private/open-sustainability-data/bin/test_23456.txt", d -> String.format("%.2f, %d", d.getField1(), d.getField0()), "job_test_1",
                .writeKafkaTopic("test_23456", d -> String.format("%d, %s", d.getField1(), d.getField0()), "job_test_1",
                        LoadProfileEstimators.createFromSpecification("wayang.java.kafkatopicsink.load", configuration) );


    }


}