package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.model.Flor;
import br.udesc.ceavi.ppr.haruichiban.model.Sapo;
import br.udesc.ceavi.ppr.haruichiban.model.abstractFactory.FactoryNenufare;
import br.udesc.ceavi.ppr.haruichiban.model.abstractFactory.Nenufare;
import br.udesc.ceavi.ppr.haruichiban.model.abstractFactory.NenufareEgg;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeferson Penz
 */
public class BoardController implements IBoardController {

    private boolean[][] board = {
        {true, false, true, false, true},
        {false, true, true, true, false},
        {true, true, false, true, true},
        {false, true, true, true, false},
        {true, false, true, false, true}
    };

    private List<BoardObserver> observers;
    private Nenufare[][] listaNenufare;

    public BoardController() {
        this.observers = new ArrayList<>();
        initTabuleiro();
    }

//    /**
//     * {@inheritdoc}
//     */
//    @Override
//    public void startBoard(){
//        for (int row = 0; row < board.length; row++) {
//            for (int column = 0; column < board[row].length; column++) {
//                for (BoardObserver observer : observers) {
//                    observer.clearTile(row, column);
//                    if(board[row][column]){
//                        observer.drawLilypad(row, column, false, GameController.getInstance().getRandomizer().nextFloat() * 360);
//                        if(row == 2){
//                            observer.drawFlower(row, column, new Color(255, 15, 35));
//                        }
//                    }
//                }
//            }
//        }
//    }
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
        for (int row = 0; row < listaNenufare.length; row++) {
            for (int column = 0; column < listaNenufare[row].length; column++) {
                for (BoardObserver observer : observers) {
                    observer.clearTile(row, column);
                    //Desenha a Nenufare
                    if (listaNenufare[row][column] != null) {
                        Nenufare nenufare = listaNenufare[row][column];
                        observer.drawLilypad(nenufare.getX(), nenufare.getY(),
                                nenufare.isShowYouDarkSide(),
                                nenufare.getRotacao());

                        //Verifica se essa tem uma peca
                        if (nenufare.getPeca() != null) {
                            //Se tem sapo
                            if (nenufare.getPeca().getClass().getSimpleName().equals("Sapo")) {
                                observer.drawFrog(row, row, ((Sapo) nenufare.getPeca()).getCor());
                                //Se tem flor
                            } else if (nenufare.getPeca().getClass().getSimpleName().equals("Flor")) {
                                observer.drawFlower(row, row, ((Flor) nenufare.getPeca()).getCor());
                            }

                            //Verifica se esta tem ovos 
                        } else if (nenufare.getClass().getSimpleName().equals("NenufareEgg")) {
                            
                            //Quebra do padrao AbstrocFactory ????
                            observer.drawEgg(row, row, ((NenufareEgg) nenufare).getColor());
                        }
                    }
                }
            }
        }
    }

    private void initTabuleiro() {
        FactoryNenufare factoryNenufare = new FactoryNenufare();
        listaNenufare = new Nenufare[5][5];
        listaNenufare[0][0] = factoryNenufare.createNenufareComum(0, 0, GameController.getInstance().getRandomizer().nextFloat() * 360);
        listaNenufare[0][2] = factoryNenufare.createNenufareComum(0, 2, GameController.getInstance().getRandomizer().nextFloat() * 360);
        listaNenufare[0][4] = factoryNenufare.createNenufareEggYellow(0, 4, GameController.getInstance().getRandomizer().nextFloat() * 360);

        listaNenufare[1][1] = factoryNenufare.createNenufareComum(1, 1, GameController.getInstance().getRandomizer().nextFloat() * 360);
        listaNenufare[1][2] = factoryNenufare.createNenufareComum(1, 2, GameController.getInstance().getRandomizer().nextFloat() * 360);
        listaNenufare[1][3] = factoryNenufare.createNenufareComum(1, 3, GameController.getInstance().getRandomizer().nextFloat() * 360);

        listaNenufare[2][0] = factoryNenufare.createNenufareComum(2, 0, GameController.getInstance().getRandomizer().nextFloat() * 360);
        listaNenufare[2][1] = factoryNenufare.createNenufareComum(2, 1, GameController.getInstance().getRandomizer().nextFloat() * 360);
        listaNenufare[2][3] = factoryNenufare.createNenufareTwoSideDark(2, 3, GameController.getInstance().getRandomizer().nextFloat() * 360);
        listaNenufare[2][4] = factoryNenufare.createNenufareComum(2, 4, GameController.getInstance().getRandomizer().nextFloat() * 360);

        listaNenufare[3][1] = factoryNenufare.createNenufareComum(3, 1, GameController.getInstance().getRandomizer().nextFloat() * 360);
        listaNenufare[3][2] = factoryNenufare.createNenufareComum(3, 2, GameController.getInstance().getRandomizer().nextFloat() * 360);
        listaNenufare[3][3] = factoryNenufare.createNenufareEggRed(3, 3, GameController.getInstance().getRandomizer().nextFloat() * 360);

        listaNenufare[4][0] = factoryNenufare.createNenufareComum(4, 0, GameController.getInstance().getRandomizer().nextFloat() * 360);
        listaNenufare[4][2] = factoryNenufare.createNenufareComum(4, 2, GameController.getInstance().getRandomizer().nextFloat() * 360);
        listaNenufare[4][4] = factoryNenufare.createNenufareComum(4, 4, GameController.getInstance().getRandomizer().nextFloat() * 360);
    }
}
