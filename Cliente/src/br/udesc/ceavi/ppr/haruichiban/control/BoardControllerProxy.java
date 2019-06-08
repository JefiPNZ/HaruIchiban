package br.udesc.ceavi.ppr.haruichiban.control;

import java.awt.Point;

import br.udesc.ceavi.ppr.haruichiban.utils.Diretion;
import br.udesc.ceavi.ppr.haruichiban.view.BoardTable;

public class BoardControllerProxy {

    private int altura;
    private int largura;

    public BoardControllerProxy() {
        super();
        this.altura = 5;
        this.largura = 5;
    }

    public int getAlturaTabuleiro() {
        return altura;
    }

    public int getLarguraTabuleiro() {
        return largura;
    }

    public void renderBoard() {

    }

    public void addObserver(BoardTable boardTable) {

    }

    public void eventoDeSelecao(Point newSelection) {

    }

    public void botaoClick(Diretion diretion) {

    }

}
