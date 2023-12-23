package com.app.application.validator.user.generic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface UserDtoValidator<T> {
    Logger logger = LogManager.getRootLogger();
    void validate(T t);

    static <T> T validateNull(T t, String logMessage) {
        if (t == null || t.toString().isEmpty()) {
            logger.error(logMessage);
            throw new IllegalStateException("Is null or empty");
        }
        return t;
    }
}
