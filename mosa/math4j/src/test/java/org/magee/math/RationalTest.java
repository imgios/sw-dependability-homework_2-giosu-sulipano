package org.magee.math;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;

public class RationalTest {

    /** test01 tests the subtract() and negate() methods.
     * The operation computed is -1[(-1/-1)-(-1/-1)] but this is wrong.
     * Subtract(long) contains a bug: it creates a Rational with -1 as denominator instead of using 1.
     * The correct operation is -1[(-1/-1)-(-1/1)] = -2
     * So, every operation done with Subtract(long) is wrong.
     *
     * @throws Throwable
     */
  @Test
  public void test01()  throws Throwable  {
      Rational rational0 = new Rational((-1L), (-1L));
      Rational rational1 = rational0.subtract((-1L));
      Rational rational2 = rational1.negate();
      //assertEquals(0.0, rational2.doubleValue(), 0.01); doesn't discover the bug
      assertEquals("Operation result is wrong.", 2.0, rational2.doubleValue(), 0.01);
  }

    /**
     * This method tests abs() and multiply(Rational r).
     * Bugs found:
     * 1. abs() doesn't compute correctly the absolute value of the denominator.
     *    In fact, if denominator < 0 it returns +denominator instead of -denominator.
     * 2. multiply(Rational) divides numerators instead of multiplying them.
     *
     * Operation computed: |(-1/-1)| * (-1/-1) = 1
     * @throws Throwable
     */
  @Test
  public void test02()  throws Throwable  {
      Rational rational0 = new Rational((-1L), (-1L));
      Rational rational1 = rational0.abs();
      Rational rational2 = rational1.multiply(rational0);
      assertEquals((-1L), rational2.numerator);
      assertEquals((byte) 1, rational1.byteValue());
  }

    /**
     * test03() tests .negate() and .inverse() methods.
     * No bugs found in the production class.
     *
     * Op. computed: [-1(-2685/-2685)]^(-1) = -2685/2685 = -1
     *
     * @throws Throwable
     */
  @Test
  public void test03()  throws Throwable  {
      Rational rational0 = new Rational((-2685L), (-2685L));
      Rational rational1 = rational0.negate();
      Rational rational2 = rational1.inverse();
      assertEquals(2685L, rational2.denominator);
      assertEquals((-2685L), rational0.numerator);
      assertEquals((short) (-1), rational2.shortValue());
  }

    /**
     * This method tests multiply(long scalar).
     * The test method seems good because catch that multiply is trying to create a number with 0 as denominator.
     *
     * @throws Throwable
     */
  @Test
  public void test04()  throws Throwable  {
      Rational rational0 = new Rational((-1432L), 24840256L);
      rational0.denominator = 0L;
      // Undeclared exception!
      try { 
        rational0.multiply(1L);
        fail("Expecting exception: NumberFormatException");
      
      } catch(NumberFormatException e) {
         //
         // Cannot create a Rational object with zero as the denominator
         //
         verifyException("org.magee.math.Rational", e);
      }
  }

    /**
     * This method tests divide(Rational r) with positive numbers.
     * There's a problem, divide() (that is fine) calls multiply(Rational r) which is bugged!
     *
     * @throws Throwable
     */
  @Test
  public void test05()  throws Throwable  {
      Rational rational0 = new Rational(667L, 1415L);
      Rational rational1 = rational0.divide(rational0);
      float float0 = rational1.floatValue();
      assertEquals(0.0F, float0, 0.01F);
      assertEquals(0.4713781F, rational0.floatValue(), 0.01F);
      //assertEquals(943805L, rational1.denominator);
      assertEquals(943805L, rational1.numerator);
  }

    /**
     * This method tests divide(Rational r) with negative numbers.
     * Same as test05, divide uses multiply(Rational r) that's bugged.
     *
     * Op. computed: (-1/-1)/(-1/-1) = 1
     * @throws Throwable
     */
  @Test
  public void test06()  throws Throwable  {
      Rational rational0 = new Rational((-1L), (-1L));
      Rational rational1 = rational0.divide(rational0);
      assertEquals((-1L), rational0.denominator);
      assertEquals(1L, rational1.longValue());
  }

    /**
     * test07 tests pow(int power) method.
     * Seems fine.
     *
     * Op. computed: (2^0)/(2^0) = 1
     * @throws Throwable
     */
  @Test
  public void test07()  throws Throwable  {
      Rational rational0 = new Rational(2L, 2L);
      Rational rational1 = rational0.pow(0);
      assertEquals(1L, rational1.numerator);
      assertEquals((short)1, rational0.shortValue());
      assertEquals(1.0, rational1.doubleValue(), 0.01);
  }

    /**
     * This one tests add(integer) method.
     * The add() method has a bug: when converting a integer into Rational it uses 0 as denonimator instead of 1.
     * So, it will always raise a NumberFormatException.
     * @throws Throwable
     */
  @Test
  public void test08()  throws Throwable  {
      Rational rational0 = new Rational(1493L, 1493L);
      // Undeclared exception!
      try { 
        rational0.add(0L);
        fail("Expecting exception: NumberFormatException");
      
      } catch(NumberFormatException e) {
         //
         // Cannot create a Rational object with zero as the denominator
         //
         verifyException("org.magee.math.Rational", e);
      }
  }

    /**
     * This one tests add(integer) method in order to exploit the bug.
     *
     * @throws Throwable
     */
    @Test
    public void testAdd() throws Throwable {
        Rational rational0 = new Rational(4L, 2L);
        Rational rational1 = rational0.add(2L);
        assertEquals("The add() method calculate the wrong number.", 4L, rational1.longValue());
    }

    /**
     * This method tests subtract(long) with a Rational having 0 as denominator.
     * Seems fine, it catches the exception trying to give in output a Rational having 0 as denominator.
     *
     * @throws Throwable
     */
  @Test
  public void test09()  throws Throwable  {
      Rational rational0 = new Rational(0L, 2503L);
      rational0.denominator = 0L;
      // Undeclared exception!
      try { 
        rational0.subtract((-4002L));
        fail("Expecting exception: NumberFormatException");
      
      } catch(NumberFormatException e) {
         //
         // Cannot create a Rational object with zero as the denominator
         //
         verifyException("org.magee.math.Rational", e);
      }
  }

    /**
     * This method tests the add() method with null value.
     * @throws Throwable
     */
  @Test
  public void test10()  throws Throwable {
      Rational rational0 = new Rational(1L, 1L);
      // Undeclared exception!
      try { 
        rational0.add((Rational) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.magee.math.Rational", e);
      }
  }

    /**
     * This test method tests intValue() method.
     * It checks if the result get successful casted.
     * @throws Throwable
     */
  @Test
  public void testIntValue() throws Throwable {
      Rational rational = new Rational(10L, 2L);
      assertEquals("Integer result is wrong.", 5, rational.intValue());
  }
}
