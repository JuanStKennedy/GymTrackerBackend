package model;

public class Rutina {
    private int id;
    private String objetivo;
    private String nombre;
    private int duracionSemanas;

    public Rutina (){

    }

    public Rutina(int id, String objetivo, String nombre, int duracionSemanas) {
        this.id = id;
        this.objetivo = objetivo;
        this.nombre = nombre;
        this.duracionSemanas = duracionSemanas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public int getDuracionSemanas() {
        return duracionSemanas;
    }

    public void setDuracionSemanas(int duracionSemanas) {
        this.duracionSemanas = duracionSemanas;
    }
}
