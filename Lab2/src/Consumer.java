import java.util.List;

public class Consumer extends Thread{

    public Integer sum = 0;
    public Buffer buffer;

    public Integer vectorSize;
    public Consumer(Buffer buffer, Integer vectorSize){
        super("Consumer");
        this.buffer = buffer;
        this.vectorSize = vectorSize;
    }

    @Override
    public void run(){
        for(int i = 0; i < vectorSize; i++){
            try{
                int val = buffer.get();
                sum += val;
                System.out.printf("Consumer: sum is %d\n", sum);

            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        System.out.printf("The final sum is: %d", sum);
    }

}
