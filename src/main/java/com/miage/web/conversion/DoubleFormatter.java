package com.miage.web.conversion;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class DoubleFormatter implements Formatter<Double> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DoubleFormatter.class);
	
	@Autowired
    MessageSource messageSource;

    @Override
    public Double parse(final String text, final Locale locale) throws ParseException {
    	final NumberFormat numberFormat = createNumberFormat(locale);
        return numberFormat.parse(text).doubleValue();
    }

    @Override
    public String print(final Double object, final Locale locale) {
    	final NumberFormat numberFormat = createNumberFormat(locale);
        return numberFormat.format(object);
    }

    private NumberFormat createNumberFormat(final Locale locale) {
    	NumberFormat formatter = NumberFormat.getInstance(locale);
    	formatter.setMaximumFractionDigits(2);
    	formatter.setMinimumFractionDigits(2);
        return formatter;
    }
}