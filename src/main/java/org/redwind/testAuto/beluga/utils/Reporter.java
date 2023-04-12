package org.redwind.testAuto.beluga.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class Reporter {

public static void main(String[] args) throws IllegalBlockSizeException, BadPaddingException {
    Encryption encryption = new Encryption();
    System.out.println(encryption.encrypt());
}

}
