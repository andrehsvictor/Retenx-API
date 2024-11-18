package andrehsvictor.retenx.exception;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDto<String>> handleAllExceptions(Exception ex) {
        LOGGER.error("An error occurred.", ex);
        ErrorDto<String> errorDto = new ErrorDto<>();
        errorDto.setErrors(List.of("An error occurred."));
        return ResponseEntity.internalServerError().body(errorDto);
    }

    @ExceptionHandler(RetenxException.class)
    public final ResponseEntity<ErrorDto<String>> handleRetenxException(RetenxException ex) {
        ErrorDto<String> errorDto = new ErrorDto<>();
        errorDto.setErrors(ex.getErrors());
        return ResponseEntity.status(ex.getStatus()).body(errorDto);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorDto<FieldErrorDto> errorDto = new ErrorDto<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            FieldErrorDto fieldErrorDto = new FieldErrorDto();
            fieldErrorDto.setField(fieldError.getField());
            fieldErrorDto.setMessage(fieldError.getDefaultMessage());
            errorDto.getErrors().add(fieldErrorDto);
        });
        return ResponseEntity.badRequest().body(errorDto);
    }

}
