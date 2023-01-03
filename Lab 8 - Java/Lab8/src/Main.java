import mpi.MPI;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        DSM dsm = new DSM();

        System.out.println("Start " + rank + " of " + size);

        if(rank == 0){
            Thread thread = new Thread(new Subscriber(dsm));

            thread.start();

            dsm.subscribeTo("first");
            dsm.subscribeTo("second");
            dsm.subscribeTo("third");
            dsm.checkAndReplace("first", 0, 10);
            dsm.checkAndReplace("third", 2, 30);
            dsm.checkAndReplace("second", 100, 20);
            dsm.close();

            thread.join();
        } else if(rank == 1){
            Thread thread = new Thread(new Subscriber(dsm));

            thread.start();

            dsm.subscribeTo("first");
            dsm.subscribeTo("third");

            thread.join();
        } else if (rank == 2){
            Thread thread = new Thread(new Subscriber(dsm));

            thread.start();

            dsm.subscribeTo("second");
            dsm.checkAndReplace("second", 1, 50);

            thread.join();
        }

        MPI.Finalize();
    }
}