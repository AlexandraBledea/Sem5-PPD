public class InitializeTask {

    public static ThreadMatrix initRowTask(int index, Matrix matrix1, Matrix matrix2, Matrix result, int threadsNumber){
        int positions = result.getRows() * result.getColumns();

        int defaultNumberOfPositionsPerThread = positions / threadsNumber;

        int numberOfThreadsWithExtraPos = positions % threadsNumber;

        int positionsToBeFilled = defaultNumberOfPositionsPerThread;

//        if(index < numberOfThreadsWithExtraPos){
//            positionsToBeFilled++;
//        }


        int startRow = positionsToBeFilled * index / result.getColumns();
        int startCol = positionsToBeFilled * index % result.getColumns();


        if(index == threadsNumber - 1){
            positionsToBeFilled += numberOfThreadsWithExtraPos;
        }

        return new RowTask(startRow, startCol, positionsToBeFilled, matrix1, matrix2, result);

    }



    public static ThreadMatrix initColumnTask(int index, Matrix matrix1, Matrix matrix2, Matrix result, int threadsNumber){

        int positions = result.getRows() * result.getColumns();

        int defaultNumberOfPositionsPerThread = positions / threadsNumber;

        int numberOfThreadsWithExtraPos = positions % threadsNumber;

        int positionsToBeFilled = defaultNumberOfPositionsPerThread;

//        if(index < numberOfThreadsWithExtraPos){
//            positionsToBeFilled++;
//        }

        int startRow = positionsToBeFilled * index % result.getRows();
        int startCol = positionsToBeFilled * index / result.getRows();


        if(index == threadsNumber - 1){
            positionsToBeFilled += numberOfThreadsWithExtraPos;
        }

        return new ColumnTask(startRow, startCol, positionsToBeFilled, matrix1, matrix2, result);

    }

    public static ThreadMatrix initKTask(int index, Matrix matrix1, Matrix matrix2, Matrix result, int threadsNumber){
        int positions = result.getRows() * result.getColumns();

        int defaultNumberOfPositionsPerThread = positions / threadsNumber;

        int numberOfThreadsWithExtraPos = positions % threadsNumber;

        if(index < positions % threadsNumber){
            defaultNumberOfPositionsPerThread++;
        }

        int startRow = index / result.getColumns();
        int startColumn = index % result.getRows();

        return new KTask(startRow, startColumn, defaultNumberOfPositionsPerThread, matrix1, matrix2, result, threadsNumber);
    }


    public static  int computeMatrixElement(Matrix matrix1, Matrix matrix2, int row, int column){
        int result = 0;
        if(row < matrix1.getRows() && column < matrix2.getColumns()){
            int i = 0;
            while(i < matrix1.getColumns()){
                result = result + matrix1.getElemFromPos(row, i) * matrix2.getElemFromPos(i, column);
                i++;
            }
        } else {
            throw new IllegalArgumentException();
        }

        return result;
    }
}
