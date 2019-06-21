package br.udesc.ceavi.ppr.haruichiban.cliente.control.channel;

import com.google.gson.Gson;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entrada
 *
 * @author Gustavo C
 */
public class PackageClientInput implements Runnable {

    private BlockingQueue<CommunicationPackage> entradas;
    private Scanner entrada;
    private PackageClientConsumer packetConsumer;
    private Gson gson;

    public PackageClientInput(Scanner scanner) {
        this.entradas = new LinkedBlockingDeque<>();
        this.entrada = scanner;
        this.gson = new Gson();
        this.packetConsumer = new PackageClientConsumer(entradas);
        new Thread(packetConsumer, "Thread Consumer").start();
    }

    public synchronized void add(String packet) {
        try {
            CommunicationPackage newPackege = gson.fromJson(packet, CommunicationPackage.class);
            entradas.put(newPackege);
        } catch (InterruptedException ex) {
            Logger.getLogger(PackageClientInput.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (entrada.hasNextLine()) {
            add(entrada.nextLine());
        }
    }

    public void close() {
        entrada.close();
        try {
            this.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(PackageClientInput.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
