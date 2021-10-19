/**
 * Author:  Dave
 * Created: 19/10/2021
 */

DROP TABLE  IF EXISTS t_productos;
CREATE TABLE t_productos (
	cod_producto VARCHAR(8) PRIMARY KEY,
	nombre_producto VARCHAR(30) NOT NULL,
	descripcion_producto VARCHAR(60), 
	precio_producto FLOAT
);
INSERT INTO t_productos (
cod_producto,nombre_producto,descripcion_producto,precio_producto) 
VALUES ('AAAAA001','PILA DOBLE AA','BATERIAS',9000);
INSERT INTO t_productos (
cod_producto,nombre_producto,descripcion_producto,precio_producto) 
VALUES ('AAAAA002','PILA TRRIPLE AAAA','BATERIAS',7000);
INSERT INTO t_productos (
cod_producto,nombre_producto,descripcion_producto,precio_producto) 
VALUES ('AAAAA003','PILA GRUESA 9V','BATERIAS',12000);
INSERT INTO t_productos (
cod_producto,nombre_producto,descripcion_producto,precio_producto) 
VALUES ('BBBBB001','UPS 1KVA','BATERIAS',160000);
INSERT INTO t_productos (
cod_producto,nombre_producto,descripcion_producto,precio_producto) 
VALUES ('BBBBB002','UPS 5KVA','BATERIAS',2500000);


DROP TABLE  IF EXISTS t_vendedores ;

CREATE TABLE t_vendedores (
    cod_vendedor character varying(50) COLLATE pg_catalog."default" NOT NULL,
    nombre_vendedor character varying(50) COLLATE pg_catalog."default" NOT NULL,
    rol_vendedor character varying(20) COLLATE pg_catalog."default",
    password_vendedor character varying(20) COLLATE pg_catalog."default",
    CONSTRAINT t_vendedores_pkey PRIMARY KEY (cod_vendedor)
);
INSERT INTO "public"."t_vendedores" ("cod_vendedor", "nombre_vendedor", "rol_vendedor", "password_vendedor") VALUES
('VEND01', 'JOSÉ MARÍA CORDOBA CORONE', 'VENDEDOR', '1234');
INSERT INTO "public"."t_vendedores" ("cod_vendedor", "nombre_vendedor", "rol_vendedor", "password_vendedor") VALUES
('VEND02', 'MANUEL MEDINA MESA', 'ADMIN', '1234');
INSERT INTO "public"."t_vendedores" ("cod_vendedor", "nombre_vendedor", "rol_vendedor", "password_vendedor") VALUES
('VEND04', 'JOSÉ MARÍA MEDINA OLARTE', 'VENDEDOR', '1234');
INSERT INTO "public"."t_vendedores" ("cod_vendedor", "nombre_vendedor", "rol_vendedor", "password_vendedor") VALUES
('VEND05', 'PRUEBA', 'VENDEDOR', '1234');

DROP TABLE  IF EXISTS t_ventas;

CREATE TABLE t_ventas (
	id_venta SERIAL PRIMARY KEY,
	numero_factura VARCHAR(6) NOT NULL,
	cod_producto VARCHAR(8) NOT NULL,
	fecha_venta DATE,
	cod_vendedor VARCHAR(8),
	valor_factura Float,
	FOREIGN KEY (cod_producto) REFERENCES t_productos (cod_producto),
	FOREIGN KEY (cod_vendedor) REFERENCES t_vendedores (cod_vendedor)
);
INSERT INTO t_ventas (
numero_factura,cod_producto, fecha_venta,cod_vendedor,valor_factura) 
VALUES ('FAC001','AAAAA002','2021-08-21','VEND01',1000);
INSERT INTO t_ventas (
numero_factura,cod_producto, fecha_venta,cod_vendedor,valor_factura) 
VALUES ('FAC002','AAAAA003','2021-08-21','VEND02',2000);
INSERT INTO t_ventas (
numero_factura,cod_producto, fecha_venta,cod_vendedor,valor_factura) 
VALUES ('FAC003','BBBBB001','2021-08-21','VEND01',3000);
INSERT INTO t_ventas (
numero_factura,cod_producto, fecha_venta,cod_vendedor,valor_factura) 
VALUES ('FAC004','AAAAA002','2021-08-21','VEND01',4000);
INSERT INTO t_ventas (
numero_factura,cod_producto, fecha_venta,cod_vendedor,valor_factura) 
VALUES ('FAC005','AAAAA001','2021-08-22','VEND01',5000);
INSERT INTO t_ventas (
numero_factura,cod_producto, fecha_venta,cod_vendedor,valor_factura) 
VALUES ('FAC006','AAAAA002','2021-08-22','VEND02',6000);


DROP TABLE  IF EXISTS t_login;

CREATE TABLE t_login
(
    cod_vendedor character varying(10) COLLATE pg_catalog."default" NOT NULL,
    nombre_vendedor character varying(30) COLLATE pg_catalog."default" NOT NULL,
    rol_vendedor character varying(10) COLLATE pg_catalog."default" NOT NULL,
    pass_vendedor character varying(10) COLLATE pg_catalog."default" NOT NULL
)