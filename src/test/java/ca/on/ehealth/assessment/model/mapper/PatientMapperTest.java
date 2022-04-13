package ca.on.ehealth.assessment.model.mapper;

import ca.on.ehealth.assessment.model.dto.PatientDto;
import ca.on.ehealth.assessment.model.entity.Patient;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Log
public class PatientMapperTest {
    @Autowired
    PatientMapper patientMapper;

    @SneakyThrows
    @Test
    public void toDto() {
        Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse("1983-04-21");
        Patient patient = new Patient("firstname","lastName",birthday);
        PatientDto patientDto = patientMapper.toDto(patient);
        assertEquals(patientDto.getFirstName(),patient.getFirstName());
        assertEquals(patientDto.getLastName(),patient.getLastName());
    }

    @Test
    @SneakyThrows
    public void toDo() {
        Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse("1983-04-21");
        PatientDto patientDto = new PatientDto("firstname","lastName",birthday);
        Patient patient = patientMapper.toDo(patientDto);
        assertEquals(patientDto.getFirstName(),patient.getFirstName());
        assertEquals(patientDto.getLastName(),patient.getLastName());
    }
}