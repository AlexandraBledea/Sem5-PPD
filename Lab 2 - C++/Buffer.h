//
// Created by night on 11/1/2022.
//

#pragma once


#include <mutex>
#include <condition_variable>
#include <queue>
class Buffer {

private:
    std::mutex mutex;
    std::condition_variable readyToSendProduct;
    std::condition_variable readyToReceiveProduct;
    std::queue<int> queue;

public:
    Buffer();

    int get();

    void put(int val);
};

