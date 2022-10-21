//
// Created by MSI on 10/17/2022.
//

#pragma once

#include "Inventory.h"
#include "Sale.h"

class InventoryChecker {

public:
    long double totalInitialValue;

    Inventory* inventory;

    std::vector<Sale*> sales;

    std::mutex lock;

    InventoryChecker(long double totalInitialValue1,Inventory* inventory1, std::vector<Sale*> sales1);

    void checkInventory(long double money);

};


