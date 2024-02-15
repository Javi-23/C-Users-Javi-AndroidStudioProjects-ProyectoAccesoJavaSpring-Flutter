# Proyecto De Acceso 2º Trimestre

## Creación de Entidades Y Repositorios JPA

1. User

 
La clase User representa la entidad de usuario en tu aplicación de red social. Aquí hay una descripción detallada:

Anotaciones de Clase:

@Data: Proyecto Lombok que genera automáticamente los métodos toString(), equals(), hashCode(), y otros métodos estándar.
@NoArgsConstructor: Genera un constructor sin argumentos para la clase.
@Entity: Indica que la clase es una entidad de base de datos.
@Table(name = "T_TWT_USERS"): Especifica el nombre de la tabla en la base de datos.
Atributos de Clase:

id: Identificador único generado automáticamente.
username: Nombre de usuario único.
email: Correo electrónico del usuario.
password: Contraseña del usuario.
description: Descripción del usuario.
creationDate: Fecha de creación del usuario.
posts: Relación uno a muchos con las publicaciones del usuario. Se usa la anotación @OneToMany para mapear la relación.
role: Rol del usuario (puede ser ADMIN, USER, etc.).
Constructores:

Constructor sin argumentos generado por Lombok.
Constructor con argumentos para inicializar las propiedades importantes del usuario.
Métodos de la interfaz UserDetails (Spring Security):

Métodos implementados para la integración con Spring Security. Estos métodos informan sobre el estado de la cuenta del usuario.
Métodos Adicionales:

getAuthorities(): Retorna una colección de roles asignados al usuario para la autorización.
isAccountNonExpired(), isAccountNonLocked(), isCredentialsNonExpired(), isEnabled(): Métodos de Spring Security que informan sobre el estado de la cuenta del usuario.
Relación con Posts:

Hay una relación @OneToMany entre un usuario y sus publicaciones (posts). Se utiliza JsonBackReference para evitar la recursión infinita en la serialización JSON.

2. Posts
   
La clase Posts representa la entidad de publicaciones en tu aplicación de red social. Aquí hay una descripción detallada:

Anotaciones de Clase:

@Data: Proyecto Lombok que genera automáticamente los métodos toString(), equals(), hashCode(), y otros métodos estándar.
@AllArgsConstructor y @NoArgsConstructor: Generan constructores con y sin argumentos respectivamente.
@Entity: Indica que la clase es una entidad de base de datos.
@Table(name = "T_TWT_POSTS"): Especifica el nombre de la tabla en la base de datos.
Atributos de Clase:

id: Identificador único generado automáticamente.
authorId: Usuario que ha realizado la publicación. Relación muchos a uno con la entidad User.
text: Texto de la publicación.
image: Imagen de la publicación (almacenada como un array de bytes en la base de datos).
creationDate: Fecha de creación de la publicación.
editDate: Fecha de edición de la publicación.
comments: Lista de comentarios asociados a la publicación.
Constructores:

Constructor con y sin argumentos generados por Lombok.
Métodos Adicionales:

No parece tener métodos adicionales específicos. Sin embargo, la clase tiene automáticamente los métodos generados por Lombok.
Relación con User y Comments:

Existe una relación @ManyToOne con la entidad User para representar al autor de la publicación (authorId).
Hay una relación @OneToMany con la entidad Comments para representar los comentarios asociados a la publicación.
Formato de Fechas:

Se utiliza la anotación @JsonFormat para especificar el formato y la zona horaria de las fechas en la serialización JSON.

3. UserToFollow

La clase UserToFollow es una entidad que representa la relación de seguir a otro usuario en tu aplicación de red social. Aquí tienes una descripción detallada:

Anotaciones de Clase:

@Data: Proyecto Lombok que genera automáticamente los métodos toString(), equals(), hashCode(), y otros métodos estándar.
@NoArgsConstructor y @AllArgsConstructor: Generan constructores sin argumentos y con todos los argumentos, respectivamente.
@Builder: Patrón de diseño de creación que permite construir objetos utilizando un constructor más fluido y legible.
@Entity: Indica que la clase es una entidad de base de datos.
Atributos de Clase:

id: Identificador único generado automáticamente.
follower: Usuario que está siguiendo a otro usuario. Relación muchos a uno con la entidad User.
followed: Usuario que está siendo seguido. Relación muchos a uno con la entidad User.
Constructores:

Constructor sin argumentos y constructor con todos los argumentos generados por Lombok.
Además, el patrón @Builder permite la creación de instancias de manera más clara y concisa.
Relaciones con User:

Hay dos relaciones @ManyToOne con la entidad User para representar al seguidor (follower) y al seguido (followed).
Uso del Patrón Builder:

Se utiliza el patrón @Builder que permite la construcción de objetos con una sintaxis más clara y facilita la creación de instancias.

4. Comments

La clase Comments es una entidad que representa los comentarios realizados en publicaciones en tu aplicación de red social. Aquí tienes una descripción detallada:

Anotaciones de Clase:

@Data: Proyecto Lombok que genera automáticamente los métodos toString(), equals(), hashCode(), y otros métodos estándar.
@NoArgsConstructor y @AllArgsConstructor: Generan constructores sin argumentos y con todos los argumentos, respectivamente.
@Entity: Indica que la clase es una entidad de base de datos.
@Table(name = "T_TWT_COMMENTS"): Especifica el nombre de la tabla en la base de datos.
Atributos de Clase:

id: Identificador único generado automáticamente.
postId: Publicación a la que pertenece el comentario. Relación muchos a uno con la entidad Posts.
authorId: Usuario que realiza el comentario. Relación muchos a uno con la entidad User.
comment: Texto del comentario. Se valida con @NotBlank para asegurarse de que no esté vacío.
image: Imagen asociada al comentario, almacenada como un arreglo de bytes.
creationDate: Fecha de creación del comentario, generada automáticamente.
Constructores:

Constructor sin argumentos y constructor con todos los argumentos generados por Lombok.
Relaciones con Posts y User:

Hay dos relaciones @ManyToOne, una con Posts para representar a qué publicación pertenece el comentario (postId) y otra con User para representar al autor del comentario (authorId).
Uso de Anotaciones Jackson:

@JsonBackReference: Indica que esta referencia inversa (en este caso, de Comments a Posts) no debe ser serializada en el JSON para evitar problemas de referencia cíclica.
Columnas de Base de Datos:

Se especifica el nombre de la tabla (T_TWT_COMMENTS) en la base de datos.
Se usa @Column, @Lob, y @CreationTimestamp para configurar las columnas de la tabla.
En general, la clase Comments está bien definida y representa de manera adecuada la información necesaria para almacenar comentarios en tu aplicación.

6. Creación de repositorios

Para cada entidad definida en el modelo de datos de la aplicación de red social, se implementarán repositorios JPA dedicados. Estos repositorios proporcionarán una interfaz para interactuar con la base de datos y realizar operaciones CRUD (Create, Read, Update, Delete) relacionadas con las entidades correspondientes. Cada repositorio JPA contendrá métodos específicos que encapsulan consultas personalizadas y operaciones de base de datos necesarias para manipular la información asociada a cada entidad. Además, los repositorios seguirán las mejores prácticas de diseño y programación en el contexto de Spring Data JPA, asegurando una correcta integración con el modelo de datos y facilitando la expansión y mantenimiento del sistema.

## Creación de DTO

Se implementarán DTOs específicos para cada entidad en el modelo de datos de la aplicación de red social. Estos DTOs se diseñarán con el propósito de filtrar y presentar información específica, evitando la exposición innecesaria de datos sensibles. Los DTOs actuarán como objetos intermedios entre las capas de servicio y la capa de presentación, permitiendo una mejor gestión de la información transmitida a través de la API REST. Cada DTO contendrá solo los campos necesarios para cumplir con los requisitos de la solicitud y se estructurará de manera eficiente para mejorar el rendimiento de las operaciones de consulta y presentación de datos en la interfaz de usuario. Este enfoque contribuirá a la seguridad y eficacia general del sistema al garantizar la transmisión de información mínima y relevante.

## Creación de servicios

1. UserServiceI

La interfaz UserServiceI define métodos para interactuar con la capa de servicios relacionados con usuarios en la aplicación de red social. Proporciona operaciones para recuperar un usuario por nombre de usuario, actualizar la descripción de un usuario, y obtener listas de seguidores y seguidos en forma de objetos DTO (Data Transfer Object) para facilitar la transmisión de datos entre las capas del sistema. Estos métodos respaldan funcionalidades clave de la red social, como la gestión de perfiles y la obtención de información sobre relaciones de seguimiento.

2. PostsServiceI

La interfaz PostServiceI define métodos para gestionar publicaciones en la aplicación de red social. Proporciona operaciones para crear una nueva publicación, actualizar una publicación existente y eliminar una publicación. Además, estos métodos admiten la inclusión de texto e imágenes asociadas a las publicaciones. La interfaz está diseñada para interactuar con la capa de servicios y facilita la gestión de contenido en el contexto de la red social.

3. UserToFollowServiceI
   
 La interfaz UserToFollowI define métodos para gestionar las relaciones de seguimiento entre usuarios en la aplicación de red social. Proporciona operaciones para que un usuario siga a otro (followUser) y deje de seguir a otro usuario (unfollowUser). Esta interfaz facilita la gestión de las conexiones de seguimiento entre usuarios, permitiendo a los usuarios establecer y deshacer relaciones de seguimiento.

4. UsersAndPostsServiceI

La interfaz UserAndPostServiceI define métodos para obtener información combinada de usuarios y publicaciones en la aplicación de red social. Proporciona operaciones para obtener publicaciones de un usuario específico (getPostByUserName), obtener publicaciones de usuarios seguidos (getPostsOfFollowedUsers), y obtener todas las publicaciones en la plataforma (getAllPosts). Esta interfaz facilita la obtención de datos combinados de usuarios y publicaciones, ofreciendo diversas formas de acceder a la información en la aplicación.

5. CommentsServiceI
   
La interfaz CommentsServiceI proporciona un método para agregar comentarios a una publicación específica en la aplicación de red social. El método addComment toma como parámetros el ID de la publicación a la que se añadirá el comentario, el ID del autor del comentario, el texto del comentario y una imagen (opcional). La interfaz facilita la interacción con la lógica de negocio relacionada con la gestión de comentarios, permitiendo la adición de comentarios a las publicaciones en la plataforma.

## Controladores

1. UserController
   
El controlador UserController gestiona las operaciones relacionadas con los usuarios en la aplicación de red social. Proporciona endpoints para obtener información sobre usuarios, editar la descripción de un usuario, y obtener la lista de seguidores y usuarios seguidos por un usuario específico. Los métodos del controlador interactúan con la capa de servicio para realizar operaciones como recuperar información de usuarios, actualizar descripciones y obtener listas de seguidores. Está anotado con Swagger para documentación API y manejo de cors.

2. PostController
   
El controlador PostsController gestiona operaciones relacionadas con publicaciones en la aplicación de red social. Ofrece endpoints para crear, actualizar y eliminar publicaciones. Está anotado con Swagger para documentación API y manejo de cors. Utiliza la interfaz PostServiceI para interactuar con la lógica del servicio que realiza operaciones como crear, actualizar y eliminar publicaciones. Además, incluye requisitos de seguridad para la autenticación del token Bearer.

3. UserToFolloweController
   
El controlador UserToFollowController maneja operaciones relacionadas con seguir y dejar de seguir a otros usuarios en la aplicación de red social. Proporciona endpoints para seguir y dejar de seguir a un usuario. Está anotado con Swagger para documentación API. Utiliza la interfaz UserToFollowI para interactuar con la lógica del servicio que realiza estas operaciones. Además, se encarga de obtener la información de autenticación del usuario.

4. CommentsController

El controlador CommentsController gestiona operaciones relacionadas con los comentarios en las publicaciones de la red social. Proporciona un endpoint para agregar un comentario a una publicación específica. Está anotado con Swagger para la documentación de la API. Utiliza la interfaz CommentsServiceI para interactuar con la lógica del servicio que realiza estas operaciones. Además, obtiene la información de autenticación del usuario a través de Spring Security.

5. UserAndPostsController

El controlador UserAndPostController maneja operaciones relacionadas con la obtención de publicaciones de usuarios en la red social. Proporciona endpoints para obtener las publicaciones de un usuario específico, las publicaciones de usuarios seguidos y todas las publicaciones. Está anotado con Swagger para la documentación de la API. Utiliza la interfaz UserAndPostServiceI para interactuar con la lógica del servicio que realiza estas operaciones. Además, obtiene la información de autenticación del usuario a través de Spring Security.

## Implementación de Spring Security

1. AuthResponse (com.vedruna.security.model.AuthResponse):

Es un modelo que representa la respuesta de autenticación.
Contiene un campo token que almacena el token de autenticación.

2. LoginRequest:

Es un modelo utilizado para solicitudes de inicio de sesión.
Contiene campos username y password para la autenticación.

3. RegisterRequest:

Es un modelo utilizado para solicitudes de registro de usuario.
Contiene campos como username, email, description y password para registrar nuevos usuarios.

4. AuthService:

Es un servicio que maneja la autenticación y el registro de usuarios.
Utiliza el UserRepository para interactuar con la base de datos.
Utiliza JWTService para generar tokens JWT después de la autenticación o el registro.
Utiliza PasswordEncoder para cifrar las contraseñas antes de almacenarlas en la base de datos.
Utiliza AuthenticationManager para autenticar a los usuarios al recibir sus credenciales.

5. JWTService:

Es un servicio que maneja la creación y verificación de tokens JWT.
Utiliza una clave secreta almacenada en una propiedad de aplicación.
Proporciona métodos para generar tokens a partir de la información del usuario.
Tiene funciones para obtener información del token, como el nombre de usuario, y verificar la validez del token.

6. ApplicationConfig:
   
Es una clase de configuración de Spring.
Configura el AuthenticationManager, AuthenticationProvider, UserDetailsService y PasswordEncoder.
Utiliza el UserRepository para recuperar información del usuario de la base de datos.
Define el método authenticationManager para proporcionar el AuthenticationManager que se utilizará en la autenticación.
Define el método authenticationProvider para configurar un DaoAuthenticationProvider, que utiliza el userDetailService y passwordEncoder.
Define el método userDetailService para implementar la interfaz UserDetailsService, que carga detalles de usuario desde el UserRepository.
Define el método passwordEncoder para proporcionar un BCryptPasswordEncoder, que se utiliza para codificar y verificar contraseñas de usuarios.

7. SecurityConfig:
   
Es una clase de configuración de Spring Security.
Habilita la seguridad web (@EnableWebSecurity).
Deshabilita la protección contra ataques CSRF.
Configura reglas de autorización para las solicitudes HTTP.
Permite el acceso sin autenticación a ciertos endpoints (por ejemplo, /auth/**, /api/v1/users/public/**, /swagger-ui/**, /v3/**).
Exige autenticación para cualquier otra solicitud.
Configura la política de creación de sesiones como STATELESS (sin sesiones).
Utiliza un proveedor de autenticación (authProvider) configurado previamente.
Agrega un filtro de autenticación basado en JWT (jwtAuthenticationFilter) antes del filtro estándar de autenticación por nombre de usuario y contraseña.

## Funciones adicionales

1. Uso de Swagger para Documentar Endpoints:

Se ha utilizado Swagger para documentar los endpoints de la aplicación.
La anotación @io.swagger.v3.oas.annotations.tags.Tag se usa para etiquetar los controladores y proporcionar información descriptiva sobre ellos.
En el controlador principal (UserController y PostsController), se utilizan anotaciones como @Operation para proporcionar descripciones detalladas de las operaciones del endpoint.
Swagger es accesible a través de rutas como /swagger-ui/** y /v3/** para visualizar y probar la documentación de la API.

2. Uso de ControllerAdvice:

Se ha utilizado un ControllerAdvice para manejar de manera centralizada las excepciones arrojadas por los controladores.
La clase GlobalExceptionHandler contiene métodos anotados con @ExceptionHandler para manejar tipos específicos de excepciones.
Esto proporciona una gestión centralizada de excepciones, permitiendo una respuesta coherente en caso de errores en diferentes partes de la aplicación.
También permite personalizar las respuestas de error según el tipo de excepción.

## Consumir API mediante FLUTTER

1. Interfaz Gráfica:

La aplicación utiliza el framework Flutter y sigue una estructura de widget.
Se define un widget MyApp como punto de entrada que establece el tema y crea un Scaffold.
La pantalla principal, UserFetchScreen, es un StatefulWidget que gestiona la lógica interna.

2. Consulta de Datos:

Se utiliza el paquete http para realizar solicitudes HTTP al backend.
La función fetchUserData se encarga de realizar una solicitud GET al endpoint correspondiente en el backend para obtener información del usuario.
La respuesta se procesa, y los datos del usuario se almacenan en la variable userData.

3. Interfaz de Usuario:

La interfaz de usuario incluye un campo de entrada (TextField) para el nombre de usuario y un botón para iniciar la consulta.
Si se encuentran datos del usuario, se muestran en una tabla (DataTable).
Si no se encuentran datos, se muestra un mensaje indicando que el perfil no ha sido identificado.

4. Ejecución:

La aplicación se ejecuta mediante el método main, que inicia MyApp.

5. Reactividad:

La interfaz se actualiza de manera reactiva cuando se obtienen datos del usuario.

![image](https://github.com/Javi-23/ProyectoAccesoJavaSpring-Flutter/assets/102307312/b7e3b797-fb38-4e2a-a300-3f568a19f610)


   

