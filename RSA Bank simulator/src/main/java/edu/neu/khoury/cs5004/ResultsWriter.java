package edu.neu.khoury.cs5004;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Class results writer that writes transaction information to file.
 */
public class ResultsWriter {
  private BufferedWriter writer;

  /**
   * Constructor for file writer.
   * @param outputFile - Opens up output file to write to.
   */
  public ResultsWriter(String outputFile) {
    try {
      writer = new BufferedWriter(new FileWriter(outputFile));
      writer.write("Transaction number, Time, Client ID, Message, "
          + "Digital signature, Verified, Transaction status\n");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Writes each transaction to file.
   * @param count - Number of transaction.
   * @param client - Client transaction being written to the file.
   * @param message - Transactions message.
   * @param verified - Whether or not transaction was verified.
   * @param status - Whether or not deposit or withdrawal was accepted.
   */
  public void writeData(int count, Client client, Message message,
      Boolean verified, TransactionStatus status) {
    try {
      writer.write(count + ", ");
      writer.write(LocalDateTime.now().withNano(0).withSecond(0) + ", ");
      writer.write(client.getIdNumber() + ", ");
      writer.write(message.getMessageContents() + ", ");
      writer.write(message.getSignature() + ", ");
      if (verified) {
        writer.write("yes, ");

      } else {
        writer.write("no, ");
      }
      switch (status) {
        case DEPOSIT_ACCEPTED:
          writer.write("deposit accepted\n");
          break;
        case DEPOSIT_DECLINED:
          writer.write("deposit declined\n");
          break;
        case WITHDRAWAL_ACCEPTED:
          writer.write("withdrawal accepted\n");
          break;
        case WITHDRAWAL_DECLINED:
          writer.write("withdrawal declined\n");
          break;
        default:
          System.out.println("None of the four transaction status cases.");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Closes file.
   * @return True if file closed false if writer was null.
   */
  public Boolean closeWriter() {
    if (writer != null) {
      try {
        writer.close();
        return true;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return false;
  }

}
