-------------------------------------------------- declaration.h --------------------------------------------------
#ifndef DECLARATION_H
#define DECLARATION_H

#include <stdexcept>

class Interval {
private:
    double a, b;

public:
    Interval(double a, double b);
    Interval(const Interval& other);
    double getA() const;
    double getB() const;
    virtual ~Interval();
};

class PlentyOfIntervals {
private:
    Interval* intervals;
    int capacity;
    int n;

    void resize();

public:
    PlentyOfIntervals();
    PlentyOfIntervals(const PlentyOfIntervals& other);
    PlentyOfIntervals& operator=(const PlentyOfIntervals& other);
    virtual ~PlentyOfIntervals();
    void add(const Interval& interval);
    int getN() const;
    Interval& operator[](int index) const;
    bool includeX(double x) const;
    void plus(const PlentyOfIntervals& other);
};

#endif


-------------------------------------------------- implementation.cpp --------------------------------------------------
#include "declaration.h"
#include <new>
#include <iostream>

Interval::Interval(double a, double b) : a(a), b(b) {}
Interval::Interval(const Interval& other) : a(other.a), b(other.b) {}
double Interval::getA() const { return a; }
double Interval::getB() const { return b; }
Interval::~Interval() {}

PlentyOfIntervals::PlentyOfIntervals() : intervals(nullptr), capacity(0), n(0) {}

PlentyOfIntervals::~PlentyOfIntervals() {
    for (int i = 0; i < n; ++i) {
        intervals[i].~Interval();
    }
    delete[] reinterpret_cast<char*>(intervals);
}

void PlentyOfIntervals::resize() {
    int newCapacity = capacity == 0 ? 2 : capacity * 2;
    Interval* newIntervals = reinterpret_cast<Interval*>(new char[newCapacity * sizeof(Interval)]);
    for (int i = 0; i < n; ++i) {
        new (&newIntervals[i]) Interval(intervals[i]);
    }
    this->~PlentyOfIntervals();
    intervals = newIntervals;
    capacity = newCapacity;
}

void PlentyOfIntervals::add(const Interval& interval) {
    if (n == capacity) {
        resize();
    }
    new (&intervals[n++]) Interval(interval);
}

int PlentyOfIntervals::getN() const {
    return n;
}

Interval& PlentyOfIntervals::operator[](int index) const {
    if (index < 0 || index >= n) {
        throw std::out_of_range("Index out of range");
    }
    return intervals[index];
}

bool PlentyOfIntervals::includeX(double x) const {
    for (int i = 0; i < n; ++i) {
        if (intervals[i].getA() < x && x < intervals[i].getB()) {
            return true;
        }
    }
    return false;
}

void PlentyOfIntervals::plus(const PlentyOfIntervals& other) {
    for (int i = 0; i < other.getN(); ++i) {
        add(other.intervals[i]);
    }
}

PlentyOfIntervals::PlentyOfIntervals(const PlentyOfIntervals& other) : intervals(reinterpret_cast<Interval*>(new char[other.capacity * sizeof(Interval)])), capacity(other.capacity), n(other.n) {
    for (int i = 0; i < n; ++i) {
        new (&intervals[i]) Interval(other.intervals[i]);
    }
}

PlentyOfIntervals& PlentyOfIntervals::operator=(const PlentyOfIntervals& other) {
    if (this != &other) {
        Interval* newIntervals = reinterpret_cast<Interval*>(new char[other.capacity * sizeof(Interval)]);
        for (int i = 0; i < other.n; ++i) {
            new (&newIntervals[i]) Interval(other.intervals[i]);
        }
        this->~PlentyOfIntervals();
        intervals = newIntervals;
        capacity = other.capacity;
        n = other.n;
    }
    return *this;
}


-------------------------------------------------- main.cpp --------------------------------------------------
#include "declaration.h"
#include <iostream>

using namespace std;

int main() {
    PlentyOfIntervals intervals;
    intervals.add(Interval(1.0, 2.0));
    intervals.add(Interval(3.5, 4.6));
    intervals.add(Interval(-5.0, -3.0));

    cout << "Total intervals: " << intervals.getN() << endl;
    cout << "Intervals:" << endl;
    for (int i = 0; i < intervals.getN(); ++i) {
        cout << "Interval " << i << ": (" << intervals[i].getA() << ", " << intervals[i].getB() << ")" << endl;
    }

    int index = 1;
    if (index >= 0 && index < intervals.getN()) {
        const Interval& interval = intervals[index];
        cout << "Displaying interval at index " << index << ": (" << interval.getA() << ", " << interval.getB() << ")" << endl;
    }
    else {
        cout << "Index " << index << " is out of range." << endl;
    }

    double x = 4.0;
    cout << "Does " << x << " belong to any interval? " << (intervals.includeX(x) ? "Yes" : "No") << endl;
    double y = 15.0;
    cout << "Does " << y << " belong to any interval? " << (intervals.includeX(y) ? "Yes" : "No") << endl;

    PlentyOfIntervals moreIntervals;
    moreIntervals.add(Interval(1.1, 1.9));
    moreIntervals.add(Interval(2.1, 2.9));

    intervals.plus(moreIntervals);

    cout << "After merging: {";
    for (int i = 0; i < intervals.getN(); ++i) {
        cout << "(" << intervals[i].getA() << ", " << intervals[i].getB() << ")";
        if (i < intervals.getN() - 1) {
            cout << ", ";
        }
    }
    cout << "}";

    return 0;
}
