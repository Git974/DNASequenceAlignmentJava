package sequencealignment;

class Cell {

    private int value;
    private int row;
    private int col;
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    private Cell from;

    public Cell(int value, int row, int col, Cell from) {
        this.value = value;
        this.row = row;
        this.col = col;
        this.from = from;
        this.flag = false;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Cell getFrom() {
        return from;
    }

    public void setFrom(Cell from) {
        this.from = from;
    }
}