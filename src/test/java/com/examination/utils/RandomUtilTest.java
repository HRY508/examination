package com.examination.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RandomUtilTest {
    @Test
    void test(){
        RandomUtil randomUtil = new RandomUtil();
        Integer a[]= {45,46,48,49,52,53,54,55};
        List random = randomUtil.random(5,a);
        random.forEach(System.out::println);
        System.out.println(random.size());
    }
}