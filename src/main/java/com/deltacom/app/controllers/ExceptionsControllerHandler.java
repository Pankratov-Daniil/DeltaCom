package com.deltacom.app.controllers;

import com.deltacom.app.exceptions.RepositoryException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;


/**
 * Class for handling exceptions and redirect user to beautiful error page :)
 */
@ControllerAdvice
public class ExceptionsControllerHandler {
    private static final String DEFAULT_ERROR_VIEW = "errors/error";

    /**
     * Default exception handler
     * @param e raised exception
     * @return default error ModelAndView
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView
    defaultErrorHandler(Exception e) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation
                (e.getClass(), ResponseStatus.class) != null)
            throw e;

        return new ModelAndView(DEFAULT_ERROR_VIEW);
    }

    /**
     * Handling repository exceptions
     * @return accessing DB error page
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(value = RepositoryException.class)
    public ModelAndView handlePersistenceException() {
        return new ModelAndView("errors/accessingDBError");
    }
}
