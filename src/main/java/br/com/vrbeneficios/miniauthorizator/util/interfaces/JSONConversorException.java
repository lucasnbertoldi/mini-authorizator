package br.com.vrbeneficios.miniauthorizator.util.interfaces;

public class JSONConversorException extends IllegalArgumentException {

    private final Object objectToConvert;

    public JSONConversorException(String message, Throwable cause, Object objectToConvert) {
        super(message, cause);
        this.objectToConvert = objectToConvert;
    }

    public Object getObjectToConvert() {
        return objectToConvert;
    }
    

}
