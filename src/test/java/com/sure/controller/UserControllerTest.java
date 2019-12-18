package com.sure.controller;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by SURE on ${DATA}.
 */
@PerfTest(invocations = 1000, threads = 40)
@Required(max = 1200, average = 250, totalTime = 6000)
public class UserControllerTest {
    @Autowired
    UserController userController;

    @Rule
    public ContiPerfRule i = new ContiPerfRule();

    @org.junit.Test
    public void login() throws Exception {
        System.out.println((1 - 0.01507) / 0.01507);
        Thread.sleep(20);
    }

}