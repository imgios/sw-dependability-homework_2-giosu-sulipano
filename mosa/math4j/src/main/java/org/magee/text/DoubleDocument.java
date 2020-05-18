/**
 * DigitDocument.java A class that limits input into a text area to digits.
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

package org.magee.text;

// import other packages
import java.util.Arrays;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * The <code>DigitDocument</code> class controls the character input into text
 * areas.
 * <P>
 * 
 * The use of this class is implemented by calling setDocument on a text
 * component. When a string is entered into a text area by a keyboard event
 * (typing or pasting) or by other means (TextComponent.setText()) it is first
 * examined to contain only digits. If it passes this test then the string is
 * inserted for the view by the user.
 * 
 * @author Anthony W. Magee, Dr. James Denvir
 * @version 1.0, December 23, 2001
 */

/*
 * version changes
 */
public class DoubleDocument extends PlainDocument {
    
    private char[] doub = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-', 'E'};
    
    /**
     * implements code for checking the validity of the input
     */
    public void insertString(int offset, String s, AttributeSet atts) throws BadLocationException {
        if(s != null && s.length() > 0) { // check if there is anything in the string to examine
            Arrays.sort(doub); // required for binarySearch
            boolean isValid = true; // the state of the string
            int i = 0;
            while(isValid && i < s.length()) { // to save process time check isValid first
                if(Arrays.binarySearch(doub, s.charAt(i)) < 0) {
                    isValid = false; // an invalid character was found
                }
                i++;
            }
            
            if(isValid) {
                super.insertString(offset, s, atts); // a valid string  inserted
            }
        }
    }

    static final long serialVersionUID = 1L;
}