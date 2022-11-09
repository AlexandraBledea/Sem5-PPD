import java.util.AbstractMap;

public class KTask extends ThreadMatrix{

    public KTask(int startRow, int startColumn, int positionsToFill, Matrix matrix1, Matrix matrix2, Matrix result, int K){
        super(startRow, startColumn, positionsToFill, matrix1, matrix2, result, K);
    }

    @Override
    protected void computeElements() {
        int currentRow = startRow;
        int currentColumn = startColumn;

        int countPositionsToFill = positionsToFill;

        int noRows = result.getRows();
        int noCols = result.getColumns();

        while(countPositionsToFill > 0 && currentRow < noRows && currentColumn < noCols) {

            elems.add(new AbstractMap.SimpleEntry<>(currentRow, currentColumn));

            countPositionsToFill--;

            currentRow = currentRow + (currentColumn + K) / noCols;
            currentColumn = (currentColumn + K) % noCols;

        }
    }


}
