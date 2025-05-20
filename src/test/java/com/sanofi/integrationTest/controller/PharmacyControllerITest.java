package com.sanofi.integrationTest.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.sanofi.response.DrugResponse;
import com.sanofi.response.PharmaciesAndContractedDrugsResponse;
import com.sanofi.service.PharmacyService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PharmacyControllerITest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private PharmacyService pharmacyService;

    @Test
    public void getHello() throws Exception {
        
        List<PharmaciesAndContractedDrugsResponse> mockResponseBody = new ArrayList<>();
        List<DrugResponse> drugs = new ArrayList<>();
        mockResponseBody.add(new PharmaciesAndContractedDrugsResponse(1L, drugs));

        when(this.pharmacyService.getAllPharmaciesWithContractedDrugs()).thenReturn(new ResponseEntity<>(mockResponseBody, HttpStatus.OK));

        mockMvc.perform(get("/pharmacies-and-contracted-drugs"))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
