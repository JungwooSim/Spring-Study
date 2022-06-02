package com.example.ymlconfigaccess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class YmlAccessTest {

    @Value("${a.b}")
    private String ab;

    @Value("${aaa.b.c}")
    private String aaabc;

    @Test
    void test1() {
        Assertions.assertEquals("aaa", ab);
    }

    @Test
    void test2() {
        Assertions.assertEquals("aaabc", aaabc);
    }
}
