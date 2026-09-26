# Tp-DSD-GrupoF
Trabajo practio DSD

Para levantar el proyecto es necesario:
1 Crear una base en SQl llamada rentar
2 editar el Aplication.Propertis para agregar las credenciales necesarias para que la aplicación se logue.

utilizar postman para agregar vehiculos, clientes y reservas 

http://localhost:8080/api/vehiculos 
{
    "patente": "AB123CD",
    "marca": "Toyota",
    "modelo": "Corolla",
    "anio": 2025,
    "color": "Blanco",
    "tipo": "SEDAN",
    "precioDiario": 50000,
    "estado": "DISPONIBLE",
    "activo": true
}

http://localhost:8080/api/clientes
{
  "documento": "123456789",
    "nombre": "carlos",
    "apellido": "carlana",
    "email": "Carloscar@gmail.com",
    "telefono": "42026503",
    "fechaNacimiento": "1985-10-10T10:00:00",
    "activo": true
}

http://localhost:8080/api/reservas

{
  "clienteId": 1,
    "vehiculoId": 1,
    "fechaInicio": "2026-10-01T10:00:00",
    "fechaFin": "2026-10-09T10:00:00"
}

Para consultas GraphQL ingresar al siguiente enlace en el navegador y realizar las consultas

http://localhost:8080/graphiql

query {
    vehiculosDisponibles(
        fechaInicio: "2026-10-10T10:00:00"
        fechaFin: "2026-10-15T10:00:00"
    ) {
        patente
        marca
        modelo
        anio
        color
        tipo
        precioDiario
    }
}

query {
    vehiculosDisponibles(
        fechaInicio: "2026-10-10T10:00:00"
        fechaFin: "2026-10-15T10:00:00"
        tipo: SUV
        marca: "Toyota"
        precioMaximo: 80000
    ) {
        patente
        marca
        modelo
        precioDiario
    }
}
