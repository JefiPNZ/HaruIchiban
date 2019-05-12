package br.udesc.ceavi.ppr.haruichiban.view;


import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.control.GameStateObserver;
import br.udesc.ceavi.ppr.haruichiban.control.IMenuController;
import br.udesc.ceavi.ppr.haruichiban.control.MenuController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Stack;
import java.util.StringJoiner;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author Jeferson Penz
 */
public class MenuPanel extends JPanel implements GameStateObserver{
    
    private JLabel labelUltimaMensagem;
    private JLabel logMensagens;
    private IMenuController menuController;
    private String ultimaMensagem;
    private List<String> mensagens;

    public MenuPanel() {
        menuController = new MenuController();
        this.mensagens = new ArrayList<>();
        this.ultimaMensagem = "";
        GameController.getInstance().addGameStateObserver(this);
        this.setBackground(new Color(140, 75, 55));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.initComponentes();
    }
    
    private void initComponentes(){
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(300, Integer.MAX_VALUE));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        JLabel haruIchiban = new JLabel("Haru Ichiban");
        haruIchiban.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        haruIchiban.setForeground(Color.white);
        JLabel men = new JLabel("Menu");
        men.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 2, 0, 2)
                                                        ,BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE)));
        men.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        men.setForeground(Color.white);
        
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridBagLayout());
        painelBotoes.setBackground(new Color(140, 75, 55));
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(5, 2, 0, 2));
        
        JLabel labelBotoes = new JLabel("Ações: ");
        labelBotoes.setForeground(Color.WHITE);
        JButton reiniciar = new JButton("Reiniciar");
        reiniciar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black)
                                                              ,BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        reiniciar.addActionListener((ActionEvent e) -> {
            menuController.reiniciar();
        });
        JButton sair = new JButton("Sair");
        sair.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black)
                                                              ,BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        sair.addActionListener((ActionEvent e) -> {
            menuController.finalizar();
        });
        painelBotoes.add(labelBotoes, gbc);
        painelBotoes.add(reiniciar, gbc);
        painelBotoes.add(sair, gbc);
        
        JPanel painelMensagens = new JPanel();
        painelMensagens.setLayout(new BoxLayout(painelMensagens, BoxLayout.Y_AXIS));
        painelMensagens.setBorder(BorderFactory.createEmptyBorder(5, 2, 5, 2));
        JLabel textoMensagem = new JLabel("Histórico: ");
        textoMensagem.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
        textoMensagem.setForeground(Color.WHITE);
        this.labelUltimaMensagem = new JLabel("...");
        this.labelUltimaMensagem.setForeground(Color.WHITE);
        this.labelUltimaMensagem.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        this.logMensagens = new JLabel();
        this.logMensagens.setForeground(Color.WHITE);
        this.logMensagens.setFont(new Font(Font.DIALOG, Font.PLAIN, 11));
        painelMensagens.setBackground(new Color(140, 75, 55));
        painelMensagens.add(textoMensagem);
        painelMensagens.add(this.labelUltimaMensagem);
        painelMensagens.add(this.logMensagens);
        
        this.add(haruIchiban, gbc);
        this.add(men, gbc);
        this.add(painelBotoes, gbc);
        gbc.weighty = 1;
        this.add(painelMensagens, gbc);
    }

    @Override
    public void notificaMudancaEstado(String mensagem) {
        this.mensagens.add(ultimaMensagem);
        this.ultimaMensagem = mensagem;
        this.labelUltimaMensagem.setText("<html> > " + this.ultimaMensagem + "</html>");
        StringJoiner joiner = new StringJoiner("<br/>");
        for (ListIterator<String> iterador = this.mensagens.listIterator(this.mensagens.size()); iterador.hasPrevious(); ){
            joiner.add(iterador.previous());
        }
        this.logMensagens.setText("<html>" + joiner.toString() + "</html>");
    }

    @Override
    public void notificaFimJogo() {}
    
}
