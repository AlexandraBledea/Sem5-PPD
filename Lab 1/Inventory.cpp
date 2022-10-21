//
// Created by MSI on 10/17/2022.
//

#include <iostream>
#include "Inventory.h"

std::vector<Product*> Inventory::getProducts() {

    Inventory::lock.lock();

    std::vector<Product*> keys;
    for (auto & product : products) {
        keys.push_back(product.first);
    }

    Inventory::lock.unlock();

    return keys;
}

bool Inventory::containsProduct(Product *product) {

    Inventory::lock.lock();

    bool result = false;
    if(products.count(product) == 1)
        result = true;

    Inventory::lock.unlock();

    return result;
}

int Inventory::getQuantityOfProduct(Product* product) {

    mutexes[product]->lock();

    int quantity = 0;

    if(products.count(product) == 1){
            quantity = products[product];
    }

    mutexes[product]->unlock();

    return quantity;
}

long double Inventory::computeValue() {

    Inventory::lock.lock();
    long double totalPrice = 0;

    for(auto & prod: products){
        totalPrice += prod.first->getPrice() * prod.second;
    }

    Inventory::lock.unlock();

    return totalPrice;
}

void Inventory::addProduct(Product* product, int quantity) {

    Inventory::lock.lock();

    if(products.count(product) == 0){
        products[product] = quantity;
        mutexes[product] = new std::mutex();
    }

    Inventory::lock.unlock();

}

void Inventory::removeProduct(Product* product, int quantity) {

    mutexes[product]->lock();

    int previousQuantity = products[product];

    products[product] = previousQuantity - quantity;

    mutexes[product]->unlock();

}

Inventory::Inventory() {

}
