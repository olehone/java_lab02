package task2;

//Піраміда
public class Pyramid {
    private final Triangle base;
    private final double height;

    public Pyramid(final Triangle base, final double height) {
        if (height <= 0)
            throw new IllegalArgumentException("Height can`t be zero or less");
        this.base = base;
        this.height = height;
    }
    public double getVolume() {
        return height * base.getArea() / 3;
    }
    public double getArea() {
        return base.getArea() + getApothem() * base.getPerimeter() / 2;
    }
    //apothem - the height of the side face
    public double getApothem(){
        return Math.sqrt(Math.pow(base.getRadiusOfInscribedCircle(), 2) +Math.pow(height, 2));
    }
}
