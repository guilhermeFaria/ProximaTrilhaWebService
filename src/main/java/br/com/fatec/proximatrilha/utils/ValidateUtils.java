package br.com.fatec.proximatrilha.utils;

public class ValidateUtils {
	
	private ValidateUtils() {
    }

    /**
     * Not null parameter.
     *
     * @param param     the param
     * @param paramName the param name
     */
    public static final void notNullParameter(final Object param, final String paramName) {
        notNull(param, paramName);
    }
    
    /**
     * Is not empty.
     *
     * @param param     the param
     * @param paramName the param name
     */
    public static final void isNotEmpty(final String param,
                                        final String paramName) {
        if (param == null || param.isEmpty()) {
            throw new IllegalArgumentException(paramName);
        }
    }
    
    private static void notNull(final Object param, final String paramName) {
        if (param == null) {
            throw new IllegalArgumentException(paramName);
        }
    }
}
