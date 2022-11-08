import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public abstract class ThreadMatrix extends Thread{

    protected final List<AbstractMap.SimpleEntry<Integer, Integer>> elems;

    protected final int startRow, startColumn, positionsToFill;

    protected final Matrix matrix1, matrix2, result;

    protected int K;

    public ThreadMatrix(int startRow, int startColumn, int positionsToFill, Matrix matrix1, Matrix matrix2, Matrix result) {
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.positionsToFill = positionsToFill;
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.result = result;

        this.elems = new ArrayList<>();
        computeElements();
    }

    public ThreadMatrix(int startRow, int startColumn, int positionsToFill, Matrix matrix1, Matrix matrix2, Matrix result, int K) {
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.positionsToFill = positionsToFill;
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.result = result;
        this.K = K;

        this.elems = new ArrayList<>();
        computeElements();
    }

    protected abstract void computeElements();


    @Override
    public void run() {
        for (AbstractMap.SimpleEntry<Integer, Integer> elem : elems) {
            int row = elem.getKey();
            int col = elem.getValue();
            result.setElemOnPos(row, col, InitializeTask.computeMatrixElement(matrix1, matrix2, row, col));
        }
    }
}
