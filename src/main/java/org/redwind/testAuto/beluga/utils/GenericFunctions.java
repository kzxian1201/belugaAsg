package org.redwind.testAuto.beluga.utils;

import java.util.Random;
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
}
