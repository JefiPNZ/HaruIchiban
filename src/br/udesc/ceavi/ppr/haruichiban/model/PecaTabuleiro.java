package br.udesc.ceavi.ppr.haruichiban.model;

/**
 * Representa a peca do jogo
 *
 * @author GustavoSantos
 * @since 09/05/2019
 *
 */
public class PecaTabuleiro {

    private int x, y;
    private float rotacao;

    public PecaTabuleiro(int x, int y, float rotacao) {
        this.x = x;
        this.y = y;
        this.rotacao = rotacao;
    }

    public int getX() {
        return x;
    }

    public float getRotacao() {
        return rotacao;
    }

    public int getY() {
        return y;
    }

    public void setPosicao(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
