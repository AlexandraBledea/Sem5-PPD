import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {


    private static final int rowMatrix1 = 10;

    private static final int rowMatrix2 = 10;

    private static final int colMatrix1 = 10;

    private static final int colMatrix2 = 10;

    private static final int THREAD_COUNT = 3;

    private static final ThreadApproach THREAD_APPROACH = ThreadApproach.THREAD_POOL;

    private static final TaskType TASK_TYPE = TaskType.KTH;


    public static void main(String[] args) {
        Matrix matrix1 = new Matrix(rowMatrix1, colMatrix1);
        Matrix matrix2 = new Matrix(rowMatrix2, colMatrix2);

        if(colMatrix1 == rowMatrix2){

            System.out.println("Start -> compute the Matrix!");

            Matrix result = new Matrix(matrix1.getRows(), matrix2.getColumns());

            matrix1.generateRandomMatrix();
            matrix2.generateRandomMatrix();

            double startTime = System.nanoTime();

            switch (THREAD_APPROACH){
                case CLASSIC:
                    classicThreadsCase(matrix1, matrix2, result);
                    break;
                case THREAD_POOL:
                    threadPoolCase(matrix1, matrix2, result);
                    break;
                default:
                    System.out.println("Invalid thread approach!");
                    break;
            }

            double stopTime = System.nanoTime();

            System.out.println("End -> compute the Matrix!");

            System.out.println("Elapsed taken time: " + (stopTime - startTime) / 1_000_000_000.0);

        }
        else{
            System.out.println("Cannot multiply the matrices! Make sure the number of columns from the first one is equal to the number of rows from the second one!\n");
        }

    }

    private static void threadPoolCase(Matrix matrix1, Matrix matrix2, Matrix result) {
        ExecutorService executorService = new ThreadPoolExecutor(
                THREAD_COUNT,
                THREAD_COUNT,
                0L,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(THREAD_COUNT, true));

        switch (TASK_TYPE){
            case ROWS:

                for(int i = 0; i < THREAD_COUNT; i++){
                    executorService.submit((InitializeTask.initRowTask(i, matrix1, matrix2, result, THREAD_COUNT)));
                }
                break;

            case COLUMNS:

                for(int i = 0; i < THREAD_COUNT; i++){
                    executorService.submit((InitializeTask.initColumnTask(i, matrix1, matrix2, result, THREAD_COUNT)));
                }
                break;

            case KTH:
                for(int i = 0; i < THREAD_COUNT; i++){
                    executorService.submit((InitializeTask.initKTask(i, matrix1, matrix2, result, THREAD_COUNT)));
                }
                break;

            default:
                System.out.println("Invalid task type!\n");
                break;
        }

        executorService.shutdown();

        try{
            if(!executorService.awaitTermination(600, TimeUnit.SECONDS)){
                executorService.shutdownNow();
            }

            System.out.println(matrix1);
            System.out.println(matrix2);

            System.out.println("Resulted matrix: ");
            System.out.println(result);

        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

    }

    private static void classicThreadsCase(Matrix matrix1, Matrix matrix2, Matrix result) {

        List<Thread> threads = new ArrayList<>();

        switch (TASK_TYPE){
            case ROWS:

                for(int i = 0; i < THREAD_COUNT; i++){
                    threads.add(InitializeTask.initRowTask(i, matrix1, matrix2, result, THREAD_COUNT));
                }
                break;

            case COLUMNS:

                for(int i = 0; i < THREAD_COUNT; i++){
                    threads.add(InitializeTask.initColumnTask(i, matrix1, matrix2, result, THREAD_COUNT));
                }
                break;

            case KTH:
                for(int i = 0; i < THREAD_COUNT; i++){
                    threads.add(InitializeTask.initKTask(i, matrix1, matrix2, result, THREAD_COUNT));
                }
                break;

            default:
                System.out.println("Invalid task type!\n");
                break;
        }


        for(Thread t: threads){
            t.start();
        }

        for(Thread t: threads){
            try {
                t.join();
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }

        System.out.println("Resulted matrix: ");
        System.out.println(result);

    }
}