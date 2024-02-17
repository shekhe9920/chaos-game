package edu.ntnu.stud.idatg2003;

import edu.ntnu.stud.idatg2003.mathoperations.Complex;
import edu.ntnu.stud.idatg2003.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.transformation.JuliaTransform;


public class Main {


    public static void main(String[] args) {

        Complex complex;

        Complex z0 = new Complex(-1, 0);  // TODO: Test sqrt() method with values: x0 < 0 and x1 = 0

        complex = z0.sqrt();
        System.out.println(complex.getX0() + " + " + complex.getX1() + "i");
        System.out.println("-----------------------------------------------------------------------------------------");

        Vector2D vector1 = new Vector2D(1, 2);
        Vector2D vector2 = new Vector2D(0.5, 0.5);
        vector1.subtract(vector2);
        System.out.println("(" + vector1.subtract(vector2).getX0() + ", " + vector1.subtract(vector2).getX1()+ ")");


        System.out.println("-----------------------------------------------------------------------------------------");

        Complex z1 = new Complex(2, 3);
        Complex z2 = new Complex(1, 2);
        Complex z3 = new Complex(z1.subtract(z2).getX0(), z1.subtract(z2).getX1());
        System.out.println(z3.getX0() + " + " + z3.getX1() + "i");


        System.out.println("-----------------------------------------------------------------------------------------");


        // Opprett et komplekst tall
        Vector2D v = new Vector2D(6, 4); // - (1 + 2i)
        Vector2D v1 = new Vector2D(-2, 3);
        Vector2D v2 = new Vector2D(1, 2);
        Vector2D v3 = new Vector2D(0.5, 0.5);
        Vector2D v4 = new Vector2D(-6, 2);
        Vector2D v5 = new Vector2D(-10, 2);

        // Utfør transformasjon og lagre resultatet
        Vector2D transformedZ = juliaTransformation(v);
        Vector2D transformedZ1 = juliaTransformation(v1);
        Vector2D transformedZ2 = juliaTransformation(v2);
        Vector2D transformedZ3 = juliaTransformation(v3);
        Vector2D transformedZ4 = juliaTransformation(v4);
        Vector2D transformedZ5 = juliaTransformation(v5);

        // Skriv ut resultatet av transformasjonen
        System.out.println("Transformert vektor: (" + transformedZ.getX0() + ", " + transformedZ.getX1() + ")");
        System.out.println("Transformert vektor: (" + transformedZ1.getX0() + ", " + transformedZ1.getX1() + ")");
        System.out.println("Transformert vektor: (" + transformedZ2.getX0() + ", " + transformedZ2.getX1() + ")");
        System.out.println("Transformert vektor: (" + transformedZ3.getX0() + ", " + transformedZ3.getX1() + ")");
        System.out.println("Transformert vektor: (" + transformedZ4.getX0() + ", " + transformedZ4.getX1() + ")");
        System.out.println("Transformert vektor: (" + transformedZ5.getX0() + ", " + transformedZ5.getX1() + ")");

        System.out.println("-----------------------------------------------------------------------------------------");

    }


    public static Vector2D juliaTransformation(Vector2D vector2D) {

        Complex c = new Complex(1, 2);
        int sign = -1;
        JuliaTransform juliaTransform = new JuliaTransform(c, sign);
        return juliaTransform.transform(vector2D);
    }

}
