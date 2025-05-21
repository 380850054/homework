package com.sanofi.unitTest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sanofi.model.Drug;
import com.sanofi.model.Patient;
import com.sanofi.model.Pharmacy;
import com.sanofi.model.PharmacyDrugContract;
import com.sanofi.repository.DosageRepository;
import com.sanofi.repository.DrugRepository;
import com.sanofi.repository.PatientRepository;
import com.sanofi.repository.PharmacyDrugContractRepository;
import com.sanofi.repository.PharmacyRepository;
import com.sanofi.request.CreatePrescriptionRequest;
import com.sanofi.request.DosageRequest;
import com.sanofi.response.CreatePrescriptionResponse;
import com.sanofi.service.PharmacyService;
import com.sanofi.service.PrescriptionService;
import static com.sanofi.util.util.getCurrentDateWithFormat;

@ExtendWith(MockitoExtension.class)
public class PharmacyServiceTest {

    @Mock
	private PharmacyRepository pharmacyRepository;

    @Mock
    private DrugRepository drugRepository;

    @Mock
    private PharmacyDrugContractRepository pharmacyDrugContractRepository;

    @Mock
    private PrescriptionService prescriptionService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DosageRepository dosageRepository;

    @InjectMocks
    private PharmacyService pharmacyService;

	@Test
	public void given_correct_param_should_create_prescription() throws Exception {
        List<DosageRequest> dosageRequests = new ArrayList<>();
        dosageRequests.add(new DosageRequest(
            "cold medicine",
            6,
            "1 piece per time, 3 time per day"
        ));
        CreatePrescriptionRequest createPrescriptionRequest = new CreatePrescriptionRequest(1L, 1L, dosageRequests);

        Pharmacy mockPharmacy = new Pharmacy(1L, new ArrayList<>());
        when(pharmacyRepository.findById(1L)).thenReturn(Optional.of(mockPharmacy));

        Patient mockPatient = new Patient(1L, "John");
        when(patientRepository.findById(1L)).thenReturn(Optional.of(mockPatient));

        List<Drug> drugs = new ArrayList<>();
        drugs.add(new Drug(
            "cold medicine",
            "manufacturer",
            "batchNumber",
            getCurrentDateWithFormat("yyyy-MM-dd"),
            10,
            mockPharmacy
        ));
        PharmacyDrugContract contract = new PharmacyDrugContract(mockPharmacy, drugs);
        when(pharmacyDrugContractRepository.findByPharmacyId(1L)).thenReturn(Optional.of(contract));

        Long mockPrescriptionId = 1L;
        when(prescriptionService.createPrescription(any())).thenReturn(mockPrescriptionId);

        when(prescriptionService.tryFullfillPrescriptionById(mockPrescriptionId, false)).thenReturn(new ArrayList<>());


		ResponseEntity<CreatePrescriptionResponse> response = this.pharmacyService.createPrescription(createPrescriptionRequest);


        assertEquals(true, response.getBody().getIs_success());
        assertEquals(mockPrescriptionId, response.getBody().getPrescription_id());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(dosageRepository).saveAll(any());
        verify(prescriptionService).createPrescription(any());
	}

    @Test
	public void given_non_existing_pharmacy_id_should_response_not_found_when_create_prescription() throws Exception {
        List<DosageRequest> dosageRequests = new ArrayList<>();
        dosageRequests.add(new DosageRequest(
            "cold medicine",
            6,
            "1 piece per time, 3 time per day"
        ));
        CreatePrescriptionRequest createPrescriptionRequest = new CreatePrescriptionRequest(1L, 1L, dosageRequests);

        when(pharmacyRepository.findById(1L)).thenReturn(Optional.empty());

		ResponseEntity<CreatePrescriptionResponse> response = this.pharmacyService.createPrescription(createPrescriptionRequest);


        assertEquals(false, response.getBody().getIs_success());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verifyNoInteractions(prescriptionService);
	}
}
