package edu.neu.khoury.cs5004;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

public class SecureBankVerificationSimulator {
  private Integer numClients;
  private Integer numVerifications;
  private double validPercentage;
  private String outputFile;
  private HashSet<Client> clients;
  private Random rnd;

  private static final Integer LOWER_BOUND = 0;
  private static final Integer CLIENTS_UPPER_BOUND = 50000;
  private static final Integer VERIFICATION_UPPER_BOUND = 10000;
  private static final Integer INVALID_MESSAGES_UPPER_BOUND = 1;
  private static final Integer ID_UPPER_BOUND = 100000;
  private static final Integer MESSAGE_SIZE_LIMIT = 5000;

  /**
   * Creates a new verification simulator based on the given parameters.
   * @param numClients The number of total clients in the system
   * @param numVerifications The number of transactions, and thus verifications, to process
   * @param validPercentage The percentage of transactions to simulate being valid
   * @param outputFile The file to write the results to
   */
  public SecureBankVerificationSimulator(Integer numClients,
      Integer numVerifications, Double validPercentage, String outputFile) {
    this.checkBound(CLIENTS_UPPER_BOUND, numClients);
    this.checkBound(VERIFICATION_UPPER_BOUND, numVerifications);
    this.checkBound(INVALID_MESSAGES_UPPER_BOUND, (int)Math.ceil(validPercentage));
    rnd = new Random();
    this.numClients = numClients;
    this.numVerifications = numVerifications;
    this.validPercentage = validPercentage;
    this.outputFile = outputFile;
  }

  /**
   * Checks whether the given parameter is within the given upper bound.
   * @param upperBound The upper bound to check
   * @param numChecked The number to check against the upper bound
   * @return Whether the number is within the upper bound
   */
  private Boolean checkBound(Integer upperBound, Integer numChecked) {
    if (LOWER_BOUND > numChecked || upperBound < numChecked) {
      throw new IllegalArgumentException("Number out of bounds");
    } else {
      return true;
    }
  }

  /**
   * Generates the set of clients randomly, giving them random keys.
   */
  private void generateClients() {
    clients = new HashSet<Client>();
    while (clients.size() < numClients) {
      Client client = new Client(rnd.nextInt(ID_UPPER_BOUND));
      clients.add(client);
    }
  }

  /**
   * Generates the set of clients first, then generates the
   * correct number of messages from those clients. Then
   * verifies the messages, and writes the results to file.
   */
  public void generateAndVerifyMessages() {
    generateClients();
    int count = 0;
    ResultsWriter writer = new ResultsWriter(outputFile);
    for (Client client : clients) {
      if (count >= numVerifications) {
        break;
      }

      BigInteger signature;
      Integer messageContents = rnd.nextInt(MESSAGE_SIZE_LIMIT);

      if (count < (this.validPercentage * numVerifications)) {
        signature = client.createSignature(messageContents, true);
      } else {
        signature = client.createSignature(messageContents, false);
      }

      Message message = new Message(messageContents, signature);
      Boolean verified = message.validateMessage(client);
      TransactionStatus status = TransactionStatus.WITHDRAWAL_DECLINED;
      if (message.isDeposit()) {
        if (verified && ((message.getMessageContents() / 10) < client.getDepositLimit())) {
          status = TransactionStatus.DEPOSIT_ACCEPTED;
        } else {
          status = TransactionStatus.DEPOSIT_DECLINED;
        }
      }
      if (message.isWithdrawal()) {
        if (verified && ((message.getMessageContents() / 10) < client.getWithdrawalLimit())) {
          status = TransactionStatus.WITHDRAWAL_ACCEPTED;
        } else {
          status = TransactionStatus.WITHDRAWAL_DECLINED;
        }
      }
      writer.writeData(count, client, message, verified, status);
      count++;
    }
    writer.closeWriter();
  }

  /**
   * Returns the number of clients that are in the current set,
   * or will be once it is created.
   * @return The number of clients
   */
  public Integer getNumClients() {
    return numClients;
  }

  /**
   * The number of messages that will have to be verified, or have
   * been verified.
   * @return The number of messages to verify
   */
  public Integer getNumVerifications() {
    return numVerifications;
  }

  /**
   * Returns the percentage of messages that will be created, or have
   * been created, to be valid (vs invalid).
   * @return The number of valid messages
   */
  public double getValidPercentage() {
    return this.validPercentage;
  }

  /**
   * Returns a String representing the name of the output file.
   * @return The name of the output file
   */
  public String getOutputFile() {
    return outputFile;
  }

  /**
   *{@inheritDoc}.
   */
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    SecureBankVerificationSimulator that = (SecureBankVerificationSimulator) object;
    return Double.compare(that.getValidPercentage(), getValidPercentage()) == 0
        && getNumClients().equals(that.getNumClients())
        && getNumVerifications().equals(that.getNumVerifications())
        && getOutputFile().equals(that.getOutputFile());
  }

  /**
   *{@inheritDoc}.
   */
  @Override
  public int hashCode() {
    return Objects
        .hash(getNumClients(), getNumVerifications(), getValidPercentage(), getOutputFile(),
            clients, rnd);
  }
}
