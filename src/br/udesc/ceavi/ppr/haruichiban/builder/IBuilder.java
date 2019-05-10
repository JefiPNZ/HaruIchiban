package br.udesc.ceavi.ppr.haruichiban.builder;

/**
 *
 * @author 03875276213
 */
public interface IBuilder<T> {

    public abstract T getProduto();

    public abstract void reset();

    public abstract void constroiPartes();
}
