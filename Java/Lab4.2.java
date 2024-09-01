-------------------------------------------------- Point3D.java --------------------------------------------------
public class Point3D {
    private double x, y, z;
    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public double distanceTo(Point3D other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2)
                + Math.pow(this.y - other.y, 2)
                + Math.pow(this.z - other.z, 2));
    }
}


-------------------------------------------------- Point3DSequence.java --------------------------------------------------
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class Point3DSequence implements Iterable<Double> {
    private List<Point3D> points;
    public Point3DSequence() {
        points = new ArrayList<>();
    }
    public void addPoint(Point3D point) {
        points.add(point);
    }
    public void removePoint(Point3D point) {
        points.remove(point);
    }
    @Override
    public Iterator<Double> iterator() {
        return new Point3DIterator();
    }
    private class Point3DIterator implements Iterator<Double> {
        private int curIndex = 0;
        @Override
        public boolean hasNext() {
            return curIndex < points.size() - 1;
        }
        @Override
        public Double next() {
            double length = points.get(curIndex).distanceTo(points.get(curIndex + 1));
            curIndex++;
            return length;
        }
    }
}


-------------------------------------------------- Test.java --------------------------------------------------
public class Test {
    public static void main(String[] args) {
        Point3DSequence points = new Point3DSequence();
        //points.addPoint(new Point3D(10, 2, 15));
        //points.addPoint(new Point3D(11, 5, 8));
        //points.addPoint(new Point3D(0, 10, 3));
        //points.addPoint(new Point3D(7, 17, 9));
        points.addPoint(new Point3D(1, 0, 0));
        points.addPoint(new Point3D(20, 0, 0));
        points.addPoint(new Point3D(21, 0, 0));
        for (double length : points) {
            System.out.println(length);
        }
    }
}
