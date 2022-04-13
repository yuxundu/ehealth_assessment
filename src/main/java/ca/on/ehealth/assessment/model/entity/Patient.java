package ca.on.ehealth.assessment.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * Patient Data Object
 * @author yuxundu
 */
@Entity
@Table(name = "t_patient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)

public class Patient extends BaseDo {
    @Column(length = 50)
    @NotBlank(message = "first name is required")
    private String firstName;

    @Column(length = 50)
    @NotBlank(message = "last name is required")
    private String lastName;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "birthday is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;


}
