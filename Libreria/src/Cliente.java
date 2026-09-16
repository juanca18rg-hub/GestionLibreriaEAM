public class Cliente {
    private String id;
    private String direccion;
    private String nombre;
    private int prestamosActivos;

    public Cliente(String id, String direccion, String nombre, int tieneLibroPrestado) {
        this.id = id;
        this.direccion = direccion;
        this.nombre = nombre;
        this.prestamosActivos = tieneLibroPrestado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrestamosActivos() {
        return prestamosActivos;
    }

    public void setPrestamosActivos(int prestamosActivos) {
        this.prestamosActivos = prestamosActivos;
    }
    public void mostrarInformacion() {

        System.out.println("Documento: " + id);
        System.out.println("Nombre: " + nombre);
        System.out.println("Direccion: " + direccion);
        System.out.println("Prestamos activos: " + prestamosActivos);
    }
}
