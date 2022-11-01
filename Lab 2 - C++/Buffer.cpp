//
// Created by night on 11/1/2022.
//

#include "Buffer.h"
#include <iostream>
#include <thread>
Buffer::Buffer() {

}

int Buffer::get() {

    std::unique_lock<std::mutex> lock(mutex);

    while(queue.empty()){
        std::cout<<"Thread with id " << std::this_thread::get_id()<<" : Buffer is currently empty"<<"\n";
        readyToReceiveProduct.wait(lock);
    }

    int value = queue.front();
    queue.pop();

    std::cout<<"Thread with id " <<std::this_thread::get_id()<<" extracted value " << value << " from the queue\n";
    readyToSendProduct.notify_one();

    return value;
}

void Buffer::put(int val) {

    std::unique_lock<std::mutex> lock(mutex);

    while(queue.size() == 1) {
        std::cout<<"Thread with id " << std::this_thread::get_id() << " : Queue is currently full" << "\n";
        readyToSendProduct.wait(lock);
    }

    queue.push(val);

    std::cout<<"Thread with id " << std::this_thread::get_id()<<" added value " << val << " to the queue\n";
    readyToReceiveProduct.notify_one();

}
