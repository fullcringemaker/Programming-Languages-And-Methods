-------------------------------------------------- Curve.h --------------------------------------------------
#ifndef CURVE_H
#define CURVE_H

#include <functional>
#include <cmath>
#include <iostream> 

template<typename T>
class Curve {
public:
    using FunctionType = std::function<T(T)>;

private:
    FunctionType f;

public:
    Curve() : f([](T x) { return std::exp(x); }) {}
    Curve(FunctionType func) : f(func) {}
    T operator()(T x) const {
        return f(x);
    }

    Curve operator+(const Curve& other) const {
        return Curve([this, other](T x) { return this->f(x) + other.f(x); });
    }

    Curve operator-(const Curve& other) const {
        return Curve([this, other](T x) { return this->f(x) - other.f(x); });
    }

    Curve operator*(T scalar) const {
        return Curve([this, scalar](T x) { return scalar * this->f(x); });
    }

    Curve operator-() const {
        return Curve([this](T x) { return -this->f(x); });
    }

    Curve derivative() const {
        const T h = 1e-5;
        return Curve([this, h](T x) {
            return (this->f(x + h) - this->f(x - h)) / (2 * h);
            });
    }

};

#endif 


-------------------------------------------------- main.cpp --------------------------------------------------
#include "Curve.h"
#include <iostream>

int main() {
    Curve<double> c1, c2;
    std::cout << "Initial curve functions:" << std::endl;
    std::cout << "c1(x) = " << c1(1) << std::endl;
    std::cout << "c2(x) = " << c2(1) << std::endl;

    std::cout << "Addition:" << std::endl;
    auto resultPlus = c1 + c2;
    std::cout << "Result: " << resultPlus(1) << std::endl;

    std::cout << "Subtraction:" << std::endl;
    auto resultMinus = c1 - c2;
    std::cout << "Result: " << resultMinus(1) << std::endl;

    std::cout << "Multiplication:" << std::endl;
    auto resultMult = c1 * 2.0;
    std::cout << "Result: " << resultMult(1) << std::endl;

    std::cout << "Unary minus:" << std::endl;
    auto resultNeg = -c1;
    std::cout << "Result: " << resultNeg(1) << std::endl;

    std::cout << "Differentiation:" << std::endl;
    auto derivC1 = c1.derivative();
    std::cout << "c1'(x) = " << derivC1(1) << std::endl;

    std::cout << "Value calculation:" << std::endl;
    std::cout << "c1(x) = " << c1(1) << std::endl;

    return 0;
}
