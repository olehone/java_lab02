package task2;

//Трикутник
public class Triangle implements FlatFigure{
    private final double firstSide;
    private final double secondSide;
    private final double thirdSide;
    public Triangle(final double firstSide, final double secondSide, final double thirdSide) {
        if (firstSide <= 0 || secondSide <= 0 || thirdSide <= 0)
            throw new IllegalArgumentException("Side can`t be zero or less");
        this.firstSide = firstSide;
        this.secondSide = secondSide;
        this.thirdSide = thirdSide;
    }
    public double getArea() {
        // Heron's formula
        final double halfPerimeter = getPerimeter() / 2;
        return Math.sqrt(halfPerimeter * (halfPerimeter - firstSide) * (halfPerimeter - secondSide) * (halfPerimeter - thirdSide));
    }
    public double getPerimeter(){
        return firstSide+secondSide+thirdSide;
    }
    public double getRadiusOfInscribedCircle(){
        final double perimeter = getPerimeter();
        return Math.sqrt((perimeter-secondSide)*(perimeter-firstSide)*(perimeter-thirdSide)/perimeter);
    }
}
