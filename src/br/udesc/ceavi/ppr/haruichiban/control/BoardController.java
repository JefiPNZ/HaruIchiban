package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.builder.BoardBuilder;
import br.udesc.ceavi.ppr.haruichiban.builder.BoardDirector;
import br.udesc.ceavi.ppr.haruichiban.exceptions.CanNotChangeSideNenufareException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.NenufareJaPossuiUmaPecaEmCimaException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PosicaoEmTabuleiroOcupadaException;
import br.udesc.ceavi.ppr.haruichiban.model.Flor;
import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;
import br.udesc.ceavi.ppr.haruichiban.model.folha.Folha;
import br.udesc.ceavi.ppr.haruichiban.model.PecaTabuleiro;
import br.udesc.ceavi.ppr.haruichiban.model.TipoPeca;
import br.udesc.ceavi.ppr.haruichiban.model.folha.Nenufera;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeferson Penz
 */
public class BoardController implements IBoardController {

    private List<BoardObserver> observers;
    private ModelBoardTile[][] tabuleiro;

    public BoardController() {
        this.observers = new ArrayList<>();
        initTabuleiro();
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
    public void startBoard() {
        for (int row = 0; row < tabuleiro.length; row++) {
            for (int column = 0; column < tabuleiro[row].length; column++) {
                for (BoardObserver observer : observers) {
                    observer.clearTile(row, column);
                    //Desenha a Nenufare
                    if (getCampoTabuleiro(row, column).hasFolha()) {
                        Folha folha = getCampoTabuleiro(row, column).getFolha();
                        observer.drawLilypad(row, column, folha.getCor(), folha.getRotacao());

                        //Verifica se essa tem uma peca
                        if (folha.hasPeca()) {
                            //Se tem sapo
                            if (folha.getPeca().getTipo() == TipoPeca.ANIMAL) {
                                observer.drawFrog(row, column, folha.getPeca().getCor());
                                //Se tem flor
                            } else if (folha.getPeca().getTipo() == TipoPeca.FLOR) {
                                observer.drawFlower(row, column, folha.getPeca().getCor());
                            }

                            //Verifica se esta tem ovos 
                        } else if (folha.hasFilhote()) {
                            observer.drawEgg(row, column, folha.getFilhote().getCor());
                        }
                    }
                }
            }
        }
    }

    private void initTabuleiro() {
        BoardBuilder builder = GameController.getInstance().getBuilder();
        BoardDirector director = new BoardDirector(builder);
        director.contruir();
        this.tabuleiro = builder.getProduto();
    }

    public ModelBoardTile getCampoTabuleiro(int x, int y) {
        return tabuleiro[x][y];
    }

    public boolean changeNenufarTo(ModelBoardTile campoDe,
            int deX, int deY, int paraX, int paraY) throws PosicaoEmTabuleiroOcupadaException {

        ModelBoardTile campoPara = getCampoTabuleiro(paraX, paraY);

        if (!campoDe.hasFolha()) {
            return false;
        }

        if (campoPara.hasFolha()) {
            throw new PosicaoEmTabuleiroOcupadaException(campoPara.getClass().getSimpleName());
        }

        campoPara.addFolha(campoDe.getFolha());
        campoDe.removeFolha();

        return true;
    }

    public boolean virarNenufar(int x, int y) throws CanNotChangeSideNenufareException {

        ModelBoardTile campoEmUso = getCampoTabuleiro(x, y);

        if (campoEmUso.hasFolha() && !campoEmUso.getFolha().isEscura()) {
            campoEmUso.getFolha().virarFolha();
            return true;
        }
        return false;
    }

    public boolean changeSapoTo(int deX, int deY,
            int paraX, int paraY) throws NenufareJaPossuiUmaPecaEmCimaException {

        ModelBoardTile campoDe = getCampoTabuleiro(deX, deY);
        ModelBoardTile campoPara = getCampoTabuleiro(paraX, paraY);

        if (!campoDe.hasFolha() || !campoPara.hasFolha()) {
            return false;
        }

        if (!campoDe.getFolha().hasFilhote() || campoPara.getFolha().hasFilhote()) {
            return false;
        }

        if (campoPara.getFolha().hasPeca()) {
            return false;
        }

        PecaTabuleiro sapo = campoDe.getFolha().removerPecaDeNenufare();
        campoPara.getFolha().colocarPecaNaFolha(sapo);

        return true;
    }

    public boolean colocarFlor(Flor flor, int x, int y) throws NenufareJaPossuiUmaPecaEmCimaException {
        ModelBoardTile campoEmUso = getCampoTabuleiro(x, y);
        if (campoEmUso.hasFolha() && !campoEmUso.getFolha().hasPeca()) {
            return false;
        }
        campoEmUso.getFolha().colocarPecaNaFolha(flor);
        return true;
    }

    @Override
    public int getAlturaTabuleiro() {
        return this.tabuleiro[0].length;
    }

    @Override
    public int getLarguraTabuleiro() {
        return this.tabuleiro.length;
    }
}