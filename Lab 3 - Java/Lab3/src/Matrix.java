import java.util.Random;

public class Matrix {

    private final int rows;

    private final int columns;

    private final int[][] matrix;


    private final int MAX_INITIAL_VALUE = 1;
    private final int MIN_INITIAL_VALUE = 0;

    Matrix(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        this.matrix = new int[rows][columns];
    }

    public void generateRandomMatrix(){

        Random random = new Random();

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                this.matrix[i][j] = 1;
            }
        }
    }


    public void clearMatrix(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                this.matrix[i][j] = 0;
            }
        }
    }

    public int getElemFromPos(int row, int column){
        if(row >= this.rows || column >= this.columns || row < 0 || column < 0){
            throw new IndexOutOfBoundsException("Invalid position!");
        }

        return this.matrix[row][column];
    }

    public void setElemOnPos(int row, int column, int value){
        if(row >= this.rows || column >= this.columns || row < 0 || column < 0){
            throw new IndexOutOfBoundsException("Invalid position!");
        }

        this.matrix[row][column] = value;
    }


    public int getRows(){
        return this.rows;
    }

    public int getColumns(){
        return this.columns;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                builder.append(matrix[i][j]).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

}
