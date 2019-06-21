package br.udesc.ceavi.ppr.haruichiban.cliente.control;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.channel.ModelPost;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IMenuController;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeferson Penz
 */
public class MenuController implements IMenuController{

    @Override
    public void reiniciar() {
        GameClienteController.getInstance().stop(null);
    }

    @Override
    public void finalizar() {
        GameClienteController game = GameClienteController.getInstance();
        game.getPackageOutput().newPost(ModelPost.DESCONTANDO, "");
        game.getPackageOutput().close();
        game.getPackageInput().close();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
    
}
