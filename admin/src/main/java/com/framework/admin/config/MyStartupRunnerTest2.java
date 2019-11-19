package com.framework.admin.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by sunh on 16/4/2017.
 */
@Component
@Order(value=2)
public class MyStartupRunnerTest2 implements CommandLineRunner
{
    @Override
    public void run(String... args) throws Exception
    {
        System.out.println(">>>>This is MyStartupRunnerTest Order=2. Only testing CommandLineRunner...<<<<");
    }
}
