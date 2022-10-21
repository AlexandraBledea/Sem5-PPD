//
// Created by MSI on 10/17/2022.
//

#pragma once

#include "Inventory.h"
#include <mutex>

class Sale {

private:
    Inventory* inventory;
    Inventory* inventorySubset;
    long double profit;
    std::mutex lock;

    void sellProduct(Product* product);

public:

    Sale(Inventory* inventory1, Inventory* inventorySubset1);
    long double getProfit() const;

    void run();

};
