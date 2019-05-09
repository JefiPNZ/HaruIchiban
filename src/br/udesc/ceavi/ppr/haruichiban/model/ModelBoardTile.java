package br.udesc.ceavi.ppr.haruichiban.model;

import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import javax.swing.ImageIcon;

/**
 * Representa uma peça no tabuleiro.
 * @author Jeferson Penz
 */
public class ModelBoardTile {
    
    private boolean lilypad;
    private final int rotation;

    /**
     * Cria uma nova peça para o tabuleiro.
     */
    public ModelBoardTile() {
        this.rotation = GameController.getInstance().getRandomizer().nextInt(360);
        this.lilypad  = false;
    }
    
    /**
     * Adiciona uma vitória regia para a peça.
     */
    public void addLilypad(){
        this.lilypad = true;
    }
    
    /**
     * Remove uma vitória regia para a peça.
     */
    public void removeLilypad(){
        this.lilypad = false;
    }
    
    /**
     * Retorna a imagem da peça para exibição.
     * @param sizeX
     * @param sizeY
     * @param isSelected
     * @return 
     */
    public ImageIcon getImage(int sizeX, int sizeY, boolean isSelected){
          // TODO: MOVER PARA A VIEW
        return null;
    }
    
}
