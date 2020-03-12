package edu.neu.khoury.cs5004;


/**
 * Class CommandLineProcessor that makes sure arguments are correct.
 */
public class CommandLineProcessor {
  private static final Integer REQUIRED_ARGS = 4;

  /**
   * Checks commandline argument if there are too many or few arguments.
   * @param args - Arguments from commandline,
   * @return - A secure bank verification object with all args from commandline,
   * @throws IllegalArgumentException - If two few or to many args.
   */
  public static SecureBankVerificationSimulator processArguments(String[] args)
      throws IllegalArgumentException {
    if (args.length != REQUIRED_ARGS) {
      throw new IllegalArgumentException("To few args");
    }
    return new SecureBankVerificationSimulator(Integer.valueOf(args[0]),
        Integer.valueOf(args[1]), Double.valueOf(args[2]), args[3]);
  }

}
