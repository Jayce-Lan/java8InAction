package org.example.chapt02.impl;

import org.example.chapt02.AppleFormatterChapt02;
import org.example.entity.Apple;

public class AppleFormatterChapt02SendMsgImpl implements AppleFormatterChapt02 {
    @Override
    public String accept(Apple apple) {
        String characteristic = apple.getWeight() > 150 ? "heavy" : "light";
        return "An apple of " + apple.getWeight() + "g, it's a " + characteristic +
                " " + apple.getColor() + " apple";
    }
}
