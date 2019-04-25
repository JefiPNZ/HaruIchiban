package br.udesc.ceavi.ppr.haruichiban.control;

import java.util.List;

/**
 * Interface para classes que realizam o controle da mão do jogador.
 * @author Jeferson Penz
 */
public interface IPlayerController {

    /**
     * Retorna o tamanho da pilha do jogador.
     * @return
     */
    public int getPileSize();

    /**
     * Retorna a mão do usuário.
     * @return 
     */
    public List<Object> getHand();
    
}
