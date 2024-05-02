package Utilities;


import java.util.Random;

import com.github.javafaker.Faker;


public class DynamicValues {
	

	 	    public static long PhoneNumber() {
	    	 //Faker faker = new Faker();
	    	 Random random = new Random();
	          
	         long random10DigitInteger = (long) (1000000000 + random.nextDouble() * 9000000000L);
//	    	 long random10DigitInteger = faker.number().randomNumber(10, false);
//	         return Math.abs(random10DigitInteger);
			return random10DigitInteger;
	    }

	}


