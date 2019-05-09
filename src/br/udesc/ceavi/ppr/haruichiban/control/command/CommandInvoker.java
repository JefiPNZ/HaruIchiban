package br.udesc.ceavi.ppr.haruichiban.control.command;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author GustavoSantos
 * @since 03/05/2019
 *
 */
public class CommandInvoker {

    private List<Command> listaCommandNotExeuted;
    private List<Command> listaCommandExecutados;

    public CommandInvoker() {
        listaCommandNotExeuted = new ArrayList<>();
        listaCommandExecutados = new ArrayList<>();
    }

    public void addCommand(Command a) {
        this.listaCommandNotExeuted.add(a);
    }

    public void executeCommand(Command a) {
        a.execute();
        this.listaCommandExecutados.add(a);
    }

    public void executeAllCommandNotExeuted() {
        for (Command command : listaCommandNotExeuted) {
            command.execute();
        }
        this.listaCommandExecutados.addAll(listaCommandNotExeuted);
        listaCommandNotExeuted.clear();
    }

}
