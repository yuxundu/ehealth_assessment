package ca.on.ehealth.assessment.controller;

import ca.on.ehealth.assessment.model.dto.PatientDto;
import ca.on.ehealth.assessment.model.entity.Patient;
import ca.on.ehealth.assessment.model.mapper.PatientMapper;
import ca.on.ehealth.assessment.service.IPatientService;
import ca.on.ehealth.assessment.util.PageConveter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * Patient Controller
 * @author yuxundu
 */
@RestController
@Validated
public class PatientController {
    @Autowired
    PatientMapper patientMapper;
    @Autowired
    private IPatientService patientService;
    @Operation(summary = "create new patient. birthDate format is yyyy-MM-dd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "create the new patient",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input supplied",
                    content = @Content)})
    @Transactional
    @PostMapping("/v1/patients")
    public ResponseEntity<PatientDto> createPatient(@RequestBody @Valid PatientDto patientDto){
        Patient savedPatient = patientService.addPatient(patientMapper.toDo(patientDto));
        return ResponseEntity.ok().body(patientMapper.toDto(savedPatient));
    }

    @Operation(summary = "get all patients ")
    @Transactional(readOnly = true)
    @GetMapping("/v1/patients")
    public Page<PatientDto> listPatients(@RequestParam(required = false) @PositiveOrZero Integer page,
                                         @RequestParam(required = false) @Min(1) Integer size){
        Pageable pageable = PageConveter.convert(page, size, null);
        Page<Patient> patientPage = patientService.getAllPatients(pageable);
        return patientMapper.toDtoPage(patientPage);

    }

    @Operation(summary = "search patients ")
    @Transactional(readOnly = true)
    @GetMapping("/v1/patients/search")
    public Page<PatientDto> searchPatients(@RequestParam(required = false) @PositiveOrZero Integer page,
                                         @RequestParam(required = false) @Min(1) Integer size,
                                         @RequestParam(required = true) @NotBlank String firstName){
        Pageable pageable = PageConveter.convert(page, size, null);
        Page<Patient> patientPage = patientService.getPatientsByFirstName(firstName,pageable);
        return patientMapper.toDtoPage(patientPage);

    }

    @Operation(summary = "get the specific patients ")
    @Transactional(readOnly = true)
    @GetMapping("/v1/patients/{id}")
    public ResponseEntity<PatientDto> getPatient(@PathVariable @Positive Long id){
        Patient  patient = patientService.getPatient(id);
        return ResponseEntity.ok().body(patientMapper.toDto(patient));
    }



}
