package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.boardmovement.BoardMovement;
import br.udesc.ceavi.ppr.haruichiban.control.observers.BoardObserver;
import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;
import java.awt.Point;

/**
 * Interface para classes que realizam o controle do tabuleiro
 *
 * @author Jeferson Penz
 */
public interface IBoardController {

    /**
     * Inicia o tabuleiro.
     */
    public void renderBoard();

    /**
     * Adiciona um observador para o controller.
     *
     * @param observer
     */
    public void addObserver(BoardObserver observer);

    public int getAlturaTabuleiro();

    public int getLarguraTabuleiro();

    public Point getFolhaEscura();

    public void setFolhaEscura(Point newFolhaEscura);

    public void eventoDeSelecao(Point newSelection);

    public void removeBoardMovement();

    public ModelBoardTile getBoardTile(Point localLerf);

    public void initBoardMovement(BoardMovement juniorFlowerBoard);

    public boolean isPosicaoValida(int x, int y);

    public ModelBoardTile[][] getTabuleiro();

    public boolean validaPontuacao();

    public void removeAnimal();

}
