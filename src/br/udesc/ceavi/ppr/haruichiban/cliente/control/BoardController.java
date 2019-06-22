package br.udesc.ceavi.ppr.haruichiban.cliente.control;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IBoardController;
import br.udesc.ceavi.ppr.haruichiban.cliente.boardmovement.BoardMovement;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.channel.ModelGet;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.observers.BoardObserver;
import br.udesc.ceavi.ppr.haruichiban.cliente.model.ModelBoardTileProxy;
import br.udesc.ceavi.ppr.haruichiban.cliente.utils.Diretion;
import com.google.gson.Gson;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeferson Penz
 */
public class BoardController implements IBoardController {

    private final List<BoardObserver> observers;
    private ModelBoardTileProxy[][] tabuleiro;
    private Point folhaEscura;
    private BoardMovement boardMovement;

    public BoardController() {
        this.observers = new ArrayList<>();
//        this.tabuleiro = new ModelBoardTileProxy[5][5];
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public void addObserver(BoardObserver observer) {
        this.observers.add(observer);
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public void renderBoard() {
        observers.forEach(observer -> {
            for (int row = 0; row < getAlturaTabuleiro(); row++) {
                for (int column = 0; column < getLarguraTabuleiro(); column++) {
                    //Apaga
                    observer.clearTile(row, column);

                    ModelBoardTileProxy tile = tabuleiro[row][column];

                    //Desenha a Desenha Folha
                    if (tile.hasFolha()) {

                        observer.drawImage(row, column, tile.getCorFolha(), tile.getFolhaRotacao(), tile.getFolhaSimpleName());

                        //Verifica se esta tem filhotes 
                        if (tile.hasFilhote()) {
                            observer.drawImage(row, column, tile.getFilhoteCor(), null, tile.getFilhoteSimpleName());
                        }

                        //Verifica se essa tem uma peca
                        if (tile.hasPeca()) {
                            observer.drawImage(row, column, tile.getPeca().getPecaCor(), tile.getPeca().getPecaRotacao(), tile.getPeca().getPecaSimpleName());
                        }
                    }
                }
            }
            observer.repaintTela();
        });
    }

    @Override
    public void atualizar() {
        GameClienteController.getInstance().getPackageOutput().newGet(ModelGet.GAME_BOARD, "");
    }

    @Override
    public int getAlturaTabuleiro() {
        return this.tabuleiro == null ? 0 : this.tabuleiro[0].length;
    }

    @Override
    public int getLarguraTabuleiro() {
        return this.tabuleiro == null ? 0 : this.tabuleiro.length;
    }

    @Override
    public void eventoDeSelecao(Point newSelection) {
        if (boardMovement != null) {
            boardMovement.addPoint(newSelection);
        }
    }

    @Override
    public void botaoClick(Diretion diretion) {
        if (boardMovement != null) {
            boardMovement.addDiretion(diretion);
        }
    }

    @Override
    public ModelBoardTileProxy getBoardTile(Point point) {
        return this.tabuleiro[point.y][point.x];
    }

    @Override
    public Point getFolhaEscura() {
        return folhaEscura;
    }

    @Override
    public void initBoardMovement(BoardMovement boardMovement) {
        this.boardMovement = boardMovement;
        observers.forEach(obs -> obs.notifyAtivarTabela());
    }

    @Override
    public void removeBoardMovement() {
        observers.forEach(obs -> obs.notifyDesativarTabela());
        observers.forEach(obs -> obs.notifyDesativarDirection());
        this.boardMovement = null;
    }

    @Override
    public void notifyDirectionAtivar() {
        observers.forEach(obs -> obs.notifyAtivarDirection());
    }

    @Override
    public ModelBoardTileProxy[][] getTabuleiro() {
        return tabuleiro;
    }

    @Override
    public void atualizar(String parametro) {
        Gson gson = new Gson();
        String[][] r = gson.fromJson(parametro, String[][].class);
        if (tabuleiro == null) {
            tabuleiro = new ModelBoardTileProxy[r[0].length][r.length];
        }
        if (tabuleiro.length != r.length) {
            tabuleiro = new ModelBoardTileProxy[r[0].length][r.length];
        }

        for (int row = 0; row < getAlturaTabuleiro(); row++) {
            for (int column = 0; column < getLarguraTabuleiro(); column++) {
                tabuleiro[row][column] = gson.fromJson(r[row][column], ModelBoardTileProxy.class);
            }
        }

        renderBoard();
    }

}
