package br.udesc.ceavi.ppr.haruichiban.cliente.state;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.GameClienteController;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gustavo C
 */
public class MakeSoundState extends State {

    @Override
    public void executar() {
        try {
            GameClienteController.getInstance().getPlayer().notifySimplesComTempo("Flores Com O Mesmo Valor", "Preparece Com Fechar 3 Grite O Mais Alto Que Puder", 3000);
            Thread.sleep(3250);
            for (int i = 5; i > 0; i--) {
                GameClienteController.getInstance().getPlayer().notifySimples("BATALHA\u0021 Em " + i);
                Thread.sleep(900);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(MakeSoundState.class.getName()).log(Level.SEVERE, null, ex);
        }

        GameClienteController.getInstance().getEmitirSomController().ativarEmitirSom();
    }

    @Override
    public void setEndFase() {
        GameClienteController.getInstance().getPlayer().notifySimplesComTempo("Emitindo Som", "", 1500);
        super.setEndFase();
    }
}
