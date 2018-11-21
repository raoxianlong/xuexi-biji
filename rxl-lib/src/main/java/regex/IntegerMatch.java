package regex;

/**
 *
 */
public class IntegerMatch {

    static {
        System.out.println("加载IntegerMatch类.....");
    }

    public static void main(String[] args) throws ClassNotFoundException {
        String str = "164654";
        System.out.println(str.matches("\\d{6}"));


        String str1 = "Wefw";
        System.out.println(str1.matches("^[a-zA-Z]{1,5}$"));

        Class clz = Class.forName("regex.IntegerMatch");

    }

}
