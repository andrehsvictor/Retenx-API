package andrehsvictor.retenx.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetenxException extends RuntimeException {

    private HttpStatus status;
    private List<String> errors;

    public RetenxException(HttpStatus status, List<String> errors) {
        this.status = status;
        this.errors = errors;
    }

    public RetenxException(HttpStatus status, String error) {
        this.status = status;
        this.errors = List.of(error);
    }

    public RetenxException(HttpStatus status) {
        this.status = status;
        this.errors = List.of();
    }
}
