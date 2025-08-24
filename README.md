# Plataforma de ecommerce 🔥🔥

El presente repositorio es el proyecto final del curso de Java patrocinado por el banco credicoop a través de la UTN BA (Facultad regional Buenos Aires). 

## Funcionalidades 🚀
  - los vendedores pueden crear una o varias tiendas propias
  - los vendedores pueden crear publicaciones en sus tiendas
  - los vendedores pueden indicar los medios de pago que aceptan en sus tiendas
  - los vendedores pueden personalizar los productos base ofrecidos y revenderlos a un precio mayor
  - los vendedores pueden crear publicaciones de sus productos personalizados en sus tiendas
  - los compradores pueden crear un carrito de compra donde pueden agregar items de productos personalizados de una tienda y luego pueden pagar todos los items juntos o descartar el carrito
  - los compradores pueden pagar los carritos a través de uno de los medios de pago que acepta el vendedor de dicha tienda
  - los compradores pueden ver todos los productos personalizados de las tiendas.
  - los vendedores pueden confirmar que recibieron el pago de un carrito
  - los vendedores son notificados cuando un comprador paga por un carrito
  - tanto compradores como vendedores deben autenticarse usando JWT para usar la plataforma
  - en caso de que un comprador intente crear una tienda o un producto personalizado, sera rechazado a menos que tenga el rol de vendor
  - en caso de que un vendedor intente comprar un producto personalizado de otro vendedor sera rechazado a menos que tenga el rol de comprador
  - el administrador de la plataforma es el unico rol autorizado para crear o modificar los diferentes productos base que solo podrán ver los vendedores

### TODOs 💻
    - Agregar ms de notificaciones con Twilio o similar
    - Agregar ms de gestión de Usuarios con JWT
    - Investigar que pasarela de pagos puedo usar
    - Agregar pasarela de pagos en ms compras
    - usar sonarQube para mejorar la calidad del codigo
    - Investigar como agregar Swagger en una arquitectura de microservicios
    - Agregar Documentación con Swagger
    - agregar carpeta con la documentacion de cada microservicio