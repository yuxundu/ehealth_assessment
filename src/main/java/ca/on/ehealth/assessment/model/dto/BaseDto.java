package ca.on.ehealth.assessment.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * basic DTO includes common attribute
 * @author yuxundu
 */
@Getter
@Setter
@ToString
public abstract class BaseDto {
    private Long id;
}
