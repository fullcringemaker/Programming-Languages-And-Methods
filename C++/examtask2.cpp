-------------------------------------------------- main.cpp --------------------------------------------------
#include <iostream>
#include <vector>
#include <iterator>
#include <cmath>

template<typename T>
class VandermondeMatrix {
public:
    class ConstIterator;

    VandermondeMatrix(const std::vector<T>& coefficients, size_t n)
        : m_coefficients(coefficients), m_n(n), m_m(coefficients.size()) {
        generateMatrix();
    }

    ConstIterator begin() const {
        return ConstIterator(this, 0, 0);
    }

    ConstIterator end() const {
        return ConstIterator(this, m_m, 0);
    }

    size_t rows() const { return m_m; }
    size_t cols() const { return m_n; }

    T operator()(size_t i, size_t j) const {
        return m_matrix[i][j];
    }

    class ConstIterator : public std::iterator<std::bidirectional_iterator_tag, T> {
    public:
        ConstIterator(const VandermondeMatrix* matrix, size_t row, size_t col)
            : m_matrix(matrix), m_row(row), m_col(col) {}

        const T& operator*() const {
            return m_matrix->m_matrix[m_row][m_col];
        }

        ConstIterator& operator++() {
            if (++m_col == m_matrix->cols()) {
                m_col = 0;
                ++m_row;
            }
            return *this;
        }

        ConstIterator operator++(int) {
            ConstIterator tmp = *this;
            ++(*this);
            return tmp;
        }

        ConstIterator& operator--() {
            if (m_col == 0) {
                m_col = m_matrix->cols() - 1;
                --m_row;
            }
            else {
                --m_col;
            }
            return *this;
        }

        ConstIterator operator--(int) {
            ConstIterator tmp = *this;
            --(*this);
            return tmp;
        }

        bool operator==(const ConstIterator& other) const {
            return m_matrix == other.m_matrix && m_row == other.m_row && m_col == other.m_col;
        }

        bool operator!=(const ConstIterator& other) const {
            return !(*this == other);
        }

    private:
        const VandermondeMatrix* m_matrix;
        size_t m_row;
        size_t m_col;
    };

private:
    void generateMatrix() {
        m_matrix.resize(m_m, std::vector<T>(m_n));
        for (size_t i = 0; i < m_m; ++i) {
            for (size_t j = 0; j < m_n; ++j) {
                m_matrix[i][j] = std::pow(m_coefficients[i], j);
            }
        }
    }

    std::vector<T> m_coefficients;
    size_t m_n;
    size_t m_m;
    std::vector<std::vector<T>> m_matrix;
};

int main() {
    std::vector<double> coefficients = { 2.0, 3.0, 7.0 };
    size_t n = 7;

    VandermondeMatrix<double> matrix(coefficients, n);

    size_t current_row = 0;
    for (auto it = matrix.begin(); it != matrix.end(); ++it) {
        std::cout << *it << " ";
        if (++current_row == matrix.cols()) {
            std::cout << std::endl;
            current_row = 0;
        }
    }
    return 0;
}
