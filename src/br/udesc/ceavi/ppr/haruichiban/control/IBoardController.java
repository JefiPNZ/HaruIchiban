package br.udesc.ceavi.ppr.haruichiban.control;

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

    public ModelBoardTile getFolhaEscura();

    public ModelBoardTile getModelBoardTile(Point point);

    public void eventoDeSelecao(Point newSelection);

    public void setControlPlayOuvinte(PlayerController ouvindo);

    public boolean hasPlayOuvindo();

    public void moveTo(Point origem, Point destino);
}
