import javax.sound.midi.Soundbank;
public class Monster extends LivingThing implements Fighter{
    public Monster(String name, int health, String pieceColor, int damage){
        super(name, health, pieceColor);
        this.damage = damage;
    }
    public Monster(String name, int health){
        super(name, health);
    }
    private int damage;


    public int getDamage() {
        return damage;
    }

@Override
   public int Hurt(LivingThing livingThing){
        livingThing.setHealth(livingThing.getHealth() - damage);
return damage;
    }

    @Override
    public void setHealth() {

    }
}