import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static final Scanner keyboard = new Scanner(System.in);
    static  Hero g = new Hero("Gazee", 20, ConsoleColors.GREEN);
    static Explorer d = new Explorer("Gazoo", 20, ConsoleColors.GREEN);

    public static void main(String[] args) {
        System.out.print("Enter 1 if you want to play with an explorer, enter 2 if you want to play with a hero --> ");
        int num = keyboard.nextInt();

        if (num == 1) {
            System.out.println(" You Choose Explorer");
            board b2 = new board(6, 6, d);
            boardLoop(b2);
        } else if (num == 2) {
            System.out.println(" You Choose Hero");
            board b2 = new board();
            boardLoop(b2);
        }




    }


    public static void boardLoop(board b){
        b.printBoard(true);
        //for spacing
        System.out.println();

        do{
            System.out.print("Enter \"w\" to go up, \"a\" to go left, \"s\" to go down, \"d\" to go right, \"r\" to print the revealed board, and \"i\" to print the number of treasures found and their value.\n>: ");
            char userIn = keyboard.next().charAt(0);
            //for spacing
            System.out.println();
            b.move(userIn);
            b.printBoard(true);
            //for spacing
            System.out.println();
        }while (!b.isGameOver());
    }
}