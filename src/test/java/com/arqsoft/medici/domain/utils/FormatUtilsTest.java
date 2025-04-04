package com.arqsoft.medici.domain.utils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import com.arqsoft.medici.domain.exceptions.FormatoEmailInvalidoException;

public class FormatUtilsTest {
	
	    @Test
	    public void testEmailValido() {
	        assertDoesNotThrow(() -> { FormatUtils.isValidEmail("agussusu@gmail.com"); });
	    }
	    
	    @Test
	    public void testEmailValidoAr() {
	        assertDoesNotThrow(() -> { FormatUtils.isValidEmail("agussusu@yahoo.com.ar"); });
	    }

	    @Test
	    public void testEmailFormatoInvalido() {
	        assertThrows(FormatoEmailInvalidoException.class, () -> { FormatUtils.isValidEmail("agussusu"); });
	    }
	    
	    @Test
	    public void testEmailFormatoInvalido2() {
	        assertThrows(FormatoEmailInvalidoException.class, () -> { FormatUtils.isValidEmail("agussusu@"); });
	    }
	    
	    @Test
	    public void testEmailFormatoInvalido3() {
	        assertThrows(FormatoEmailInvalidoException.class, () -> { FormatUtils.isValidEmail("agussusu@gmail"); });
	    }
	    
	    @Test
	    public void testEmailVacio() {
	        assertThrows(FormatoEmailInvalidoException.class, () -> { FormatUtils.isValidEmail(""); });
	    }

	    @Test
	    public void testEmailNull() {
	        assertThrows(NullPointerException.class, () -> { FormatUtils.isValidEmail(null); });
	    }

}
