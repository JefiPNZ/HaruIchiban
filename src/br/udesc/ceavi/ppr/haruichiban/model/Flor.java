package br.udesc.ceavi.ppr.haruichiban.model;

import java.awt.Color;

/**
 *
 * @author GustavoSantos
 * @since 09/05/2019
 *
 */
public class Flor extends PecaTabuleiro {

    private final int valor;
    private ModelPlayer playerOrigem;

    public Flor(float rotacao, Color cor, int valor,ModelPlayer playerOrigem) {
        super(rotacao, cor);
        this.valor = valor;
        this.playerOrigem = playerOrigem;
    }

    public int getValor() {
        return valor;
    }
    
    public TipoPeca getTipo(){
        return TipoPeca.FLOR;
    }

}
