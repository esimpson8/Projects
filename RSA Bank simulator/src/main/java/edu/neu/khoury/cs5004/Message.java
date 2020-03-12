package edu.neu.khoury.cs5004;

import java.math.BigInteger;

/**
 * Class that is a message from a bank client.
 */
public class Message {
  private Integer messageContents;
  private BigInteger signature;

  /**
   * Constructor for messege.
   * @param messageContents - The content of the messege.
   * @param signature - The digital signature of the messege.
   */
  public Message(Integer messageContents, BigInteger signature) {
    this.messageContents = messageContents;
    this.signature = signature;
  }

  /**
   * RSA validates message.
   * @param client - Client that sent the message.
   * @return - True if RSA verification is correct.
   */
  public Boolean validateMessage(Client client) {
    BigInteger[] pkey = client.getPublicKey();
    BigInteger mprime = signature.modPow(pkey[0], pkey[1]);
    if (mprime.intValueExact() == (messageContents.intValue())) {
      return true;
    }
    return false;
  }

  /**
   * Checks whether or not the message is a deposit
   * @return - True if deposit.
   */
  public Boolean isDeposit() {
    return ((messageContents % 10) <= 4) && ((messageContents % 10) >= 0);
  }
  /**
   * Checks whether or not the message is a withdrawal
   * @return - True if withdrawal.
   */
  public Boolean isWithdrawal() {
    return ((messageContents % 10) <= 9) && ((messageContents % 10) >= 5);
  }

  /**
   * Returns message contents.
   * @return - the message content
   */
  public Integer getMessageContents() {
    return messageContents;
  }
  /**
   * Returns digital signature.
   * @return - the digital signature.
   */
  public BigInteger getSignature() {
    return signature;
  }
}
