

import java.awt.Point;

/**
 *
 * @author Gustavo C Santos
 * @since 15/05/2019
 *
 */
public class Posicao {

    private Point posicao;

    public static enum Direcao {
        NORTE, SUL, LEST, OESTE;
    };
    private Direcao direcao;

    public Posicao(Point posicao) {
        this.posicao = posicao;
    }

    public Posicao(Direcao direcao) {
        this.direcao = direcao;
    }

    public Point getPosicao() {
        return posicao;
    }

    public Direcao getDirecao() {
        return direcao;
    }

    public boolean hasPoint() {
        return posicao != null;
    }

    public boolean hasDirecao() {
        return direcao != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Posicao other = (Posicao) obj;
        if (this.hasPoint() && other.hasPoint()) {
            return (this.posicao.x == other.getPosicao().x && this.posicao.y == other.getPosicao().y);
        } else if (this.hasDirecao() && other.hasDirecao()) {
            return (this.direcao == other.getDirecao());
        }
        return false;
    }

}
