package edu.neu.khoury.cs5004;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SecureBankVerificationSimulatorTest {

  SecureBankVerificationSimulator sim;

  @Before
  public void setUp() throws Exception {
    sim = new SecureBankVerificationSimulator(10, 8, 0.8, "test.txt");
  }

  @Test
  public void validateInput() {
    try {
      sim = new SecureBankVerificationSimulator(90000, 200, 0.8, "test.txt");
    } catch (IllegalArgumentException e) {
      System.out.println("Passed valid input test 1.");
    }
    try {
      sim = new SecureBankVerificationSimulator(1000, 20000, 0.8, "test.txt");
    } catch (IllegalArgumentException e) {
      System.out.println("Passed valid input test 2.");
    }
    try {
      sim = new SecureBankVerificationSimulator(1000, 200, 1.5, "test.txt");
    } catch (IllegalArgumentException e) {
      System.out.println("Passed valid input test 3.");
    }
  }

  @Test
  public void generateAndVerifyMessages() {
    sim.generateAndVerifyMessages();
  }

  @Test
  public void getNumClients() {
    assertEquals((Integer)10, sim.getNumClients());
  }

  @Test
  public void getNumVerifications() {
    assertEquals((Integer)8, sim.getNumVerifications());
  }

  @Test
  public void getValidPercentage() {
    assertEquals((Double)0.8, sim.getValidPercentage(), 0);
  }

  @Test
  public void getOutputFile() {
    assertEquals("test.txt", sim.getOutputFile());
  }

  @Test
  public void equals1() {
    SecureBankVerificationSimulator sim2;
    sim2 = new SecureBankVerificationSimulator(10, 8, 0.8, "test.txt");
    assertTrue(sim2.equals(sim));
  }

  @Test
  public void hashCode1() {
  }
}