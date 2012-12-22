package org.cc.extension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * Daneel Yaitskov
 */

public class GlobalBinderInitializer implements WebBindingInitializer {

    @Autowired
    private Validator validator;

    @Override
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.setValidator(validator);
    }
}
