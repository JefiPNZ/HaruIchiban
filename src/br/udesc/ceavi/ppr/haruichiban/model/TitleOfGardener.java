package br.udesc.ceavi.ppr.haruichiban.model;

/**
 *
 * @author GustavoSantos
 * @since 03/05/2019
 *
 */
public interface TitleOfGardener {

    public void throwFlower(ModelPlayer play) throws Exception;

    public void moveNenufares(ModelPlayer play) throws Exception;

    public void chooseNewDarkNenufares(ModelPlayer play) throws Exception;

    public void becomeUntitledGardener(ModelPlayer play) throws Exception;

    public void becomeJuniorGardener(ModelPlayer play) throws Exception;

    public void becomeSeniorGardener(ModelPlayer play) throws Exception;
}
