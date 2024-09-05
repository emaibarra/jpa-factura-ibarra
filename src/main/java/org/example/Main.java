package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("en marcha Ema");

        try {

            entityManager.getTransaction().begin();

            //Creamos la primera factura
            Factura factura1 = Factura.builder()
                    .fecha("31-10-18")
                    .total(517)
                    .numero(32)
                    .detalleFacturas(new HashSet<>())
                    .build();
            //Creamos el primera domicilio
            Domicilio domicilio = Domicilio.builder()
                    .calle("Pedro Molina")
                    .nro(4353)
                    .build();
            //Creamos el primer cliente
            Cliente cliente = Cliente.builder()
                    .apellido("Ibarra")
                    .nombre("Emanuel")
                    .dni(50228)
                    .facturas(new HashSet<>())
                    .build();
            //Creamos la relacion entre Cliente y Domicilio
            cliente.setDomicilio(domicilio);
            cliente.getFacturas().add(factura1); //relacionamos cliente con la factura
            domicilio.setCliente(cliente);  //relacionamos el domicilio con su cliente
            factura1.setCliente(cliente);   //Relacionamos factura con cliente

            //Creamos 4 articulos
            Articulo articulo1 = Articulo.builder()
                    .precio(34)
                    .cantidad(4)
                    .denominacion("Hojas A3")
                    .detalleFacturas(new HashSet<>())
                    .categorias(new HashSet<>())
                    .build();
            Articulo articulo2 = Articulo.builder()
                    .precio(250)
                    .cantidad(10)
                    .denominacion("Portaminas Pizzini")
                    .detalleFacturas(new HashSet<>())
                    .categorias(new HashSet<>())
                    .build();
            Articulo articulo3 = Articulo.builder()
                    .precio(15)
                    .cantidad(20)
                    .denominacion("Lapiceras azules")
                    .detalleFacturas(new HashSet<>())
                    .categorias(new HashSet<>())
                    .build();
            Articulo articulo4 = Articulo.builder()
                    .precio(20)
                    .cantidad(300)
                    .denominacion("Lapiceras de colores")
                    .detalleFacturas(new HashSet<>())
                    .categorias(new HashSet<>())
                    .build();

            //Creamos 3 categorias
            Categoria papeleria = Categoria.builder()
                    .denominacion("Papeleria")
                    .articulos(new HashSet<>())
                    .build();
            Categoria escolares = Categoria.builder()
                    .denominacion("Escolares")
                    .articulos(new HashSet<>())
                    .build();
            Categoria dibujo = Categoria.builder()
                    .denominacion("Dibujo")
                    .articulos(new HashSet<>())
                    .build();

            //Las relacionamos a todas las categorias con todos los articulos
            articulo1.getCategorias().add(papeleria);
            articulo1.getCategorias().add(escolares);
            articulo1.getCategorias().add(dibujo);
            articulo2.getCategorias().add(papeleria);
            articulo2.getCategorias().add(escolares);
            articulo2.getCategorias().add(dibujo);
            articulo3.getCategorias().add(papeleria);
            articulo3.getCategorias().add(escolares);
            articulo3.getCategorias().add(dibujo);
            articulo4.getCategorias().add(papeleria);
            articulo4.getCategorias().add(escolares);
            articulo4.getCategorias().add(dibujo);

            papeleria.getArticulos().add(articulo1);
            papeleria.getArticulos().add(articulo2);
            papeleria.getArticulos().add(articulo3);
            papeleria.getArticulos().add(articulo4);

            escolares.getArticulos().add(articulo1);
            escolares.getArticulos().add(articulo2);
            escolares.getArticulos().add(articulo3);
            escolares.getArticulos().add(articulo4);

            dibujo.getArticulos().add(articulo1);
            dibujo.getArticulos().add(articulo2);
            dibujo.getArticulos().add(articulo3);
            dibujo.getArticulos().add(articulo4);

            //Aca creamos los detalles de la factura y los relacionamos con sus articulos
            DetalleFactura det1 = DetalleFactura.builder()
                    .factura(factura1)
                    .articulo(articulo1)
                    .cantidad(3)
                    .subtotal(102)
                    .build();

            DetalleFactura det2 = DetalleFactura.builder()
                    .factura(factura1)
                    .articulo(articulo2)
                    .cantidad(1)
                    .subtotal(250)
                    .build();
            DetalleFactura det3 = DetalleFactura.builder()
                    .factura(factura1)
                    .articulo(articulo3)
                    .cantidad(3)
                    .subtotal(45)
                    .build();
            DetalleFactura det4 = DetalleFactura.builder()
                    .factura(factura1)
                    .articulo(articulo4)
                    .cantidad(6)
                    .subtotal(120)
                    .build();

            //Relacionamos los articulos con sus detalles de factura
            articulo1.getDetalleFacturas().add(det1);
            articulo1.getDetalleFacturas().add(det2);
            articulo1.getDetalleFacturas().add(det3);
            articulo1.getDetalleFacturas().add(det4);

            factura1.getDetalleFacturas().add(det1);
            factura1.getDetalleFacturas().add(det2);
            factura1.getDetalleFacturas().add(det3);
            factura1.getDetalleFacturas().add(det4);



            entityManager.persist(factura1);
            entityManager.persist(cliente);

            entityManager.flush();


            entityManager.getTransaction().commit();


            // Consultar y mostrar la entidad persistida
            //Persona retrievedPerson = entityManager.find(Persona.class, person.getId());
            //System.out.println("Retrieved Person: " + retrievedPerson.getName());

        }catch (Exception e){

            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
            System.out.println("No se pudo grabar la clase");}

        // Cerrar el EntityManager y el EntityManagerFactory
        entityManager.close();
        entityManagerFactory.close();
    }
}
