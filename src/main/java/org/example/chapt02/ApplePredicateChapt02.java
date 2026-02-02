package org.example.chapt02;

import org.example.entity.Apple;

/**
 * 将判定方法抽象化（接口化），让后续调用自行实现
 */
public interface ApplePredicateChapt02 {
    boolean test(Apple apple);
}
