public class Utilities {
    public static void pause(int millis){
        //need a try catch because the sleep method can throw an exception.
        //java will not compile if you do not handle a method that declares it may throw and exception.
        try{
            Thread.sleep(millis);
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static int parseInt(String s, int ifInvalid){
        try{
            return Integer.parseInt(s);
        }catch(Exception e){
            return ifInvalid;
        }
    }
}
