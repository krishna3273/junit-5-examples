package com.healthycoderapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DietPlannerTest {
    private DietPlanner dietPlanner;

    @BeforeEach
    void setDietPlanner(){
        this.dietPlanner=new DietPlanner(20,30,50);
    }

    @AfterEach
    void afterEach(){
        System.out.println("A unit test is completed");
    }

//    @Test
    @RepeatedTest(value=10,name = RepeatedTest.LONG_DISPLAY_NAME)
    void should_return_correct_diet_plan(){
        //Given
        Coder coder=new Coder(1.82,75,26,Gender.MALE);
        DietPlan expected=new DietPlan(2202,110,73,275);

        //When
        DietPlan actual=dietPlanner.calculateDiet(coder);
        //Then
        assertAll(
                ()->assertEquals(expected.getCalories(),actual.getCalories()),
                ()->assertEquals(expected.getCarbohydrate(),actual.getCarbohydrate()),
                ()->assertEquals(expected.getFat(),actual.getFat()),
                ()->assertEquals(expected.getProtein(),actual.getProtein())
        );
    }
}
