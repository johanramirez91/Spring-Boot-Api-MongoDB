# Spring-Boot-Api-MongoDB

Vamos a crear con Spring Boot y MongoDB una aplicación que permita administrar un biblioteca publica.



Es bien sabido que las bibliotecas cuentan con mucho recurso que se puede prestar. En esta biblioteca cualquier recurso se puede prestar y retirar de la biblioteca por tiempos indeterminados, es decir, puedo prestar un libro hoy y devolverlo cuando quiera. Esta situación hace que constantemente se tenga que revisar si un recurso esta disponible o no para su préstamo.



Debemos exponer los siguientes servicios para administrar la biblioteca

- Consultar disponibilidad de un recurso indicando en un mensaje si esta disponible o no. en caso de no estar disponible presentar la fecha del préstamo actual del ultimo ejemplar.
- Prestar un recurso, se debe comprobar si esta prestado o no, indicarlo mediante un mensaje. Si se encuentra disponible debemos marcarlo como prestado y registrar la fecha del préstamo (no es necesario llevar el historia de prestamos).
- Recomendar un listado de recursos al usuario a partir del tipo de recurso, del área temática o de los dos. Los recursos están clasificados por tipo de recurso (libros, revistas, fichas, etc) pero también por área temática (ciencias, naturaleza, historia, etc).
- Devolver un recurso que se encontraba prestado, obviamente si un recurso no se encuentra en préstamo no podrá ser devuelto. Indicar el resultado con un mensaje.
- Servicios para hacer CRUD de los recursos. 

No olvide probar el desarrollo con Postman e implementa pruebas unitarias. 

Desarrolla y comparte tu repositorio git, ten en cuenta que tu solución debe administrar una base de datos mongodb en el cluster de mongoatlas.
