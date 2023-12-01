import java.util.ArrayList;
import java.util.Random;

public class Dice {

     public  static ArrayList <Integer> values = new ArrayList<>();
    public static Random roller = new Random();


    public Dice(int min, int max){

        for (int i = min; i < max; i++) {
            values.add(i);
        }

    }

    public Dice(ArrayList<Integer> Integers){
      values = (Integers);
    }

    public Dice() {
        for (int i = 0; i < 6; i++) {
            values.add(i);
        }
    }

    public  int getRollValue(){

        int randnum = values.get(roller.nextInt(values.size()));

        if (values.isEmpty()){
            return 0;
        }else {
            return randnum;
        }
    }

}
