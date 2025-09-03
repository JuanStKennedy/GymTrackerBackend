package model;

public class usuario {
    private int idUsuario = 0;
    private String nUsuario;
    private String eUsuario;

    public usuario(String n, String e) {
        this.nUsuario = n;
        this.eUsuario = e;
    }public usuario(int idUsuario, String n, String e) {
        this.idUsuario= idUsuario;
        this.nUsuario = n;
        this.eUsuario = e;
    }

    public int getID() {
        return idUsuario;
    }
    public String getNombre() {
        return nUsuario;
    }
    public String getEmail() {
        return eUsuario;
    }
}
