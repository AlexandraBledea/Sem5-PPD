import Algorithms.ParallelClassic;
import Algorithms.ParallelKaratsuba;
import Algorithms.SequentialClassic;
import Algorithms.SequentialKaratsuba;
import Model.AlgorithmType;
import Model.MethodType;
import Model.Polynomial;

import java.util.concurrent.ExecutionException;

public class Main {

    private static final MethodType METHOD = MethodType.PARALLEL;

    private static final AlgorithmType ALGORITHM = AlgorithmType.KARATSUBA;
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Polynomial p1 = new Polynomial(100);
        System.out.println("Polynomial p1 = " + p1);

        Polynomial p2 = new Polynomial(100);
        System.out.println("Polynomial p2 = " + p2);

        long startTime = System.nanoTime();

        run(p1, p2);

        long stopTime = System.nanoTime();
        double totalTime = ((double) stopTime - (double) startTime) / 1_000_000_000.0;

        System.out.println("Elapsed running time: " + totalTime + " s");
    }

    private static void run(Polynomial polynomial1, Polynomial polynomial2) throws InterruptedException, ExecutionException {
        Polynomial result = null;
        if(METHOD.equals(MethodType.SEQUENTIAL)){
            if(ALGORITHM.equals(AlgorithmType.CLASSIC)){
                result = SequentialClassic.multiply(polynomial1, polynomial2);
            }
            else if(ALGORITHM.equals(AlgorithmType.KARATSUBA)){
                result = SequentialKaratsuba.multiply(polynomial1, polynomial2);
            }
        } else if (METHOD.equals(MethodType.PARALLEL)){
            if(ALGORITHM.equals(AlgorithmType.CLASSIC)){
                result = ParallelClassic.multiply(polynomial1, polynomial2);
            }
            else if(ALGORITHM.equals(AlgorithmType.KARATSUBA)){
                result = ParallelKaratsuba.multiply(polynomial1, polynomial2, 1);
            }
        }

        System.out.println("p1 * p2 = " + result.toString());
    }
}