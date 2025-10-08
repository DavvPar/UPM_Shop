# UPM_Shop
Project POO group 8
El cliente solicita un módulo de tickets que permita crear y gestionar productos, aplicar
descuentos por categoría y emitir factura de un ticket ordenada alfabéticamente por nombre
de producto.
Los productos están definidos por un identificador en el sistema (dos productos no son iguales
si tienen diferente ID), numero positivo, que los define dentro del sistema, un nombre no vacío
que y de menos de 100 caracteres, una categoría (MERCH, PAPELERIA, ROPA, LIBRO,
ELECTRONICA) y un precio que es un número superior a 0 sin límite superior. No podrá haber
dos productos con el mismo id en el sistema. No existirán mas de 200 productos diferentes en
el sistema para esta versión.
Cuando se genera un ticket se aplican descuentos de manera automática cuando hay más de un
producto de la misma categoría, los descuentos son MERCH(0%), PAPELERIA(5%), ROPA(7%),
LIBRO(10%), ELECTRONICA(3%).
En la primera versión de la aplicación se arranca con una lista de productos vacía Ticket vacío y
se insertan los productos de manera progresiva. Se permite en cualquier momento reiniciar el
proceso e iniciar un nuevo ticket. Al imprimir el ticket, se imprime por pantalla y se inicia un
nuevo ticket hasta que se cierra la aplicación. Al incorporar un producto al ticket, modificarlo o
borrarlo, se debe imprimir el importe provisional del ticket, aplicando los descuentos actuales.
El borrado de un producto borrara todas las apariciones del ticket. Para esta versión se asume
que los tickets no tendrán más de 100 productos.
Los comandos para implementar son los siguientes:

• prod add <id> "<nombre>" <categoria> <precio> (agrega un producto con nuevo id)
• prod list (lista productos actuales)
• prod update <id> campo valor (campos: nombre|categoria|precio)
• prod remove <id>
• ticket new (resetea ticket en curso)
• ticket add <prodId> <cantidad> (agrega al ticket la cantidad de ese producto)
• ticket remove <prodId> (elimina todas las apariciones del producto, revisa si existe el id )
• ticket print (imprime factura)
• help (lista los comandos)
• echo “<texto>” (imprime el texto en el valor texto)
• exit