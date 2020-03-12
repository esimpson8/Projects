package edu.neu.khoury.cs5004;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CommandLineProcessorTest {

  SecureBankVerificationSimulator simulator;
  String args[] = {"1200", "500", "0.8",
      "Assignment9"};
  String argsInvalid1[] = {"1200", "500",
      "Assignment9"};
  String argsInvalid2[] = {"1200", "500", "80", "2"};

  @Before
  public void setUp() throws Exception {
    simulator = new SecureBankVerificationSimulator(1200, 500, 0.8,
        "Assignment9");
  }

  @Test
  public void processArguments() throws IllegalArgumentException {
    assertTrue(simulator.equals(CommandLineProcessor.processArguments(args)));
  }


  @Test(expected = IllegalArgumentException.class)
  public void processArgumentsInvalid() throws IllegalArgumentException {
    CommandLineProcessor.processArguments(argsInvalid1);
  }
}