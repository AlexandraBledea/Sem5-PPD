//
// Created by MSI on 10/17/2022.
//

#include "Product.h"

#include <utility>

Product::Product(const std::string& name, int price) {
    this->name = name;
    this->price = price;
}

const std::string &Product::getName() {
    return name;
}

void Product::setPrice(int price) {
    this->price = price;
}

void Product::setName(std::string name) {
    this->name = std::move(name);
}

const int &Product::getPrice() {
    return price;
}
