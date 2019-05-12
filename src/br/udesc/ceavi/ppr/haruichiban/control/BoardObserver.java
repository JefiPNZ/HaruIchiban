package br.udesc.ceavi.ppr.haruichiban.control;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jeferson Penz
 */
public interface BoardObserver {
    
    /**
     * Limpa uma célula do tabuleiro para permitir desenhos futuros.
     * @param row
     * @param col 
     */
    public void clearTile(int row, int col);
    
    /**
     * Realiza o desenho de uma vitoria régia no tabuleiro.
     * @param row
     * @param col
     * @param lilypadColor
     * @param rotation
     * @param imagem
     */
    public void drawImage(int row, int col, Color lilypadColor, Float rotation, BufferedImage imagem);
    
}
