# UPM_Shop
**Proyecto POO grupo 8 - UPM Shop**

El cliente solicita un módulo de tickets que permita crear y gestionar productos, aplicar
descuentos por categoría y emitir factura de un ticket, también se solicitó un sistema de usuarios, 
añadiendo cajeros que administran los tickets, y clientes que los compran.

Los **productos** pueden ser de varios tipos: básicos, eventos, personalizables y servicios.
Los básicos y personalizables pueden ser de las categorías MERCH, PAPELERIA, ROPA, LIBRO y ELECTRONICA.
Los productos personalizables permiten añadir un texto personalizado al producto, los eventos
son comidas y reuniones, con una fecha de expiracion y una cantidad de personas, y los servicios
permiten añadir una fecha de prestación del servicio y no tienen precio al añadirse, y pueden ser transportes, 
espectáculos y seguros. Todo producto tiene un precio, nombre e id (único) y hay un máximo de 200
productos en el sistema.

Los **tickets** pueden llegar a contener 100 productos y aplican descuentos de manera automática cuando hay más de un
producto de la misma categoría, los descuentos son MERCH(0%), PAPELERIA(5%), ROPA(7%),
LIBRO(10%), ELECTRONICA(3%). Los tickets pueden ser distintos dependiendo del usuario que los crea, siendo posible
hacer uno para clientes comunes y otro para clientes empresa, que permiten añadir servicios, cada servicio contratado
añadirá un 15% de descuento en los productos, además un ticket de empresa no se puede cerrar si no hay
al menos un producto y un servicio contratados.

El ticket se inicializa con una lista vacía de productos y se van añadiendo productos
progresivamente, permitiendo reiniciar el ticket en cualquier momento. Se pueden tener varios tickets abiertos
al mismo tiempo siempre que no sean con los mismos usuarios implicados. Al imprimir el ticket, se imprime por pantalla
y se cierra el ticket, no permitiendo que se añada nada más. Por último, un ticket no tendrá más de 100 productos.

Los **usuarios** en el proyecto son cajeros y clientes, los cajeros pueden crear tickets y añadir clientes a la
base de datos, mientras que los clientes pueden comprar productos añadiéndolos a sus tickets. Los clientes pueden ser
comunes o empresas. Los cajeros no pueden comprar productos con su mismo usuario y deben registrarse como cliente 
si quieren hacerlo.

Se ha implementado un **patrón de diseño** Command para gestionar los comandos que el usuario introduce por consola,
redirigiendo cada comando a las clases que lo implementan en los Controllers correspondientes.

El **sistema de persistencia** elegido es MapDB, una base de datos NoSQL basada en documentos que permite almacenar
objetos Java directamente en disco de forma sencilla y eficiente. Se utiliza MapDB para almacenar la información
de productos y usuarios, permitiendo que los datos persistan entre ejecuciones del programa. Esto se explica
en más detalle en el PDF sobre la persistencia, adjunto con la entrega del proyecto. Esto también añade una librería
externa de alto nivel, aumentando la vistosidad y la estabilidad del proyecto.

El **manejo de excepciones** se ha implementado In-Situ, con bloques try-catch en los puntos críticos del código,
asegurando que el programa pueda manejar errores de manera adecuada sin interrumpir su funcionamiento. 
Como resultado el programa es robusto y no se cierra inesperadamente ante entradas erróneas del usuario, 
fallos en la lógica del programa o superación de los límites propuestos.

Finalmente, los comandos implementados en el producto final son los siguientes:

◦ Client/Cash:

client add "name" (DNI|NIF) email cashId

client remove (DNI|NIF)

client list

cash add [id] "name" email

cash remove id

cash list

cash tickets id

◦ Ticket:

ticket new [id] cashId userId -[c|p|s] (default -p)

ticket add ticketId cashId prodId amount [--p(txt) --p(txt)]

ticket remove ticketId cashId prodId

ticket print ticketId cashId

ticket list

◦ Product:

prod add [id] "name" category price [maxPers] || "name" category

prod update id NAME|CATEGORY|PRICE value

prod addFood [<id] "name" price <expiration: yyyy-MM-dd> <max_people>

prod addMeeting [id] "name" price <expiration: yyyy-MM-dd> <max_people>

prod list

prod remove id

◦ General:

echo "text"

help

exit
