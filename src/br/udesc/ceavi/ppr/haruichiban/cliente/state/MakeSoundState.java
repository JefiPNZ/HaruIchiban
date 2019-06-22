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
            GameClienteController.getInstance().
                    getPlayer().notifySimplesComTempo("Flores com o mesmo valor", "Prepare-se para coaxar o mais alto que puder", 3500);
            Thread.sleep(5000);
            
            for (int i = 5; i > 0; i--) {
                GameClienteController.getInstance().getPlayer().notifySimples("Prepare-se para coaxar em: " + i);
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
