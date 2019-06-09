package br.udesc.ceavi.ppr.haruichiban.control;

import java.net.Socket;

import br.udesc.ceavi.ppr.haruichiban.view.MainFrame;
import br.udesc.ceavi.ppr.haruichiban.view.WaitFrame;
import javax.swing.JOptionPane;

public class HaruIchibanClient {

    private Socket socket;
    private PlayerControllerProxy player;

    public HaruIchibanClient(String ip) {
        WaitFrame aguarde = new WaitFrame();
        try {
            socket = new Socket(ip, 60000);
            player = new PlayerControllerProxy(socket);
            if(player.aguardaPronto()){
                player.inicia();
                aguarde.fechaAguarde();
                MainFrame frame = new MainFrame();
                frame.begin(player);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(aguarde, "Não foi possível conectar: " + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
        }

    }

    public static void main(String[] args) throws Exception {
        new HaruIchibanClient("127.0.0.1");
    }

    public PlayerControllerProxy getClient() {
        return player;
    }

}
