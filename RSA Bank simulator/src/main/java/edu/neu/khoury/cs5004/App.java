package edu.neu.khoury.cs5004;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        SecureBankVerificationSimulator simulator = CommandLineProcessor.processArguments(args);
        simulator.generateAndVerifyMessages();
    }
}
