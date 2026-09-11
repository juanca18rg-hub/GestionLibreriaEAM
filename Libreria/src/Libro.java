public class Libro {

        private String identificador;
        private String titulo;
        private String autor;
        private String editorial;
        private int anioPublicacion;
        private String categoria;
        private String estado;

        public Libro(String identificador, String titulo, String autor,
                     String editorial, int anioPublicacion, String categoria) {

            this.identificador = identificador;
            this.titulo = titulo;
            this.autor = autor;
            this.editorial = editorial;
            this.anioPublicacion = anioPublicacion;
            this.categoria = categoria;
            this.estado = "Disponible";
        }

        public String getIdentificador() {
            return identificador;
        }

        public String getTitulo() {
            return titulo;
        }

        public String getAutor() {
            return autor;
        }

        public String getEditorial() {
            return editorial;
        }

        public int getAnioPublicacion() {
            return anioPublicacion;
        }

        public String getCategoria() {
            return categoria;
        }

        public String getEstado() {
            return estado;
        }

        public void cambiarEstado(String nuevoEstado) {
            estado = nuevoEstado;
        }

        public void mostrarInformacion() {
            System.out.println("ID: " + identificador);
            System.out.println("Titulo: " + titulo);
            System.out.println("Autor: " + autor);
            System.out.println("Editorial: " + editorial);
            System.out.println("Año de publicacion: " + anioPublicacion);
            System.out.println("Categoria: " + categoria);
            System.out.println("Estado: " + estado);
            System.out.println("-----------------------------");
        }
}

