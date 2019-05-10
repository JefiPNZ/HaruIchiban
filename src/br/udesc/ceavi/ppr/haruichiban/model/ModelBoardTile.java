package br.udesc.ceavi.ppr.haruichiban.model;

import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.model.Nenufera;

/**
 * Representa uma célula no tabuleiro.
 * @author Jeferson Penz
 */
public class ModelBoardTile {
    
    private Nenufera nenufera;
    private final int rotation;

    /**
     * Cria uma nova peça para o tabuleiro.
     */
    public ModelBoardTile() {
        this.rotation = GameController.getInstance().getRandomizer().nextInt(360);
        this.nenufera  = null;
    }
    
    /**
     * Adiciona uma nenufera para a célula.
     * @param nenufera
     */
    public void addNenufera(Nenufera nenufera){
        this.nenufera = nenufera;
    }
    
    /**
     * Remove uma a nenufera da peça.
     */
    public void removeNenufera(){
        this.nenufera = null;
    }
    
    /**
     * Retorna se tem ou não uma vitória régia.
     * @return 
     */
    public boolean hasNenufera(){
        return this.nenufera != null;
    }
    
    public Nenufera getNenufera(){
        return this.nenufera;
    }
    
}
