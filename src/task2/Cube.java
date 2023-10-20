package task2;
//Куб
public class Cube implements VolumetricFigure {
    private final double sideLength;
    public Cube(final double sideLength) {
        if (sideLength <= 0)
            throw new IllegalArgumentException("Side length can`t be zero or less");
        this.sideLength = sideLength;
    }
    public double getVolume() {
        return Math.pow(sideLength, 3);
    }
    public double getArea() {
        return Math.pow(sideLength, 2) * 6;
    }
}
