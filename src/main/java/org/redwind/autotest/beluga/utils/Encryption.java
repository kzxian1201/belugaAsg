package org.redwind.autotest.beluga.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasypt.util.text.StrongTextEncryptor;

import java.io.IOException;
import java.util.Scanner;

public class Encryption {
    private StrongTextEncryptor strongTextEncryptor;
    private PropertyReader propertyReader = new PropertyReader();
    public static final Logger logger = LogManager.getFormatterLogger();

    public Encryption() throws IOException {
        strongTextEncryptor = new StrongTextEncryptor();
        strongTextEncryptor.setPassword(propertyReader.getGenericProperty("ENCRYPTION_PASSWORD"));
    }
    public String encrypt() {

        logger.info("Enter the password which needs to be encrypted: ");
        Scanner scanner = new Scanner(System.in);
        String stringToEncrypt = scanner.nextLine();
        String result =strongTextEncryptor.encrypt(stringToEncrypt);
        return result;
    }

    public String decrypt(String encryptedPassword) {
        String decrptedString = strongTextEncryptor.decrypt(encryptedPassword);
        return decrptedString;
    }
}
