package Algorithms;

import Model.Polynomial;
import Utils.PolynomialUtils;

import java.util.concurrent.*;

public class ParallelKaratsuba {

    private static final int NO_THREADS = 4;

    private static final int MAX_DEPTH = 4;

    public static Polynomial multiply(Polynomial p1, Polynomial p2, int currentDepth) throws InterruptedException, ExecutionException {

        if(currentDepth > MAX_DEPTH) {
            return SequentialKaratsuba.multiply(p1, p2);
        }

        if(p1.getDegree() < 2 || p2.getDegree() < 2) {
            return SequentialKaratsuba.multiply(p1, p2);
        }

        int len = Math.max(p1.getDegree(), p2.getDegree()) / 2;

        Polynomial lowP1 = new Polynomial(p1.getCoefficients().subList(0, len));
        Polynomial highP1 = new Polynomial(p1.getCoefficients().subList(len, p1.getCoefficients().size()));

        Polynomial lowP2 = new Polynomial(p2.getCoefficients().subList(0, len));
        Polynomial highP2 = new Polynomial(p2.getCoefficients().subList(len, p2.getCoefficients().size()));

        ExecutorService executorService = Executors.newFixedThreadPool(NO_THREADS);

        Future<Polynomial> f1 = executorService.submit(() -> multiply(lowP1, lowP2, currentDepth + 1));
        Future<Polynomial> f2 = executorService.submit(() -> multiply(PolynomialUtils.add(lowP1, highP1), PolynomialUtils.add(lowP2, highP2), currentDepth + 1));
        Future<Polynomial> f3 = executorService.submit(() -> multiply(highP1, highP2, currentDepth + 1));

        executorService.shutdown();

        Polynomial z1 = f1.get();
        Polynomial z2 = f2.get();
        Polynomial z3 = f3.get();

        executorService.awaitTermination(60, TimeUnit.SECONDS);

        // Compute the final result

        Polynomial r1 = PolynomialUtils.addZeros(z3, 2 * len);
        Polynomial r2 = PolynomialUtils.addZeros(PolynomialUtils.subtract(PolynomialUtils.subtract(z2, z3), z1), len);
        Polynomial result = PolynomialUtils.add(PolynomialUtils.add(r1, r2), z1);

        return result;

    }
}
