package br.udesc.ceavi.ppr.haruichiban.model;

import br.udesc.ceavi.ppr.haruichiban.model.Flor.Flor;
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
    private final List<Flor> listaDeFlores;
    private List<Flor> listaMao;
    private Color myColor;

    public ModelPlayer(Color cor) {
        this.points = 0;
        listaDeFlores = new ArrayList<>();
        listaMao = new ArrayList<>();
        this.myColor = cor;
        initHand();
        controlHand();
    }

    private void initHand() {
        for (int i = 1; i < 9; i++) {
            listaDeFlores.add(GameController.getInstance().getFactoryPecas().createFlor(myColor, i, this));
        }
    }

    public void addPontos(int newPoints) {
        this.points += newPoints;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Fores: {");
        for (Flor listaDeFlore : listaDeFlores) {
            sb.append("[");
            sb.append("Valor: \"").append(listaDeFlore.getValor());
            sb.append("\",");
            sb.append("Cor: \"").append(listaDeFlore.getCor());
            sb.append("\"]");
        }
        sb.append("}");
        StringBuilder sb2 = new StringBuilder("Fores: {");
        for (Flor mao : listaMao) {
            sb2.append("[");
            sb2.append("Valor: \"").append(mao.getValor());
            sb2.append("\",");
            sb2.append("Cor: \"").append(mao.getCor());
            sb2.append("\"]");
        }
        sb2.append("}");
        return "ModelPlayer{" + "points=" + points + "\nListaDeFlores=" + sb.toString() + "\nMao: " + sb2.toString() + '}';
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
        return listaMao.get(i);
    }

    public Color getColor() {
        return myColor;
    }

}
