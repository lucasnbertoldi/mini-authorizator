package br.com.vrbeneficios.miniauthorizator.util.interfaces;

public interface HashGenerator {
    public String generateSaltHash(String text, String salt);
}
