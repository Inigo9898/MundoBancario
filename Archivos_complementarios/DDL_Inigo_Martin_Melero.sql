create table clientes(
id int auto_increment,
usuario varchar(20) unique,
pass varchar(20),
nombre varchar(20),
email varchar(30),
primary key(id)
);
create table cuentas(
num_cuenta int auto_increment,
alias varchar(20),
id_cliente int,
saldo double,
primary key(num_cuenta),
foreign key(id_cliente) references clientes(id)
);
create table prestamos(
id int auto_increment,
descripcion varchar(40),
fecha date,
id_cuenta int,
importe double,
plazos int,
primary key(id),
foreign key(id_cuenta) references cuentas(num_cuenta)
);
create table amortizaciones(
id int auto_increment,
id_prestamo int,
fecha date,
importe double,
primary key(id),
foreign key (id_prestamo) references prestamos(id)
);
create table tipos_movimiento(
id int auto_increment,
tipo enum('INGRESO','PRESTAMO','PAGO','AMORTIZACION','INTERESES'),
primary key(id)
);
create table movimientos(
id int auto_increment,
id_cuenta int,
id_tipo int,
descripcion varchar(40),
fecha date,
importe double,
primary key(id),
foreign key(id_cuenta) references cuentas(num_cuenta),
foreign key (id_tipo) references tipos_movimiento(id)
);
