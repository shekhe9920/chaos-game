package edu.ntnu.stud.idatg2003;

import edu.ntnu.stud.idatg2003.mathoperations.Complex;



public class Main {


    public static void main(String[] args) {

        Complex complex;

        Complex z0 = new Complex(1, 1);

        complex = z0.sqrt();
        System.out.println(complex.getX0() + " + " + complex.getX1() + "i");

    }


}
