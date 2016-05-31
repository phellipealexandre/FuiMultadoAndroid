package com.manolosmobile.fuimultado.exceptions;

/**
 * Created by phellipe on 4/21/16.
 */
public class ParserNotFoundException extends Exception {

    private String estate;
    private final String DETAILED_ERROR_MSG = "A busca de multas do estado %s não está implementada";

    public ParserNotFoundException(String estate) {
        super("A busca de multas do estado selecionado não está implementada");
        this.estate = estate;
    }

    public String getDetailedMessage() {
        return String.format(DETAILED_ERROR_MSG, estate);
    }

}
