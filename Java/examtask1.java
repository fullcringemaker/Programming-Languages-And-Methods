-------------------------------------------------- Fraction.java --------------------------------------------------
public class Fraction implements Comparable<Fraction> {
    private int numerator;
    private int denominator;

    public Fraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero");
        }
        this.numerator = numerator;
        this.denominator = denominator;
        simplify();
    }

    private void simplify() {
        int gcd = gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
    }

    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    public Fraction add(Fraction other) {
        int newNumerator = this.numerator * other.denominator + other.numerator * this.denominator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public int compareTo(Fraction other) {
        return Integer.compare(this.numerator * other.denominator, other.numerator * this.denominator);
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }
}


-------------------------------------------------- NDimensionalVector.java --------------------------------------------------
import java.util.Arrays;

public class NDimensionalVector implements Comparable<NDimensionalVector> {
    private Fraction[] components;

    public NDimensionalVector(Fraction[] components) {
        this.components = Arrays.copyOf(components, components.length);
    }

    public NDimensionalVector add(NDimensionalVector other) {
        if (this.components.length != other.components.length) {
            throw new IllegalArgumentException("Vectors must have the same dimension");
        }
        Fraction[] result = new Fraction[this.components.length];
        for (int i = 0; i < this.components.length; i++) {
            result[i] = this.components[i].add(other.components[i]);
        }
        return new NDimensionalVector(result);
    }

    public boolean isOrthogonal(NDimensionalVector other) {
        if (this.components.length != other.components.length) {
            throw new IllegalArgumentException("Vectors must have the same dimension");
        }
        Fraction dotProduct = new Fraction(0, 1);
        for (int i = 0; i < this.components.length; i++) {
            dotProduct = dotProduct.add(new Fraction(
                    this.components[i].getNumerator() * other.components[i].getNumerator(),
                    this.components[i].getDenominator() * other.components[i].getDenominator()
            ));
        }
        return dotProduct.compareTo(new Fraction(0, 1)) == 0;
    }

    @Override
    public int compareTo(NDimensionalVector other) {
        int minLength = Math.min(this.components.length, other.components.length);
        for (int i = 0; i < minLength; i++) {
            int cmp = this.components[i].compareTo(other.components[i]);
            if (cmp != 0) return cmp;
        }
        return Integer.compare(this.components.length, other.components.length);
    }

    @Override
    public String toString() {
        return Arrays.toString(components);
    }
}


-------------------------------------------------- Test.java --------------------------------------------------
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        Fraction[] vector1Components = { new Fraction(1, 2), new Fraction(2, 3) };
        Fraction[] vector2Components = { new Fraction(3, 4), new Fraction(1, 3) };
        Fraction[] vector3Components = { new Fraction(1, 2), new Fraction(2, 3) };

        NDimensionalVector vector1 = new NDimensionalVector(vector1Components);
        NDimensionalVector vector2 = new NDimensionalVector(vector2Components);
        NDimensionalVector vector3 = new NDimensionalVector(vector3Components);

        NDimensionalVector[] vectors = { vector2, vector1, vector3 };
        Arrays.sort(vectors);

        System.out.println("Sorted Vectors:");
        for (NDimensionalVector vector : vectors) {
            System.out.println(vector);
        }

        NDimensionalVector sum = vector1.add(vector2);
        System.out.println("Sum of vector1 and vector2: " + sum);

        boolean orthogonal = vector1.isOrthogonal(vector3);
        System.out.println("Vector1 and Vector3 are orthogonal: " + orthogonal);
    }
}
