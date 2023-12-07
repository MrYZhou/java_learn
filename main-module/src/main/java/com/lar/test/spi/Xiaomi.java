package com.lar.test.spi;

import lombok.NoArgsConstructor;


public class Xiaomi implements  Phone{
    @Override
    public String getName() {
        return "Xiaomi";
    }

    public Xiaomi() {
    }
}
