public class Treasure {
    private int value;
    private String description;

    public Treasure(int value, String description){
        this.value = value;
        this.description = description;
    }

    public Treasure(){
        value = 500;
        description = "Gold";
    }

    public String getConsoleStr(){
        return ConsoleColors.YELLOW + String.valueOf(description.charAt(0)).toUpperCase() + ConsoleColors.RESET + " ";
    }

    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
