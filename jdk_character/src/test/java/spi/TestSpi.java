package spi;

import java.util.ServiceLoader;

public class TestSpi {
    public static void main(String[] args) {

        ServiceLoader<Phone> phoneServiceLoader = ServiceLoader.load(Phone.class);
        for (Phone phone : phoneServiceLoader) {
            System.out.println(phone);
        }
    }
}
