package com.arqsoft.medici.domain.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.arqsoft.medici.domain.exceptions.FormatoEmailInvalidoException;

public class FormatUtils {
	
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    public static void isValidEmail(String email) throws FormatoEmailInvalidoException {
    	
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()) {
        	throw new FormatoEmailInvalidoException();
        }
        
    }

}
