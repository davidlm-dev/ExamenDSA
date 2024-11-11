# Examen de David Lamas Martínez - Control 1

## Descripción

El examen está basado en la implementación de un servicio REST con las siguientes clases:

- **Usuario**: Gestión de los usuarios del sistema.
- **CampusMap**: Representación del mapa del campus.
- **PointOfInterest**: Definición de los puntos de interés en el campus.
- **UserLog**: Registro de las acciones realizadas por los usuarios.
- **Element Type**: Tipo de elemento, contenido en un archivo separado.

## Parte 1: Implementación de Clases

Las clases mencionadas anteriormente han sido implementadas y se han completado las siguientes tareas:

1. Clase **Usuario**: Completada.
2. Clase **CampusMap**: Completada.
3. Clase **PointOfInterest**: Completada.
4. Clase **UserLog**: Completada.

**Observaciones:** Se han realizado pruebas unitarias en las clases y funcionan correctamente. No obstante, es posible que haya algún problema no detectado en alguna de las funciones o en algún otro aspecto del código.

## Parte 2: Implementación del Servicio REST

El servicio REST implementado responde correctamente a la mitad de las peticiones. Los siguientes endpoints funcionan correctamente:

- `GET /users`: Obtiene todos los usuarios.
- `POST /users`: Crea un nuevo usuario.
- `GET /users/id`: Obtiene los datos de un usuario específico por su ID.

Sin embargo, los siguientes endpoints no funcionan como se esperaba:

- El resto de las peticiones no están funcionando correctamente o solo funcionan parcialmente.

Un caso específico a resaltar es el `POST /users/points`, que, según los logs de **Log4j**, indica que el punto de interés se crea correctamente. Sin embargo, al realizar las peticiones `GET`, no se muestra el punto creado. No se sabe si esto se debe a un problema en la creación de los puntos o si se trata de un error en la implementación de la API.

## Dependencias

Las dependencias del proyecto han sido gestionadas utilizando **Maven**.

## Observaciones

- No estoy seguro con que versión de JAVA estoy utilizando asi que cabe tenerlo en cuenta (Creo que la 23).
- El ID de usuario es un string ya que podramos utilizar por ejemplo su DNI como identificador único.
- No se ha utilizado `System.out.println` en ninguna parte del código, salvo en el método principal (`main`), donde se ha utilizado para acceder a la web y entrar en el endpoint `/swagger/`.
- El código ha sido subido al repositorio de GitHub (con Git) para su revisión.
- El commit en local ha sido realizado a la 13.05 y el push a la 13.30



