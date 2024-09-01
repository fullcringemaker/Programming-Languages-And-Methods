-------------------------------------------------- SequenceOfSets.h --------------------------------------------------
#ifndef SEQUENCEOFSETS_H
#define SEQUENCEOFSETS_H

#include <vector>
#include <set>
#include <iterator>
#include <algorithm>

template <typename T>
class SequenceOfSets {
public:
    using SetType = std::set<T>;
    using ContainerType = std::vector<SetType>;

    SequenceOfSets() = default;

    void addSet(const SetType& s) {
        sets.push_back(s);
    }

    class CBIterator {
    public:
        using iterator_category = std::bidirectional_iterator_tag;
        using value_type = SetType;
        using difference_type = std::ptrdiff_t;
        using pointer = const SetType*;
        using reference = const SetType&;

        CBIterator(const ContainerType& sets, size_t index)
            : sets(sets), index(index) {
            if (index > 0 && index < sets.size()) {
                std::set_intersection(sets[index - 1].begin(), sets[index - 1].end(),
                    sets[index].begin(), sets[index].end(),
                    std::inserter(intersection, intersection.begin()));
            }
        }

        value_type operator*() const {
            return intersection;
        }

        CBIterator& operator++() {
            if (index < sets.size()) {
                ++index;
                updateIntersect();
            }
            return *this;
        }

        CBIterator operator++(int) {
            CBIterator temp = *this;
            ++(*this);
            return temp;
        }

        CBIterator& operator--() {
            if (index > 1) {
                --index;
                updateIntersect();
            }
            return *this;
        }

        CBIterator operator--(int) {
            CBIterator temp = *this;
            --(*this);
            return temp;
        }

        bool operator==(const CBIterator& other) const {
            return index == other.index && &sets == &other.sets;
        }

        bool operator!=(const CBIterator& other) const {
            return !(*this == other);
        }

    private:
        void updateIntersect() {
            intersection.clear();
            if (index > 0 && index < sets.size()) {
                std::set_intersection(sets[index - 1].begin(), sets[index - 1].end(),
                    sets[index].begin(), sets[index].end(),
                    std::inserter(intersection, intersection.begin()));
            }
        }

        const ContainerType& sets;
        SetType intersection;
        size_t index;
    };

    CBIterator begin() const {
        return CBIterator(sets, sets.size() > 1 ? 1 : 0);
    }

    CBIterator end() const {
        return CBIterator(sets, sets.size());
    }

    const SetType& operator[](size_t index) const {
        return sets.at(index);
    }

private:
    ContainerType sets;
};

#endif 


-------------------------------------------------- main.cpp --------------------------------------------------
#include <iostream>
#include "SequenceOfSets.h"

int main() {
    SequenceOfSets<int> sequence;
    sequence.addSet({ 1, 2, 3, 4 });
    sequence.addSet({ 3, 4, 5 });
    sequence.addSet({ 4, 5, 6, 7 });
    sequence.addSet({ 1, 2, 3, 0 });
    sequence.addSet({ 1, 2, 3, 4 });
    sequence.addSet({ 1, 7, 8, 9 });
    sequence.addSet({ 1, 7, 8, 9, 5, 6 });



    for (auto it = sequence.begin(); it != sequence.end(); ++it) {
        const auto& intersection = *it;
        std::cout << "Intersection: ";
        for (const auto& elem : intersection) {
            std::cout << elem << " ";
        }
        std::cout << std::endl;
    }

    return 0;
}
