//
// Created by MSI on 10/17/2022.
//

#pragma once

#include <unordered_map>
#include "Product.h"
#include <mutex>
#include <vector>

class Inventory {

private:
    std::unordered_map<Product*, int> products;
    std::unordered_map<Product*, std::mutex*> mutexes;


public:

    Inventory();

    std::mutex lock;

    std::vector<Product*> getProducts();

    bool containsProduct(Product *product);

    int getQuantityOfProduct(Product* product);

    long double computeValue();

    void addProduct(Product* product, int quantity);

    void removeProduct(Product* product, int quantity);


};

