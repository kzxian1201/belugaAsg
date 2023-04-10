package org.redwind.testAuto.beluga.utils;

import org.jasypt.util.text.StrongTextEncryptor;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.util.Scanner;

public class Encryption {
    private StrongTextEncryptor strongTextEncryptor;

    public Encryption() {
        strongTextEncryptor = new StrongTextEncryptor();
        strongTextEncryptor.setPassword("qwerty");
    }
    public String encrypt() throws IllegalBlockSizeException, BadPaddingException {
        System.out.println("Enter the password which needs to be encrypted: ");
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
