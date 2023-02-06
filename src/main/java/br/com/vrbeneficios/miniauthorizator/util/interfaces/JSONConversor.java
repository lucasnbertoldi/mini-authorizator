package br.com.vrbeneficios.miniauthorizator.util.interfaces;

public interface JSONConversor {
    public String convertDTOToJSON(Object dto) throws JSONConversorException;
}
