package br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces;

import br.udesc.ceavi.ppr.haruichiban.cliente.boardmovement.BoardMovement;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.observers.BoardObserver;
import br.udesc.ceavi.ppr.haruichiban.cliente.model.ModelBoardTileProxy;
import br.udesc.ceavi.ppr.haruichiban.cliente.utils.Diretion;
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

    public void eventoDeSelecao(Point newSelection);

    public void botaoClick(Diretion newSelection);

    public void removeBoardMovement();

    public ModelBoardTileProxy getBoardTile(Point localLerf);

    public void initBoardMovement(BoardMovement juniorFlowerBoard);

    public void notifyDirectionAtivar();

    public ModelBoardTileProxy[][] getTabuleiro();

    public void atualizar(String parametro);

    public void atualizar();

}
