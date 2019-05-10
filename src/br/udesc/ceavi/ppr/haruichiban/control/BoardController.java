package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;
import br.udesc.ceavi.ppr.haruichiban.model.Nenufera;
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
                        Nenufera nenufera = tabuleiro[row][column].getNenufera();
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
                            
                            //Quebra do padrao AbstrocFactory ????
                            observer.drawEgg(row, column, null);
                        }
                    }
                }
            }
        }
    }

    private void initTabuleiro() {
        tabuleiro = new ModelBoardTile[5][];
        //@todo será feito pelo builder
        for (int i = 0; i < 5; i++) {
            tabuleiro[i] = new ModelBoardTile[5];
            for (int j = 0; j < 5; j++) {
                tabuleiro[i][j] = new ModelBoardTile();
                if(board[i][j]){
                    tabuleiro[i][j].addNenufera(GameController.getInstance().getFactoryPecas().createNenufera());
                }
            }
        }
    }
}
