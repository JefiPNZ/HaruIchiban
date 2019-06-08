package br.udesc.ceavi.ppr.haruichiban.control;

import java.awt.Color;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import br.udesc.ceavi.ppr.haruichiban.control.RequestSocket.Product;
import br.udesc.ceavi.ppr.haruichiban.control.RequestSocket.Request;
import br.udesc.ceavi.ppr.haruichiban.view.IPlayerPanelObserver;
import br.udesc.ceavi.ppr.haruichiban.view.Jogador;

public class PlayerControllerProxy implements Jogador {

	private Socket socket;

	private boolean top;
	private Color cor;

	public PlayerControllerProxy(Socket socket) throws Exception {
		this.socket = socket;
		ClientController.getInstance().initRequestSocket(new Scanner(socket.getInputStream()),
				new PrintWriter(socket.getOutputStream(),true));
		definirPosicao();
		definirCor();
	}

	private void definirCor() {
		getCanal().newRequest(Request.MYCOLOR).enviar();
		this.cor = (Color) new Gson().fromJson(getCanal().getResposta(), Color.class);
	}

	private void definirPosicao() {
		getCanal().newRequest(Request.MYPOSITION).enviar();
		top = getCanal().getResposta().contains("TOP");
	}

	public boolean isTop() {
		return top;
	}

	@Override
	public Color getColor() {
		return cor;
	}

	@Override
	public List<Integer> getHand() {
		getCanal().newRequest(Request.MYHAND).enviar();
		String resposta = getCanal().getResposta();
		List<String> asList = Arrays.asList(resposta.split(",")[0], resposta.split(",")[1], resposta.split(",")[2]);
		return asList.stream().map(valor -> Integer.parseInt(valor)).collect(Collectors.toList());
	}

	@Override
	public int getPileSize() {
		getCanal().newRequest(Request.MYPILESIZE).enviar();
		String resposta = getCanal().getResposta();
		return Integer.parseInt(resposta);
	}

	public void chooseFlowerDeckEnd(int selectedColumn) {
		getCanal().newProduct(Product.CHOOSEFLOWER).addParametro("x=" + selectedColumn).enviar();
	}

	public void addObserver(IPlayerPanelObserver obs) {

	}

	public Socket getSocket() {
		return socket;
	}

	@Override
	public void setCor(Color cor) {
		this.cor = cor;
	}

	private RequestSocket getCanal() {
		return ClientController.getInstance().getCanal();
	}
}
