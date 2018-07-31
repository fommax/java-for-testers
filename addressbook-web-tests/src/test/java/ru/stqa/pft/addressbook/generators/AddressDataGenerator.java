package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.AddressData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class AddressDataGenerator {

  @Parameter(names = "-c", description = "Contacts count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data format")
  public String format;

  public static void main(String[] args) throws IOException {
    AddressDataGenerator generator = new AddressDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private void run() throws IOException {
    List<AddressData> addresses = generateAddresses(count);
    if (format.equals("csv")) {
      saveAsCsv(addresses, new File(file));
    } else if (format.equals("xml")) {
      saveAsXml(addresses, new File(file));
    } else {
      System.out.println("Unrecognized format: " + format);
    }
  }

  private void saveAsXml(List<AddressData> addresses, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(AddressData.class);
    String xml = xstream.toXML(addresses);
    try (Writer writer = new FileWriter(file)) {
      writer.write(xml);
    }
  }

  private void saveAsCsv(List<AddressData> addresses, File file) throws IOException {
    System.out.println(new File(".").getAbsolutePath());
    try (Writer writer = new FileWriter(file)) {
      for (AddressData address : addresses) {
        writer.write(String.format("%s:%s:%s:%s:%s:%s:%s:%s:%s:%s:%s:", address.getFirstname(), address.getLastname(), address.getAddress(),
                address.getHomeNumber(), address.getMobilePhoneNumber(), address.getWorkPhoneNumber(),
                address.getEmail(),address.getSecond_email(),address.getThird_email(),
                address.getSecond_address()));
      }
    }
  }

  private List<AddressData> generateAddresses(int count) {
    List<AddressData> addresses = new ArrayList<AddressData>();
    for (int i = 0; i < count; i++) {
      addresses.add(new AddressData().withFirstname(String.format("firstname %s", i)).withLastname(String.format("lastname %s", i))
              .withAddress(String.format("address %s", i)).withHomeNumber(String.format("hnumber %s", i))
              .withMobilePhoneNumber(String.format("mnumber %s", i)).withWorkPhoneNumber(String.format("wnumber %s", i))
              .withEmail(String.format("email@%s", i)).withSecond_email(String.format("semail@%s", i))
              .withThird_email(String.format("3email@%s", i)));
    }
    return addresses;
  }
}
