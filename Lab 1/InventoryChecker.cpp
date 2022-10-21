//
// Created by MSI on 10/17/2022.
//

#include "InventoryChecker.h"
#include <iostream>

void InventoryChecker::checkInventory(long double money) {
    lock.lock();

    long double totalSumSale = 0;
    for(auto & sale: sales){
        totalSumSale += sale->getProfit();
    }

    if(money >= totalSumSale){
        std::cout << "Inventory is ok!\n";
    } else
        std::cout << "Invalid inventory!\n";

    lock.unlock();
}

InventoryChecker::InventoryChecker(long double totalInitialValue1, Inventory *inventory1, std::vector<Sale *> sales1) {
    totalInitialValue = totalInitialValue1;
    inventory = inventory1;
    sales = sales1;
}
