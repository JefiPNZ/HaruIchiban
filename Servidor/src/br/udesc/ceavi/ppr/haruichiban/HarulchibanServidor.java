package br.udesc.ceavi.ppr.haruichiban;

import java.awt.EventQueue;
import java.net.ServerSocket;

import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.control.PlayerController;
import br.udesc.ceavi.ppr.haruichiban.model.GameConfig;
import br.udesc.ceavi.ppr.haruichiban.view.FrameConfig;

public class HarulchibanServidor {

	private ServerSocket listener;

	public static void main(String[] args) {
		GameController.getInstance();
		GameController.getInstance().setServidor(new HarulchibanServidor());
		EventQueue.invokeLater(() -> {
			FrameConfig tela = new FrameConfig();
			tela.setVisible(true);
		});
	}

	public void startServidor(GameConfig gameConfig) {
		try {
			listener = new ServerSocket(60000);
			System.out.println("Harulchiban Servidor Begin.....\n");

			System.out.println("Esperando Conex\u00E3o do Player Top Iniciar....\n");
			PlayerController topPlayer = new PlayerController(gameConfig.getColorTop(), gameConfig.getTamanho());
			topPlayer.setSocket(listener.accept(), "TOP");
			GameController.getInstance().setTopPlayer(topPlayer);

//			System.out.println("Esperando Conex\u00E3o do Player BOTTON Iniciar....\n");
//			PlayerController bottomPlayer = new PlayerController(gameConfig.getColorBotton(),
//					gameConfig.getTamanho());
//			bottomPlayer.setSocket(listener.accept(), "BOTTON");
//			GameController.getInstance().setBottonPlayer(bottomPlayer);

//			GameController.getInstance().startGame();

		} catch (Exception ex) {

		}
	}

	public void fecharServidor() throws Exception {
		listener.close();
		System.out.println("Server finished...");
		System.exit(0);
	}

}
