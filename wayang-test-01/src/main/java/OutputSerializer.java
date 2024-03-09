import org.apache.wayang.core.function.FunctionDescriptor.SerializableFunction;
import org.apache.wayang.basic.data.Tuple2;

public class OutputSerializer implements SerializableFunction<Tuple2<String, Integer>, String> {

    @Override
    public String apply(Tuple2<String, Integer> tuple) {
        return tuple.getField0() + ": " + tuple.getField1();
    }

    // Example usage within a main method or similar test environment
    public static void main(String[] args) {
        OutputSerializer formatter = new OutputSerializer();
        Tuple2<String, Integer> exampleTuple = new Tuple2<>("Age", 30);
        String result = formatter.apply(exampleTuple);
        System.out.println(result); // Output: Age: 30
    }
}
