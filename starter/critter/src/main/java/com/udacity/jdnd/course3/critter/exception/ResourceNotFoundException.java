package com.udacity.jdnd.course3.critter.exception;

import com.udacity.jdnd.course3.critter.constants.CritterAppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

/**
 * ResourceNotFoundException.
 *
 * @author NguyenT4.
 * @since 2023/10/24.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private final String rescourceName;

    private final String fieldName;

    private final String fieldValue;

    public ResourceNotFoundException(String rescourceName, String fieldName, String fieldValue) {
        super(MessageFormat.format(CritterAppConstant.MessagePattem.RESOURCE_NOT_FOUND, rescourceName, fieldName, fieldValue));
        this.rescourceName = rescourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getRescourceName() {
        return rescourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }
}
