package edu.neu.khoury.cs5004;

import static org.junit.Assert.*;

import java.math.BigInteger;
import org.junit.Before;
import org.junit.Test;

public class MessageTest {
  BigInteger signature;
  Integer messageContent;
  Message message;
  Client client;
  Message message2;
  Integer message1Content;
  BigInteger signatureInvalid;

  @Before
  public void setUp() throws Exception {
    messageContent = 30009;
    message1Content = 30000;
    client = new Client(1);
    signature = client.createSignature(messageContent, true);
    signatureInvalid = client.createSignature(messageContent, false);
    message = new Message(messageContent, signature);
    message2 = new Message(message1Content, signature);


  }

  @Test
  public void validateMessage() {
    assertTrue(message.validateMessage(client));
    message = new Message(messageContent, signatureInvalid);
    assertFalse(message.validateMessage(client));
  }

  @Test
  public void isDeposit() {
    assertFalse(message.isDeposit());
    assertTrue(message2.isDeposit());
  }

  @Test
  public void isWithdrawal() {
    assertFalse(message2.isWithdrawal());
    assertTrue(message.isWithdrawal());
  }

  @Test
  public void getMessageContents() {
    assertEquals(messageContent, message.getMessageContents());
  }

  @Test
  public void getSignature() {
    assertEquals(signature, message.getSignature());
  }
}