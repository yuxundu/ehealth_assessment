package ca.on.ehealth.assessment.controller;

import ca.on.ehealth.assessment.model.dto.PatientDto;
import ca.on.ehealth.assessment.model.entity.Patient;
import ca.on.ehealth.assessment.model.mapper.PatientMapper;
import ca.on.ehealth.assessment.model.mapper.PatientMapperImpl;
import ca.on.ehealth.assessment.service.IPatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;

/**
 * Test Patient Controller Restful API
 *
 * @author yuxundu
 */

@WebMvcTest(PatientController.class)
public class PatientControllerWebMvcTest {
    @TestConfiguration
    public static class PatientControllerWebMvcTestConfiguration {
        Date now = new Date();
        @Bean
        PatientMapper patientMapper() {
            return new PatientMapperImpl();
        }
        @Bean
        List<Patient> patients(){
            List<Patient> patients = new ArrayList<>();
            Patient patient1 = new Patient("1", "1", now);
            patient1.setId(1L);
            Patient patient2 = new Patient("2", "2", now);
            patient2.setId(2L);
            patients.add(patient1);
            patients.add(patient2);
            return patients;
        }

        @Bean
        PatientDto patientDto(){
            return new PatientDto("1", "1", now);
        }
    }

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    IPatientService patientService;
    @Autowired
    List<Patient> patients;

    @Autowired
    PatientDto patientDto;

    /**
     * Test get all patients
     */
    @Test
    void listPatients_success() throws Exception {

        Page<Patient> page = new PageImpl<>(patients);
        Mockito.when(patientService.getAllPatients(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/patients"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Is.is(2)))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * search patients by first name
     */
    @Test
    void searchPatients_success() throws Exception{
        List<Patient> firstPatientList = patients.stream().limit(1).collect(Collectors.toList());
        Page<Patient> page = new PageImpl<>(firstPatientList);
        Mockito.when(patientService.getPatientsByFirstName(any(String.class),any(Pageable.class))).thenReturn(page);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/patients/search").param("firstName","1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Is.is(1)))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * get patient by key id
     */
    @Test
    void getPatient_success() throws Exception{
        Patient  patient = patients.get(0);
        Mockito.when(patientService.getPatient(any(Long.class))).thenReturn(patient);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/v1/patients/"+patient.getId());
        mockMvc.perform(mockRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Is.is("1")))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * creat new patient
     */
    @Test
    void createPatient_success() throws Exception{
        Mockito.when(patientService.addPatient(any(Patient.class))).thenReturn(this.patients.get(0));
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/v1/patients/")
                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_ATOM_XML)
                .content(this.mapper.writeValueAsString(this.patientDto));
        mockMvc.perform(mockRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andDo(MockMvcResultHandlers.print());



    }


}
