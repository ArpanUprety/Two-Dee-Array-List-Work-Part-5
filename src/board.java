import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class board {
    public int enemiesDefeated = 0;
    public int bounty = (enemiesDefeated * 10);

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

    public board(int columns, int rows, Explorer e){
        this.rows = columns;
        this.columns = rows;

        Gazoo = e;
        board = new ArrayList<ArrayList<Space>>(rows);
        BuildBoard();
        CreateEntities();
    }

    public board(){
        this(8, 10, new Hero("Gazee", 45, ConsoleColors.GREEN));

    }

    public Fighter Fight(Fighter f1, Fighter f2){
        LivingThing F1 = (LivingThing) f1;
        LivingThing F2 = (LivingThing) f2;

        ((LivingThing) F1).setPieceColor(ConsoleColors.GREEN);
        ((LivingThing) F2).setPieceColor(ConsoleColors.RED);
        while (!f1.isDead() && !f2.isDead()){

            System.out.println( F2.getPieceColor() +  F2.getName() + ConsoleColors.RESET+  " applies " +  f2.Hurt((LivingThing) f1) + " damage to " + F1.getPieceColor() + ((LivingThing) f1).getName() + ConsoleColors.RESET );
            System.out.println(((LivingThing) f1).getName() + " has " + f1.getHealth() + " health remaining");
            Utilities.pause(500);
            if (f1.isDead()){
                System.out.println( F2.getPieceColor() +  F2.getName() + ConsoleColors.RESET +  " has defeated " + F1.getPieceColor() +  F1.getName() + ConsoleColors.RESET);
                return f2;
            } else {
                System.out.println(F1.getPieceColor() + F1.getName() + ConsoleColors.RESET + " applies " + f1.Hurt((LivingThing) f2) + " damage to " + F2.getPieceColor() +  ((LivingThing) f2).getName() + ConsoleColors.RESET );
                System.out.println(((LivingThing) f2).getName() + " has " + f2.getHealth() + " health remaining");
            }
            Utilities.pause(500);
if (f2.isDead()){
    System.out.println( F1.getPieceColor() +  F1.getName() + ConsoleColors.RESET +  " has defeated " + F2.getPieceColor() +  F2.getName() + ConsoleColors.RESET);
    return f1;
}

        }
        return null;
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
        } else if (character == 'i' && Gazoo instanceof Hero) {
            System.out.printf("%s has collected (%d/%d) treasures and claimed %s bounties with a total  value of %s gold.\n",Gazoo.getName(), collectedTreasureCount(), totalTreasuresCount(), enemiesDefeated, (Gazoo.getTreasureValue() + bounty));
            return true;
        }else if (character == 'i' && Gazoo instanceof Explorer && !(Gazoo instanceof Hero) ){
            System.out.printf("%s has collected (%d/%d) treasures with a total  value of %s gold.\n",Gazoo.getName(), collectedTreasureCount(), totalTreasuresCount(), (Gazoo.getTreasureValue()  ));
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
        if(Gazoo.isDead() && Gazoo instanceof Explorer && !(Gazoo instanceof Hero)){
            System.out.println(ConsoleColors.RED + "Gazoo DIED. You lose!" + ConsoleColors.RESET);
            System.out.printf("%s collected (%d/%d) treasures with a total value of %s.\n",Gazoo.getName(), collectedTreasureCount(), totalTreasuresCount(), Gazoo.getTreasureValue());
            return true;
        }else if (Gazoo.isDead() && Gazoo instanceof Hero){
            System.out.printf("%s has collected (%d/%d) treasures and claimed %s bounties with a total  value of %s gold.\n",Gazoo.getName(), collectedTreasureCount(), totalTreasuresCount(), enemiesDefeated, (Gazoo.getTreasureValue() + bounty));
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
        if (remainingTreasures.isEmpty() && Gazoo instanceof Explorer){
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
            boolean allowMove = true;
            Space destinationSpace = board.get(newGazooRow).get(newGazooCol);
            LivingThing destinationOccupant = destinationSpace.getOccupant();
            Treasure destinationCache = destinationSpace.getCache();

                if (destinationOccupant instanceof Monster && (!(Gazoo instanceof Hero)) ){
                    Monster m = (Monster) destinationOccupant;
                    m.Hurt(Gazoo);
                    System.out.println(ConsoleColors.RED + Gazoo.getName() + " was attacked by " + m.getName() + " for a loss of " + m.getDamage() + " damage. " + Gazoo.getName() + " has " + Gazoo.getHealth() + " health left." + ConsoleColors.RESET);
                    allowMove = false;
                }else if (destinationOccupant instanceof Monster && Gazoo instanceof Hero){

                    if ( Fight((Fighter) Gazoo, (Fighter) destinationOccupant) == Gazoo){
                        allowMove = true;
                        System.out.println(Gazoo.getName() + " has collected " + destinationOccupant.getName() + "'s bounty of 10 gold");
                        enemiesDefeated += 1;
                        bounty +=10;

                    }else {
                        allowMove = false;
                        isGameOver();
                    }
                }
                if (destinationOccupant instanceof Healer && Gazoo instanceof Explorer && !(Gazoo instanceof Hero)){
                    Healer h = (Healer) destinationOccupant;
                    h.Heal(Gazoo);
                    System.out.println(ConsoleColors.BLUE + Gazoo.getName() + " was healed by " + h.getName() + " for an increase of " + h.getHealValue() + " health. " + Gazoo.getName() + " has " + Gazoo.getHealth() + " health left." + ConsoleColors.RESET);
                }else if (destinationOccupant instanceof Healer && Gazoo instanceof Hero){
                    Healer h = (Healer) destinationOccupant;
                    h.Heal(Gazoo);
                    System.out.println(ConsoleColors.BLUE + Gazoo.getName() + " was healed by " + h.getName() + " for an increase of " + h.getHealValue() + " health. " + Gazoo.getName() + " has " + Gazoo.getHealth() + " health left." + ConsoleColors.RESET);

                    boolean placed = false;
                    while (!placed){
                        int randRow = ThreadLocalRandom.current().nextInt(rows);
                        int randCol = ThreadLocalRandom.current().nextInt(columns);
                        Space newspace = board.get(randRow).get(randCol);
                        if (emptySpace(newspace)){
                            newspace.setOccupant(h);
                            placed = true;
                        }
                    }

                }
                if (destinationCache != null){
                    Gazoo.addTreasure(destinationCache);
                    remainingTreasures.remove(destinationCache);
                    System.out.println(ConsoleColors.YELLOW + Gazoo.getName() + " found " + destinationCache.getDescription().toLowerCase() + " worth " + destinationCache.getValue() + ". There are " + remainingTreasures.size() + " treasures left on the board." + ConsoleColors.RESET);
                    destinationSpace.setCache(null);
                }

                if(allowMove){
                    destinationSpace.setOccupant(occupant);
                    space.setOccupant(null);
                    gazooCol = newGazooCol;
                    gazooRow = newGazooRow;
                }

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
