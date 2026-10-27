public class Cliente {

	private String id;
	private String nombre;
	private String telefono;
	private String direccion;
	private boolean tieneLibroPrestado;
	private int multaPendiente;

	public Cliente(String id, String nombre, String telefono, String direccion) {

		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		this.tieneLibroPrestado = false;
		this.multaPendiente = 0;
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

	public int getMultaPendiente() {
		return multaPendiente;
	}

	public void setMultaPendiente(int multaPendiente) {
		this.multaPendiente = multaPendiente;
	}

	public void agregarMulta(int valor) {
		multaPendiente = multaPendiente + valor;
	}


	public void pagarMultas() {
		multaPendiente = 0;
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

		System.out.println("Multa pendiente: $" + multaPendiente + " COP");
	}
}
