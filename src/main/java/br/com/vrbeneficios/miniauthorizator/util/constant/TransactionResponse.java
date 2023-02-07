package br.com.vrbeneficios.miniauthorizator.util.constant;

public enum TransactionResponse {

    CARTAO_INEXISTENTE("CARTAO_INEXISTENTE"), SENHA_INVALIDA("SENHA_INVALIDA"),
    SALDO_INSUFICIENTE("SALDO_INSUFICIENTE");

    TransactionResponse(String code) {
        this.code = code;
    }

    public final String code;

    public static TransactionResponse getTransactionResponseFromAString(String code) {
        for (TransactionResponse value : TransactionResponse.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

}
