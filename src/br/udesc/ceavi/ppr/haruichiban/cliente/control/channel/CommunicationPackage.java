package br.udesc.ceavi.ppr.haruichiban.cliente.control.channel;

import com.google.gson.Gson;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gustavo C
 */
public class CommunicationPackage {

    private ModelGet get;
    private ModelPost post;
    private String parametro;

    public CommunicationPackage() {
    }

    public void addGet(ModelGet get, String parametro) {
        this.get = get;
        this.post = null;
        this.parametro = parametro;
    }

    public void addPost(ModelPost post, String parametro) {
        this.get = null;
        this.post = post;
        this.parametro = parametro;
    }

    public String getParametro() {
        return parametro;
    }

    public ModelPost getModelPost() {
        return post;
    }

    public ModelGet getModelGet() {
        return get;
    }

    public boolean isGet() {
        return get != null;
    }

    public boolean isPost() {
        return post != null;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public String toString() {
        String toString = "CommunicationPackage{";
        if (isGet()) {
            toString += "Get=" + get;
        } else {
            toString += "Post=" + post;
        }
        toString += ",parametro=" + parametro;
        toString += "}";

        return toString;
    }

    public void close() {
        try {
            finalize();
        } catch (Throwable ex) {
            Logger.getLogger(CommunicationPackage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
