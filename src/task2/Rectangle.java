package task2;

//Прямокутник
public class Rectangle {
    private final double length;
    private final double width;
    public Rectangle(final double length, final double width) {
        if (length <= 0 || width <= 0)
            throw new IllegalArgumentException("Side can`t be zero or less");
        this.length = length;
        this.width = width;
    }
    public double getArea() {
        return length * width;
    }

}
