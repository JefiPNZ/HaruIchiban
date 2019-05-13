package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.builder.BoardBuilder;
import br.udesc.ceavi.ppr.haruichiban.builder.BuilderDirector;
import br.udesc.ceavi.ppr.haruichiban.exceptions.CanNotChangeSideNenufareException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.FolhaJaPossuiUmaPecaEmCimaException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PosicaoEmTabuleiroOcupadaException;
import br.udesc.ceavi.ppr.haruichiban.model.Flor;
import br.udesc.ceavi.ppr.haruichiban.model.ModelBoardTile;
import br.udesc.ceavi.ppr.haruichiban.model.ModelPlayer;
import br.udesc.ceavi.ppr.haruichiban.model.folha.Folha;
import br.udesc.ceavi.ppr.haruichiban.model.PecaTabuleiro;
import br.udesc.ceavi.ppr.haruichiban.model.TipoPeca;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeferson Penz
 */
public class BoardController implements IBoardController {

    private List<BoardObserver> observers;
    private ModelBoardTile[][] tabuleiro;
    private PlayerController playOuvindo;

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
    public void renderBoard() {
        observers.stream().map((observer) -> {
            for (int row = 0; row < tabuleiro.length; row++) {
                for (int column = 0; column < tabuleiro[row].length; column++) {
                    observer.clearTile(row, column);
                    //Desenha a Nenufare
                    if (getCampoTabuleiro(row, column).hasFolha()) {
                        Folha folha = getCampoTabuleiro(row, column).getFolha();
                        observer.drawImage(row, column, folha.getCor(), folha.getRotacao(), folha.getClass().getSimpleName());
                        //Verifica se esta tem filhotes 
                        if (folha.hasFilhote()) {
                            observer.drawImage(row, column, folha.getFilhote().getCor(), null, folha.getFilhote().getClass().getSimpleName());
                        }
                        //Verifica se essa tem uma peca
                        if (folha.hasPeca()) {
                            observer.drawImage(row, column, folha.getPeca().getCor(), folha.getPeca().getRotacao(), folha.getPeca().getClass().getSimpleName());
                        }
                    }
                }
            }
            return observer;
        }).forEachOrdered((observer) -> {
            observer.repaintTela();
        });
    }

    private void initTabuleiro() {
        BoardBuilder builder = GameController.getInstance().getBuilder();
        BuilderDirector director = new BuilderDirector(builder);
        director.contruir();
        this.tabuleiro = builder.getProduto();
    }

    public ModelBoardTile getCampoTabuleiro(int x, int y) {
        return this.tabuleiro[x][y];
    }

    public ModelBoardTile getCampoTabuleiro(Point point) {
        return this.tabuleiro[point.y][point.x];
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
            int paraX, int paraY) throws FolhaJaPossuiUmaPecaEmCimaException {

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

    public boolean colocarFlor(Flor flor, int x, int y) throws FolhaJaPossuiUmaPecaEmCimaException {
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

    @Override
    public ModelBoardTile getFolhaEscura() {
        for (int row = 0; row < tabuleiro.length; row++) {
            for (int column = 0; column < tabuleiro[row].length; column++) {
                if (getCampoTabuleiro(row, column).hasFolha()
                        && getCampoTabuleiro(row, column).getFolha().isEscura()) {
                    return getCampoTabuleiro(row, column);
                }
            }
        }
        return null;
    }

    @Override
    public void eventoDeSelecao(Point newSelection) {
        try {
            playOuvindo.getTitle().getFolha(playOuvindo, newSelection);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void setControlPlayOuvinte(PlayerController ouvindo) {
        this.playOuvindo = ouvindo;
        if (ouvindo != null) {
            observers.forEach(obs -> obs.notifyAtivarTabela());
        } else {
            observers.forEach(obs -> obs.notifyDesativarTabela());
        }
    }

    @Override
    public boolean hasPlayOuvindo() {
        return playOuvindo != null;
    }

    @Override
    public ModelBoardTile getModelBoardTile(Point point) {
        return getCampoTabuleiro(point);
    }

    @Override
    public void moveTo(Point origem, Point destino) {
        ModelBoardTile origemT = tabuleiro[origem.y][origem.x];
        ModelBoardTile destinoT = tabuleiro[destino.y][destino.x];

        tabuleiro[destino.y][destino.x] = origemT;
        tabuleiro[origem.y][origem.x] = destinoT;
        renderBoard();
        observers.forEach(obs -> obs.repaintTela());
    }
    
    /**
     * Valida se um dos jogadores pontuou no tabuleiro e adiciona os respectivos pontos, retornando se algum ou ambos pontuou(aram).
     * @return 
     */
    public boolean validaPontuacao(){
        int maiorPontuacaoPrimeiroPontuador = 0;
        int maiorPontuacaoSegundoPontuador = 0;
        ModelPlayer primeiroPontuador = null;
        ModelPlayer segundoPontuador = null;
        for (int row = 0; row < tabuleiro.length; row++) {
            for (int column = 0; column < tabuleiro[row].length; column++) {
                if(tabuleiro[row][column].hasFolha() && tabuleiro[row][column].getFolha().hasPeca() &&
                   tabuleiro[row][column].getFolha().getPeca().getTipo() == TipoPeca.FLOR){
                    int maiorPontuacao = 0;
                    ModelPlayer origem = ((Flor)tabuleiro[row][column].getFolha().getPeca()).getPlayerOrigem();
                    int linear = this.validaFloresLineares(row, column, origem);
                    if(linear > maiorPontuacao){
                        maiorPontuacao = linear;
                    }
                    int diagonal = this.validaFloresDiagonais(row, column, origem);
                    if(diagonal > maiorPontuacao){
                        maiorPontuacao = diagonal;
                    }
                    int bloco = this.validaFloresBloco(row, column, origem);
                    if(bloco > maiorPontuacao){
                        maiorPontuacao = bloco;
                    }
                    if(maiorPontuacao > 0){
                        if(primeiroPontuador == null){
                            primeiroPontuador = origem;
                        }
                        else if(segundoPontuador == null){
                            segundoPontuador = origem;
                        }
                    }
                }
            }
        }
        if(primeiroPontuador != null){
            primeiroPontuador.addPontos(maiorPontuacaoPrimeiroPontuador);
            if(segundoPontuador != null){
                segundoPontuador.addPontos(maiorPontuacaoSegundoPontuador);
            }
        }
        return primeiroPontuador != null;
    }
    
    /**
     * Valida se há uma sequencia de flores em linha (horizontal ou vertical) e retorna a pontuação desta.
     * @param row
     * @param column
     * @param origem
     * @return 
     */
    private int validaFloresLineares(int row, int column, ModelPlayer origem){
        int horizontal = 1 + validaFloresDirecao(row, column, -1, 0, origem) + validaFloresDirecao(row, column, 1, 0, origem);
        int vertical   = 1 + validaFloresDirecao(row, column, 0, -1, origem) + validaFloresDirecao(row, column, 0, 1, origem);
        if(horizontal >= 4 || vertical >= 4){
            int maior = (horizontal > vertical ? horizontal : vertical);
            if(maior >= 5){
                return 5;
            }
            return 2;
        }
        return 0;
    }
    
    /**
     * Valida se há uma sequencia de flores em linha (diagonal) e retorna a pontuação desta.
     * @param row
     * @param column
     * @param origem
     * @return 
     */
    private int validaFloresDiagonais(int row, int column, ModelPlayer origem){
        int decendente = 1 + validaFloresDirecao(row, column, -1, 1, origem) + validaFloresDirecao(row, column, 1, -1, origem);
        int ascendente = 1 + validaFloresDirecao(row, column, -1, -1, origem) + validaFloresDirecao(row, column, 1, 1, origem);
        if(decendente >= 4 || ascendente >= 4){
            int maior = (decendente > ascendente ? decendente : ascendente);
            if(maior >= 5){
                return 5;
            }
            return 3;
        }
        return 0;
    }
    
    /**
     * Valida se há uma sequencia de flores em bloco (qualquer direção) e retorna a pontuação desta.
     * @param row
     * @param column
     * @param origem
     * @return 
     */
    private int validaFloresBloco(int row, int column, ModelPlayer origem){
        if(this.validaFloresQuadrado(row, column, origem) || this.validaFloresQuadrado(row - 1, column, origem) ||
           this.validaFloresQuadrado(row, column - 1, origem) || this.validaFloresQuadrado(row - 1, column - 1, origem)){
            return 1;
        }
        return 0;
    }
    
    private int validaFloresDirecao(int row, int column, int deltaX, int deltaY, ModelPlayer origem){
        int count = 0;
        ModelBoardTile atual = tabuleiro[row][column];
        while(atual != null && isPosicaoValida(column + deltaX, row + deltaY)){
            atual = tabuleiro[row + deltaY][column + deltaX];
            if(tilePertenceJogador(atual, origem)){
                count++;
            }
            else {
                atual = null;
            }
        }
        return count;
    }
    
    private boolean validaFloresQuadrado(int xEsquerda, int yInferior, ModelPlayer origem){
        if(isPosicaoValida(yInferior + 1, xEsquerda) && isPosicaoValida(yInferior, xEsquerda + 1) && isPosicaoValida(yInferior + 1, xEsquerda + 1)){
            if(tilePertenceJogador(tabuleiro[yInferior + 1][xEsquerda], origem) && tilePertenceJogador(tabuleiro[yInferior][xEsquerda + 1], origem) &&
               tilePertenceJogador(tabuleiro[yInferior + 1][xEsquerda + 1], origem)){
                return true;
            }
        }
        return false;
    }
    
    private boolean tilePertenceJogador(ModelBoardTile tile, ModelPlayer origem){
        return tile.hasFolha() && tile.getFolha().hasPeca() && tile.getFolha().getPeca().getTipo() == TipoPeca.FLOR &&
               ((Flor) tile.getFolha().getPeca()).getPlayerOrigem().equals(origem);
    }
    
    private boolean isPosicaoValida(int x, int y){
        return !(y < 0 || y >= tabuleiro.length || x < 0 || x >= tabuleiro[0].length);
    }

}
