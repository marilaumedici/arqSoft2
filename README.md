# Proyecto Libre Mercado

Libre Mercado es un servicio para gestión de productos al estilo Mercado Libre.
Este proyecto cuenta con una gran variedad de endpoints para que un vendedor pueda publicar sus productos y que diferentes usuarios puedan comprarlos desde la comodidad de sus casas.

## Requerimientos

- Java 21
- Maven 3.3.3 o superior
- MongoAtlas 8.0.8

## Instalación

### Instalar Java en Windows

Se puede descargar la última versión de Java desde el sitio oficial de Oracle:

- [Descargar Java - Oracle](https://www.oracle.com/java/technologies/javase-downloads.html)

Instálela y recuerde el path dónde se instaló el JDK.
Luego configure las siguientes variables de entorno:

- **JAVA_HOME**
  - Crear una nueva variable de sistema:
    - **Nombre**: `JAVA_HOME`
    - **Valor**: la ruta donde instaló el JDK

- **Path**
  - Editar la variable existente `Path`.
  - Agregar al final:
    ```
    ;%JAVA_HOME%\bin
    ```

### Instalar Maven en Windows

Se puede descargar Maven desde el repositorio oficial de Apache:

- [Descargar Maven 3.3.3 - Apache Archive](https://archive.apache.org/dist/maven/maven-3/3.3.3/binaries/)

Descargue el archivo apache-maven-3.3.3-bin.zip y extraiga su contenido en un path definitivo.
Luego configure las siguientes variables de entorno:

- **MAVEN_HOME**
  - Crear nueva variable de sistema:
    - **Nombre**: `MAVEN_HOME`
    - **Valor**: la ruta donde copió Maven

- **Path**
  - Editar la variable existente `Path`.
  - Agregar al final:
    ```
    ;%MAVEN_HOME%\bin
    ```

### Instalación del proyecto Libre Mercado

```bash
git clone https://github.com/marilaumedici/arqSoft2.git
```


### Configurar MongoAtlas

Ve a [MongoDB Atlas](https://www.mongodb.com/cloud/atlas) y crea una cuenta si no tienes una, o inicia sesión si ya tienes una.

Crear un nuevo cluster:
  - Una vez dentro, selecciona el botón **"Create a New Cluster"**.
  - Elige tu proveedor de nube preferido (AWS, Google Cloud o Azure) y la región donde se desplegará tu cluster.
  - Selecciona el **tipo de cluster**.
  - Haz clic en **Create Cluster**.

Crear un usuario en MongoDB Atlas:
- Ve a la sección **Database Access**.
- Haz clic en **Add New Database User**.
- Crea un usuario con nombre y contraseña.

Obtener la URI de conexión:
- En el panel de MongoDB Atlas, ve a **Clusters** y haz clic en **Connect** para tu cluster.
- Elige **Connect your application**.
- Selecciona la versión de tu controlador de MongoDB para Java.
- Copia la **URI de conexión** y sustituye `<username>`, `<password>` y `<database>` con los valores correspondientes.

Configurar el archivo `application.properties` del proyecto Libre Mercado:
- Abre el archivo `application.properties` del proyecto previamente descargado y modifica las siguientes properties:
```properties
spring.data.mongodb.uri=**URI de conexión** recientemente copiada
spring.data.mongodb.database=**Nombre de la database**
```

## Ejecución

Abrir una consola CMD en donde se encuentra el codigo del proyecto descargado y ejecutar el siguiente comando:

```bash
mvn spring-boot:run
```

El servicio estará disponible en esta url http://localhost:8080/