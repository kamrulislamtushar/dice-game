package com.dice.game.advice;

import com.dice.game.exception.BadRequestAlertException;
import com.dice.game.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	// 400
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		log.info(ex.getClass().getName());
		log.warn("Error Log: ", ex);

		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
				ex.getLocalizedMessage(), errors);
		return handleExceptionInternal(ex, apiError, headers, apiError.getStatusName(), request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		log.info(ex.getClass().getName());
		log.warn("Error Log: ", ex);

		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
				ex.getLocalizedMessage(), errors);
		return handleExceptionInternal(ex, apiError, headers, apiError.getStatusName(), request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		log.info(ex.getClass().getName());
		log.warn("Error Log: ", ex);

		final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type "
				+ ex.getRequiredType();

		final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
				ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatusName());
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		log.info(ex.getClass().getName());
		log.warn("Error Log: ", ex);

		final String error = ex.getRequestPartName() + " part is missing";
		final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
				ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatusName());
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		log.info(ex.getClass().getName());
		log.warn("Error Log: ", ex);

		final String error = ex.getParameterName() + " parameter is missing";
		final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
				ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatusName());
	}
	

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
			final WebRequest request) {
		log.info(ex.getClass().getName());
		log.warn("Error Log: ", ex);

		final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

		final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
				ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatusName());
	}


	// 404
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		log.info(ex.getClass().getName());
		log.warn("Error Log: ", ex);

		final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
		final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(),
				ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatusName());
	}


	// 405
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		log.info(ex.getClass().getName());
		log.error("Error Log", ex);

		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
		final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.METHOD_NOT_ALLOWED,
				HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getLocalizedMessage(), builder.toString());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatusName());
	}

	// 415
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		log.info(ex.getClass().getName());
		log.error("Error Log", ex);

		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

		final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
				HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), ex.getLocalizedMessage(),
				builder.substring(0, builder.length() - 2));
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatusName());
	}

	@ExceptionHandler({ JsonProcessingException.class }) // Or whatever exception type you want to handle
	public ResponseEntity<Object> handleJsonParsingException(final JsonProcessingException ex, final WebRequest request) {
		log.info(ex.getClass().getName());
		log.error("Error Log", ex);

		final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY,
				HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage(), "Json Processing Exception");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatusName());
	}


	@ExceptionHandler({ IOException.class }) // Or whatever exception type you want to handle
	public ResponseEntity<Object> handleIOException(final IOException ex, final WebRequest request) {
		log.info(ex.getClass().getName());
		log.error("Error Log", ex);

		final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.PRECONDITION_REQUIRED,
				HttpStatus.PRECONDITION_REQUIRED.value(), ex.getMessage(), "Relation Exists");
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatusName());
	}

	@ExceptionHandler({BadRequestAlertException.class})
	public ResponseEntity<Object> handleBadRequestException(final BadRequestAlertException ex, final WebRequest request) {
		log.info(ex.getClass().getName());
		log.error("Error Log", ex);

		final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST,
				HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getTitle());
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatusName());
	}

	@ExceptionHandler({URISyntaxException.class})
	public ResponseEntity<Object> handleUriSyntaxException(final URISyntaxException ex, final WebRequest request) {
		log.info(ex.getClass().getName());
		log.error("Error Log", ex);

		final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST,
				HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getReason());
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatusName());
	}

	@ExceptionHandler({NotFoundException.class})
	public ResponseEntity<Object> handleNotFoundException(final NotFoundException ex, final WebRequest request) {
		log.info(ex.getClass().getName());
		log.error("Error Log", ex);

		final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.NOT_FOUND,
				HttpStatus.NOT_FOUND.value(), ex.getMessage(), "No Such element exists");
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatusName());
	}

	@ExceptionHandler({NoSuchElementException.class})
	public ResponseEntity<Object> handleNoSuchElementException(final NotFoundException ex, final WebRequest request) {
		log.info(ex.getClass().getName());
		log.error("Error Log", ex);

		final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.NOT_FOUND,
				HttpStatus.NOT_FOUND.value(), ex.getMessage(), "No Such element exists");
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatusName());
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
		log.info(ex.getClass().getName());
		log.error("Error Log", ex);

		final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
				HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something Went Wrong", "Something Went Wrong");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatusName());
	}
}
