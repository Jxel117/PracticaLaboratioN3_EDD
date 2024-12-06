package models;

public class Familia {
    private Integer id;
    private String canton;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private int integrantes;
    private boolean tieneGenerador;

    public Familia() {}

    public Familia(Integer id, String canton, String apellidoPaterno, String apellidoMaterno, int integrantes, boolean tieneGenerador) {
        this.id = id;
        this.canton = canton;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.integrantes = integrantes;
        this.tieneGenerador = tieneGenerador;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApellidoPaterno() {
        return this.apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public int getIntegrantes() {
        return this.integrantes;
    }

    public void setIntegrantes(int integrantes) {
        this.integrantes = integrantes;
    }

    public String getCanton() {
        return this.canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getApellidoMaterno() {
        return this.apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public boolean getTieneGenerador() {
        return this.tieneGenerador;
    }

    public void setTieneGenerador(boolean tieneGenerador) {
        this.tieneGenerador = tieneGenerador;
    }

    @Override
    public String toString() {
        return "Familia {id=" + id + ", canton=" + canton + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno="
                + apellidoMaterno + ", integrantes=" + integrantes + ", tieneGenerador=" + tieneGenerador + "}";
    }
}