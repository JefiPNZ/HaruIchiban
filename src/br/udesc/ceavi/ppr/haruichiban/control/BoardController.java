package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.exceptions.CanNotChangeSideNenufareException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.NenufareJaPossuiUmaPecaEmCimaException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PosicaoEmTabuleiroOcupadaException;
import br.udesc.ceavi.ppr.haruichiban.model.Flor;
import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;
import br.udesc.ceavi.ppr.haruichiban.model.Nenufera;
import br.udesc.ceavi.ppr.haruichiban.model.PecaTabuleiro;
import br.udesc.ceavi.ppr.haruichiban.model.TipoPeca;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeferson Penz
 */
public class BoardController implements IBoardController {

    // @todo Remover
    private boolean[][] board = {
        {true, false, true, false, true},
        {false, true, true, true, false},
        {true, true, false, true, true},
        {false, true, true, true, false},
        {true, false, true, false, true}
    };

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
                    if (tabuleiro[row][column].hasNenufera()) {
                        Nenufera nenufera = getCampoTabuleiro(row, column).getNenufera();
                        observer.drawLilypad(row, column, nenufera.getCor(), nenufera.getRotacao());

                        //Verifica se essa tem uma peca
                        if (nenufera.hasPeca()) {
                            //Se tem sapo
                            if (nenufera.getPeca().getTipo() == TipoPeca.SAPO) {
                                observer.drawFrog(row, column, nenufera.getPeca().getCor());
                                //Se tem flor
                            } else if (nenufera.getPeca().getTipo() == TipoPeca.FLOR) {
                                observer.drawFlower(row, column, nenufera.getPeca().getCor());
                            }

                            //Verifica se esta tem ovos 
                        } else if (nenufera.hasOvo()) {
                            observer.drawEgg(row, column, nenufera.getOvo().getCor());
                        }
                    }
                }
            }
        }
    }

    private void initTabuleiro() {
        tabuleiro = new ModelBoardTile[5][5];
        //@todo será feito pelo builder
        for (int i = 0; i < 5; i++) {
            tabuleiro[i] = new ModelBoardTile[5];
            for (int j = 0; j < 5; j++) {
                tabuleiro[i][j] = new ModelBoardTile();
                if (board[i][j]) {
                    tabuleiro[i][j].addNenufera(GameController.getInstance().getFactoryPecas().createNenufera());
                }
            }
        }
    }

    public ModelBoardTile getCampoTabuleiro(int x, int y) {
        return tabuleiro[x][x];
    }

    public boolean changeNenufarTo(ModelBoardTile campoDe,
            int deX, int deY, int paraX, int paraY) throws PosicaoEmTabuleiroOcupadaException {
        
        ModelBoardTile campoPara = getCampoTabuleiro(paraX, paraY);
        
        if (!campoDe.hasNenufera()) {
            return false;
        }
        
        if (campoPara.hasNenufera()) {
            throw new PosicaoEmTabuleiroOcupadaException(campoPara.getClass().getSimpleName());
        }

        campoPara.addNenufera(campoDe.getNenufera());
        campoDe.removeNenufera();

        return true;
    }

    public boolean virarNenufar(int x, int y) throws CanNotChangeSideNenufareException {

        ModelBoardTile campoEmUso = getCampoTabuleiro(x, y);
        
        if (campoEmUso.hasNenufera() && !campoEmUso.getNenufera().isEscura()) {
            campoEmUso.getNenufera().virarNenufare();
            return true;
        }
        return false;
    }

    public boolean changeSapoTo(int deX, int deY,
            int paraX, int paraY) throws NenufareJaPossuiUmaPecaEmCimaException {

        ModelBoardTile campoDe = getCampoTabuleiro(deX, deY);
        ModelBoardTile campoPara = getCampoTabuleiro(paraX, paraY);

        if (!campoDe.hasNenufera() || !campoPara.hasNenufera()) {
            return false;
        }
        
        if (!campoDe.getNenufera().hasSapo() || campoPara.getNenufera().hasSapo()) {
            return false;
        }
        
        if (campoPara.getNenufera().hasPeca()) {
            return false;
        }
        
        PecaTabuleiro sapo = campoDe.getNenufera().removerPecaDeNenufare();
        campoPara.getNenufera().colocarPecaEmNenufare(sapo);

        return true;
    }

    public boolean colocarFlor(Flor flor, int x, int y) throws NenufareJaPossuiUmaPecaEmCimaException {
        ModelBoardTile campoEmUso = getCampoTabuleiro(x, y);
        if (campoEmUso.hasNenufera() && !campoEmUso.getNenufera().hasPeca()) {
            return false;
        }
        campoEmUso.getNenufera().colocarPecaEmNenufare(flor);
        return true;
    }
}
