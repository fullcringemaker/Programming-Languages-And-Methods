-------------------------------------------------- declaration.h --------------------------------------------------
#ifndef DECLARATION_H
#define DECLARATION_H

class Matrix {
private:
    int** data;
    int size;

public:
    Matrix(int size);
    Matrix(const Matrix& other);
    virtual ~Matrix();
    Matrix& operator=(const Matrix& rhs);

    int getSize() const;
    int& elementAt(int row, int col);
    const int& elementAt(int row, int col) const;
    bool isSaddlePoint(int row, int col) const;
};

#endif 


-------------------------------------------------- implementation.cpp --------------------------------------------------
#include "declaration.h"
#include <algorithm>
#include <stdexcept>

Matrix::Matrix(int size) : size(size), data(new int* [size]) {
    for (int i = 0; i < size; ++i) {
        data[i] = new int[size];
        std::fill(data[i], data[i] + size, 0);
    }
}

Matrix::Matrix(const Matrix& other) : size(other.size), data(new int* [size]) {
    for (int i = 0; i < size; ++i) {
        data[i] = new int[size];
        std::copy(other.data[i], other.data[i] + size, data[i]);
    }
}

Matrix::~Matrix() {
    for (int i = 0; i < size; ++i) {
        delete[] data[i];
    }
    delete[] data;
}

Matrix& Matrix::operator=(const Matrix& rhs) {
    if (this != &rhs) {
        for (int i = 0; i < size; ++i) {
            delete[] data[i];
        }
        delete[] data;
        size = rhs.size;
        data = new int* [size];
        for (int i = 0; i < size; ++i) {
            data[i] = new int[size];
            std::copy(rhs.data[i], rhs.data[i] + size, data[i]);
        }
    }
    return *this;
}

int Matrix::getSize() const {
    return size;
}

int& Matrix::elementAt(int row, int col) {
    if (row < 0 || row >= size || col < 0 || col >= size) {
        throw std::out_of_range("Index out of range");
    }
    return data[row][col];
}

const int& Matrix::elementAt(int row, int col) const {
    if (row < 0 || row >= size || col < 0 || col >= size) {
        throw std::out_of_range("Index out of range");
    }
    return data[row][col];
}

bool Matrix::isSaddlePoint(int row, int col) const {
    int elem = data[row][col];
    for (int i = 0; i < size; ++i) {
        if (data[row][i] < elem) return false;
    }
    for (int i = 0; i < size; ++i) {
        if (data[i][col] > elem) return false;
    }
    return true;
}


-------------------------------------------------- main.cpp -------------------------------------------------- 
#include "declaration.h"
#include <iostream>

int main() {
    Matrix m(3); 
    m.elementAt(0, 0) = 3;
    m.elementAt(0, 1) = 4;
    m.elementAt(0, 2) = 5;
    m.elementAt(1, 0) = 2;
    m.elementAt(1, 1) = 5;
    m.elementAt(1, 2) = 6;
    m.elementAt(2, 0) = 1;
    m.elementAt(2, 1) = 8;
    m.elementAt(2, 2) = 9;

    std::cout << "Size of the matrix: " << m.getSize() << "x" << m.getSize() << std::endl;

    std::cout << "Element at position (1,1): " << m.elementAt(1, 1) << std::endl;

    std::cout << "Original matrix with possible saddle points:\n";
    for (int i = 0; i < m.getSize(); ++i) {
        for (int j = 0; j < m.getSize(); ++j) {
            std::cout << m.elementAt(i, j) << " ";
            if (m.isSaddlePoint(i, j)) {
                std::cout << "(saddle point) ";
            }
        }
        std::cout << std::endl;
    }
    int i = 0;
    int j = 0;
    if (m.isSaddlePoint(i, j)) {
        std::cout << "yes";
    }
    else {
        std::cout << "no";
    }
    return 0;
}
