import java.util.LinkedList;
import java.util.Queue;import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.sql.Types.NULL;

public class Buffer {

    private final Queue<Integer> queue = new LinkedList<>();
    private final Lock lock = new ReentrantLock();

    private final Condition readyToSendProduct = lock.newCondition();

    private final Condition readyToReceiveProduct = lock.newCondition();

    private Integer partialResult = NULL;

    public Buffer(){

    }
    public int get() throws InterruptedException{

        lock.lock();

        try{
            while(partialResult == NULL){
                System.out.println(Thread.currentThread().getName() + ": Buffer is currently empty");
                readyToReceiveProduct.await();
            }

            Integer value = queue.poll();
            if(value != null){
                partialResult = NULL;
                System.out.printf("%s extracted value %d from the queue\n", Thread.currentThread().getName(), value);
                readyToSendProduct.signal();
            }
            return value;
        } finally {
            lock.unlock();
        }
    }

    public void put(int val) throws InterruptedException{

        lock.lock();

        try{
            while (partialResult != NULL){
                System.out.println(Thread.currentThread().getName() + ": Queue is currently full");
                readyToSendProduct.await();
            }

            queue.add(val);
            partialResult = val;
            System.out.printf("%s added value %d to the queue\n", Thread.currentThread().getName(), val);
            readyToReceiveProduct.signal();

        }
        finally {
            lock.unlock();
        }
    }



}
