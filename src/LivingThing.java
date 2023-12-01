public class LivingThing {

    private String name;
    private int health;
    private String pieceColor;

    public String getConsoleStr(){
        return pieceColor + String.valueOf(name.charAt(0)).toUpperCase() + ConsoleColors.RESET + " ";
    }

    public String getConsoleStr(boolean reveal){
        return pieceColor + String.valueOf(name.charAt(0)).toUpperCase() + ConsoleColors.RESET + " ";
    }

    public LivingThing(String name, int health, String pieceColor){
        setName(name);
        setHealth(health);
        setPieceColor(pieceColor);
    }

    public LivingThing(String name, int health){
        setName(name);
        setHealth(health);
        setPieceColor(ConsoleColors.YELLOW);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name.trim();
        if (name.isEmpty()){
            this.name = "undefined";
        }else{
            this.name = name;
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {

        this.health = Math.max(0, health);
    }

    public boolean isDead(){
        return health <= 0;
    }

    public String getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(String pieceColor) {
        this.pieceColor = pieceColor;
    }
}
