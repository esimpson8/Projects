package edu.neu.khoury.cs5004;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ClientTest {

  Client client;

  @Before
  public void setUp() {
    client = new Client(42);
  }

  @Test
  public void createSignature() {
    System.out.println(client.createSignature(1000, true));
  }

  @Test
  public void getPublicKey() {
    System.out.println(client.getPublicKey());
  }

  @Test
  public void getWithdrawalLimit() {
    for (int i = 0; i < 100; i++) {
      client = new Client(i);
      assertTrue(client.getWithdrawalLimit() <= 3000);
    }
  }

  @Test
  public void getDepositLimit() {
    for (int i = 0; i < 100; i++) {
      client = new Client(i);
      assertTrue(client.getDepositLimit() <= 2000);
    }
  }

  @Test
  public void getIdNumber() {
    assertEquals((Integer)42, client.getIdNumber());
  }

  @Test
  public void equals1() {
    Client client2 = new Client(42);
    assertTrue(client.equals(client2));
  }

  @Test
  public void hashCode1() {

  }
}