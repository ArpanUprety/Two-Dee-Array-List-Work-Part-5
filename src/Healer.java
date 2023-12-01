public class Healer extends LivingThing{
    public Healer(String name, int health, String pieceColor, int healValue) {
        super(name, health, pieceColor);
        this.healValue = healValue;
    }
    private final int healValue;
    public void Heal(LivingThing livingThing){
        livingThing.setHealth(livingThing.getHealth() + healValue);
    }
    public int getHealValue() {
        return healValue;
    }
}
