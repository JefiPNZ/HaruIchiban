package br.udesc.ceavi.ppr.haruichiban.model;

import java.awt.Color;

/**
 *
 * @author GustavoSantos
 * @since 09/05/2019
 *
 */
public class Flor extends PecaTabuleiro {

    private final Color cor;
    private final int valor;

    public Flor(int x, int y, float rotacao, Color cor,int valor) {
        super(x, y, rotacao);
        this.cor = cor;
        this.valor = valor;
    }

    public Color getCor() {
        return cor;
    }

    public int getValor() {
        return valor;
    }

}
