import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> v1 = new ArrayList<>();
        ArrayList<Integer> v2 = new ArrayList<>();

        for(int i = 0; i < 100; i++){
            v1.add(1);
            v2.add(i+1);
        }

        Buffer buffer = new Buffer();
        Producer producer = new Producer(buffer,v1,v2);
        Consumer consumer = new Consumer(buffer,v1.size());

        producer.start();
        consumer.start();
    }
}