package com.lar.test.spi;

import lombok.NoArgsConstructor;


public class Huawei implements  Phone{
    @Override
    public String getName() {
        return "Huawei";
    }

    public Huawei() {

    }
}
