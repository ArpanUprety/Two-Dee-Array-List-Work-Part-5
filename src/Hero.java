public class Hero  extends Explorer implements Fighter {
public Dice attackDice = new Dice();

    public Hero(String name, int health, String pieceColor) {
        super(name, health, pieceColor);
        attackDice = new  Dice(1,6);
    }

    public Hero(String name, int health, String pieceColor, Dice d) {
        super(name, health, pieceColor);
        attackDice = d;
    }



    @Override
    public int Hurt(LivingThing l) {

  l.setHealth(l.getHealth() - attackDice.getRollValue() );
        return attackDice.getRollValue();

    }

    @Override
    public void setHealth() {

    }



}
