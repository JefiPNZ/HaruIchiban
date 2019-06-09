package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.control.observers.BoardObserverProxy;
import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTileProxy;
import br.udesc.ceavi.ppr.haruichiban.model.Request;
import java.awt.Point;

import br.udesc.ceavi.ppr.haruichiban.utils.Diretion;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class BoardControllerProxy {

    private int altura;
    private int largura;
    private List<BoardObserverProxy> observers = new ArrayList<>();

    public BoardControllerProxy() {
        super();
        getCanal().newRequest(Request.GAME_BOARD_HEIGHT).enviar();
        this.altura = Integer.parseInt(getCanal().getResposta());

        getCanal().newRequest(Request.GAME_BOARD_WIDTH).enviar();
        this.largura = Integer.parseInt(getCanal().getResposta());
    }

    public int getAlturaTabuleiro() {
        return altura;
    }

    public int getLarguraTabuleiro() {
        return largura;
    }

    public void renderBoard() {
        Gson gson = new Gson();
        observers.forEach(observer -> {
            for (int row = 0; row < getAlturaTabuleiro(); row++) {
                for (int column = 0; column < getLarguraTabuleiro(); column++) {
                    getCanal().newRequest(Request.GAME_BOARD_TILE).addParametro(row).addParametro(column).enviar();
                    observer.clearTile(row, column);
                    String resposta = getCanal().getResposta();
                    
                    ModelBoardTileProxy tile = (ModelBoardTileProxy) gson.fromJson(resposta, ModelBoardTileProxy.class);
                    //Apaga
                    observer.clearTile(row, column);

                    //Desenha a Desenha Folha
                    if (tile.hasFolha()) {
                        
                        observer.drawImage(row, column, tile.getCorFolha(), tile.getFolhaRotacao(), tile.getFolhaSimpleName());

                        //Verifica se esta tem filhotes 
                        if (tile.hasFilhote()) {
                            observer.drawImage(row, column, tile.getFilhoteCor(), null, tile.getFilhoteSimpleName());
                        }

                        //Verifica se essa tem uma peca
                        if (tile.hasPeca()) {
                            observer.drawImage(row, column, tile.getPecaCor(), tile.getPecaRotacao(), tile.getPecaSimpleName());
                        }
                    }
                }
            }
            observer.repaintTela();
        });
    }

    public void addObserver(BoardObserverProxy boardTable) {
        this.observers.add(boardTable);
    }

    public void eventoDeSelecao(Point newSelection) {

    }

    public void botaoClick(Diretion diretion) {

    }

    private RequestSocket getCanal() {
        return ClientController.getInstance().getCanal();
    }

}
