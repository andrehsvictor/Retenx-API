package andrehsvictor.retenx.exception;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDto<T> {
    private List<T> errors;
}
