package com.framework.admin.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by sunh on 16/4/2017.
 */
@Component
@Order(value = 1)
public class MyStartupRunnerTest implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>>>This is MyStartupRunnerTest Order=1. Only testing CommandLineRunner...<<<<");
    }
}