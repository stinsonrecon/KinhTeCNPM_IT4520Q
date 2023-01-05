package vn.com.core.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import vn.com.core.common.config.ErrorCode;
import vn.com.core.common.dto.APIErrorResponse;
import vn.com.core.common.dto.ErrorResponse;
import vn.com.core.common.dto.MessagesResponse;
import vn.com.core.common.utils.ExceptionUtil;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"rawtypes"})
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        MessagesResponse msg = new MessagesResponse();
        msg.error(ex);
        msg.setData(new APIErrorResponse().forException(ex));
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @ExceptionHandler({RuntimeException.class})
    public final ResponseEntity<Object> handleAllRuntimeException(RuntimeException ex, WebRequest request) {
        RuntimeException exProcessed = ExceptionUtil.getRuntimException(ex);
        MessagesResponse msg = new MessagesResponse();
        msg.error(ex);
        msg.setData(new APIErrorResponse().forException(exProcessed));
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @ExceptionHandler({AppException.class})
    public final ResponseEntity<Object> handleAllAppException(AppException ex, WebRequest request) {
        MessagesResponse msg = new MessagesResponse();
        msg.error(ex);
        msg.setCode(ErrorCode.APP.EXCEPTION);
        msg.setData(new APIErrorResponse().forException(ex));
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @ExceptionHandler({ValidateException.class})
    public final ResponseEntity<Object> handleAllValidateException(ValidateException ex, WebRequest request) {
        MessagesResponse msg = new MessagesResponse();
        msg.error(ex);
        msg.setCode(ErrorCode.VALIDATE.EXCEPTION);
        msg.setData(new APIErrorResponse().forException(ex));
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public final ResponseEntity<Object> handleAllMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse(ErrorCode.VALIDATE.EXCEPTION, "Validation failed", details);
        MessagesResponse msg = new MessagesResponse();
        msg.error(ex);
        msg.setData(error);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
