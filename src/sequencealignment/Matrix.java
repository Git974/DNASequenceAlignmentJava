package sequencealignment;

class Matrix{

    private String sequenceA;
    private String sequenceB;
    private StringBuilder alignedSequenceA;
    private StringBuilder alignedSequenceB;
    private int match;
    private int mismatch;
    private int gap;
    private int rows;
    private int cols;
    private Cell[][] matrix;

    public Matrix(String sequenceA, String sequenceB) {
        this.sequenceA = sequenceA;
        this.sequenceB = sequenceB;
        this.match = 1;
        this.mismatch = -1;
        this.gap = -2;
        this.rows = sequenceA.length() + 1;
        this.cols = sequenceB.length() + 1;

        matrix = new Cell[rows][cols];
    }

    public int getMatch() {
        return match;
    }

    public void setMatch(int match) {
        this.match = match;
    }

    public int getMismatch() {
        return mismatch;
    }

    public void setMismatch(int mismatch) {
        this.mismatch = mismatch;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public Cell[][] getMatrix() {
        return matrix;
    }

    public void initializeMatrix(){
        for (int row = 0; row < this.matrix.length; row++) {
            int value = row * this.gap;
            matrix[row][0] = new Cell(value, row, 0, null);
        }
        for (int col = 0; col < this.matrix[0].length; col++) {
            int value = col * this.gap;
            matrix[0][col] = new Cell(value, col, 0, null);
        }
    }

    public void fillScoreMatrix(){
        for (int j = 1; j < this.cols; j++) {
            for (int i = 1; i < this.rows; i++) {

                int value1 = matrix[i-1][j-1].getValue() + (this.sequenceA.charAt(i-1) == sequenceB.charAt(j-1) ? this.match : this.mismatch);
                int value2 = matrix[i][j-1].getValue() + this.gap;
                int value3 = matrix[i-1][j].getValue() + this.gap;

                if((value1 >= value2) && (value1 >= value3)){
                    Cell cell = this.matrix[i-1][j-1];
                    this.matrix[i][j] = new Cell(value1, i, j, cell);
                }
                else if((value2 >= value1) && (value2 >= value3)){
                    Cell cell = this.matrix[i][j-1];
                    this.matrix[i][j] = new Cell(value2, i, j, cell);
                }
                else if((value3 >= value1) && (value3 >= value2)){
                    Cell cell = this.matrix[i-1][j];
                    this.matrix[i][j] = new Cell(value3, i, j, cell);
                }
            }
        }
    }
    
    public String[] getAlignedSequences(){
        String[] sequences = new String[2];
        sequences[0] = this.alignedSequenceA.reverse().toString();
        sequences[1] = this.alignedSequenceB.reverse().toString();
        
        return sequences;
    }

    public void matrixTraceback(){
        System.out.println(rows + " " + cols);
        Cell tail = this.matrix[rows-1][cols-1];
        this.alignedSequenceA = new StringBuilder();
        this.alignedSequenceB = new StringBuilder();
        int row = tail.getRow();
        int col = tail.getCol();
        
        while(tail != null){
            System.out.print(tail.getRow());
            System.out.println(tail.getCol());
            tail.setFlag(true);
            
            if(row != tail.getRow() && col != tail.getCol()){
                if(tail.getRow() == 0 && tail.getCol() == 0){
                    
                }
                else if(tail.getRow() == 0){
                    this.alignedSequenceA.append("_");
                    this.alignedSequenceB.append(this.sequenceB.charAt(tail.getCol()-1));
                }
                else if(tail.getCol() == 0){
                    this.alignedSequenceA.append(this.sequenceA.charAt(tail.getRow()-1));
                    this.alignedSequenceB.append("_");
                }
                else{
                    int trow = tail.getFrom().getRow();                
                    int tcol = tail.getFrom().getCol();
                    System.out.println("Outer Handler " + "trow:" + trow + "crow:" + row);                    
                    System.out.println("Outer Handler " + "tcol:" + tcol + "ccol:" + col);
//                    row = tail.getRow();
//                    col = tail.getCol();

                    if(trow != tail.getRow() && tcol == tail.getCol()){
                        this.alignedSequenceA.append(this.sequenceA.charAt(tail.getRow()-1));
                        this.alignedSequenceB.append("_");
                        row = tail.getRow();
                        System.out.println("Inner Handler");
                    }else if(trow == tail.getRow() && tcol != tail.getCol()){
                        this.alignedSequenceA.append("_");
                        this.alignedSequenceB.append(this.sequenceB.charAt(tail.getCol()-1));
                        col = tail.getCol();
                    }else{
                        this.alignedSequenceA.append(this.sequenceA.charAt(tail.getRow()-1));
                        this.alignedSequenceB.append(this.sequenceB.charAt(tail.getCol()-1));
                        row = tail.getRow();
                        col = tail.getCol();
                    }                 
                }
            }
            else if(row != tail.getRow() && col == tail.getCol()){
                if(tail.getRow() == 0){
                    this.alignedSequenceA.append(this.sequenceA.charAt(tail.getRow()));
                    this.alignedSequenceB.append("_");
                }else{
                    this.alignedSequenceA.append(this.sequenceA.charAt(tail.getRow()-1));
                    this.alignedSequenceB.append("_");
                }
                
                row = tail.getRow();
            }
            else if(row == tail.getRow() && col != tail.getCol()){
                if(tail.getCol() == 0){
                    this.alignedSequenceA.append("_");
                    this.alignedSequenceB.append(this.sequenceB.charAt(tail.getCol()-1));
                }else{
                    this.alignedSequenceA.append("_");
                    this.alignedSequenceB.append(this.sequenceB.charAt(tail.getCol()-1));
                }
                
                col = tail.getCol();
            }else if(tail.getFrom() != null) {
                int trow = tail.getFrom().getRow();                
                int tcol = tail.getFrom().getCol();

                if(trow != row && tcol == col){
                    this.alignedSequenceA.append(this.sequenceA.charAt(tail.getRow()-1));
                    this.alignedSequenceB.append("_");
                    row = tail.getFrom().getRow();
                }else if(trow == row && tcol != col){
                    this.alignedSequenceA.append("_");
                    this.alignedSequenceB.append(this.sequenceB.charAt(tail.getCol()-1));
                    col = tail.getFrom().getCol();
                }else{
                   this.alignedSequenceA.append(this.sequenceA.charAt(tail.getRow()-1));
                   this.alignedSequenceB.append(this.sequenceB.charAt(tail.getCol()-1));
                }            
            }         
            tail = tail.getFrom();
        }
        row = row - 2;
        col = col - 2;
        if(row > 0){
            row = row - 1;
            System.out.println("Remaining Sequence A and row=" + row);
            this.alignedSequenceA.append(this.sequenceA.charAt(row));
            this.alignedSequenceB.append("_");
        }else if(col > 0){
            System.out.println("Remaining Sequence B and col=" + col);
        }
    }


}