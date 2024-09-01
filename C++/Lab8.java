-------------------------------------------------- Queue.h --------------------------------------------------
#pragma once
#include <vector>
#include <iostream>
template <typename T, int N = 0>
class Queue {
private:
    T data[N];
    int front = 0;
    int rear = -1;
    int count = 0;
public:
    void enqueue(T element) {
        if (count < N) {
            rear = (rear + 1) % N;
            data[rear] = element;
            count++;
        }
        else {
            std::cerr << "Queue is full" << std::endl;
        }
    }
    T dequeue() {
        if (!is_empty()) {
            T result = data[front];
            front = (front + 1) % N;
            count--;
            return result;
        }
        else {
            std::cerr << "Queue is empty" << std::endl;
            return T();
        }
    }

    bool is_empty() const {
        return count == 0;
    }
};
template <typename T>
class Queue<T, 0> {
private:
    std::vector<T> data;
public:
    void enqueue(T element) {
        data.push_back(element);
    }
    T dequeue() {
        if (!is_empty()) {
            T result = data.front();
            data.erase(data.begin());
            return result;
        }
        else {
            std::cerr << "Queue is empty" << std::endl;
            return T();
        }
    }
    bool is_empty() const {
        return data.empty();
    }
};


-------------------------------------------------- main.cpp --------------------------------------------------
#include "Queue.h"
#include <iostream>

int main() {
    Queue<int, 2> q1;
    Queue<int> q2;

    std::cout << "Enqueue to q1: 10" << std::endl;
    q1.enqueue(10);
    std::cout << "Enqueue to q1: 20" << std::endl;
    q1.enqueue(20);

    std::cout << "Dequeue from q1: " << q1.dequeue() << std::endl;
    std::cout << "Queue q1 is empty: " << (q1.is_empty() ? "Yes" : "No") << std::endl;

    std::cout << "Dequeue from q1: " << q1.dequeue() << std::endl;
    std::cout << "Queue q1 is empty: " << (q1.is_empty() ? "Yes" : "No") << std::endl;

    std::cout << "Enqueue to q2: 30" << std::endl;
    q2.enqueue(30);
    std::cout << "Enqueue to q2: 40" << std::endl;
    q2.enqueue(40);

    std::cout << "Dequeue from q2: " << q2.dequeue() << std::endl;
    std::cout << "Queue q2 is empty: " << (q2.is_empty() ? "Yes" : "No") << std::endl;

    std::cout << "Dequeue from q2: " << q2.dequeue() << std::endl;
    std::cout << "Queue q2 is empty: " << (q2.is_empty() ? "Yes" : "No") << std::endl;

    return 0;
}
