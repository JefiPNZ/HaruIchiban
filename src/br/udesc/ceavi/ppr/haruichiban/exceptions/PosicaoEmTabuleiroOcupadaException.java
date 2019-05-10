package br.udesc.ceavi.ppr.haruichiban.exceptions;

/**
 *
 * @author Gustavo C Santos
 * @since 10/05/2019
 * 
 */
public class PosicaoEmTabuleiroOcupadaException extends Exception {

    public PosicaoEmTabuleiroOcupadaException(String simpleName) {
        super("Posicao Em Tabuleiro Ocupada " + simpleName);
    }

}
