import java.io.Serializable;

public class Util implements Serializable {
    public static String formatData( String f1, Integer f2 ) {
        return String.format("%d, %s", f1, f2);
    }
}
