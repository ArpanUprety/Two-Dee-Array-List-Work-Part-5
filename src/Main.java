import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static final Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {
        board b2 = new board(8,9);
        boardLoop(b2);
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