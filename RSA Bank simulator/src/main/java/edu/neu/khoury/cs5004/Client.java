package edu.neu.khoury.cs5004;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Random;

public class Client {
  private BigInteger[] publicKey;
  private BigInteger[] privateKey;
  private Integer idNumber;
  private Random rnd;

  private static final Integer WITHDRAWAL_LIMIT_MAX = 3000;
  private static final Integer DEPOSIT_LIMIT_MAX = 2000;
  private static final Integer BIT_LENGTH_BIG_INT = 11;
  private static final Integer BIT_LENGTH_MIN_INT = Integer.bitCount(30010) + 1;

  private Integer withdrawalLimit;
  private Integer depositLimit;

  /**
   * Constructor for client object that generates withdrawl limit, deposit limit and keys
   * @param idNumber - Id number of client.
   */
  public Client(Integer idNumber) {
    rnd = new Random();
    this.idNumber = idNumber;
    generateKeys();
    this.withdrawalLimit = rnd.nextInt(WITHDRAWAL_LIMIT_MAX + 1);
    this.depositLimit = rnd.nextInt(DEPOSIT_LIMIT_MAX + 1);
  }

  /**
   * Creates an RSA signature for a given message contents based on this
   * client's private key. Will create an invalid, random message if needed.
   * @param messageContents The contents of the message
   * @param valid Whether the signature to create should be valid or not
   * @return A BigInteger representing the signature for the message
   */
  public BigInteger createSignature(Integer messageContents, Boolean valid) {
    BigInteger signature = new BigInteger(10, rnd);
    if (valid) {
      signature = BigInteger.valueOf(messageContents.longValue()).modPow(privateKey[0],
          privateKey[1]);
    }
    return signature;
  }

  // https://stackoverflow.com/questions/2290057/how-to-generate-a-random-biginteger-value-in-java

  /**
   * Generates and saves the public and private key pairs for this Client,
   * using the standard RSA encryption method.
   */
  private void generateKeys() {
    BigInteger one = new BigInteger("1");
    BigInteger prime;
    do {
      prime = BigInteger.probablePrime(BIT_LENGTH_BIG_INT, rnd);
    } while (prime.bitLength() < BIT_LENGTH_MIN_INT);

    BigInteger prime2;
    do {
      prime2 = BigInteger.probablePrime(BIT_LENGTH_BIG_INT, rnd);
    } while (prime2.bitLength() < BIT_LENGTH_MIN_INT);

    BigInteger nphi = (prime2.subtract(one)).multiply((prime.subtract(one)));
    BigInteger nnormal = prime2.multiply(prime);
    long num = 2;
    BigInteger alpha = BigInteger.valueOf(num);
    while (nphi.gcd(alpha).equals(one) == false || nnormal.gcd(alpha).equals(one) == false ) {
      num++;
      alpha = BigInteger.valueOf(num);
    }
    long num2 = 2;
    BigInteger beta = BigInteger.valueOf(2);
    while ((((alpha.multiply(beta)).mod(nphi))).equals(one) == false || alpha.equals(beta)) {
      num2++;
      beta = BigInteger.valueOf(num2);
    }
    this.publicKey = new BigInteger[]{beta, nnormal};
    this.privateKey = new BigInteger[]{alpha, nnormal};
  }

  /**
   * Returns this client's public key.
   * @return This client's public key
   */
  public BigInteger[] getPublicKey() {
    return publicKey;
  }

  /**
   * Returns this client's withdrawal limit.
   * @return This client's withdrawal limit
   */
  public Integer getWithdrawalLimit() {
    return withdrawalLimit;
  }

  /**
   * Returns this client's deposit limit.
   * @return This client's deposit limit
   */
  public Integer getDepositLimit() {
    return depositLimit;
  }

  /**
   * Returns this client's unique id number.
   * @return This client's unique id number
   */
  public Integer getIdNumber() {
    return idNumber;
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
    Client client = (Client) object;
    return idNumber.equals(client.idNumber);
  }

  /**
   *{@inheritDoc}.
   */
  @Override
  public int hashCode() {
    return Objects.hash(idNumber);
  }
}
