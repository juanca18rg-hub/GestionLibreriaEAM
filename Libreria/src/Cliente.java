public class Cliente {

	private String id;
	private String nombre;
	private String telefono;
	private String direccion;
	private boolean tieneLibroPrestado;

	public Cliente(String id, String nombre, String telefono, String direccion) {

		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		this.tieneLibroPrestado = false;
	}

	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public boolean tieneLibroPrestado() {
		return tieneLibroPrestado;
	}

	public void setTieneLibroPrestado(boolean tieneLibroPrestado) {
		this.tieneLibroPrestado = tieneLibroPrestado;
	}

	public void mostrarInformacion() {

		System.out.println("ID: " + id);
		System.out.println("Nombre: " + nombre);
		System.out.println("Telefono: " + telefono);
		System.out.println("Direccion: " + direccion);

		if (tieneLibroPrestado) {
			System.out.println("Tiene libro prestado: Si");
		} else {
			System.out.println("Tiene libro prestado: No");
		}
	}
}