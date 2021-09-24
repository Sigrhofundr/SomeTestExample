package com.example.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.Assertions.*;

//@SpringBootTest
class DemoApplicationTests {

	/*
	@Test
	void contextLoads() {
	}*/

	Calculator underTest = new Calculator();

	@Test
	void itShouldAddTwoNumbers() {
		//given
		int numberOne = 20;
		int numberTwo = 30;

		//when
		int result = underTest.add(numberOne,numberTwo);

		//then
		int expected = 50;
		Assertions.assertThat(result).isEqualTo(expected);
		//assertThat(result).isEqualTo(expected);
	}

	class Calculator {
		int add(int a, int b) {return a+b;}
	}
//https://www.baeldung.com/spring-boot-testing junit-4
// https://www.baeldung.com/junit-5
}
