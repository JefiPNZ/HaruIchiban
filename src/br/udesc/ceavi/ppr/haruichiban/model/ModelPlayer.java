package br.udesc.ceavi.ppr.haruichiban.model;

import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeferson Penz, Gustavo C. Santos
 */
public class ModelPlayer {

    private int points;
    private List<Flor> listaDeFlores;
    private List<Flor> listaMao;
    private Color myColor;

    public ModelPlayer(Color cor, int tamanhoDeck) {
        this.points = 0;
        listaDeFlores = new ArrayList<>();
        listaMao = new ArrayList<>();
        this.myColor = cor;
        initDeck(tamanhoDeck);
        controlHand();
    }

    private void initDeck(int tamanho) {
        for (int i = 1; i < tamanho; i++) {
            listaDeFlores.add(GameController.getInstance().getFactoryPecas().createFlor(myColor, i, this));
        }
    }

    public void addPontos(int newPoints) {
        this.points += newPoints;
    }

    public int getPoints() {
        return points;
    }

    public void controlHand() {
        while (listaMao.size() != 3) {
            listaMao.add(listaDeFlores.remove((int) (Math.random() * listaDeFlores.size())));
        }
    }

    public List<Flor> getListaDeFlores() {
        return listaDeFlores;
    }

    public List<Flor> getListaMao() {
        return listaMao;
    }

    public Flor getFlorFromHand(int i) {
        Flor florARemover = listaMao.remove(i);
        controlHand();
        return florARemover;
    }

    public Color getColor() {
        return myColor;
    }

    public void devolverFlor(Flor florEmJogo) {
        this.listaDeFlores.add(florEmJogo);
    }

}
