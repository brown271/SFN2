package ca.sheridancollege.fourothreeindustries.services;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class PersonalInfoServiceTest {
	
	private PersonalInfoService personalInfoService = new PersonalInfoService();

	@Test
	void testIsFirstNameValidGood() {
		assertTrue(personalInfoService.isFirstNameValid("Johnathon"));
	}
	@Test
	void testIsFirstNameValidBad() {
		assertFalse(personalInfoService.isFirstNameValid(null));
	}
	@Test
	void testIsFirstNameValidBoundary() {
		assertFalse(personalInfoService.isFirstNameValid("     ?      "));
	}

	@Test
	void testIsLastNameValidGood() {
		assertTrue(personalInfoService.isLastNameValid("Joestar"));
	}
	@Test
	void testIsLastNameValidBad() {
		assertFalse(personalInfoService.isLastNameValid(""));
	}
	@Test
	void testIsLastNameValidBoundary() {
		assertFalse(personalInfoService.isLastNameValid("               "));
	}

	@Test
	void testIsBirthDateValidGood() {
		assertTrue(personalInfoService.isBirthDateValid(LocalDate.of(1995, 8, 15)));
	}
	@Test
	void testIsBirthDateValidBad() {
		assertFalse(personalInfoService.isBirthDateValid(null));
	}
	@Test
	void testIsBirthDateValidBoundary() {
		assertFalse(personalInfoService.isBirthDateValid(LocalDate.of(2022, 8, 15)));
	}

	@Test
	void testIsPhoneNumberValidGood() {
		assertTrue(personalInfoService.isPhoneNumberValid("123-456-7890"));
	}
	@Test
	void testIsPhoneNumberValidBad() {
		assertFalse(personalInfoService.isPhoneNumberValid("wowza"));
	}
	@Test
	void testIsPhoneNumberValidBoundary() {
		assertTrue(personalInfoService.isPhoneNumberValid("123------------45?6----------------------???7890"));
	}

	@Test
	void testIsEmailValidGood() {
		assertTrue(personalInfoService.isEmailValid("403industries@hotmail.com"));
	}
	@Test
	void testIsEmailValidBad() {
		assertFalse(personalInfoService.isEmailValid("?????"));
	}
	@Test
	void testIsEmailValidBoundary() {
		assertFalse(personalInfoService.isEmailValid("403industrieshotmail.com"));
	}

}
