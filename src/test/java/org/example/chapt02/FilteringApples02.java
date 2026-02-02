package org.example.chapt02;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.chapt02.impl.AppleColorPredicateChapt02Impl;
import org.example.chapt02.impl.AppleFormatterChapt02SendMsgImpl;
import org.example.chapt02.impl.AppleRedAndHeavyPredicateChapt02Impl;
import org.example.chapt02.impl.AppleWeightPredicateChapt02Impl;
import org.example.entity.Apple;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 通过行为参数化传递代码
 * 行为参数化就是可以帮助你处理频繁变更的需求的一种软件开发模式
 * 它意味着拿出一个代码块，把它准备好却不去执行它。
 * 这个代码块以后可以被你程序的其他部分调用，这意味着你可以推迟这块代码的执行。
 * 例如，你可以将代码块作为参数传递给另一个方法，稍后再去执行它。这样，这个方法的行为就基于那块代码被参数化了。
 */
public class FilteringApples02 {
    private final Logger log = LogManager.getLogger(this.getClass());

    @Test
    public void testFilteringApples02() {
        List<Apple> inventory = Arrays.asList(new Apple(150, "green"),
                new Apple(155, "green"),
                new Apple(120, "red"));

        List<Apple> apples1 = filterApplesByColor(inventory, "red");
        log.info("apples1: {}", apples1);

        List<Apple> apples2 = filterApples(inventory, "green", 120, true);
        log.info("apples2: {}", apples2);

        List<Apple> apples3 = filterApples(inventory, "green", 120, false);
        log.info("apples3: {}", apples3);

        List<Apple> apples4 = filterApplesByApplePredicate(inventory, new AppleWeightPredicateChapt02Impl());
        log.info("apples4: {}", apples4);

        List<Apple> apples5 = filterApplesByApplePredicate(inventory, new AppleColorPredicateChapt02Impl());
        log.info("apples5: {}", apples5);

        List<Apple> apples6 = filterApplesByApplePredicate(inventory, new AppleRedAndHeavyPredicateChapt02Impl());
        log.info("apples6: {}", apples6);

        prettyPrintApple(inventory, new AppleFormatterChapt02SendMsgImpl());

        List<Apple> apples7 = filterAnyByPredicate(inventory, (Apple apple) -> "green".equals(apple.getColor()));
        log.info("apples7: {}", apples7);
    }

    /**
     * 将部分参数抽象化
     * @param inventory 待选的list
     * @param color 不再在方法中写死选项
     * @return 过滤结果
     */
    public static List<Apple> filterApplesByColor(List<Apple> inventory, String color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (color.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * 笨拙方法，通过flag传参确定判定重量还是颜色
     * @param inventory 待选list
     * @param color 颜色
     * @param weight 重量
     * @param flag true判定颜色，false判定重量
     * @return 最终结果
     */
    public static List<Apple> filterApples(List<Apple> inventory, String color, int weight, boolean flag) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ((flag && apple.getColor().equals(color)) || (!flag && apple.getWeight() > weight)) {
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * 使用策略模式，定义一个接口，让各个需要的类去实现这个接口
     * @param inventory 待选list
     * @param predicate 接口提供的方法，承接实现类
     * @return 过滤后的list
     */
    public static List<Apple> filterApplesByApplePredicate(List<Apple> inventory, ApplePredicateChapt02 predicate) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (predicate.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    public void prettyPrintApple(List<Apple> inventory, AppleFormatterChapt02 formatterChapt02) {
        for (Apple apple : inventory) {
            log.info(formatterChapt02.accept(apple));
        }
    }

    /**
     * 彻底抽象化，包括类型也不用固定，那么即使传入banana、orange都可以完成
     * @param inventory 完整list
     * @param predicate 过滤的接口
     * @param <T> 泛型
     * @return 返回过滤后的list
     */
    public <T> List<T> filterAnyByPredicate(List<T> inventory, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : inventory) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }
}
