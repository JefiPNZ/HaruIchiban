package br.udesc.ceavi.ppr.haruichiban.control;

import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarUntitledGardenerException;
import br.udesc.ceavi.ppr.haruichiban.model.Flor;
import br.udesc.ceavi.ppr.haruichiban.model.ModelPlayer;
import br.udesc.ceavi.ppr.haruichiban.state.TitleOfGardener;
import br.udesc.ceavi.ppr.haruichiban.state.UntitledGardener;
import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador de Player, servirar para controlar as acoes do play no jogo
 *
 * @author Gustavo C Santos
 * @since 08/05/2019
 *
 */
public class PlayerController implements IPlayerController {

    /**
     * Representa o jogador.
     */
    private ModelPlayer play;

    /**
     * Padrao State para guiar as açoes do play perante o estado dele
     */
    private TitleOfGardener title;

    private Flor florEmJogo;

    /**
     *
     * @param cor identifica a cor das flores do jogador
     */
    public PlayerController(Color cor) {
        this.title = new UntitledGardener();
        this.play = new ModelPlayer(cor);
    }

    public void throwFlower(int i) throws Exception {
    }

    public void moveNenufares() throws Exception {
    }

    public void chooseNewDarkNenufares() throws Exception {
    }

    public void becomeUntitledGardener() throws PlayNaoPodeSeTornarUntitledGardenerException {
        title.becomeUntitledGardener(this);
    }

    public void becomeJuniorGardener() throws PlayNaoPodeSeTornarJuniorException {
        title.becomeJuniorGardener(this);
    }

    public void becomeSeniorGardener() throws PlayNaoPodeSeTornarSeniorException {
        title.becomeSeniorGardener(this);
    }

    public void setTitle(TitleOfGardener title) {
        this.title = title;
    }

    public void addPontos(int pontos) {
        play.addPontos(pontos);
    }

    public ModelPlayer getPlay() {
        return play;
    }

    @Override
    public int getPileSize() {
        return play.getListaDeFlores().size();
    }

    @Override
    public List<Object> getHand() {
        return play.getListaMao().stream().map(flor -> flor.getValor()).collect(Collectors.toList());
    }

    @Override
    public void selecionarFlor(int x) {
        //Tira da mao do play a flor para coloca-la em jogo
        florEmJogo = play.getFlorFromHand(x);
        System.out.println("Flor em Jogo É " + florEmJogo.getValor() + " e cor ao " + florEmJogo.getCor());
        //Sera Necessario Tratamento de View Aqui
        /**
         * Logica de flores em jogo no GameControl
         **/        
    }

    public Flor getFlorEmJogo() {
        return florEmJogo;
    }
    
    public Color getColor(){
        return play.getColor();
    }

}
