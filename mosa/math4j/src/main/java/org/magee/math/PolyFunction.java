/**
 * PolyFunction.java A class for defining and exploring a polynomial function.
 */

/* This file is part of Math4J.
 * Math4J is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Math4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Math4J; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 * 
 * Copyright 2005 Anthony Magee
 */

package org.magee.math;

// import other packages
import org.magee.util.ArrayUtil;
import org.magee.util.MathUtil;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

/**
 * The <code>PolyFunction</code> class defines a polynomial function.
 * <p>
 * 
 * It contains methods for exploring the properties of such a function. There
 * are methods for finding roots, polynomial degree, factoring, etc.
 * <p>
 * 
 * The coeffients of this class are stored in an array such that coefs[0]
 * represents the coeffient of the x<sup>n</sup> term, coefs[1] represents the coeffient
 * of the term x<sup>n-1</sup>, therefore coefs[n] represents the coeffient of the term
 * x<sup>0</sup>.<br>
 * new PolyFunction( { 6.0, 0.0, 5.1, 3.0 } ) => f(x) = 6x<sup>3</sup> + 5.1x<sup>2</sup> + 3
 * 
 * @author Anthony W. Magee
 * @version 1.1, November 5, 2005
 */

/*
 * version changes
 * 1.0 intial writing 
 * 1.1 finalized getRoots and rewrite of constructors
 */
public class PolyFunction extends Function {

    // private members
    private double[] coefs; // the coeffients of this function

    private int degree; // the degree of this function

    /**
     * Constructor that creates a PolyFunction with degree zero and no
     * coeffients.
     * 
     */
    public PolyFunction() {
        this.degree = 0;
        this.coefs = new double[0];
    }

    /**
     * Overloaded constructor that creates a PolyFunction with the double
     * coeffients as defined by <code>coefs</code>. The degree is determined
     * to be the highest possible polynomial with non-zero degree.
     * 
     * @param coefs The coeffients of the polynomial function
     */
    public PolyFunction(double[] coefs) {
        degree = coefs.length - 1;
        for(int i = 0; i < coefs.length; i++) { // determine degree to be the highest polynomial with non-zero coeffient
            if(coefs[i] == 0.0)
                degree -= 1;
            else
                break;
        }

        if(degree != coefs.length - 1) {
            coefs = ArrayUtil.subArray(coefs, coefs.length - degree - 1, coefs.length - 1);
        }

        this.coefs = ArrayUtil.reverse(coefs);
    }

    /**
     * Overloaded constructor that creates a PolyFunction with the long
     * coeffients as defined by <code>coefs</code>. The degree is determined
     * to be the highest possible polynomial with non-zero degree.
     * 
     * @param coefs The coeffients of the polynomial function
     */
    public PolyFunction(long[] coefs) {
        degree = coefs.length - 1;
        for(int i = 0; i < coefs.length; i++) { // determine degree to be the highest polynomial with non-zero coeffient
            if(coefs[i] == 0.0)
                degree -= 1;
            else
                break;
        }

        if(degree != coefs.length - 1) {
            coefs = ArrayUtil.subArray(coefs, coefs.length - degree - 1, coefs.length - 1);
        }

        double[] temp = new double[coefs.length];
        for(int i = 0; i < coefs.length; i++) {
            temp[i] = (double) coefs[i];
        }

        this.coefs = ArrayUtil.reverse(temp);
    }

    /**
     * Overloaded constructor that creates a PolyFunction with the int
     * coeffients as defined by <code>coefs</code>. The degree is determined
     * to be the highest possible polynomial with non-zero degree.
     * 
     * @param coefs The coeffients of the polynomial function
     */
    public PolyFunction(int[] coefs) {
        degree = coefs.length - 1;
        for(int i = 0; i < coefs.length; i++) { // determine degree to be the highest polynomial with non-zero coeffient
            if(coefs[i] == 0.0)
                degree -= 1;
            else
                break;
        }

        if(degree != coefs.length - 1) {
            coefs = ArrayUtil.subArray(coefs, coefs.length - degree - 1, coefs.length - 1);
        }

        double[] temp = new double[coefs.length];
        for(int i = 0; i < coefs.length; i++) {
            temp[i] = (double) coefs[i];
        }

        this.coefs = ArrayUtil.reverse(temp);
    }

    /**
     * Overloaded constructor that creates a PolyFunction with degree as defined
     * by <code>degree</code>. All coeffients will be zero.
     * 
     * @param degree The degree of the constructed polynomial
     */
    public PolyFunction(int degree) {
        this.degree = degree;

        // fill coefs with zeros
        coefs = new double[degree + 1];
        for(int i = 0; i < coefs.length; i++) {
            coefs[i] = 0.0;
        }

    }

    /**
     * Returns the degree of the polynomial.
     */
    public int getDegree() {
        return degree;
    }

    /**
     * Returns the coefs of the polynomial as an array of type double.
     */
    public double[] getCoeffients() {
        return ArrayUtil.reverse(coefs);
    }

    /**
     * Sets the degree of the polynomial to <code>degree</code> and adjusts
     * the coeffients to match <code>degree</code>.
     * 
     * @param degree The degree of the polynomial
     */
    public void setDegree(int degree) {
        if(degree != this.degree) {
            double[] temp = new double[degree + 1];
            for(int i = 0; i < temp.length; i++) { // transfer coefs to new array
                try {
                    temp[i] = coefs[i]; // if degree < this.degree then coefs is truncated
                } catch(ArrayIndexOutOfBoundsException e) { // but if degree > this.degree then coefs is
                    temp[i] = 0.0; // expanded and filled with zeros
                }
            }
            coefs = temp; // set coefs to the new array of coeffients
            this.degree = degree; // finally set the degree
        }
    }

    /**
     * Sets the coeffients of the polynomial to <code>coefs</code> and
     * redefines its degree.
     * 
     * @param coefs The coefs of the polynomial
     */
    public void setCoeffients(double[] coefs) {
        degree = coefs.length - 1;
        for(int i = coefs.length - 1; i >= 0; i--) { // determine degree to be the highest polynomial with non-zero coeffient
            if(coefs[i] == 0.0)
                degree -= 1;
            else
                break; // if we hit a non-zero stop because we don't want to blindly remove all zeros
        }

        if(degree != coefs.length - 1) {
            coefs = ArrayUtil.subArray(coefs, degree - 1, coefs.length);
        }

        this.coefs = ArrayUtil.reverse(coefs);
    }

    /**
     * Returns a string representation of the polynomial function with or
     * without zeros.
     * 
     * @param fill A boolean to return zero coeffients or not
     */
    public String toString(boolean fill) {
        StringBuffer value = new StringBuffer();

        try {
            if(fill) {
                for(int i = coefs.length - 1; i >= 0; i--) {
                    value.append(Double.toString(coefs[i]));
                    if(i != 0) {
                        if(i > 1) {
                            value.append(" x^");
                            value.append(Integer.toString(i));
                        } else {
                            value.append(" x");
                        }
                        if(i != 0)
                            value.append(" + ");
                    }
                }
            } else {
                for(int i = coefs.length - 1; i >= 0; i--) {
                    if(coefs[i] != 0.0) {
                        value.append(Double.toString(coefs[i]));
                        if(i != 0) {
                            if(i > 1) {
                                value.append(" x^");
                                value.append(Integer.toString(i));
                            } else
                                value.append(" x");
                            
                            value.append(" + ");
                        }
                    }
                }
            }
        } catch(ArrayIndexOutOfBoundsException e) {
            if(coefs.length == 0)
                value.append("0");
            else
                e.printStackTrace();
        }
        
        String tmp = value.toString();
        if(tmp.endsWith("+ "))
            tmp = tmp.substring(0, tmp.length() - 3);
        return tmp;
    }

    /**
     * Returns a string representation of the polynomial function without zeros.
     */
    public String toString() {
        return this.toString(false);
    }

    public double valueAt(double x) {
        double value = 0.0;

        if(degree != 0) {
            for(int i = 0; i < coefs.length; i++) {
                value += (Math.pow(x, i) * coefs[i]);
            }
        }

        return value;
    }

    public PolyFunction getDerivFunc() {
        double[] derivCoefs = new double[degree];
        for(int i = 0; i < degree; i++) {
            derivCoefs[i] = coefs[i + 1] * (i + 1);
        }
        return new PolyFunction(ArrayUtil.reverse(derivCoefs));
    }

    public PolyFunction getAntiderivFunc() {
        double[] antiCoefs = new double[coefs.length + 1];
        antiCoefs[0] = 0.0;
        for(int i = 1; i < antiCoefs.length; i++) {
            antiCoefs[i] = coefs[i - 1] * (i - 1);
        }
        return new PolyFunction(ArrayUtil.reverse(antiCoefs));
    }

    public double area(double x1, double x2) {
        if(x1 == x2)
            return 0.0;
        
        if(x1 < x2) {
            PolyFunction ad = getAntiderivFunc();
            return ad.valueAt(x2) - ad.valueAt(x1);
        } else
            throw new ArithmeticException("Error x1 > x2; please enter as x1 < x2");
    }
    
    /**
     * Determines all real roots of the function.
     * 
     * @return An array of class <code>double</code> that contains the real
     *         roots of this function.
     */
    public double[] getRealRoots() {
        if(degree == 2) {
            return getQRealRoots();
        } else if (degree == 1) {
            return getLRealRoot();
        } else if (degree == 0) {
            return new double[] {};
        }
        
        Complex[] allRoots = getComplexRoots();
        double[] roots;

        int reals = 0;
        for(int i = 0; i < degree; i++) {
            if(!allRoots[i].isPure()) {
                reals++; // count the number of real roots
            }
        }

        roots = new double[reals]; // set the length of the root array

        int r = 0;
        for(int i = 0; i < degree; i++) {
            if(!allRoots[i].isPure()) {
                roots[r] = MathUtil.errorRound(allRoots[i].real); // store the real roots in the array
                r++;
            }
        }

        return roots;
    }
    
    private double[] getQRealRoots() {
        double a = coefs[2],
               b = coefs[1],
               c = coefs[0];
        
        double radicand = b * b - 4 * a * c;
        if(radicand >= 0) {
            double[] roots = new double[2];
            roots[0] = (-b + Math.sqrt(radicand)) / 2 / a;
            roots[1] = (-b - Math.sqrt(radicand)) / 2 / a;
            return roots;
        }
        return new double[] {};
    }
    
    private double[] getLRealRoot() {
        if(coefs[0] != 0)
            return new double[] {-coefs[0] / coefs[1]};
        else
            return new double[] {0};
    }

    /**
     * Determines all roots, real and complex, of the function.
     * 
     * @return An array of class <code>Complex</code> that contains the roots
     *         of this function.
     */
    public Complex[] getComplexRoots() {
        double an = coefs[coefs.length - 1];
        double[][] m = new double[degree][degree];

        /* this constructs the companion matrix for this polynomial
         * 
         * | 0 0 ... 0   -a(0)/a(n) |
         * | 1 0 ... 0   -a(1)/a(n) |
         * | 0 1 ... 0   -a(2)/a(n) |
         * | . .     .       ...    |
         * | 0 0 ... 1 -a(n-1)/a(n) | 
         */
        for(int row = 0; row < degree; row++) {
            for(int col = 0; col < degree; col++) {
                if(col == degree - 1)
                    m[row][col] = -coefs[row] / an;
                else if(col == row)
                    m[row + 1][col] = 1;
            }                                       
        }

        Complex[] roots = new Complex[degree];
        EigenvalueDecomposition ed = new EigenvalueDecomposition(new Matrix(m));
        double[] imag = ed.getImagEigenvalues();
        double[] real = ed.getRealEigenvalues();
        for(int r = 0; r < degree; r++) {
            roots[r] = new Complex(MathUtil.errorRound(real[r]), MathUtil.errorRound(imag[r]));
        }

        return roots;
    }

    static final long serialVersionUID = 11L;
}
