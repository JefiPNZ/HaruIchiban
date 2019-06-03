package br.udesc.ceavi.ppr.haruichiban.control;

import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;

import com.google.gson.Gson;

import br.udesc.ceavi.ppr.haruichiban.command.ChooseFlowerPlayer;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarUntitledGardenerException;
import br.udesc.ceavi.ppr.haruichiban.model.ModelPlayer;
import br.udesc.ceavi.ppr.haruichiban.model.flores.Flor;
import br.udesc.ceavi.ppr.haruichiban.state.TitleOfGardener;
import br.udesc.ceavi.ppr.haruichiban.state.UntitledGardener;

/**
 * Controlador de Player, servirar para controlar as acoes do player no jogo
 *
 * @author Gustavo C Santos
 * @since 08/05/2019
 *
 */
public class PlayerController extends Thread implements IPlayerController {

	/**
	 * Representa o jogador.
	 */
	private ModelPlayer player;

	/**
	 * Padrao State para guiar as açoes do player perante o estado dele
	 */
	private TitleOfGardener title;

	/**
	 * Este guarda a flor do turno
	 */
	private Flor florEmJogo;

	private IFluxoController fluxoController;

	private Fase fase;

	private Scanner clienteRequest;
	private PrintWriter clienteAnswer;
	private boolean jogador;

	private RequestProcess requestProcess;

	/**
	 *
	 * @param cor           identifica a cor das flores do jogador
	 * @param tamanhoDoDeck tamanho do deck
	 * @throws Exception
	 */
	public PlayerController(Color cor, int tamanhoDoDeck) {
		this.title = new UntitledGardener();
		this.player = new ModelPlayer(cor, tamanhoDoDeck);
		jogador = true;
		this.fase = Fase.CARREGANDO_DADOS;
	}

	@Override
	public void becomeUntitledGardener() throws PlayNaoPodeSeTornarUntitledGardenerException {
		title.becomeUntitledGardener(this);
	}

	@Override
	public void becomeJuniorGardener() throws PlayNaoPodeSeTornarJuniorException {
		title.becomeJuniorGardener(this);
	}

	@Override
	public void becomeSeniorGardener() throws PlayNaoPodeSeTornarSeniorException {
		title.becomeSeniorGardener(this);
	}

	@Override
	public void setTitle(TitleOfGardener title) {
		this.title = title;
	}

	@Override
	public void initDeck() {
		this.player.initDeck();
	}

	public void addPontos(int pontos) {
		player.addPontos(pontos);
	}

	public ModelPlayer getPlayer() {
		return player;
	}

	@Override
	public int getPileSize() {
		return player.getListaDeFlores().size();
	}

	public Color getColor() {
		return player.getColor();
	}

	@Override
	public synchronized List<Object> getHand() {
		return player.getListaMao().stream().map(flor -> flor.getValor()).collect(Collectors.toList());
	}

	@Override
	public void choseFlowerDeck() {
		SwingUtilities.invokeLater(() -> {
			sendResource("C,ESCOLHAUMAFLOR");
		});
	}

	@Override
	public void chooseFlowerDeckEnd(int x) {
		GameController.getInstance().executeCommand(new ChooseFlowerPlayer(this, x));
		fase = fluxoController.chooseFlowerEnd();
		fluxoController.chooseFlower();
		notifiyFlowerChoise();
	}

	@Override
	public Flor getFlower() {
		return florEmJogo;
	}

	@Override
	public void getFlorFromHand(int x) {
		florEmJogo = player.getFlorFromHand(x);
	}

	@Override
	public Flor removeFlower() {
		Flor flor = florEmJogo;
		florEmJogo = null;
		return flor;
	}

	@Override
	public void putFlowerTable() {
		title.putFlowerTable(this);
	}

	@Override
	public void fristWint() {
		title.firstWind(this);
	}

	@Override
	public void newDarkLeaf() {
		title.newDarkLeaf(this);
	}

	@Override
	public TitleOfGardener getTitle() {
		return title;
	}

	@Override
	public void devolverFlorAoDeck() {
		player.devolverFlor(removeFlower());
	}

	@Override
	public void setFluxoController(IFluxoController aThis) {
		this.fluxoController = aThis;
	}

	public IFluxoController getFluxoController() {
		return fluxoController;
	}

	@Override
	public int getPlayerScore() {
		return this.player.getPoints();
	}

	@Override
	public void notifySemTitulo() {
		sendResource("S,YouAreSemTitulo");
	}

	@Override
	public void notifyYouAJunior() {
		sendResource("S,YouAreJunior");
	}

	@Override
	public void notifyYouASenior() {
		sendResource("S,YouASenior");
	}

	@Override
	public void notifySimples(String messagem) {
		sendResource("M," + messagem);
	}

	private void notifiyFlowerChoise() {
		sendResource("C,EscolhaUmaFlorEnd");
	}

	@Override
	public void setFase(Fase fase) {
		this.fase = fase;
	}

	@Override
	public Fase getFase() {
		return fase;
	}

	@Override
	public boolean haveFlowers() {
		return player.haveFlowers();
	}

	public void setSocket(Socket mySocket, String msg) {
		try {
			this.clienteRequest = new Scanner(mySocket.getInputStream());
			this.clienteAnswer = new PrintWriter(mySocket.getOutputStream(), true);
			requestProcess = new RequestProcess(clienteRequest, clienteAnswer,this);
			this.start();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public void run() {
		while (clienteRequest.hasNextLine()) {
			requestProcess.processar();
		}
	}

	public void sendResource(String resource) {
		clienteAnswer.println(resource);
	}

	public void atualizarMao() {
		sendResource("M,A"); // Manda o Cliente Requerir a Atualizacao da sua mao e do seu Oponente
	}
}
