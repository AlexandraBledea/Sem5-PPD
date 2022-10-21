cmake_minimum_required(VERSION 3.23)
project(Lab_2)

set(CMAKE_CXX_STANDARD 14)

add_executable(Lab_2 main.cpp Product.cpp Product.h Inventory.cpp Inventory.h InventoryChecker.cpp InventoryChecker.h Sale.cpp Sale.h)
