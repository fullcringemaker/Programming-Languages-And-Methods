-------------------------------------------------- GeometricFigure.java --------------------------------------------------
class GeometricFigure {
    public String figureType;

    public GeometricFigure(String figureType) {
        this.figureType = figureType;
    }

    public void move(String action) {
        System.out.println("Moving the " + figureType + " by " + action);
    }
}

class ColoredFigure extends GeometricFigure {
    public String figureName;

    public ColoredFigure(String figureType, String figureName) {
        super(figureType);
        this.figureName = figureName;
    }

    public void assignColor(String color) {
        System.out.println("Assigning color " + color + " to the " + figureName);
    }
}


-------------------------------------------------- Main.java --------------------------------------------------
public class Main{
    public static void main(String[] args) {
        ColoredFigure coloredFigure = new ColoredFigure("Circle", "MyCircle");
        coloredFigure.move("rolling");
        coloredFigure.assignColor("red");
    }
}
