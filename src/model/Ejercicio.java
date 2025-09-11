package model;

public class Ejercicio {
    private int id;
    private String nombre;
    private Dificultad dificultad;
    private int grupoMuscular;

    public Ejercicio() {
    }

    // Constructor con todos los atributos
    public Ejercicio(int id, String nombre, int grupoMuscular) {
        this.id = id;
        this.nombre = nombre;
        this.grupoMuscular = grupoMuscular;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(int grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public void setDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
    }
}

