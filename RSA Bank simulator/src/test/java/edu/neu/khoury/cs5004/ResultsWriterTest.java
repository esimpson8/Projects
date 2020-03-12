package edu.neu.khoury.cs5004;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

public class ResultsWriterTest {
  String outputFile;
  BufferedReader reader;
  ResultsWriter writer;
  Client client;
  BigInteger signature;
  Integer messageContent;
  Message message;
  @Before
  public void setUp() throws Exception {
    outputFile = "test.txt";
    reader = new BufferedReader(new FileReader(outputFile));
    writer = new ResultsWriter(outputFile);
    client = new Client(1);
    messageContent = 30009;
    signature = client.createSignature(messageContent, true);
    message = new Message(messageContent, signature);
  }

  @Test
  public void writeData() {
    writer.writeData(1, client, message, true, TransactionStatus.DEPOSIT_ACCEPTED );
    writer.closeWriter();
    String testline = "Transaction number, Time, Client ID, Message, "
        + "Digital signature, Verified, Transaction status";
    String testline2 = "1, " + LocalDateTime.now().withNano(0).withSecond(0)+ ", " + "1, " + message.getMessageContents()
        + ", " + message.getSignature() + ", " + "yes, " + "deposit accepted";
    try {
      assertEquals(testline, reader.readLine());
      assertEquals(testline2, reader.readLine());
    }catch (IOException e){
      e.printStackTrace();
    }

  }



  @Test
  public void closeWriter() {
    assertTrue(writer.closeWriter());

  }
}