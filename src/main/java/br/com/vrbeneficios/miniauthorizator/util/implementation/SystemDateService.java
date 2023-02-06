package br.com.vrbeneficios.miniauthorizator.util.implementation;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.vrbeneficios.miniauthorizator.util.interfaces.SystemDate;

@Service
public class SystemDateService implements SystemDate {

    @Override
    public Date getCurrentDateTime() {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }
     
}
