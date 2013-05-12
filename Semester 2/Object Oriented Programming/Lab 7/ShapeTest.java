/**
 * Lab07 - ShapeTest
 *
 * Compiles with JDK 1.7.0
 *
 * @author Orfeas-Ioannis Zafeiris (2250) <cs122250>
 */

interface ShapeFunctions
{
    public double CalculatePerimeter();
    public double CalculateArea();
}

abstract class Shape implements ShapeFunctions
{
    protected String m_Type;

    public String GetType() { return m_Type; }

    public double areaToPerimeterRatio()
    {
        return CalculateArea() / CalculatePerimeter();
    }

    public String toString()
    {
        return "Type: " + m_Type + ". Area: " + CalculateArea() + ". Perimeter: " + CalculatePerimeter() + ". Ratio: " + areaToPerimeterRatio() + ".";
    }
}

class Circle extends Shape
{
    public double Radius;

    Circle(double p_Radius)
    {
        m_Type = "Circle";
        Radius = p_Radius;
    }

    public double CalculatePerimeter()
    {
        return Math.PI * Radius * 2;
    }

    public double CalculateArea()
    {
        return Math.PI * (Radius * Radius);
    }
}

class Square extends Shape
{
    public double SideWidth;

    Square(double p_SideWidth)
    {
        m_Type = "Square";
        SideWidth = p_SideWidth;
    }

    public double CalculatePerimeter()
    {
        return 4 * SideWidth;
    }

    public double CalculateArea()
    {
        return SideWidth * SideWidth;
    }
}

class ShapeTest
{
    public static void main(String[] p_Args)
    {
        Shape[] s_Shapes = new Shape[4];

        s_Shapes[0] = new Circle(4.0);
        s_Shapes[1] = new Circle(6.0);
        s_Shapes[2] = new Square(2.0);
        s_Shapes[3] = new Square(8.0);

        for (Shape s_Shape : s_Shapes)
            System.out.println(s_Shape);
    }
}