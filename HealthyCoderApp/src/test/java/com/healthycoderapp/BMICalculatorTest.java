package com.healthycoderapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class BMICalculatorTest {

    private final String environment = "dev";

    @BeforeAll
    static  void beforeAll(){
        System.out.println("Starting the unit tests");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("Unit tests completed");
    }

    @Nested
    class isDietRecommendedTests{
        //    @Test
        //    void should_return_true_when_diet_recommended(){
        //        //Given
        //        double weight=89.0;
        //        double height=1.72;
        //
        //        //when
        //        boolean recommended=BMICalculator.isDietRecommended(weight,height);
        //
        //        //Then
        //        assertTrue(recommended);
        //    }

        //    @ParameterizedTest
        //    @ValueSource(doubles = {89,95,110})
        //    void should_return_true_when_diet_recommended(Double weight){
        //        //Given
        //        double height=1.72;
        //
        //        //when
        //        boolean recommended=BMICalculator.isDietRecommended(weight,height);
        //
        //        //Then
        //        assertTrue(recommended);
        //    }

        //    @ParameterizedTest(name = "weight={0},height={1}")
        //    @CsvSource(value = {"89,1.72","95,1.75","110,1.78"})
        //    void should_return_true_when_diet_recommended(Double weight,Double height){
        //        //Given
        //
        //        //when
        //        boolean recommended=BMICalculator.isDietRecommended(weight,height);
        //
        //        //Then
        //        assertTrue(recommended);
        //    }

        @ParameterizedTest(name = "weight={0},height={1}")
        @CsvFileSource(resources = "/diet-recommended-input-data.csv",numLinesToSkip = 1)
        void should_return_true_when_diet_recommended(Double weight,Double height){
            //Given

            //when
            boolean recommended=BMICalculator.isDietRecommended(weight,height);

            //Then
            assertTrue(recommended);
        }

        @Test
        void should_return_false_when_diet_not_recommended(){
            //Given
            double weight=45.0;
            double height=1.72;

            //when
            boolean recommended=BMICalculator.isDietRecommended(weight,height);

            //Then
            assertFalse(recommended);
        }

        @Test
        void should_throw_arithmetic_exception_when_height_zero(){
            //Given
            double weight=45.0;
            double height=0;

            //when
            Executable executable =()->BMICalculator.isDietRecommended(weight,height);

            //Then
            assertThrows(ArithmeticException.class,executable);
        }
    }

    @Nested
    @DisplayName("{{}} sample inner class display name")
    class FindCoderWithWorstBMITests{
        @Test
        @DisplayName(">>> sample method display name")
        @DisabledOnOs(OS.WINDOWS)
        void should_return_coder_with_worst_bmi_when_coderList_notEmpty(){
            //Given
            List<Coder> coders=new ArrayList<>();
            coders.add(new Coder(1.8,52));
            coders.add(new Coder(1.6,92));
            coders.add(new Coder(1.8,72));

            //When
            Coder coderWithWorstBMI=BMICalculator.findCoderWithWorstBMI(coders);

            //Then

            assertAll(
                    ()->assertEquals(1.6,coderWithWorstBMI.getHeight()),
                    ()->assertEquals(92,coderWithWorstBMI.getWeight())
            );
        }

        @Test
        @Disabled
        void should_return_coder_with_worst_bmi_in_1ms_when_coderList_has10000elements(){
            //Given
            assumeTrue(BMICalculatorTest.this.environment.equals("prod"));
            List<Coder> coders=new ArrayList<>();
            for (int i=0;i<10000;i++){
                coders.add(new Coder(1+i/10000,40+i/200));
            }

            //When

            Executable executable=()->BMICalculator.findCoderWithWorstBMI(coders);

            //Then

            assertTimeout(Duration.ofMillis(1),executable);
        }

        @Test
        void should_return_null_when_coderList_Empty(){
            //Given
            List<Coder> coders=new ArrayList<>();


            //When
            Coder coderWithWorstBMI=BMICalculator.findCoderWithWorstBMI(coders);

            //Then

            assertNull(coderWithWorstBMI);
        }
    }


    @Test
    void should_return_correct_bmi_score_when_coderList_notEmpty(){
        //Given
        List<Coder> coders=new ArrayList<>();
        coders.add(new Coder(1.8,60));
        coders.add(new Coder(1.82,98));
        coders.add(new Coder(1.82,64.7));
        double[] expected={18.52,29.59,19.53};

        //When
        double[] bmiScores=BMICalculator.getBMIScores(coders);

        //Then
        assertArrayEquals(expected,bmiScores);
    }
}
