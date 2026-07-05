package com.personalproject.productservice.calculator;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public int AddIntService(int a , int b) {
        System.out.println("Some logic before addition");
        int result = a + b;
        System.out.println("Some logic after addition");
        return result;
    }

}
