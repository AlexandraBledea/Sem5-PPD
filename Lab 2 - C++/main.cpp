
#include <iostream>
#include <thread>
#include <vector>
#include "Buffer.h"

std::vector<int> vector1{1, 2, 3, 4, 5, 10, 20};
std::vector<int> vector2{2, 2, 2, 2, 2, 10, 20};
int sum = 0;

void consumer(Buffer* buffer){
    for(int i = 0; i < vector1.size(); i++){
        int value = buffer->get();
        sum += value;
        std::cout<<"Consumer: sum is " << sum << "\n";
    }

    std::cout<<"The final sum is " << sum << "\n";
}

void producer(Buffer* buffer){
    for(int i = 0; i < vector1.size(); i++){
        int val1 = vector1[i];
        int val2 = vector2[i];
        std::cout<<"Consumer: sending " << val1 << " * " << val2 << " = " << val1 * val2 << "\n";
        int result = val1 * val2;
        buffer->put(result);
    }
}

int main ()
{
    auto* buffer = new Buffer();
    std::thread Consumer(consumer, buffer);
    std::thread Producer(producer, buffer);

    Consumer.join();
    Producer.join();

    return 0;
}