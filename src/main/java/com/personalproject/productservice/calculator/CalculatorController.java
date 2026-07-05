package com.personalproject.productservice.calculator;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

    CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    public int add(int a, int b) {
        System.out.println("Controller : Some logic before addition");
        int result = calculatorService.AddIntService(a, b);
        System.out.println("Controller : Some logic after addition");
        return result;
    }
}
