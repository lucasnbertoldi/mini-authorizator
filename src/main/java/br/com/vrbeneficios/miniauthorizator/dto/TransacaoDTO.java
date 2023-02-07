package br.com.vrbeneficios.miniauthorizator.dto;

public class TransacaoDTO {

    private String numeroCartao;
    private String senhaCartao;
    private String valor;
    
    public String getNumeroCartao() {
        return numeroCartao;
    }
    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }
    public String getSenhaCartao() {
        return senhaCartao;
    }
    public void setSenhaCartao(String senhaCartao) {
        this.senhaCartao = senhaCartao;
    }
    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
    
}
