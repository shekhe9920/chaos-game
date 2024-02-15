package edu.ntnu.stud.idatg2003;

import edu.ntnu.stud.idatg2003.mathoperations.Complex;
import edu.ntnu.stud.idatg2003.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.transformation.JuliaTransform;


public class Main {


    public static void main(String[] args) {

        Complex complex;

        Complex z0 = new Complex(1, 1);

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

        Complex complexNumber = new Complex(2, 4);
        Complex zMinusC = (Complex) complexNumber.subtract(z2);

        double magnitude = Math.sqrt(zMinusC.getX0() * zMinusC.getX0() + zMinusC.getX1() * zMinusC.getX1());

        Complex numerator = (Complex) zMinusC.sqrt().multiply(new Complex(1, 0)).add(z2);

        Complex denominator = new Complex(Math.sqrt(magnitude), 0);

        Vector2D vec = new Vector2D(numerator.getX0()/denominator.getX0(), numerator.getX1()/denominator.getX0());

        System.out.println(vec.getX0() + "," + vec.getX1());

        System.out.println("-----------------------------------------------------------------------------------------");


        // Opprett et komplekst tall
        //Complex zComplex1 = new Complex(1, 1);

        Vector2D v = new Vector2D(2, 4);

        // Utfør transformasjon og lagre resultatet
        Vector2D transformedZ = juliaTransformation(v);

        // Skriv ut resultatet av transformasjonen
        System.out.println("Transformert vektor: (" + transformedZ.getX0() + ", " + transformedZ.getX1() + ")");




    }


    public static Vector2D juliaTransformation(Vector2D vector2D) {
        JuliaTransform juliaTransform = new JuliaTransform(new Complex(1, 2), 1);
        Vector2D result = juliaTransform.transform(vector2D);

        return result;
    }


//commit test

}
