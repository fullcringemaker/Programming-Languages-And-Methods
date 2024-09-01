-------------------------------------------------- InvalidCoordinateException.java --------------------------------------------------
public class InvalidCoordinateException extends Exception {
    public InvalidCoordinateException(String message) {
        super(message);
    }
}


-------------------------------------------------- myCustomException.java --------------------------------------------------
public class myCustomException extends Exception {
    public myCustomException() {
        super();
    }

    public void getMsg(String s) {
        System.out.println(s);
    }
}


-------------------------------------------------- Point.java --------------------------------------------------
import static java.lang.Math.*;

public class Point {
    private String name;
    private double x;
    private double y;
    private double z;
    private static int n;
    public static int val;

    public Point(String argName) {
        System.out.println("Запущен конструктор объекта Point");
        this.name = argName;
    }

    public String getName() {
        return name;
    }

    public void setCoord(double varX, double varY, double varZ) throws InvalidCoordinateException {
        if (varX < 0 || varY < 0 || varZ < 0) {
            throw new InvalidCoordinateException("Координаты не могут быть отрицательными");
        }
        this.x = varX;
        this.y = varY;
        this.z = varZ;
    }

    public double getR() {
        return pow(pow(this.x, 2) + pow(this.y, 2) + pow(this.z, 2), 0.5);
    }

    public static void setMass(int valN) {
        n = valN;
    }

    public void someExample() throws myCustomException {
        System.out.println("Старт someExmple");
        throw new myCustomException(); // Искусственно создаем исключение
    }
}


--------------------------------------------------Main.java --------------------------------------------------
public class Main {
    public static void main(String[] args) {

        Point PointA = new Point("A");
        System.out.println("Имя точки: " + PointA.getName());
        try {
            PointA.setCoord(1.0, 1.0, -1.0); // Пример с отрицательной координатой
        } catch (InvalidCoordinateException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Длинна радиус-вектора: " + PointA.getR());

        try {
            PointA.someExample();
        } catch (myCustomException qqq) {
            qqq.getMsg("Поимали исключительную ситуацию");
        }
    }
}
