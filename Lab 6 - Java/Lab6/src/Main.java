import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    private static final int THREAD_COUNT = 5;

    private static List<List<Integer>> generateRandomGraph(Integer nrVertices, Integer nrEdges) throws Exception {

        if(nrVertices * (nrVertices - 1) < nrEdges){
            throw new Exception("Invalid number of edges for the graph!");
        }

        List<List<Integer>> graph = new ArrayList<>();
        for(int i = 0; i < nrVertices; i++){
            graph.add(new ArrayList<>());
        }

        Random random = new Random();
        int index = 1;
        while(index <= nrEdges){

            int nodeA = random.nextInt(nrVertices);
            int nodeB = random.nextInt(nrVertices);

            if(!graph.get(nodeA).contains(nodeB)){
                graph.get(nodeA).add(nodeB);
                index++;
            }
        }

        return graph;
    }

    private static List<List<Integer>> loadGraph(String path) throws FileNotFoundException {
        List<List<Integer>> graph = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(path))) {
            int size = Integer.parseInt(scanner.nextLine());
            for(int i = 0; i < size; i++){
                graph.add(new ArrayList<>());
            }
            while (scanner.hasNextLine()){
                String[] splitEdge = scanner.nextLine().split(" ");
                graph.get(Integer.parseInt(splitEdge[0])).add(Integer.parseInt(splitEdge[1]));
            }
        }

        return graph;
    }
    public static void main(String[] args) throws Exception {
//        List<List<Integer>> graph = loadGraph("graphs/g1.txt");

        List<List<Integer>> graph = generateRandomGraph(5, 10);
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        AtomicBoolean foundHamiltonianCycle = new AtomicBoolean(false);
        List<Integer> output = new ArrayList<>();

        for(int i = 0; i < graph.size(); i++){
            executorService.submit(new HamiltonianCycleSearchTask(graph, i, foundHamiltonianCycle, output));
        }

        executorService.shutdown();
        executorService.awaitTermination(20, TimeUnit.SECONDS);

        if(foundHamiltonianCycle.get()){
            System.out.println(output);
        }
        else {
            System.out.println("No hamiltonian cycles");
        }


    }
}