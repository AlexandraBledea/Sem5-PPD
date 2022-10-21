//
// Created by MSI on 10/17/2022.
//

#include <iostream>
#include "Sale.h"

void Sale::sellProduct(Product *product) {

    int quantity = this->inventory->getQuantityOfProduct(product);
    this->inventory->removeProduct(product, quantity);
    this->profit += product->getPrice() * quantity;
}

long double Sale::getProfit() const {
    return this->profit;
}

void Sale::run() {
    for (const auto &item: this->inventorySubset->getProducts()) {
        sellProduct(item);
    }
}

Sale::Sale(Inventory *inventory1, Inventory *inventorySubset1) {
    inventory = inventory1;
    inventorySubset = inventorySubset1;
    profit = 0;
}
