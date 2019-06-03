package br.udesc.ceavi.ppr.haruichiban.view;

import java.awt.Color;
import java.util.List;

public interface Jogador {

	Color getColor();

	List<Integer> getHand();

	int getPileSize();

	void sendRequest(String request);

	void setCor(Color cor);

	void addObserver(IPlayerPanelObserver playerPanel);

	void chooseFlowerDeckEnd(int selectedColumn);

}
