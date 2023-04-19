package org.redwind.autotest.beluga.utils;

import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadLocalRandom;

public class GenericFunctions {

    public String getRandomString() {
        String name = "Redwind";
        int random = ThreadLocalRandom.current().nextInt(1,399);
        name=name+Integer.toString(random);
        return name;
    }

    public String getRandomNumber(int startValue, int lastValue) {
        int number = ThreadLocalRandom.current().nextInt(startValue, lastValue);
        return Integer.toString(number);
    }
    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("dd-MM-yyyy HH.mm.ss").format(new java.util.Date());
    }
}
