//
// Created by MSI on 10/17/2022.
//

#pragma once
#include <string>


class Product {

private:
    std::string name;
    int price;

public:

    Product(): price{0}, name{ "" }{}

    Product(const std::string& name, int price);

    void setPrice(int price);

    void setName(std::string name);

    const int& getPrice();

    const std::string& getName();

    bool operator==(const Product& p1)
    {
        return p1.price == this->price && p1.name == this->name;
    }

};

