-------------------------------------------------- StringContainer.h --------------------------------------------------
#ifndef STRINGCONTAINER_H
#define STRINGCONTAINER_H

#include <iostream>
#include <vector>
#include <string>
#include <stdexcept>

class StringContainer {
public:
    class Iterator;
    using size_type = std::vector<std::string>::size_type;

    void add(const std::string& str) {
        strings_.emplace_back(str);
    }

    std::string& operator[](size_type index) {
        if (index >= strings_.size()) {
            throw std::out_of_range("Index out of range");
        }
        return strings_[index];
    }

    const std::string& operator[](size_type index) const {
        if (index >= strings_.size()) {
            throw std::out_of_range("Index out of range");
        }
        return strings_[index];
    }

    Iterator begin() {
        return Iterator(strings_.begin());
    }

    Iterator end() {
        return Iterator(strings_.end());
    }

    class Iterator {
    public:
        using iterator_category = std::bidirectional_iterator_tag;
        using value_type = std::string;
        using difference_type = std::ptrdiff_t;
        using pointer = std::string*;
        using reference = std::string&;

        Iterator(std::vector<std::string>::iterator it) : it_(it) {}

        reference operator*() {
            return *it_;
        }

        pointer operator->() {
            return &(*it_);
        }

        Iterator& operator++() {
            ++it_;
            return *this;
        }

        Iterator operator++(int) {
            Iterator tmp = *this;
            ++it_;
            return tmp;
        }

        Iterator& operator--() {
            --it_;
            return *this;
        }

        Iterator operator--(int) {
            Iterator tmp = *this;
            --it_;
            return tmp;
        }

        bool operator==(const Iterator& other) const {
            return it_ == other.it_;
        }

        bool operator!=(const Iterator& other) const {
            return it_ != other.it_;
        }

        std::string& operator[](size_type len) {
            std::string& str = *it_;
            if (len > str.size()) {
                str.append(len - str.size(), ' ');
            }
            else if (len < str.size()) {
                str.erase(len);
            }
            return str;
        }

    private:
        std::vector<std::string>::iterator it_;
    };

private:
    std::vector<std::string> strings_;
};

#endif 


-------------------------------------------------- main.cpp --------------------------------------------------
#ifndef STRINGCONTAINER_H
#define STRINGCONTAINER_H

#include <iostream>
#include <vector>
#include <string>
#include <stdexcept>

class StringContainer {
public:
    class Iterator;
    using size_type = std::vector<std::string>::size_type;

    void add(const std::string& str) {
        strings_.emplace_back(str);
    }

    std::string& operator[](size_type index) {
        if (index >= strings_.size()) {
            throw std::out_of_range("Index out of range");
        }
        return strings_[index];
    }

    const std::string& operator[](size_type index) const {
        if (index >= strings_.size()) {
            throw std::out_of_range("Index out of range");
        }
        return strings_[index];
    }

    Iterator begin() {
        return Iterator(strings_.begin());
    }

    Iterator end() {
        return Iterator(strings_.end());
    }

    class Iterator {
    public:
        using iterator_category = std::bidirectional_iterator_tag;
        using value_type = std::string;
        using difference_type = std::ptrdiff_t;
        using pointer = std::string*;
        using reference = std::string&;

        Iterator(std::vector<std::string>::iterator it) : it_(it) {}

        reference operator*() {
            return *it_;
        }

        pointer operator->() {
            return &(*it_);
        }

        Iterator& operator++() {
            ++it_;
            return *this;
        }

        Iterator operator++(int) {
            Iterator tmp = *this;
            ++it_;
            return tmp;
        }

        Iterator& operator--() {
            --it_;
            return *this;
        }

        Iterator operator--(int) {
            Iterator tmp = *this;
            --it_;
            return tmp;
        }

        bool operator==(const Iterator& other) const {
            return it_ == other.it_;
        }

        bool operator!=(const Iterator& other) const {
            return it_ != other.it_;
        }

        std::string& operator[](size_type len) {
            std::string& str = *it_;
            if (len > str.size()) {
                str.append(len - str.size(), ' ');
            }
            else if (len < str.size()) {
                str.erase(len);
            }
            return str;
        }

    private:
        std::vector<std::string>::iterator it_;
    };

private:
    std::vector<std::string> strings_;
};

#endif 
