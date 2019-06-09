package br.udesc.ceavi.ppr.haruichiban.control;

import java.net.Socket;

import br.udesc.ceavi.ppr.haruichiban.view.MainFrame;
import javax.swing.JOptionPane;

public class HarulchibanClient {

    private Socket socket;
    private PlayerControllerProxy player;

    public HarulchibanClient(String ip) {
        MainFrame frame = new MainFrame();
        try {
            socket = new Socket(ip, 60000);
            System.out.println("Conex\u00E3o Realizada");
            player = new PlayerControllerProxy(socket);
            System.out.println("PlayerControllerProxy Criado Com Sucesso");
            ClientController.getInstance();
            System.out.println("Iniciando Interfase");
            frame.begin(player);
            System.out.println("Interfase Iniciada Com Sucesso");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Nao foi possivel conectar: " + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

    }

    public static void main(String[] args) throws Exception {
        new HarulchibanClient("127.0.0.1");
    }

    public PlayerControllerProxy getClient() {
        return player;
    }

}
