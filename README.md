# GestionLibreriaEAM
Hola gente, muy buenas a todos! Guapisimos!!! aqui niño777 en un gameplay directo

---

# Biblioteca LIBROSYMAS - Taller de Dockerizacion

Sistema de gestion de biblioteca por consola (Java) con prestamos, devoluciones,
multas por retraso, reservas y reporte general. Los datos se guardan en archivos
de texto dentro de una carpeta que se monta como **volumen de Docker**.

## Funcionalidades

| Opcion | Descripcion |
| --- | --- |
| 1-4 | Registrar/mostrar libros y clientes |
| 5 | Prestar libro (plazo de **7 dias**, se guarda la fecha limite) |
| 6 | Devolver libro: calcula la multa si hay retraso y avisa quien sigue en la lista de espera |
| 7 | Mostrar prestamos activos |
| 8 | Ver las multas de un cliente (con el total pendiente) |
| 9 | Pagar las multas de un cliente |
| 10 | Registrar reserva de un libro que esta prestado |
| 11 | Consultar las reservas de un libro (en orden de solicitud) |
| 12 | Reporte general del sistema |
| 13 | Salir |

**Multas:** $1.000 COP por cada dia de retraso (constante `Multa.VALOR_POR_DIA`).
La multa se suma al total pendiente del cliente.

**Reporte general:** total de libros, total de clientes, prestamos activos, total de
prestamos historicos, cliente con mas prestamos y libro menos solicitado
(si hay empate se muestra el primero y cuantos mas empatan).

## Ejecutar con Docker

1. Construir la imagen (una sola vez, desde la carpeta donde esta el `Dockerfile`):

```
docker build -t libreria-eam .
```

2. Iniciar la aplicacion con un unico comando:

```
docker run -it --rm -v libreria-datos:/app/datos libreria-eam
```

- `-it` es necesario porque la aplicacion es interactiva (menu por consola).
- `-v libreria-datos:/app/datos` crea (la primera vez) y conecta el volumen
  `libreria-datos`, donde se guardan los archivos de datos.
- `--rm` borra el contenedor al salir; **los datos no se pierden** porque estan en el volumen.

### Probar la persistencia

1. Ejecute el comando anterior, registre un libro y un cliente y salga con la opcion 13.
2. El contenedor se elimina (`--rm`). Ejecute el mismo comando otra vez:
   el libro y el cliente siguen ahi.

Para ver donde guarda Docker el volumen: `docker volume inspect libreria-datos`.
Para borrar los datos definitivamente: `docker volume rm libreria-datos`.

## Archivos de datos

Se guardan en la carpeta `/app/datos` del contenedor (variable `DATOS_DIR`):

| Archivo | Contenido |
| --- | --- |
| `libros.txt` | Libros y su estado |
| `clientes.txt` | Clientes y su multa pendiente |
| `prestamos.txt` | Prestamos activos (con fecha limite) |
| `historial.txt` | Prestamos ya devueltos |
| `multas.txt` | Detalle de cada multa (pendiente o pagada) |
| `reservas.txt` | Listas de espera de cada libro, en orden |

## Ejecutar sin Docker (opcional)

```
javac -encoding UTF-8 -d out Libreria/src/*.java
java -cp out Libreria
```

Sin la variable `DATOS_DIR`, los archivos se guardan en una carpeta `datos/`
junto al lugar desde donde se ejecuta el programa.

