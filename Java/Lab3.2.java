-------------------------------------------------- Polynomial.java --------------------------------------------------
class Polynomial implements Comparable<Polynomial> {
    private int[] coefficients;
    public Polynomial(int... coefficients) {
        this.coefficients = coefficients;
    }
    private int countIntegerRoots() {
        int count = 0;
        for (int x = 0; x <= 10; x++) {
            if (evaluatePolynomial(x) == 0) {
                count++;
                while (x < 10 && evaluatePolynomial(x + 1) == 0) {
                    x++;
                }
            }
        }
        return count;
    }
    private int evaluatePolynomial(int x) {
        int value = 0;
        for (int i = 0; i < coefficients.length; i++) {
            value += coefficients[i] * Math.pow(x, i);
        }
        return value;
    }
    @Override
    public int compareTo(Polynomial other) {
        return Integer.compare(other.countIntegerRoots(), this.countIntegerRoots());
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = coefficients.length - 1; i >= 0; i--) {
            if (coefficients[i] == 0)
                continue;
            if (sb.length() > 0 && coefficients[i] > 0)
                sb.append("+");
            if (i == 0 || coefficients[i] != 1 || (i == 1 && coefficients[i] == 1)) {
                sb.append(coefficients[i]);
            }
            if (i >= 1)
                sb.append("x");
            if (i > 1) {
                sb.append("^").append(i);
            }
        }
        return sb.length() > 0 ? sb.toString() : "0";
    }
}


-------------------------------------------------- Test.java --------------------------------------------------
import java.util.Arrays;
public class Test {
    public static void main(String[] args) {
        Polynomial[] polynomials = {
                new Polynomial(-56, 50 , -13, 1),
                new Polynomial(490,-413, 108, -21, 1),
                new Polynomial(120,-199, 95, -17, 1),
        };
        Arrays.sort(polynomials);
        for (Polynomial p : polynomials) {
            System.out.println(p);
        }
    }
}
