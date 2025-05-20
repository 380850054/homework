package com.sanofi.unitTest.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.sanofi.model.Drug;
import com.sanofi.model.Pharmacy;
import com.sanofi.repository.HelloRepository;;

public class HelloServiceTest {


	private final HelloRepository helloRepository = mock(HelloRepository.class);

	@Test
	public void getHello() throws Exception {
		when(helloRepository.findById(1L)).thenReturn(Optional.of(new Pharmacy(new Drug(),2)));
		Optional<Pharmacy> result = helloRepository.findById(1L);
		result.ifPresent(pharmacy -> System.out.println("inventory: " + pharmacy.getInventory()));
		assertEquals(result.get().getInventory(), 2);
	}
}
