-------------------------------------------------- Point.java --------------------------------------------------
public class Point {
    public double x, y;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}


-------------------------------------------------- Line.java --------------------------------------------------
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

class Line {
    double a, b, c;
    Line(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    public Stream<Line> PerpendThroughPoint(Point p) {
        return Stream.of(new Line(this.b, -this.a, this.a * p.y - this.b * p.x));
    }
    public Optional<Double> maxDistance(List<Line> lines) {
        Stream<Double> distances = lines.stream()
                .filter(line -> this.a == line.a && this.b == line.b)
                .map(line -> Math.abs(this.c - line.c) / Math.sqrt(this.a * this.a + this.b * this.b));
        Optional<Double> maxDistance = distances.max(Double::compareTo);
        return maxDistance;
    }
    public String formatLine() {
        int aInt = (int) Math.round(this.a);
        int bInt = (int) Math.round(this.b);
        int cInt = (int) Math.round(this.c);
        String xPart = aInt + "x";
        String signB;
        if (bInt >= 0) {
            signB = " + ";
        } else {
            signB = " - ";
        }
        String yPart = Math.abs(bInt) + "y";
        String signC;
        if (cInt >= 0) {
            signC = " + ";
        } else {
            signC = " - ";
        }
        String cPart = Math.abs(cInt) + "";
        return xPart + signB + yPart + signC + cPart + " = 0";
    }
}


-------------------------------------------------- Test.java --------------------------------------------------
import java.util.*;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        List<Line> lines = List.of(
                new Line(1, -1, -2),
                new Line(1, -1, 9),
                new Line(0, 1, -2),
                new Line(1, 1, -8),
                new Line(1, 0, -3),
                new Line(1, 1, 7)
        );
        Point p = new Point(2, 2);
        Map<String, List<String>> groupedPerpendiculars = new HashMap<>();
        for (Line line : lines) {
            for (Line perpLine : line.PerpendThroughPoint(p).collect(Collectors.toList())) {
                String formattedLine = perpLine.formatLine();
                String key;
                if (perpLine.a == 0) {
                    key = "Horizontal";
                } else if (perpLine.b == 0) {
                    key = "Verticals";
                } else {
                    key = "Others";
                }
                groupedPerpendiculars.computeIfAbsent(key, k -> new ArrayList<>()).add(formattedLine);
            }
        }
        groupedPerpendiculars.forEach((type, eqs) -> {
            System.out.println(type + ":");
            eqs.forEach(System.out::println);
        });
        Optional<Double> maxDistanceOptional = lines.stream()
                .flatMap(line -> line.maxDistance(lines).stream())
                .max(Double::compareTo);
        if (!maxDistanceOptional.isPresent()) {
            System.out.println("Maximum distance: none, as there are no parallel lines");
        } else {
            System.out.println("Maximum distance: " + maxDistanceOptional.get());
        }
    }
}
