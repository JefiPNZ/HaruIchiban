package br.udesc.ceavi.ppr.haruichiban.command;

import br.udesc.ceavi.ppr.haruichiban.control.IPlayerController;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarJuniorException;
import br.udesc.ceavi.ppr.haruichiban.exceptions.PlayNaoPodeSeTornarSeniorException;
import java.util.Map;

/**
 *
 * @author Gustavo C Santos
 * @since 14/05/2019
 *
 */
public class DefineTitleCommand implements Command {

    private Map<Integer, IPlayerController> map;
    private IPlayerController junior;
    private IPlayerController senior;

    public DefineTitleCommand(Map<Integer, IPlayerController> map,
            IPlayerController junior, IPlayerController senior) {
        this.map = map;
        this.junior = junior;
        this.senior = senior;
    }

    @Override
    public void execute() {
        try {
            junior.becomeJuniorGardener();
            senior.becomeSeniorGardener();
        } catch (PlayNaoPodeSeTornarJuniorException | PlayNaoPodeSeTornarSeniorException ex) {
        }

        map.put(1, junior);
        map.put(2, senior);
    }

}
