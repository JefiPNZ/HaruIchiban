package br.udesc.ceavi.ppr.haruichiban.control;

import java.awt.Color;

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
     */
    public void drawLilypad(int row, int col, Color lilypadColor, float rotation);
    
    /**
     * Desenha um ovo de sapo com a cor informada.
     * @param row
     * @param col
     * @param eggColor 
     */
    public void drawEgg(int row, int col, Color eggColor);
    
    /**
     * Desenha uma flor com a cor informada.
     * @param row
     * @param col
     * @param flowerColor 
     */
    public void drawFlower(int row, int col, Color flowerColor);
    
    /**
     * Desenha um sapo com a cor informada.
     * @param row
     * @param col
     * @param frogColor 
     */
    public void drawFrog(int row, int col, Color frogColor);
    
}
