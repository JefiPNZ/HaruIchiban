package br.udesc.ceavi.ppr.haruichiban.cliente.view;

import br.udesc.ceavi.ppr.haruichiban.cliente.control.GameClienteController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IEmitirSomController;
import br.udesc.ceavi.ppr.haruichiban.cliente.utils.ColorScale;
import br.udesc.ceavi.ppr.haruichiban.cliente.utils.Images;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.interfaces.IPlayerController;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.observers.PlayerPanelObserver;
import br.udesc.ceavi.ppr.haruichiban.cliente.state.State;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalDateTime;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import br.udesc.ceavi.ppr.haruichiban.cliente.control.observers.IEmitirSomObserver;

/**
 * Painel para representação dos dados de um jogador.
 *
 * @author Jeferson Penz
 */
public class PlayerPanel extends JPanel implements PlayerPanelObserver, IEmitirSomObserver {

    private IPlayerController controller;
    private BufferedImage floorImg;
    private BufferedImage flowerImg;
    private BufferedImage baseImg;
    private BufferedImage clothImg;
    private BufferedImage faceImg;
    private IEmitirSomController emitirSomController;
    private PlayerHandTable playerHand;
    private JScrollPane playerHandPane;
    private JButton botaoCoaxar;
    private int rotation;
    private String estado;
    private String notificacao;

    /**
     * Cria um novo painel para o jogador com a cor desejada.
     *
     * @param color Cor do jogador.
     */
    /**
     * Cria um novo painel para o jogador com a cor desejada.
     *
     * @param color Cor do jogador.
     * @param controller
     */
    public PlayerPanel(Color color, IPlayerController controller) {
        super();
        this.estado = "";
        this.notificacao = "";
        this.controller = controller;
        this.controller.addObserver(this);
        ColorScale scale = new ColorScale(color);
        try {
            this.floorImg = Images.getImagem(Images.JOGADOR_TABUA);
            this.baseImg = Images.getImagem(Images.JOGADOR_BASE);
            this.clothImg = scale.convert(Images.JOGADOR_ROUPA);
            this.faceImg = Images.getImagem(Images.JOGADOR_ROSTO);

            if (GameClienteController.getInstance().getEstacao().equalsIgnoreCase("Inverno")) {
                this.flowerImg = scale.convert(Images.JOGADOR_FLOR_INV);
            } else {
                this.flowerImg = scale.convert(Images.JOGADOR_FLOR_PRIM);

            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "N\u00E3o foi poss\u00EDvel ler os arquivos de imagem do jogo.");
        }
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                playerHand.revalidate();
            }
        });
        this.rotation = 0;
        this.setBackground(new Color(0, 0, 0, 0));
        this.setPreferredSize(new Dimension(0, baseImg.getHeight()));
        this.setOpaque(false);
        this.initializePlayerHand();
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    }

    /**
     * Realiza o carregamento da tabela de jogo.
     */
    private void initializePlayerHand() {
        this.playerHand = new PlayerHandTable(this, this.controller);
        playerHandPane = new JScrollPane();

        playerHandPane.setViewportView(playerHand);
        playerHandPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        playerHandPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        playerHandPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        playerHandPane.setBackground(new Color(0, 0, 0, 0));
        playerHandPane.setOpaque(false);
        playerHandPane.getViewport().setOpaque(false);
        playerHandPane.setViewportBorder(BorderFactory.createEmptyBorder());
        playerHandPane.getViewport().setBackground(new Color(0, 0, 0, 0));
        this.add(playerHandPane);
    }

    @Override
    public void notifyUtilizaBotaoCoaxar() {
        this.emitirSomController = GameClienteController.getInstance().getEmitirSomController();
        this.emitirSomController.addObserver(this);
        this.botaoCoaxar = new JButton("Coaxar!"){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension((int)(getParent().getSize().getWidth() * 0.35)
                                    ,(int) getParent().getSize().getHeight());
            }
        };
        this.botaoCoaxar.setPreferredSize(new Dimension(75, 40));
        this.botaoCoaxar.addActionListener((ActionEvent event) -> {
            emitirSomController.emitirSom();
        });
    }

    @Override
    public void ativarButao() {
        this.remove(this.playerHandPane);
        this.add(this.botaoCoaxar);
        this.notificacao = "";
        this.revalidate();
        this.repaint();
    }

    @Override
    public void desativarButao() {
        this.remove(this.botaoCoaxar);
        this.add(this.playerHandPane);
        this.revalidate();
        this.repaint();
    }

    @Override
    /**
     * {@inheritdoc}
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.drawFloor(g);
        this.drawPlayer(g);
        this.drawPile(g);
        this.drawState(g);
        this.drawNotify(g);
    }

    /**
     * Desenha o chao no painel.
     *
     * @param g
     */
    private void drawFloor(Graphics g) {
        int xImg = (int) Math.ceil((float) this.getWidth() / (float) floorImg.getWidth());
        AffineTransform rotTransform = AffineTransform.getRotateInstance(Math.toRadians(this.rotation),
                floorImg.getWidth() / 2, floorImg.getHeight() / 2);
        AffineTransformOp rotOperator = new AffineTransformOp(rotTransform, AffineTransformOp.TYPE_BICUBIC);
        Random rand = GameClienteController.getInstance().getFixedRandomizer();
        AffineTransform scltransform = AffineTransform.getScaleInstance(1, (float) this.getHeight() / floorImg.getHeight());
        AffineTransformOp scloperator = new AffineTransformOp(scltransform, AffineTransformOp.TYPE_BICUBIC);
        for (int i = 0; i < xImg; i++) {
            AffineTransform tslTransform = AffineTransform.getTranslateInstance(0, rand.nextInt(10));
            AffineTransformOp tslOperator = new AffineTransformOp(tslTransform, AffineTransformOp.TYPE_BICUBIC);
            g.drawImage(scloperator.filter(rotOperator.filter(tslOperator.filter(floorImg, null), null), null), i * floorImg.getWidth(), 0, null);
        }
    }

    /**
     * Desenha o jogador no painel.
     *
     * @param g
     */
    private void drawPlayer(Graphics g) {
        AffineTransform transformation = AffineTransform.getRotateInstance(Math.toRadians(this.rotation),
                baseImg.getWidth() / 2, baseImg.getHeight() / 2);
        float scaleX = this.getWidth() * 0.3f / baseImg.getWidth();
        float scaleY = this.getHeight() * 1.0f / baseImg.getHeight();
        AffineTransform scltransform = null;
        if (scaleX > scaleY) {
            scltransform = AffineTransform.getScaleInstance(scaleY / scaleX * scaleX,
                    scaleY);
        } else {
            scltransform = AffineTransform.getScaleInstance(scaleX,
                    scaleX / scaleY * scaleY);
        }
        AffineTransformOp scloperator = new AffineTransformOp(scltransform, AffineTransformOp.TYPE_BICUBIC);
        AffineTransformOp operator = new AffineTransformOp(transformation, AffineTransformOp.TYPE_BICUBIC);
        g.drawImage(scloperator.filter(operator.filter(baseImg, null), null), 0, 0, null);
        g.drawImage(scloperator.filter(operator.filter(clothImg, null), null), 0, 0, null);
        g.drawImage(scloperator.filter(operator.filter(faceImg, null), null), 0, 0, null);
    }

    /**
     * Desenha a pilha do jogador no painel.
     *
     * @param g
     */
    private void drawPile(Graphics g) {
        Random rand = GameClienteController.getInstance().getFixedRandomizer();
        float scaleX = this.getWidth() * 0.1f / getFlowerImg().getWidth();
        float scaleY = this.getHeight() * 0.8f / getFlowerImg().getHeight();
        AffineTransform scltransform = null;
        if (scaleX > scaleY) {
            scltransform = AffineTransform.getScaleInstance(scaleY / scaleX * scaleX,
                    scaleY);
        } else {
            scltransform = AffineTransform.getScaleInstance(scaleX,
                    scaleX / scaleY * scaleY);
        }
        AffineTransformOp scloperator = new AffineTransformOp(scltransform, AffineTransformOp.TYPE_BICUBIC);
        for (int i = 0; i < controller.getPileSize(); i++) {
            BufferedImage filtered = scloperator.filter(getFlowerImg(), null);
            int displacementx = (int) (i * filtered.getWidth() * 0.2f);
            int displacementy = this.getHeight() / 2 - filtered.getHeight() / 2;
            g.drawImage(filtered,
                    this.getWidth() - filtered.getWidth() - displacementx,
                    displacementy + rand.nextInt(displacementy / 2) - displacementy / 4, null);
        }
    }

    /**
     * Desenha o estado do jogador no painel.
     *
     * @param g
     */
    private void drawState(Graphics g) {
        if (!this.estado.isEmpty()) {
            g.setColor(Color.WHITE);
            int tamFonte = (int) (getSize().getHeight() * 0.125);
            Font fonte = new Font(Font.MONOSPACED, Font.BOLD, tamFonte);
            g.setFont(fonte);
            FontMetrics metrics = g.getFontMetrics(fonte);
            g.drawString(this.estado, (this.getWidth() - metrics.stringWidth(this.estado)) / 2,
                    metrics.getHeight() + tamFonte - 10);
        }
    }

    /**
     * Desenha o estado da última notificação do jogador no painel.
     *
     * @param g
     */
    private void drawNotify(Graphics g) {
        if (!notificacao.isEmpty()) {
            g.setColor(Color.WHITE);
            int tamFonte = (int) (getSize().getHeight() * 0.125);
            Font fonte = new Font(Font.MONOSPACED, Font.BOLD, tamFonte);
            g.setFont(fonte);
            FontMetrics metrics = g.getFontMetrics(fonte);
            g.drawString(this.notificacao, (this.getWidth() - metrics.stringWidth(this.notificacao)) / 2,
                    this.getHeight() - tamFonte + 2);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        float scaleX = (float) this.getRootPane().getSize().getWidth() * 0.7f;
        float scaleY = (float) this.getRootPane().getSize().getHeight();
        if (scaleX > scaleY) {
            scaleY = scaleY * 0.2f;
        } else {
            scaleY = scaleX / scaleY * scaleY * 0.2f;
        }
        return new Dimension((int) scaleX,
                (int) scaleY);
    }

    /**
     * Define a rotação da imagem do jogador.
     *
     * @param rotation
     */
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getRotation() {
        return rotation;
    }

    /**
     * Retorna a imagem da flor do jogador.
     *
     * @return
     */
    public BufferedImage getFlowerImg() {
        return flowerImg;
    }

    @Override
    public void notifyYouAreJunior() {
        this.estado = "Voc\u00EA \u00E9  o Junior...";
    }

    @Override
    public void notifyYouAreSenior() {
        this.estado = "Voc\u00EA \u00E9 o Senior...";
    }

    @Override
    public void notifyLoserScream() {
       this.estado = "Perdeu no Gritos";
    }

    @Override
    public void notifyWinerScream() {
       this.estado = "Ganhou no Gritos";
    }

    @Override
    public void notifyYouAreSemTitulo() {
        this.estado = "";
    }

    @Override
    public void notifyJogadorEscolhaUmaFlor() {
        this.notificacao = "Escolha Sua Flor";
        this.repaint();
    }

    @Override
    public void notifyJogadorEscolhaUmaFlorEnd() {
        this.notificacao = "";
        this.repaint();
    }

    @Override
    public void notifySimpleMessager(String mensagem) {
        this.notificacao = mensagem;
        this.repaint();
    }

    @Override
    public void repitarTela() {
        this.repaint();
    }
}
