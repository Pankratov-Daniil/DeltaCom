package com.deltacom.app.controllers;

import com.deltacom.app.exceptions.RepositoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;


/**
 * Class for handling exceptions and redirect user to beautiful error page :)
 */
@ControllerAdvice
public class ExceptionsControllerHandler {
    private static final String DEFAULT_ERROR_VIEW = "errors/error";
    private static final Logger logger = LogManager.getLogger(ExceptionsControllerHandler.class);

    /**
     * Default exception handler
     * @param e raised exception
     * @return default error ModelAndView
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation
                (e.getClass(), ResponseStatus.class) != null)
            throw e;
        logger.error("Exception raised in controller with request: " + req.getRequestURL() + " raised " + e);
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

    /**
     * Handling 404 error
     * @return 404 error page
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ModelAndView handleNotFoundException(HttpServletRequest req) {
        logger.error("Exception raised in controller with request: " + req.getRequestURL() + " Page not found!");
        return new ModelAndView("errors/404");
    }
}
