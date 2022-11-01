import java.util.List;

public class Producer extends Thread{

    public Buffer buffer;

    public List<Integer> vector1, vector2;

    public Producer(Buffer buffer, List<Integer> vector1, List<Integer> vector2){
        super("Producer");
        this.buffer = buffer;
        this.vector1 = vector1;
        this.vector2 = vector2;
    }

    @Override
    public void run(){
        for(int i = 0; i < vector1.size(); i++){
            try{

                Integer val1 = vector1.get(i);
                Integer val2 = vector2.get(i);
                System.out.printf("Producer: Sending %d * %d = %d\n", val1, val2, val1 * val2);
                buffer.put(val1*val2);

            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
