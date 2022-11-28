import java.util.Random;
import java.util.Scanner;

public class Board {
    static final String RESET = "\033[0m";
    static final String RED = "\033[0;31m";
    static final String GREEN = "\033[0;32m";
    static final String YELLOW = "\033[0;33m";
    static final String CYAN = "\033[0;36m";
    private final int sizeX, sizeY;
    public Field[][] boardMatrix;

    public Board() {
        this.sizeX = 10;
        this.sizeY = 10;
        create();
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void create() {
        boardMatrix = new Field[this.getSizeX()][this.getSizeY()];

        for (int i = 0; i < this.getSizeX(); i++) {
            for (int j = 0; j < this.getSizeY(); j++) {
                boardMatrix[i][j] = new Field(i, j, FieldStatus.EMPTY);
            }
        }
    }

    public void display(boolean player) {
        System.out.println("  A B C D E F G H I J");
        for (int i = 0; i < this.getSizeX(); i++) {
            System.out.print(i + " ");
            for (int j = 0; j < this.getSizeY(); j++) {
                if (this.boardMatrix[i][j].getMark(player) == '?') {
                    System.out.print(CYAN + this.boardMatrix[i][j].getMark(player) + " " + RESET);
                } else if (this.boardMatrix[i][j].getMark(player) == '*') {
                    System.out.print(YELLOW + this.boardMatrix[i][j].getMark(player) + " " + RESET);
                } else if (this.boardMatrix[i][j].getMark(player) == '-') {
                    System.out.print(this.boardMatrix[i][j].getMark(player) + " ");
                } else if (this.boardMatrix[i][j].getMark(player) == '#') {
                    System.out.print(RED + this.boardMatrix[i][j].getMark(player) + " " + RESET);
                } else if (this.boardMatrix[i][j].getMark(player) == '+') {
                    System.out.print(GREEN + this.boardMatrix[i][j].getMark(player) + " " + RESET);
                }
            }
            System.out.println();
        }
    }

    public void update(Ship ship, boolean player) {
        for (int i = 0; i < this.getSizeX(); i++) {
            for (int j = 0; j < this.getSizeY(); j++) {
                for (int k = 0; k < ship.getShipFields().size(); k++) {
                    if (this.boardMatrix[i][j].getX() == ship.getShipFields().get(k).getX() && this.boardMatrix[i][j].getY()
                    == ship.getShipFields().get(k).getY()) {
                        this.boardMatrix[i][j].setFieldStatus(FieldStatus.SHIP);
                    }
                }
            }
        }
        if (player) {
            this.display(true);
        }
    }

    public void placeShips(Field field,Ship ship, boolean player) {
        switch (ship.getNumberOfMasts()) {
            case 1 -> {
                field.setFieldStatus(FieldStatus.SHIP);
                ship.addFields(field);
            }
            case 2 -> {
                field.setFieldStatus(FieldStatus.SHIP);
                ship.addFields(field);
                if (ship.getDirection() == 'h') {
                    ship.addFields(new Field(field.getX(), field.getY() + 1, FieldStatus.SHIP));
                } else {
                    ship.addFields(new Field(field.getX() + 1, field.getY(), FieldStatus.SHIP));
                }
            }
            case 3 -> {
                field.setFieldStatus(FieldStatus.SHIP);
                ship.addFields(field);
                if (ship.getDirection() == 'h') {
                    ship.addFields(new Field(field.getX(), field.getY() + 1, FieldStatus.SHIP));
                    ship.addFields(new Field(field.getX(), field.getY() + 2, FieldStatus.SHIP));
                } else {
                    ship.addFields(new Field(field.getX() + 1, field.getY(), FieldStatus.SHIP));
                    ship.addFields(new Field(field.getX() + 2, field.getY(), FieldStatus.SHIP));
                }
            }
            case 4 -> {
                field.setFieldStatus(FieldStatus.SHIP);
                ship.addFields(field);
                if (ship.getDirection() == 'h') {
                    ship.addFields(new Field(field.getX(), field.getY() + 1, FieldStatus.SHIP));
                    ship.addFields(new Field(field.getX(), field.getY() + 2, FieldStatus.SHIP));
                    ship.addFields(new Field(field.getX(), field.getY() + 3, FieldStatus.SHIP));
                } else {
                    ship.addFields(new Field(field.getX() + 1, field.getY(), FieldStatus.SHIP));
                    ship.addFields(new Field(field.getX() + 2, field.getY(), FieldStatus.SHIP));
                    ship.addFields(new Field(field.getX() + 3, field.getY(), FieldStatus.SHIP));
                }
            }
        }
        this.update(ship, player);
    }

    public void shoot(boolean player) {
        if (player) {
            boolean noErrors;
            Scanner scan = new Scanner(System.in);
            System.out.println("Podaj pole w które chcesz strzelić (np. d6h)");
            this.display(false);

            int hPosition = 0, vPosition = 0;
            char vPositionChar = 0, hPositionChar;
            String position;

            int numberOfShips = 0;

            for (int i = 0; i < this.getSizeX(); i++) {
                for (int j = 0; j < this.getSizeY(); j++) {
                    if (this.boardMatrix[i][j].getFieldStatus() == FieldStatus.SHIP) {
                        numberOfShips++;
                    }
                }
            }

            System.out.println("Rywalowi zostało " + numberOfShips + " pól!");

            do {
                noErrors = true;
                position = scan.nextLine();
                if (position.length() != 2) {
                    System.out.println("Podano niewłaściwe koordynaty!");
                    System.out.println(position.length());
                    noErrors = false;
                } else {
                    vPositionChar = position.charAt(0);
                    hPositionChar = position.charAt(1);
                    hPosition = Character.getNumericValue(hPositionChar);
                }
                

                switch (vPositionChar) {
                    case 'b' -> vPosition = 1;
                    case 'c' -> vPosition = 2;
                    case 'd' -> vPosition = 3;
                    case 'e' -> vPosition = 4;
                    case 'f' -> vPosition = 5;
                    case 'g' -> vPosition = 6;
                    case 'h' -> vPosition = 7;
                    case 'i' -> vPosition = 8;
                    case 'j' -> vPosition = 9;
                }

                if (hPosition > 9 || hPosition < 0) {
                    System.out.println("Nie ma takiego pola!");
                    noErrors = false;
                } else if (this.boardMatrix[hPosition][vPosition].getFieldStatus() == FieldStatus.HIT
                || this.boardMatrix[hPosition][vPosition].getFieldStatus() == FieldStatus.MISSED) {
                    System.out.println("Już strzelałeś w to pole!");
                    noErrors = false;
                } else if (noErrors) {
                    if (this.boardMatrix[hPosition][vPosition].getFieldStatus() == FieldStatus.SHIP) {
                        this.boardMatrix[hPosition][vPosition].setFieldStatus(FieldStatus.HIT);
                        System.out.println("Trafiłeś!");
                        this.display(false);
                        this.shoot(true);
                    } else if (this.boardMatrix[hPosition][vPosition].getFieldStatus() == FieldStatus.EMPTY) {
                        this.boardMatrix[hPosition][vPosition].setFieldStatus(FieldStatus.MISSED);
                        System.out.println("Nie trafiłeś!");
                        this.display(false);
                    }
                }
            } while(!noErrors);
        } else {
            boolean noErrors;
            Random rand = new Random();

            do {
                noErrors = true;
                int hPosition = rand.nextInt(10);
                int vPosition = rand.nextInt(10);

                if (this.boardMatrix[hPosition][vPosition].getFieldStatus() == FieldStatus.HIT
                        || this.boardMatrix[hPosition][vPosition].getFieldStatus() == FieldStatus.MISSED) {
                    noErrors = false;
                } else {
                    if (this.boardMatrix[hPosition][vPosition].getFieldStatus() == FieldStatus.SHIP) {
                        this.boardMatrix[hPosition][vPosition].setFieldStatus(FieldStatus.HIT);
                        System.out.println("Twój rywal trafił!");
                        this.display(true);
                        this.shoot(false);
                    } else if (this.boardMatrix[hPosition][vPosition].getFieldStatus() == FieldStatus.EMPTY) {
                        this.boardMatrix[hPosition][vPosition].setFieldStatus(FieldStatus.MISSED);
                        System.out.println("Twój rywal nie trafił!");
                        this.display(true);
                    }
                }
            } while (!noErrors);
        }
    }
    public boolean gameOver(boolean player) {
        int count = 0;

        for (int i = 0; i < this.getSizeX(); i++) {
            for (int j = 0; j < this.getSizeY(); j++) {
                if (this.boardMatrix[i][j].getFieldStatus() == FieldStatus.SHIP) {
                    count++;
                }
            }
        }

        if (count == 0) {
            if (player) {
                System.out.println("Przegrałeś!");
            } else {
                System.out.println("Wygrałeś!");
            }
            return true;
        } else {
            return false;
        }
    }
}
