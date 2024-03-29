package br.udesc.ceavi.ppr.haruichiban.view;

import br.udesc.ceavi.ppr.haruichiban.abstractfactory.FactoryPecasInverno;
import br.udesc.ceavi.ppr.haruichiban.control.GameController;
import br.udesc.ceavi.ppr.haruichiban.utils.ColorScale;
import br.udesc.ceavi.ppr.haruichiban.utils.Images;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import br.udesc.ceavi.ppr.haruichiban.control.IPlayerController;
import br.udesc.ceavi.ppr.haruichiban.control.observers.PlayerPanelObserver;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.JOptionPane;

/**
 * Painel para representação dos dados de um jogador.
 *
 * @author Jeferson Penz
 */
public class PlayerPanel extends JPanel implements PlayerPanelObserver {

    private IPlayerController controller;
    private BufferedImage floorImg;
    private BufferedImage flowerImg;
    private BufferedImage baseImg;
    private BufferedImage clothImg;
    private BufferedImage faceImg;
    private PlayerHandTable playerHand;
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
            if (GameController.getInstance().getFactoryPecas().getClass().getSimpleName()
                    .equals(FactoryPecasInverno.class.getSimpleName())) {
                this.flowerImg = scale.convert(Images.JOGADOR_FLOR_INV);
            } else {
                this.flowerImg = scale.convert(Images.JOGADOR_FLOR_PRIM);

            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não foi possível ler os arquivos de imagem do jogo.");
        }
        this.rotation = 0;
        this.setBackground(new Color(0, 0, 0, 0));
        this.setPreferredSize(new Dimension(0, baseImg.getHeight()));
        this.setOpaque(false);
        this.initializePlayerHand();
    }

    /**
     * Realiza o carregamento da tabela de jogo.
     */
    private void initializePlayerHand() {
        this.playerHand = new PlayerHandTable(this, this.controller);
        JScrollPane pane = new JScrollPane();

        pane.setViewportView(playerHand);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        pane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pane.setBackground(new Color(0, 0, 0, 0));
        pane.setOpaque(false);
        pane.getViewport().setOpaque(false);
        pane.setViewportBorder(BorderFactory.createEmptyBorder());
        pane.getViewport().setBackground(new Color(0, 0, 0, 0));
        this.add(pane);
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
        AffineTransform rotTransform = AffineTransform.getRotateInstance(Math.toRadians(this.rotation), floorImg.getWidth() / 2, floorImg.getHeight() / 2);
        AffineTransformOp rotOperator = new AffineTransformOp(rotTransform, AffineTransformOp.TYPE_BICUBIC);
        Random rand = GameController.getInstance().getFixedRandomizer();
        for (int i = 0; i < xImg; i++) {
            AffineTransform tslTransform = AffineTransform.getTranslateInstance(0, rand.nextInt(10));
            AffineTransformOp tslOperator = new AffineTransformOp(tslTransform, AffineTransformOp.TYPE_BICUBIC);
            g.drawImage(rotOperator.filter(tslOperator.filter(floorImg, null), null), i * floorImg.getWidth(), 0, null);
        }
    }

    /**
     * Desenha o jogador no painel.
     *
     * @param g
     */
    private void drawPlayer(Graphics g) {
        AffineTransform transformation = AffineTransform.getRotateInstance(Math.toRadians(this.rotation), baseImg.getWidth() / 2, baseImg.getHeight() / 2);
        AffineTransformOp operator = new AffineTransformOp(transformation, AffineTransformOp.TYPE_BICUBIC);
        g.drawImage(operator.filter(baseImg, null), 0, 0, null);
        g.drawImage(operator.filter(clothImg, null), 0, 0, null);
        g.drawImage(operator.filter(faceImg, null), 0, 0, null);
    }

    /**
     * Desenha a pilha do jogador no painel.
     *
     * @param g
     */
    private void drawPile(Graphics g) {
        Random rand = GameController.getInstance().getFixedRandomizer();
        int displacement = this.getHeight() / 2 - getFlowerImg().getHeight() / 2;
        for (int i = 0; i < controller.getPileSize(); i++) {
            g.drawImage(getFlowerImg(),
                    this.getWidth() - getFlowerImg().getWidth() - 15 - i * (rand.nextInt(10) + 25),
                    displacement + rand.nextInt(displacement / 2) - displacement / 4,
                    null);
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
            Font fonte = new Font(Font.MONOSPACED, Font.BOLD, 14);
            g.setFont(fonte);
            FontMetrics metrics = g.getFontMetrics(fonte);
            g.drawString(this.estado, (this.getWidth() - metrics.stringWidth(this.estado)) / 2, metrics.getHeight() + 5);
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
            Font fonte = new Font(Font.MONOSPACED, Font.BOLD, 14);
            g.setFont(fonte);
            FontMetrics metrics = g.getFontMetrics(fonte);
            g.drawString(this.notificacao, (this.getWidth() - metrics.stringWidth(this.notificacao)) / 2, this.getHeight() - 15);
        }
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
        this.estado = "Você é o Junior...";
    }

    @Override
    public void notifyYouAreSenior() {
        this.estado = "Você é o Senior...";
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

}
