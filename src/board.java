import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class board {
    //Update board to have two integer variables (gazooRow and gazooCol) that contain the current location of gazoo.
    private final ArrayList<ArrayList<Space>> board;
    private ArrayList<Treasure> remainingTreasures;
    private ArrayList<LivingThing> monsters;
    private Explorer Gazoo;
    private Healer healer;
    private int columns = 0;
    private int rows = 0;
    private int gazooRow = 0;
    private int gazooCol = 0;

    public board(int columns, int rows){
        this.rows = columns;
        this.columns = rows;

        Gazoo = new Explorer("Gazoo", 20, ConsoleColors.GREEN);
        board = new ArrayList<ArrayList<Space>>(rows);
        BuildBoard();
        CreateEntities();
    }

    public board(){
        columns = 4;
        rows = 4;

        Gazoo = new Explorer("Gazoo", 20, ConsoleColors.GREEN);
        board = new ArrayList<ArrayList<Space>>(rows);
        BuildBoard();
        CreateEntities();
    }

    public void CreateEntities(){
        createFiveTreasures();
        createFiveMonsters();
        createHealer();
    }

    public void createHealer(){
        healer = new Healer("Healer", 1, ConsoleColors.BLUE, 5);
        boolean placed = false;
        while (!placed){
            int randRow = ThreadLocalRandom.current().nextInt(rows);
            int randCol = ThreadLocalRandom.current().nextInt(columns);
            Space space = board.get(randRow).get(randCol);
            if (emptySpace(space)){
                space.setOccupant(healer);
                placed = true;
            }
        }

    }
    public void createFiveTreasures(){
        //fills the remainingTreasures list
        remainingTreasures = new ArrayList<Treasure>();
        for (int i = 0; i <= 4; i++) {
            remainingTreasures.add(new Treasure());
        }

        //adds the treasures from the remainingTreasures list to the board
        placeEntities(remainingTreasures);
    }

    public void placeEntity(Object entity){
        if (entity instanceof LivingThing){
            LivingThing l = (LivingThing) entity;
            boolean placed = false;
            while (!placed){
                int randRow = ThreadLocalRandom.current().nextInt(rows);
                int randCol = ThreadLocalRandom.current().nextInt(columns);
                Space space = board.get(randRow).get(randCol);
                if (emptySpace(space)){
                    space.setOccupant(l);
                    placed = true;
                }
            }
        }else if(entity instanceof Treasure){
            Treasure t = (Treasure) entity;
            boolean placed = false;
            while (!placed){
                int randRow = ThreadLocalRandom.current().nextInt(rows);
                int randCol = ThreadLocalRandom.current().nextInt(columns);
                Space space = board.get(randRow).get(randCol);
                if (emptySpace(space)){
                    space.setCache(t);
                    placed = true;
                }
            }
        }
    }
    public void placeEntities(ArrayList<?> entityList){
        for (Object entity : entityList) {
            placeEntity(entity);
        }
    }
    public boolean emptySpace(Space space){
        return space.cache == null && space.occupant == null;
    }
    private void createFiveMonsters(){
        //creates 5 new monsters and adds them to the monsters list
        monsters = new ArrayList<LivingThing>();
        monsters.add(new Monster("Razorclaw", 3, ConsoleColors.RED, 9));
        monsters.add(new Monster("Gloomfury", 3, ConsoleColors.RED, 8));
        monsters.add(new Monster("Fangsnarl", 3, ConsoleColors.RED, 7));
        monsters.add(new Monster("Vilespike", 3, ConsoleColors.RED, 6));
        monsters.add(new Monster("Grimscowl", 3, ConsoleColors.RED, 5));

        //places those monsters on the board.
        placeEntities(monsters);
    }

    public boolean move(char character){
        if (character == 'w'){
            return moveByCoords(0, -1);
        }else if(character == 'a'){
            return moveByCoords(-1, 0);
        } else if (character == 's') {
            return moveByCoords(0, 1);
        } else if (character == 'd') {
            return moveByCoords(1, 0);
        } else if (character == 'r') {
            printBoard(true);
            return true;
        } else if (character == 'i') {
            System.out.printf("%s has collected (%d/%d) treasures with a total value of %s.\n",Gazoo.getName(), collectedTreasureCount(), totalTreasuresCount(), Gazoo.getTreasureValue());
            return true;
        }

        System.out.println(ConsoleColors.RED + "Illegal Input.\n" + ConsoleColors.RESET);
        return false;
    }

    public boolean isGameOver(){
        if (checkForWin()){
            return true;
        }
        return checkForDeath();
    }

    public boolean checkForDeath(){
        if(Gazoo.isDead()){
            System.out.println(ConsoleColors.RED + "Gazoo DIED. You lose!" + ConsoleColors.RESET);
            System.out.printf("%s collected (%d/%d) treasures with a total value of %s.\n",Gazoo.getName(), collectedTreasureCount(), totalTreasuresCount(), Gazoo.getTreasureValue());
            return true;
        }
        return false;
    }

    private int collectedTreasureCount() {
        return Gazoo.getTreasures().size();
    }
    private int remainingTreasureCount()
    {
        return remainingTreasures.size();
    }
    private int totalTreasuresCount() {
        return collectedTreasureCount() + remainingTreasureCount();
    }
    public boolean checkForWin(){
        if (remainingTreasures.isEmpty()){
            System.out.println(ConsoleColors.YELLOW + "Gazoo collected all the treasure. You win!");
            return true;
        }
        return false;
    }

    private boolean moveByCoords(int xMovement, int yMovement){
        if (Gazoo.isDead()){
            return false;
        }

        int newGazooCol = gazooCol + xMovement;
        int newGazooRow = gazooRow + yMovement;

        if (isSpaceValid(newGazooCol, newGazooRow)){
            Space space = board.get(gazooRow).get(gazooCol);
            LivingThing occupant = space.getOccupant();

            Space destinationSpace = board.get(newGazooRow).get(newGazooCol);
            LivingThing destinationOccupant = destinationSpace.getOccupant();
            Treasure destinationCache = destinationSpace.getCache();

                if (destinationOccupant instanceof Monster){
                    Monster m = (Monster) destinationOccupant;
                    m.Hurt(Gazoo);
                    System.out.println(ConsoleColors.RED + Gazoo.getName() + " was attacked by " + m.getName() + " for a loss of " + m.getDamage() + " damage. " + Gazoo.getName() + " has " + Gazoo.getHealth() + " health left." + ConsoleColors.RESET);
                }
                if (destinationOccupant instanceof Healer){
                    Healer h = (Healer) destinationOccupant;
                    h.Heal(Gazoo);
                    System.out.println(ConsoleColors.BLUE + Gazoo.getName() + " was healed by " + h.getName() + " for an increase of " + h.getHealValue() + " health. " + Gazoo.getName() + " has " + Gazoo.getHealth() + " health left." + ConsoleColors.RESET);
                }
                if (destinationCache != null){
                    Gazoo.addTreasure(destinationCache);
                    remainingTreasures.remove(destinationCache);
                    System.out.println(ConsoleColors.YELLOW + Gazoo.getName() + " found " + destinationCache.getDescription().toLowerCase() + " worth " + destinationCache.getValue() + ". There are " + remainingTreasures.size() + " treasures left on the board." + ConsoleColors.RESET);
                    destinationSpace.setCache(null);
                }

            destinationSpace.setOccupant(occupant);
            space.setOccupant(null);
            gazooCol = newGazooCol;
            gazooRow = newGazooRow;
            return true;
        }
        return false;
    }

    private boolean isSpaceValid(int x, int y){
        return x <= board.get(0).size()-1 && x >= 0 && y <= board.size()-1 && y >= 0;
    }

    public void printBoard(boolean showContents){
        for (ArrayList<Space> rows : board) {
            for(Space space: rows) {
                System.out.print(space.getConsoleString(showContents));
            }
            //for correct spacing :)
            System.out.println();
        }
    }

    public void BuildBoard(){
        board.clear();
        for(int i = 0; i < this.rows; i++) {
            board.add(new ArrayList<Space>());
            for (int x = 0; x < this.columns; x++) {
                board.get(i).add(new Space());
            }
        }
        board.get(gazooRow).get(gazooCol).setOccupant(Gazoo);
    }
}
