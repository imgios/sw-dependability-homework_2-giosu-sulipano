package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.cli.Option;
import org.junit.runner.RunWith;

public class OptionTest {

    /**
     * This method tests hasArgName() method.
     * hasArgName() should return true only if the name has been set.
     * It's not clear if an empty name can be considered ok, I will assume it's not.
     *
     * @throws Throwable
     */
  @Test
  public void test01()  throws Throwable  {
      Option option0 = new Option("", "");
      option0.setArgName("");
      boolean boolean0 = option0.hasArgName();
      //assertTrue(boolean0); // buggy?
      assertFalse("It shouldn't be empty", boolean0);
  }

    /**
     * This method tests getId() method.
     * getId() has a bug, it should return the first char (index 0) instead of the second (index 1)
     * @throws Throwable
     */
  @Test
  public void test02()  throws Throwable  {
      Option option0 = new Option("arg", "arg");
      int int0 = option0.getId();
      assertEquals("arg", option0.getArgName());
      assertFalse(option0.hasLongOpt());
      assertFalse(option0.hasArgs());
      //assertEquals(114, int0);
      assertEquals("It should be char 'a'", "a", int0);
  }

    /**
     * This method should tests getKey() but there isn't any check.
     * @throws Throwable
     */
  @Test
  public void test03()  throws Throwable  {
      Option option0 = new Option((String) null, (String) null);
      String optionKey = option0.getKey();
      assertEquals("arg", option0.getArgName());
      assertFalse(option0.hasArgs());
      assertNull("The key should be null", optionKey);
  }

    /**
     * This method tests hasArgs() method.
     * @throws Throwable
     */
  @Test
  public void test04()  throws Throwable  {
      Option option0 = new Option("NXb", true, "NXb");
      boolean boolean0 = option0.hasArgs();
      assertEquals("arg", option0.getArgName());
      assertFalse(option0.hasLongOpt());
      assertTrue(boolean0);
      assertEquals(1, option0.getArgs());
  }

    /**
     * This method tests equals() method.
     * There's a problem in the method, because it return true when comparing null and "" (empty string) of two different instances.
     * So, we can say that it's buggy, it should return true in this case.
     * @throws Throwable
     */
  @Test
  public void test05()  throws Throwable  {
      Option option0 = new Option("", "");
      Option option1 = new Option("", "", false, "");
      boolean boolean0 = option0.equals(option1);
      assertEquals("arg", option1.getArgName());
      assertFalse(option0.hasLongOpt());
      assertFalse(option1.hasArgs());
      //assertTrue(boolean0);
      assertFalse(boolean0);
  }

    /**
     * This is another test for equals().
     * I have spotted it as buggy in test05().
     *
     * @throws Throwable
     */
  @Test
  public void test06()  throws Throwable  {
      Option option0 = new Option((String) null, "", false, "");
      Option option1 = new Option((String) null, "");
      boolean boolean0 = option0.equals(option1);
      assertTrue(option0.hasLongOpt());
      //assertTrue(boolean0);
      assertFalse(boolean0);
      assertEquals("arg", option1.getArgName());
      assertFalse(option1.hasLongOpt());
      assertFalse(option1.hasArgs());
  }

    /**
     * Another test for equals().
     *
     * @throws Throwable
     */
  @Test
  public void test07()  throws Throwable  {
      Option option0 = new Option("", "Pz");
      Option option1 = new Option("Pz", "");
      boolean boolean0 = option0.equals(option1);
      assertFalse(boolean0);
      assertEquals((-1), option1.getArgs());
      assertFalse(option1.hasLongOpt());
      assertEquals("", option1.getDescription());
      assertEquals("Pz", option1.getOpt());
      assertEquals("arg", option1.getArgName());
  }

    /**
     * This test method should spot the bug of equals().
     * Seems it isn't able to recognize two different longOpt.
     *
     * @throws Throwable
     */
  @Test
  public void testEquals() throws Throwable {
      Option option0 = new Option("g", "gios", false, "");
      Option option1 = new Option("g", "suli", false, "");
      boolean equalLongOpt = option0.equals(option1);
      assertFalse("It should be false", equalLongOpt);
  }

    /**
     * This method tests hasArg().
     * @throws Throwable
     */
  @Test
  public void test08()  throws Throwable  {
      Option option0 = new Option((String) null, (String) null);
      boolean boolean0 = option0.hasArg();
      assertFalse(boolean0);
      assertEquals((-1), option0.getArgs());
      assertEquals("arg", option0.getArgName());
  }

    /**
     * This method tests clone();
     * @throws Throwable
     */
  @Test
  public void test09()  throws Throwable  {
      Option option0 = new Option("", "", true, "");
      Option option1 = (Option)option0.clone();
      boolean boolean0 = option0.equals(option1);
      assertEquals("arg", option1.getArgName());
      assertTrue(boolean0);
      assertTrue(option1.hasArg());
      assertNotSame(option1, option0);
  }

    /**
     * This method should tests getOpt() but it doesn't check anything.
     * @throws Throwable
     */
  @Test
  public void test10()  throws Throwable  {
      Option option0 = new Option("NO_ARGS_ALLOWED", "NO_ARGS_ALLOWED");
      String optionOpt = option0.getOpt();
      assertEquals("arg", option0.getArgName());
      assertFalse(option0.hasLongOpt());
      assertFalse(option0.hasArgs());
      assertEquals("NO_ARGS_ALLOWED", optionOpt);
  }
}
