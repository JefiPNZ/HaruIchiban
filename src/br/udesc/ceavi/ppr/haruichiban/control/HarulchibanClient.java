package br.udesc.ceavi.ppr.haruichiban.control;

import java.net.Socket;

import br.udesc.ceavi.ppr.haruichiban.view.MainFrame;
import javax.swing.JOptionPane;
import br.udesc.ceavi.ppr.haruichiban.model.Product;

public class HarulchibanClient {

    private Socket socket;
    private PlayerControllerProxy player;

    public HarulchibanClient(String ip) {
        MainFrame frame = new MainFrame();
        try {
            socket = new Socket(ip, 60000);
            player = new PlayerControllerProxy(socket);
            ClientController.getInstance();
            frame.begin(player);
        } catch (Exception ex) {
            ClientController.getInstance().getCanal().newProduct(Product.GAME_ENDGAME).enviar();
            JOptionPane.showMessageDialog(frame, "Nao foi possivel conectar: " + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
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
