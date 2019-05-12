package br.udesc.ceavi.ppr.haruichiban.control;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Interface para classes que realizam o controle do tabuleiro
 * @author Jeferson Penz
 */
public interface IBoardController {
    
    /**
     * Inicia o tabuleiro.
     */
    public void renderBoard() throws IOException;

    /**
     * Adiciona um observador para o controller.
     * @param observer
     */
    public void addObserver(BoardObserver observer);
    
    public int getAlturaTabuleiro();
    public int getLarguraTabuleiro();
    
}
