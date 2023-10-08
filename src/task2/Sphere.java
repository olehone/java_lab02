package task2;

//Сфера/куля
public class Sphere {
    private final double radius;

    public Sphere(final double radius) {
        this.radius = radius;
    }

    public double getVolume() {
        return Math.PI * Math.pow(radius, 3) * 4 / 3;
    }

    public double getArea() {
        return Math.PI * Math.pow(radius, 2) * 4;
    }
}
