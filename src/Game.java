import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public static void promptEnterKey(){
        System.out.println("Naciśnij \"ENTER\" aby kontynuować...");
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
    }
    public static void introduction() {
        System.out.println("Witaj w grze w statki!");
        System.out.println("Legenda:");
        System.out.println("'-' pole puste");
        System.out.println(Board.YELLOW + "'*' pole na którym jest statek" + Board.RESET);
        System.out.println(Board.RED + "'#' pole nietrafione" + Board.RESET);
        System.out.println(Board.GREEN + "'+' pole trafione" + Board.RESET);
        System.out.println(Board.CYAN + "'?' pole nieznane" + Board.RESET);
        Game.promptEnterKey();
    }
    public static void play() {
        Board playerBoard = new Board();
        Board enemyBoard = new Board();

        playerBoard.display(true);

        Ship playerShips = new Ship(new ArrayList<>());
        Ship enemyShips = new Ship(new ArrayList<>());

        System.out.println("Ustaw czteromasztowca np. e2h");
        playerShips.createShip(4, playerBoard, true);

        for (int i = 0; i < 2; i++) {
            System.out.println("Podaj pozycję trójmasztowca np. d6h");
            playerShips.createShip(3, playerBoard, true);
        }
        for (int i = 0; i < 3; i++) {
            System.out.println("Podaj pozycję łodzi podwodnej dwumasztowca np. f5v");
            playerShips.createShip(2, playerBoard, true);
        }
        for (int i = 0; i < 4; i++) {
            System.out.println("Podaj pozycję niszczyciela jednomasztowca np. b1");
            playerShips.createShip(1, playerBoard, true);
        }

        System.out.println("Twoja plansza:");
        playerBoard.display(true);
        Game.promptEnterKey();

        enemyShips.createShip(4, playerBoard, false);
        for (int i = 0; i < 2; i++) {
            enemyShips.createShip(3, enemyBoard, false);
        }
        for (int i = 0; i < 3; i++) {
            enemyShips.createShip(2, enemyBoard, false);
        }
        for (int i = 0; i < 4; i++) {
            enemyShips.createShip(1, enemyBoard, false);
        }

        while (!playerBoard.gameOver(true) && !enemyBoard.gameOver(false)) {
            enemyBoard.shoot(true);
            Game.promptEnterKey();
            playerBoard.shoot(false);
            Game.promptEnterKey();
        }

        System.out.println("Koniec gry!");
    }
}
