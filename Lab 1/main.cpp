#include <iostream>
#include <thread>
#include <vector>
#include <fstream>
#include <string>
#include <shared_mutex>
#include "Inventory.h"
#include "Sale.h"
#include "InventoryChecker.h"
#include <unistd.h>

using namespace std::chrono_literals;
int count = 0;
const int THREAD_COUNT = 10000;
long double money = 0;
int nrBills = 0;
std::mutex mutex;

Inventory *loadInventory() {
    auto *inventory = new Inventory();

    int currentPrice;
    int currentQuantity;
    std::string currentName;

    std::ifstream readStream(R"(C:\Users\MSI\Desktop\Facultate An 3\Semestru 1\PPD\Lab 2\products.txt)");

    while (!readStream.eof()) {
        readStream >> currentName >> currentQuantity >> currentPrice;
        inventory->addProduct(new Product(currentName, currentPrice), currentQuantity);
    }

    return inventory;
}

Inventory *generateInventorySubsets(Inventory *inventory) {
    int productCount = (rand() % 1000) + 1;

    std::vector<Product *> products = inventory->getProducts();

    auto *inventorySubset = new Inventory();

    for (int i = 0; i < productCount; i++) {
        bool foundNewProduct = false;

        do {
            int productIndex = (rand() % 4000) + 1;
            Product *selectedProduct = products[productIndex];

            if (!inventorySubset->containsProduct(selectedProduct)) {
                foundNewProduct = true;
                int quantity = (rand() % 40) + 1;
                inventorySubset->addProduct(selectedProduct, quantity);
            }

        } while (!foundNewProduct);
    }

    return inventorySubset;
}

void run(Sale *sale) {
    sale->run();
    mutex.lock();
    money += sale->getProfit();
    nrBills++;
    mutex.unlock();
}

void doInventoryCheck(InventoryChecker *inventoryChecker) {
    while (true) {
        if (nrBills % 100 == 0) {
            std::cout<<nrBills<<"\n";
            sleep(0.001);
            inventoryChecker->checkInventory(money);
        }
        if(nrBills == THREAD_COUNT)
            break;
    }
    mutex.unlock();
}

int main() {

    Inventory *inventory = loadInventory();

    long double totalInitialValue = inventory->computeValue();

    std::vector<Sale *> sales;
    std::thread threads[THREAD_COUNT];


    for (int i = 0; i < THREAD_COUNT; i++) {
        sales.push_back(new Sale(inventory, generateInventorySubsets(inventory)));
    }

    auto *inventoryChecker = new InventoryChecker(totalInitialValue, inventory, sales);

    std::thread timerThread(doInventoryCheck, inventoryChecker);

    for (int i = 0; i < THREAD_COUNT; i++) {
        threads[i] = std::thread(run, sales[i]);

    }

    for (int i = 0; i < THREAD_COUNT; i++) {
        threads[i].join();
        count++;
    }
    timerThread.join();

    sleep(4);


    std::cout << "All sales have finished. Result of the final check - ";
    inventoryChecker->checkInventory(money);


    return 0;
}
