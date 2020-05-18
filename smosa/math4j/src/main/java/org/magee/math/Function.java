/**
 * Function.java An abstract class for describing classes of the function form.
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

/**
 * The <code>Function</code> class is the foundation for a function form
 * class.
 * 
 * @author Anthony W. Magee
 * @version 1.0, February 2, 2002
 */

/*
 * version changes
 */
public abstract class Function implements Serializable {

    /**
     * Returns a string representation of this function.
     */
    public abstract String toString();

    /**
     * Returns the value of this function at x.
     * 
     * @param x The value to evaluate the function at.
     * @return The value of y at x.
     */
    public abstract double valueAt(double x);
    
    /**
     * Returns the area under the curve from <code>x1</code> to <code>x2</code>.
     * 
     * @param x1 The lower bound.
     * @param x2 The upper bound.
     * @return The area under the curve in the specified interval.
     */
    public abstract double area(double x1, double x2);
    
    /**
     * Gets the derivative of this function.
     */
    public abstract Function getDerivFunc();
    
    /**
     * Gets the antiderivative of this function.
     */
    public abstract Function getAntiderivFunc();
}
