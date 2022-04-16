package com.example.api.response.error;

import com.example.exception.*;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.util.NestedServletException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Collections.singletonList;

@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ApiResponseErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiResponseErrorHandler.class);

    private static final String INTERNAL_ERROR_MESSAGE = "An unexpected internal server error occurred";
    private static final String VALIDATED_ERROR_MESSAGE = "Некорректный ввод данных";
    private static final int INTERNAL_SERVER_ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();

    @Hidden
    @ExceptionHandler(value = {
        AlreadyExistException.class,
        ReadonlyModificationException.class,
        ConflictException.class
    })
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    public ApiResponseError handleConstraintException(HttpServletRequest request, Exception ex) {
        log.warn(ex.getMessage(), ex);

        String reason = getCauseMessageOrDefault(ex, ex.getMessage());
        return getErrorResponse(HttpStatus.CONFLICT.value(), reason, ex.getMessage(), singletonList(ex.getMessage()), request.getRequestURI());
    }

    @Hidden
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResponseError handleNotFoundException(HttpServletRequest request, Exception ex) {
        // 404-е в сентри не логгируем!
        log.warn("404 - {}", ex.getMessage());
        return getErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @Hidden
    @ExceptionHandler({NestedServletException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResponseError handleNotImplementedError(HttpServletRequest request, NestedServletException ex) {
        log.error("", ex);

        return getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    /**
     * Обработка InternalServerError и всех остальных необработанных исключений
     */
    @ExceptionHandler(value = {Throwable.class, InternalServerError.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResponseError handleUnhandledErrors(HttpServletRequest request, Exception ex) {
        log.error(ex.getMessage(), ex);

        String reason = getCauseMessageOrDefault(ex, INTERNAL_ERROR_MESSAGE);
        return getErrorResponse(INTERNAL_SERVER_ERROR_CODE, reason, ex.getMessage(), singletonList(ex.getMessage()), request.getRequestURI());
    }

    @ExceptionHandler(value = {NotImplemented.class})
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ResponseBody
    public ApiResponseError handleNotImplementedException(HttpServletRequest request, Exception ex) {
        log.error(ex.getMessage(), ex);

        return getErrorResponse(HttpStatus.NOT_IMPLEMENTED, ex.getMessage(), request);
    }

    @ExceptionHandler({
        BadRequestException.class,
        ValidationException.class,
        MissingRequestValueException.class,
        HttpMessageNotReadableException.class,
        MethodArgumentTypeMismatchException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponseError handleBadRequestException(HttpServletRequest request, Exception ex) {
        Throwable throwable = ex;
        if (ex instanceof MethodArgumentTypeMismatchException nested) {
            throwable = nested.getMostSpecificCause();
        }

        log.error("", throwable);

        return getErrorResponse(HttpStatus.BAD_REQUEST, throwable.getMessage(), request);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ApiResponseError handleForbiddenException(HttpServletRequest request, NestedServletException exception) {
        log.error("", exception);

        return getErrorResponse(HttpStatus.FORBIDDEN, exception.getMessage(), request);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ApiResponseError handleUnauthorizedException(HttpServletRequest request, NestedServletException exception) {
        log.error("", exception);

        return getErrorResponse(HttpStatus.UNAUTHORIZED, exception.getMessage(), request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponseError handleBadRequestException(HttpServletRequest request, ConstraintViolationException ex) {
        log.error("", ex);

        if (!CollectionUtils.isEmpty(ex.getConstraintViolations())) {
            Set<ConstraintViolation<?>> constraints = ex.getConstraintViolations();
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<?> constraint : constraints) {
                errorMessages.add(constraint.getMessage());
            }
            return getErrorResponse(HttpStatus.BAD_REQUEST, VALIDATED_ERROR_MESSAGE, errorMessages, request);
        } else {
            return getErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponseError handleValidationException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        log.error("", ex);

        List<String> allValidErrors = new ArrayList<>();
        for (ObjectError error : ex.getAllErrors()) {
            allValidErrors.add(error.getDefaultMessage());
        }

        return getErrorResponse(HttpStatus.BAD_REQUEST, VALIDATED_ERROR_MESSAGE, allValidErrors, request);
    }

    @ExceptionHandler(PromoActivityValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponseError handleActivityValidationException(HttpServletRequest request, PromoActivityValidationException ex) {
        log.error("", ex);

        return getErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getErrors(), request);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ApiResponseError handleForbiddenException(HttpServletRequest request, Exception ex) {
        log.warn("", ex);
        return getErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage(), request);
    }


    private ApiResponseError getErrorResponse(HttpStatus httpStatus, String errorMessage, HttpServletRequest request) {
        return getErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase(), errorMessage, singletonList(errorMessage), request.getRequestURI());
    }

    private ApiResponseError getErrorResponse(HttpStatus httpStatus, String errorMessage, List<String> messages, HttpServletRequest request) {
        return getErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase(), errorMessage, messages, request.getRequestURI());
    }

    private ApiResponseError getErrorResponse(int code, String reason, String headerMessage, List<String> errorMessages, String location) {
        List<ApiResponseErrorItem> errorItems = new ArrayList<>();

        for (String errorMessage : errorMessages) {
            errorItems.add(new ApiResponseErrorItem(reason, errorMessage, location));
        }

        ApiResponseErrorData errorData = new ApiResponseErrorData(code, headerMessage, errorItems);
        return new ApiResponseError(errorData);
    }

    private String getCauseMessageOrDefault(Throwable t, String defaultMessage) {
        if (t != null && t.getCause() != null && t.getCause().getMessage() != null) {
            return t.getCause().getMessage();
        }
        return defaultMessage;
    }

}
