public class Field {
    private final int x, y;
    private FieldStatus fieldStatus;

    public Field(int x, int y, FieldStatus fieldStatus) {
        this.x = x;
        this.y = y;
        this.fieldStatus = fieldStatus;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public FieldStatus getFieldStatus() {
        return fieldStatus;
    }

    public void setFieldStatus(FieldStatus fieldStatus) {
        this.fieldStatus = fieldStatus;
    }

    public char getMark(boolean player) {
        char result = 0;

        switch (fieldStatus) {
            case EMPTY -> result = '-';
            case SHIP -> result = '*';
            case MISSED -> result = '#';
            case HIT -> result = '+';
            case UNKNOWN -> result = '?';
        }

        if (!player) {
            if (result == '-' || result == '*') {
                result = '?';
            }
        }

        return result;
    }
}
