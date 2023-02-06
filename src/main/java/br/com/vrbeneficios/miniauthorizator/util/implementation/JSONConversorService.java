package br.com.vrbeneficios.miniauthorizator.util.implementation;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import br.com.vrbeneficios.miniauthorizator.util.interfaces.JSONConversor;
import br.com.vrbeneficios.miniauthorizator.util.interfaces.JSONConversorException;

@Service
public class JSONConversorService implements JSONConversor {

    public String convertDTOToJSON(Object dto) throws JSONConversorException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new JSONConversorException("Erro ao converter objeto em JSON.", e, dto);
        }
    }
}
