import org.apache.wayang.api.JavaPlanBuilder;
import org.apache.wayang.basic.data.Tuple2;
import org.apache.wayang.core.api.Configuration;
import org.apache.wayang.core.api.WayangContext;
import org.apache.wayang.core.optimizer.cardinality.DefaultCardinalityEstimator;
import org.apache.wayang.java.Java;

import java.util.Arrays;
import java.util.Collection;

public class RemoteFileWordCount {

    public static void main(String[] args){

        System.out.println( ">>> Apache Wayang Test #01");
        System.out.println( "    We use a remote file and a 'Java Context'.");
        int i = 0;
        for (String arg : args) {
            String line = String.format( "  %d    - %s", i,arg);
            System.out.println(line);
            i=i+1;
        }

        // Settings
        String inputUrl = args[1];

        // Get a plan builder.
        WayangContext wayangContext = new WayangContext(new Configuration())
                .withPlugin(Java.basicPlugin());
        //        .withPlugin(Spark.basicPlugin());
        JavaPlanBuilder planBuilder = new JavaPlanBuilder(wayangContext)
                .withJobName(String.format("WordCount (%s)", inputUrl))
                .withUdfJarOf(RemoteFileWordCount.class);

        // Start building the WayangPlan.
        Collection<Tuple2<String, Integer>> wordcounts = planBuilder
                // Read the text file.
                .readTextFile(inputUrl).withName("Load file")

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

        System.out.println(wordcounts);
        System.out.println( "### Done. ###" );
    }
}