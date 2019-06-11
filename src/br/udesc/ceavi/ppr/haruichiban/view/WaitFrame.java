package br.udesc.ceavi.ppr.haruichiban.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Jeferson Penz, Gustavo Carvalho
 */
public class WaitFrame extends JFrame{
    
    private boolean aguardando;
    
    public WaitFrame(){
        super("Aguarde");
        aguardando = true;
        this.initFrame();
    }
    
    private void initFrame(){
        this.setSize(new Dimension(400, 200));
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.setBackground(Color.white);
        this.getContentPane().setBackground(Color.white);
        JLabel aguarde = new JLabel("Aguardando Oponente");
        aguarde.setFont(new Font("Times New Roman", Font.BOLD, 30));
        this.add(aguarde);
        JLabel pontos = new JLabel("");
        pontos.setFont(new Font("Times New Roman", Font.BOLD, 30));
        new Thread(() -> {
            StringBuilder sb = new StringBuilder();
            while(aguardando){
                sb.append('.');
                if(sb.length() > 3){
                    sb.setLength(0);
                }
                SwingUtilities.invokeLater(() ->{
                    pontos.setText(sb.toString());
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {}
            }
        }).start();
        this.add(pontos);
        this.setVisible(true);
    }
    
    public void fechaAguarde(){
        aguardando = false;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
    
}
