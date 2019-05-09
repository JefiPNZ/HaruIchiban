package br.udesc.ceavi.ppr.haruichiban.control;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeferson Penz
 */
public class TestBoardController implements IBoardController {
    
    private boolean[][] board = {
        { true,  false, true,  false, true },
        { false, true,  true,  true,  false },
        { true,  true,  false, true,  true },
        { false, true,  true,  true,  false },
        { true,  false, true,  false, true }
    };
    
    private List<BoardObserver> observers;

    public TestBoardController() {
        this.observers = new ArrayList<>();
    }
    
    /**
     * {@inheritdoc}
     */
    @Override
    public void startBoard(){
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                for (BoardObserver observer : observers) {
                    observer.clearTile(row, column);
                    if(board[row][column]){
                        observer.drawLilypad(row, column, false, GameController.getInstance().getRandomizer().nextFloat() * 360);
                        if(row == 2){
                            observer.drawFlower(row, column, new Color(255, 15, 35));
                        }
                    }
                }
            }
        }
    }
    
    /**
     * {@inheritdoc}
     */
    @Override
    public void addObserver(BoardObserver observer){
        this.observers.add(observer);
    }
    
}
