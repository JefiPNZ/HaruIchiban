package br.udesc.ceavi.ppr.haruichiban.control;

import java.awt.image.BufferedImage;

/**
 * Interface para classes que realizam o controle do tabuleiro
 * @author Jeferson Penz
 */
public interface IBoardController {

    /**
     * Retorna a imagem para um tile do jogo.
     * @param row
     * @param col
     * @return 
     */
    public BufferedImage getImage(int row, int col);
    
}
