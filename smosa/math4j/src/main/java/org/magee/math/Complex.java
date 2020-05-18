/**
 * Complex.java Class for describing and manipulating complex numbers.
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
import java.io.Serializable;

import org.magee.util.MathUtil;

/**
 * The <code>Complex</code> class provides functionality for the complex
 * number system where <i>i</i> is not ignored. An object of type
 * <code>Complex</code> contains two fields whose type is <code>double</code>.
 * <p>
 * By necessity, this class provides methods for addition, subtraction,
 * multiplication, division, exponential, and other trigonometric and
 * logarithmic functions.
 * 
 * @author Anthony W. Magee
 * @version 1.2, November 5, 2005
 */

/*
 * version changes
 * 1.0 intial writing: February 5, 2002
 * 1.1 added trimming to toString() method: March 4, 2002
 * 1.2 modified methods for accuracy and added other methods for completeness
 */
public class Complex implements Serializable {
    static final long serialVersionUID = 12L;

    public double real, imag;

    /**
     * Constructor that defines the <code>real</code> and
     * <code>imaginary</code> parts of the number.
     * 
     * @param real The real part of the number.
     * @param imag The imaginary part of the number.
     */
    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    /**
     * Computes <code>this</code>^<code>power</code>.
     * 
     * @param power The exponential power.
     * @return <code>this</code>^<code>power</code>.
     */
    public Complex pow(double power) {
        double r = abs();
        double theta = angle();

        return new Complex(Math.pow(r, power) * Math.cos(power * theta), Math.pow(r, power) * Math.sin(power * theta));
    }

    /**
     * Computes <code>this</code>^<code>p</code>.
     * 
     * @param p The exponential power.
     * @return <code>this</code>^<code>p</code>.
     */
    public Complex pow(Complex p) {
        double a = real, b = imag, c = p.real, d = p.imag;
        double ns = a * a + b * b;
        double dArg = d / 2;
        double cArg = c * arg();
        double dDenom = Math.pow(Math.E, d * arg());

        double newReal = Math.pow(ns, c / 2) / dDenom * (Math.cos(dArg) * Math.cos(cArg) * Math.log(ns) - Math.sin(dArg) * Math.sin(cArg) * Math.log(ns));
        double newImag = Math.pow(ns, c / 2) / dDenom * (Math.cos(dArg) * Math.sin(cArg) * Math.log(ns) + Math.sin(dArg) * Math.cos(cArg) * Math.log(ns));
        return new Complex(newReal, newImag);
    }

    /**
     * Computes <code>this</code>+<code>c</code>.
     * 
     * @param c The addend to be added to <code>this</code>.
     * @return <code>this</code>+<code>c</code>.
     */
    public Complex add(Complex c) {
        return new Complex(real + c.real, imag + c.imag);
    }

    /**
     * Computes <code>this</code>*<code>c</code>.
     * 
     * @param c The multiplicand to be multiplied with <code>this</code>.
     * @return <code>this</code>*<code>c</code>.
     */
    public Complex multiply(Complex c) {
        double real = 0.0, imag = 0.0;

        real += this.real * c.real;
        real -= this.imag * c.imag;
        imag += this.real * c.imag;
        imag += this.imag * c.real;
        return new Complex(real, imag);
    }

    /**
     * Computes <code>this</code>*<code>d</code>.
     * 
     * @param d The multiplicand to be multiplied with <code>this</code>.
     * @return <code>this</code>*<code>d</code>.
     */
    public Complex multiply(double d) {
        return new Complex(real * d, imag * d);
    }

    /**
     * Determines if this number is purely complex, it has a real part of zero.
     * 
     * @return false if <code>this.real == 0.0</code>, true otherwise
     */
    public boolean isPure() {
        if(real != 0.0)
            return false;
        return true;
    }

    /**
     * Computes the absolute value of <code>this</code>.
     * <code>Complex c</code> = a + ib<br>
     * sqrt(a^2 + b^2)
     * 
     * @return sqrt(a^2 + b^2).
     */
    public double abs() {
        return Math.sqrt(real * real + imag * imag);
    }

    /**
     * Refer to abs()
     * 
     * @see org.magee.math.Complex#abs()
     */
    public double mod() {
        return abs();
    }

    /**
     * Refer to abs()
     * 
     * @see org.magee.math.Complex#abs()
     */
    public double norm() {
        return abs();
    }

    /**
     * Computes <code>this</code>-<code>c</code>.
     * 
     * @param c The subtrahend to be subtracted from <code>this</code>.
     * @return <code>this</code>-<code>c</code>.
     */
    public Complex subtract(Complex c) {
        return new Complex(real - c.real, imag - c.imag);
    }

    /**
     * Computes the negative of this number.
     * 
     * @return -<code>this</code>
     */
    public Complex negate() {
        return new Complex(-real, -imag);
    }

    /**
     * Computes the conjugate of this number.
     * 
     * @return (real, -imag)
     */
    public Complex conjugate() {
        return new Complex(real, -imag);
    }

    /**
     * Computes <code>this</code>/<code>c</code>.
     * 
     * @param c The divisor of <code>this</code>.
     * @return <code>this</code>/<code>c</code>.
     */
    public Complex divide(Complex c) {
        Complex result = multiply(c.conjugate());
        double divisor = Math.pow(c.abs(), 2);
        return new Complex(result.real / divisor, result.imag / divisor);
    }

    /**
     * Computes <code>this</code>/<code>d</code>.
     * 
     * @param d The divisor of <code>this</code>.
     * @return <code>this</code>/<code>d</code>.
     */
    public Complex divide(double d) {
        return multiply(1.0 / d);
    }

    /**
     * Computes the inverse of <code>this</code>.
     * 
     * @return <code>this</code>^-1.
     */
    public Complex inverse() {
        return new Complex(1.0, 0.0).divide(this);
    }

    /**
     * Computes the principal angle of this number.
     * 
     * @return The polar coordinate angle of this number in radians.
     */
    public double angle() {
        return Math.atan2(imag, real);
    }

    /**
     * Refer to angle()
     * 
     * @see org.magee.math.Complex#angle()
     */
    public double arg() {
        return angle();
    }

    /**
     * Refer to angle()
     * 
     * @see org.magee.math.Complex#angle()
     */
    public double phase() {
        return angle();
    }

    /**
     * Computes the principal branch of the logarithm of this complex.
     * 
     * @return The principal log of <code>this</code>
     */
    public Complex log() {
        return new Complex(Math.log(mod()), arg());
    }

    /**
     * Returns a string representation of this object.
     */
    public String toString() {
        return toString(8);
    }

    /**
     * Returns a string representation of this object with double truncation.
     * 
     * @param doubleLength The number of decimal places to return
     */
    public String toString(int doubleLength) {
        StringBuffer temp = new StringBuffer();
        temp.append(MathUtil.trim(real, doubleLength));
        if(imag < 0.0) {
            temp.append(" - ");
            temp.append(MathUtil.trim(-imag, doubleLength));
            temp.append(" i");
        } else {
            temp.append(" + ");
            temp.append(MathUtil.trim(imag, doubleLength));
            temp.append(" i");
        }
        return temp.toString();
    }
}