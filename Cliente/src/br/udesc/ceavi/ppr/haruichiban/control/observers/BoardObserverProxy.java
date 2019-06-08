package br.udesc.ceavi.ppr.haruichiban.control.observers;

import java.awt.Color;

public interface BoardObserverProxy {

	void notifyAtivarTabela();

	void notifyDesativarTabela();

	void notifyAtivarDirection();

	void notifyDesativarDirection();

	void clearTile(int row, int col);

	void drawImage(int row, int col, Color lilypadColor, Float rotation, String imagem);

	void repaintTela();

}
