import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Ship {
    private final List<Field> shipFields;
    private int numberOfMasts;
    private char direction;

    public char getDirection() {
        return direction;
    }

    public Ship(List<Field> shipFields) {
        this.shipFields = shipFields;
    }

    public List<Field> getShipFields() {
        return shipFields;
    }

    public int getNumberOfMasts() {
        return numberOfMasts;
    }

    public void addFields(Field field) {
        shipFields.add(field);
    }

    public void createShip(int numberOfMasts, Board board, boolean player) {
        Field field;
        boolean noErrors = true;
        this.numberOfMasts = numberOfMasts;
        if (player) {
            Scanner scan = new Scanner(System.in);
            int hPosition, vPosition = 0;
            char vPositionChar, hPositionChar;
            String position;

            do {
                noErrors = true;
                position = scan.nextLine();
                vPositionChar = position.charAt(0);
                hPositionChar = position.charAt(1);
                hPosition = Character.getNumericValue(hPositionChar);

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

                if (numberOfMasts != 1) {
                    this.direction = position.charAt(2);
                }
                try {
                    if (board.boardMatrix[hPosition][vPosition].getFieldStatus() == FieldStatus.SHIP
                            || board.boardMatrix[hPosition + 1][vPosition].getFieldStatus() == FieldStatus.SHIP
                            || board.boardMatrix[hPosition - 1][vPosition].getFieldStatus() == FieldStatus.SHIP
                            || board.boardMatrix[hPosition][vPosition + 1].getFieldStatus() == FieldStatus.SHIP
                            || board.boardMatrix[hPosition][vPosition - 1].getFieldStatus() == FieldStatus.SHIP) {
                        System.out.println("To pole jest zajęte!");
                        noErrors = false;
                    }
                } catch(ArrayIndexOutOfBoundsException e) {
                    System.out.println("");
                }

                if (noErrors) {
                    field = new Field(hPosition, vPosition, FieldStatus.SHIP);
                    this.shipFields.add(field);
                    board.placeShips(field, this, true);
                }
            } while (!noErrors);
        } else {
            Random rand = new Random();
            int hPosition, vPosition, directionInteger;

            do {
                noErrors = true;

                vPosition = rand.nextInt(10);
                hPosition = rand.nextInt(10);

                if (numberOfMasts != 1) {
                    directionInteger = rand.nextInt(2);

                    switch (directionInteger) {
                        case 0 -> this.direction = 'h';
                        case 1 -> this.direction = 'v';
                    }
                }
                try {
                    if (hPosition != 9 && vPosition != 9) {
                        if (board.boardMatrix[hPosition][vPosition].getFieldStatus() == FieldStatus.SHIP
                                || board.boardMatrix[hPosition + 1][vPosition].getFieldStatus() == FieldStatus.SHIP
                                || board.boardMatrix[hPosition - 1][vPosition].getFieldStatus() == FieldStatus.SHIP
                                || board.boardMatrix[hPosition][vPosition + 1].getFieldStatus() == FieldStatus.SHIP
                                || board.boardMatrix[hPosition][vPosition - 1].getFieldStatus() == FieldStatus.SHIP) {
                            noErrors = false;
                        }
                    }
                } catch(ArrayIndexOutOfBoundsException e) {
                    System.out.println();
                }

                if (noErrors) {
                    field = new Field(hPosition, vPosition, FieldStatus.SHIP);
                    this.shipFields.add(field);
                    board.placeShips(field, this, false);
                }
            } while (!noErrors);
        }
    }
}
