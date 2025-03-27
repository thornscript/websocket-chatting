package dev.poporo.websocketchat.common;

import dev.poporo.websocketchat.common.exception.ErrorCode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private String message;
    private List<FieldError> errors;

    private ErrorResponse(ErrorCode errorCode) {
        this(errorCode, Collections.emptyList());
    }

    private ErrorResponse(ErrorCode errorCode, List<FieldError> errors) {
        this.message = errorCode.getMessage();
        this.errors = errors;
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {
        return new ErrorResponse(errorCode, FieldError.of(bindingResult));
    }

    public static ErrorResponse of(List<ParameterValidationResult> parameterValidationResults) {
        return new ErrorResponse(ErrorCode.INVALID_DATA_FORMAT, FieldError.of(parameterValidationResults));
    }

    public static ErrorResponse of(MethodArgumentTypeMismatchException ex) {
        return new ErrorResponse(ErrorCode.INVALID_DATA_FORMAT, FieldError.of(ex.getName(), ex.getErrorCode()));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class FieldError {

        private String field;
        private String message;

        private FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public static List<FieldError> of(String field, String message) {
            List<FieldError> errors = new ArrayList<>();
            errors.add(new FieldError(field, message));
            return errors;
        }

        public static List<FieldError> of(BindingResult bindingResult) {
            List<org.springframework.validation.FieldError> errors = bindingResult.getFieldErrors();
            return errors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getDefaultMessage()))
                    .toList();
        }

        public static List<FieldError> of(List<ParameterValidationResult> parameterValidationResults) {
            return parameterValidationResults.stream()
                    .flatMap(r -> r.getResolvableErrors().stream()
                            .map(error -> {
                                String param = (error instanceof ObjectError objectError
                                        ? objectError.getObjectName() :
                                        ((MessageSourceResolvable) Objects.requireNonNull(
                                                error.getArguments())[0]).getDefaultMessage());
                                return new FieldError(param, error.getDefaultMessage());
                            }))
                    .toList();
        }
    }
}
