# Hospital

Este proyecto está hecho para la gestión de citas de un hospital que permite a los administradores y personal médico programar, gestionar y realizar un seguimiento de las citas de los pacientes. 

## Funcionalidades principales
- Registro del personal y de los pacientes con detalles personales.
- Programación de citas para pacientes con fecha, hora y profesional asignado.
- Visualización del historial de citas de los pacientes, médicos y enfermeros.
- Administración de médicos y sus horarios de disponibilidad.
- Baja del personal y pacientes.

### Otras funcionalidades
- Visualización de los médicos más ocupados (médicos con más citas).
  

## Dependencias
Para este proyecto, he usado las siguientes dependencias: 
~~~
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-validation'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.liquibase:liquibase-core:4.24.0'
	implementation 'io.springfox:springfox-boot-starter:3.0.0'
	compileOnly 'org.projectlombok:lombok'
	runtimeOnly 'mysql:mysql-connector-java'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'

}
~~~

## Documentación
La API de este proyecto está documentada utilizando Swagger, lo que facilita la comprensión de los endpoints, métodos admitidos, parámetros, y las respuestas esperadas.

### Acceso a la documentación 
Para ello es necesario correr el proyecto.
Puedes acceder a la documentación en el siguiente enlace: [Swagger](http://localhost:8080/swagger-ui/index.html)


## Excepciones
Todas las excepciones en este proyecto han sido personalizadas y creadas específicamente para distintos casos de uso. Se han implementado excepciones personalizadas para abordar situaciones específicas y facilitar un mejor manejo de errores.


### Ejemplo de excepciones personalizadas
- Excepciones **NotFound** para validar cuando un objeto no exista. Ej: DoctorNotFoundException.
- Excepciones **AlreadyExists** para validar si ese objeto existe. Ej: PatientAlreadyExistsException.
- Excepciones **NotAvailable** para validar si, en el caso de mi excepción, la fecha no está disponible. Ej: DateNotAvailableException.


### Manejador Global de Excepciones
He implementado un manejador global de excepciones para capturar y manejar de manera centralizada todas las excepciones que puedan surgir durante la ejecución del proyecto. Esto asegura un manejo consistente de errores en todo el sistema.


### Ventajas de las Excepciones Personalizadas y el Manejador Global
- Facilita el entendimiento y la depuración de errores al contar con excepciones específicas para cada caso.
- Aumenta la consistencia en el manejo de errores en todo el proyecto.
- Mejora la experiencia del usuario al presentar mensajes de error claros y detallados.
