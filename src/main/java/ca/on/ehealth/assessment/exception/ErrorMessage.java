package ca.on.ehealth.assessment.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

/**
 * Customize Error Message
 * @author yuxundu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorMessage {
    private List<String> errors;
    public ErrorMessage(String ... errors) {
        this(Arrays.asList(errors));
    }
}
