package br.udesc.ceavi.ppr.haruichiban.model;

import java.awt.Color;

/**
 *
 * @author GustavoSantos
 * @since 09/05/2019
 *
 */
public abstract class Flor extends PecaTabuleiro {

    protected final int valor;
    protected ModelPlayer playerOrigem;

    public Flor(float rotacao, Color cor, int valor,ModelPlayer playerOrigem) {
        super(rotacao, cor);
        this.valor = valor;
        this.playerOrigem = playerOrigem;
    }

    public int getValor() {
        return valor;
    }
    
    @Override
    public TipoPeca getTipo(){
        return TipoPeca.FLOR;
    }

    @Override
    public String toString() {
        return "Flor{" + "valor=" + valor + ", playerOrigem=" + playerOrigem.getColor() + '}';
    }

    
}
