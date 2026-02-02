package org.example.chapt02.impl;

import org.example.chapt02.ApplePredicateChapt02;
import org.example.entity.Apple;

public class AppleRedAndHeavyPredicateChapt02Impl implements ApplePredicateChapt02 {
    @Override
    public boolean test(Apple apple) {
        return apple.getWeight() > 150 && "red".equals(apple.getColor());
    }
}
