package me.memorytalk.common.base;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class BaseObject {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MessageSource messageSource;
}
