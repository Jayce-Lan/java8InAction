package org.example.chapt01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entity.Apple;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilteringApples {
    private final Logger log = LogManager.getLogger(this.getClass());

    @Test
    public void testFilteringApplesFunction() {
        List<Apple> inventory = Arrays.asList(new Apple(150, "green"),
                new Apple(155, "green"),
                new Apple(120, "red"));

        List<Apple> apples1 = filterGreenApples(inventory);
        log.info("apples1: {}", apples1);

        // 调用FilteringApples的isGreenApple方法判定过滤
        List<Apple> apples2 = filterApples(inventory, FilteringApples::isGreenApple);
        log.info("apples2: {}", apples2);

        // 直接使用lambda表达式传入方法
        List<Apple> apples3 = filterApples(inventory, (Apple apple) ->
                apple.getWeight() > 150 || "red".equals(apple.getColor()));
        log.info("apples3: {}", apples3);

        // 转换为stream处理
        List<Apple> apples4 = inventory.stream().filter(apple -> apple.getWeight() > 150).collect(Collectors.toList());
        log.info("apples4: {}", apples4);

        // 使用parallelStream并行处理
        List<Apple> apples5 = inventory.parallelStream().filter(apple -> apple.getWeight() < 150)
                .collect(Collectors.toList());
        log.info("apples5: {}", apples5);
    }

    /**
     * 普通方法，直接在方法中处理过滤
     * @param inventory 传入的list
     * @return 根据条件过滤后得到的list
     */
    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ("green".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    public static boolean isGreenApple(Apple apple) {
        return "green".equals(apple.getColor());
    }

    /**
     * 使用Predicate去实现方法内调用方法
     * @param inventory 未过滤的list
     * @param predicate 判定方法（条件）
     * @return 根据条件过滤的list结果集
     */
    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> predicate) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (predicate.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

}
