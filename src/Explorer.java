import java.util.ArrayList;
public class Explorer extends LivingThing{
    private final ArrayList<Treasure> treasures = new ArrayList<>();
    public Explorer(String name, int health, String pieceColor) {
        super(name, health, pieceColor);
    }
    public Explorer(String name, int health) {
        super(name, health);
    }
    public void addTreasure(Treasure treasure){
        treasures.add(treasure);
    }
    public int getTreasureValue(){
        int value = 0;
        for (Treasure treasure:treasures) {
            value += treasure.getValue();
        }
        return value;
    }
    public ArrayList<Treasure> getTreasures() {
        return treasures;
    }
}
