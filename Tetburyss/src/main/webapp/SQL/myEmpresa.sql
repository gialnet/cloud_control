--
-- Datos del gestor de clientes de la operadora del sistema
--


DROP TABLE customers_facturacion;
DROP TABLE LogSesion;
DROP TABLE CobrosElectronicos;
DROP TABLE UsuariosServicios;
DROP TABLE ServiciosFacturables;
DROP TABLE Servicios;
DROP TABLE PoliticaCuentas cascade;
DROP TABLE VistasAplicacion;
DROP TABLE AltaCustomersNoResueltas;
DROP TABLE DataBases_Servidor;
DROP TABLE customers_users_adviser;
DROP TABLE customers_users;
DROP TABLE customers;
DROP TABLE Servidores;
DROP TABLE customers_pais;
DROP TABLE AutoridadCA;
/


CREATE TABLE AutoridadCA
(
    id                  serial NOT NULL,
    descripcion         varchar(90),
    raizCA              bytea,
    primary key (id)
);

INSERT INTO AutoridadCA (descripcion) VALUES ('Cadena de certificados de confianza de la CA Tetbury');
/
--
-- Los distintos tipos de cleintes en función de su residencia y consideraciones
-- especiales tributarias
--
CREATE TABLE customers_pais
(
    id              serial      NOT NULL,
    descripcion     varchar(50),
    ISO_3166_1      varchar(2),
    cuenta_cliente  varchar(5),
    cuenta_ventas   varchar(5),
    primary key (id)
);


INSERT INTO customers_pais (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('ESPAÑA',     'ES','43001' ,'70001');
INSERT INTO customers_pais (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('ARGENTINA',  'AR','43002' ,'70002');
INSERT INTO customers_pais (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('BOLIVIA',    'BO','43003' ,'70003');
INSERT INTO customers_pais (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('CHILE',      'CL','43004' ,'70004');
INSERT INTO customers_pais (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('COLOMBIA',   'CO','43005' ,'70005');
INSERT INTO customers_pais (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('COSTA RICA', 'CR','43006' ,'70006');
INSERT INTO customers_pais (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('CUBA',       'CU','43007' ,'70007');
INSERT INTO customers_pais (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('REPUBLICA DOMINICANA', 'DO','43008' ,'70008');
INSERT INTO customers_pais (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('ECUADOR',    'EC','43009' ,'70009');
INSERT INTO customers_pais (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('EL SALVADOR', 'SV','43010' ,'70010');
INSERT INTO customers_pais (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('GUINEA ECUATORIAL', 'GQ','43011' ,'70011');
INSERT INTO customers_pais (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('HONDURAS',   'HN','43012' ,'70012');
INSERT INTO customers_pais (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('MEXICO',     'MX','43013' ,'70013');
INSERT INTO customers_pais (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('NICARAGUA',  'NI','43014' ,'70014');
INSERT INTO customers_pais (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('PANAMA',     'PA','43015' ,'70015');
INSERT INTO customers_pais (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('PARAGUAY',   'PY','43016' ,'70016');
INSERT INTO customers_pais (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('PERU',       'PE','43017' ,'70017');
INSERT INTO customers_pais (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('VENEZUELA',  'VE','43018' ,'70018');
/

--
-- Servidores
--

CREATE TABLE Servidores
(
    id                  serial NOT NULL,
    id_pais             integer references customers_pais(id),
    fecha               date default CURRENT_DATE,
    IP                  varchar(20),
    ServerName          varchar(50),
    RAM                 numeric(4,2),
    HD                  numeric(8,2),
    Proveedor_Cloud     varchar(50),
    NumeroDB            integer,
    primary key (id)
);
/

--
-- Tipos de cuentas
--
CREATE TABLE PoliticaCuentas
(
    id              serial      NOT NULL,
    nombre          varchar(90),
    precio          numeric(8,2),
    duracion        varchar(10), -- mensual, anual, trimestral
    servicios        json,
    primary key (id)
);
/

--
-- Tipos de cuentas
--
INSERT INTO PoliticaCuentas ( id, nombre, precio, duracion, servicios) VALUES ( 1, 'Chanquete Free', 0, 'año',
'{   "Contabilidad": "no", 
    "Impuestos": "yes",
    "Nominas": "yes",
    "NominasApp": "no",
    "Firma": "yes",
    "Burofax":  "no",
    "Almacenamiento": "no",
    "Indexacion":  "no",
    "myHD": "yes",
    "LimiteUsuarios": "1"
}'::json
);

INSERT INTO PoliticaCuentas ( id, nombre, precio, duracion, servicios) VALUES ( 2, 'Boqueron Premiun', 60, 'año',
'{   "Contabilidad": "yes", 
    "Impuestos": "yes",
    "Nominas": "yes",
    "NominasApp": "no",
    "Firma": "yes",
    "Burofax":  "no",
    "Almacenamiento": "no",
    "Indexacion":  "no",
    "myHD": "yes",
    "LimiteUsuarios": "1"
}'::json
);

INSERT INTO PoliticaCuentas ( id, nombre, precio, duracion, servicios) VALUES ( 3, 'Pargo Enterprise', 32, 'mes',
'{   "Contabilidad": "yes", 
    "Impuestos": "yes",
    "Nominas": "yes",
    "NominasApp": "no",
    "Firma": "yes",
    "Burofax":  "yes",
    "Almacenamiento": "yes",
    "Indexacion":  "yes",
    "myHD": "yes",
    "LimiteUsuarios": "no"
}'::json
);

INSERT INTO PoliticaCuentas ( id, nombre, precio, duracion, servicios) VALUES ( 4, 'Atún Adviser', 35, 'mes',
'{   "Contabilidad": "yes", 
    "Impuestos": "yes",
    "Nominas": "yes",
    "NominasApp": "yes",
    "Firma": "yes",
    "Burofax":  "no",
    "Almacenamiento": "no",
    "Indexacion":  "no",
    "myHD": "yes",
    "LimiteUsuarios": "no"
}'::json
);
/

--
-- Clientes
--

CREATE TABLE customers
(
    id                       serial      NOT NULL,
    id_customers_pais        integer references customers_pais(id),
    id_servidor              integer references Servidores(id),
    id_tipocuenta            integer references PoliticaCuentas(id),
   primary key (id)
);
/

--
-- Usuarios autorizados por el administrador de un cliente
--
CREATE TABLE customers_users
(
    id                      serial NOT NULL,
    mail                    varchar(90),
    id_customers            integer references customers(id),
    genero                  varchar(20), -- male female
    GooglePlus              text,   -- dirección de la cuenta Google + https://plus.google.com/108068397209142441065
    telefono_Twilio         varchar(25),
    rol                     varchar(50), -- Administrador, Socio, Empleado directivo, Empleado plantilla,Asesor Fiscal,Asesor Laboral, Asesor Juridico
    nif                     varchar(20),
    nombre                  varchar(60),
    direccion               varchar(90), -- Avenida Europa, 21
    objeto                  varchar(40), -- bloque A 2ºD
    poblacion               varchar(90), -- 18690 Almuñécar Granada
    movil                   varchar(10),
    saldo                   numeric(5),
    estado                  varchar(50),
    passwd                  varchar(128),
    IP                      varchar(20),
    certdatabase            varchar(30),
    databasename            varchar(30),
    passdatabase            varchar(128),
    url_wellcome            varchar(512),
    url_site                varchar(512),
    api_token               text,
    api_key                 text,
    descargo_token          varchar(1) default 'N',
    certificado             bytea,
   primary key (id)
);

create index customers_users_customers on customers_users(id_customers);
create index customers_users_amil on customers_users(mail);
create index customers_users_site on customers_users(url_site);
create index customers_databasename on customers_users(databasename);
create index customers_certdatabase on customers_users(certdatabase);
/

--
-- Vista de tipos de cuentas y los servicios asociados
--

create or replace view TiposCuentas (mail,idcliente,tipocuenta,descripcion, servicios) AS
SELECT customers_users.mail,customers_users.id_customers, customers.id_tipocuenta, PoliticaCuentas.nombre, PoliticaCuentas.servicios
    FROM customers_users, customers, PoliticaCuentas 
    WHERE customers.id_tipocuenta = PoliticaCuentas.id
    AND customers_users.id_customers = customers.id;
/

--
-- Usuarios externos autorizados por el administrador de un cliente
--
CREATE TABLE customers_users_adviser
(
    id_customers            integer references customers(id),
    telefono_Twilio         varchar(25), -- telefono de la plataforma
    mail                    varchar(90), -- donde se encuentra el certificado del ASESOR en la tabla de customers_users
    rol                     varchar(50), -- Asesor Fiscal,Asesor Laboral, Asesor Juridico
   primary key (id_customers,mail)
);
/

--
-- Bases de datos que hay en un servidor
--
CREATE TABLE DataBases_Servidor
(
    id                  serial NOT NULL,
    id_servidor         integer references Servidores(id),
    id_cliente          integer references customers(id),
    fecha               date default CURRENT_DATE,
    Estado              varchar(10) default 'libre',
    primary key (id)
);
/

--
-- Registrar las peticiones de alta
--
CREATE TABLE AltaCustomersNoResueltas
(
    id                      serial      NOT NULL,
    GooglePlus              text,   -- dirección de la cuenta Google + https://plus.google.com/108068397209142441065
    pais                    varchar(5),
    mail                    varchar(90),
    nombre                  varchar(60),
    estado                  varchar(50) default 'pendiente',
    causa                   varchar(50) default 'País no implementado',
   primary key (id)
);
/

--
-- VistasAplicacion, las distintas opcines del programa
--

CREATE TABLE VistasAplicacion
(
    id              serial      NOT NULL,
    vista           varchar(50),
    descripcion     varchar(150),
    primary key (id)
);

INSERT INTO VistasAplicacion (vista, descripcion) VALUES ('BrowseBancosMovimientos.jsp','Bancos, cuentas y movimientos bancarios');
/

-- openssl dgst -sha512 -hex certificado-gf4-cert.pem
-- 57814921bc06302660a5f56c190b77f230dcfcc558d9a7cd5ac98c4556aa23312f0f8fd1fb029ff7f4a8932bdc884bf7ea90761dbea44e46bd754f2212844730


-- Relación de Servicios

CREATE TABLE Servicios
(
    id              serial      NOT NULL,
    nombre          varchar(90),
    precio          numeric(8,2),
    duracion        varchar(10), -- mensual, anual, trimestral
    primary key (id)
);


-- Servicios de valor añadido software
INSERT INTO Servicios ( id, nombre, precio, duracion) VALUES ( 1, 'Facturacion', 1, 'mes');
INSERT INTO Servicios ( id, nombre, precio, duracion) VALUES ( 2, 'Contabilidad', 1, 'mes');
INSERT INTO Servicios ( id, nombre, precio, duracion) VALUES ( 3, 'Nóminas', 1, 'mes');
INSERT INTO Servicios ( id, nombre, precio, duracion) VALUES ( 4, 'Firma transfronteriza europea', 1, 'hito');
INSERT INTO Servicios ( id, nombre, precio, duracion) VALUES ( 5, 'eMail & Burofax', 1, 'hito');
INSERT INTO Servicios ( id, nombre, precio, duracion) VALUES ( 6, 'Almacenamineto a largo plazo', 1, 'mes');
INSERT INTO Servicios ( id, nombre, precio, duracion) VALUES ( 7, 'Indexación y recuperación', 2, 'mes');

-- Servicios de Telefonia
INSERT INTO Servicios ( id, nombre, precio, duracion) VALUES (  8, 'Número de teléfono', 2, 'mes');
INSERT INTO Servicios ( id, nombre, precio, duracion) VALUES (  9, 'llamadas teléfono', 2, 'mes');
INSERT INTO Servicios ( id, nombre, precio, duracion) VALUES ( 10, 'SMS', 1, 'hito');


-- Servicios Consultoría Fiscal
INSERT INTO Servicios ( id, nombre, precio, duracion) VALUES (11, 'Presentación telemática de impuestos', 7, 'hito');
INSERT INTO Servicios ( id, nombre, precio, duracion) VALUES (12, 'Cuentas anuales', 7, 'año');
INSERT INTO Servicios ( id, nombre, precio, duracion) VALUES (13, 'Registro Mercantil', 25, 'año');
INSERT INTO Servicios ( id, nombre, precio, duracion) VALUES (14, 'Consultoría Fiscal Telefonica', 25, 'hora');

-- Servicios Consultoría Laboral
INSERT INTO Servicios ( id, nombre, precio, duracion) VALUES (15, 'Nóminas trabajdores', 6, 'hito');
INSERT INTO Servicios ( id, nombre, precio, duracion) VALUES (16, 'Nóminas presentación de TC', 6, 'hito');
INSERT INTO Servicios ( id, nombre, precio, duracion) VALUES (17, 'Consultoría Laboral Telefonica', 25, 'hora');

-- Servicios Consultoría Jurídica
INSERT INTO Servicios ( id, nombre, precio, duracion) VALUES (18, 'Consultoría Jurídica Telefonica', 25, 'hora');
/


--
-- Relación usuarios servicios
--

CREATE TABLE UsuariosServicios
(
    id_cliente          integer references customers(id),
    id_servicio         integer references Servicios(id),
    estado              varchar(10), -- activo, baja, moroso, pruebas, etc.
    fecha_alta          date default now(),
    fecha_limite_uso    date, -- hasta cuando tiene permiso de uso
    fecha_baja          date,
    causa_baja          text
);
/

--
-- Transacciones de nuestros clientes con repercusión económica
--
CREATE TABLE ServiciosFacturables
(
    id                  serial      NOT NULL,
    id_cliente          integer references customers(id),
    id_servicio         integer references Servicios(id),
    estado              varchar(10) default 'pendiente', -- pendiente/pagado
    Fecha               TIMESTAMP default now(),
    primary key (id)
);
/



--
-- Transacciones de la pasarela de pago
--

-- TransactionId - The transaction ID for this transaction.
-- 2. TransactionStatus - The status of the payment transaction.
-- 3. ResponseMetatdata - The request ID of the Pay action request.
-- You should store the transaction ID and request ID in your database and cross-reference them
-- to the caller reference value you provided in the Pay request.

CREATE TABLE CobrosElectronicos
(
    id                  serial      NOT NULL,
    Fecha               date,
    id_cliente          integer references Customers(id),
    id_servicio         integer references Servicios(id),
    TransactionId       varchar(90),
    TransactionStatus   varchar(90),
    ResponseMetatdata   varchar(90),
    primary key (id)
);
/
--
-- Logs de inicio de sesion
--

CREATE TABLE LogSesion
(
    id                  serial      NOT NULL,
    Fecha               TIMESTAMP default now(),
    ip                  varchar(20),
    hostname            varchar(90),
    mail                varchar(90),
    URI                 text,
    primary key (id)
);
/
--
-- Datos de facturación
--

CREATE TABLE customers_facturacion
(
    nif                 char(10) not null,
    id_customers        integer references customers(id),
    forma_juridica      varchar(60),
    IBAN                varchar(34), -- los dos primeros digitos indican el país ES codigo para españa
    CNAE                varchar(25), -- ejemplo: CNAE J6311: Proceso de datos, hosting y actividades relacionadas
    fecha_constitucion  date, -- fecha de constitución de la sociedad
    tipo_actividad      varchar(25) default 'empresarial', -- empresarial o profesional
    nombre              varchar(60),
    direccion           varchar(90), -- Avenida Europa, 21
    objeto              varchar(40), -- bloque A 2ºD
    poblacion           varchar(90), -- 18690 Almuñécar Granada
    movil               char(10),
    mail                varchar(90),
    primary key (nif)
);
/

--
-- Tabla de cambios Banco Central Europeo
--

CREATE TABLE Reference_rates_ECB
(
    fecha               date not null,
    rate                numeric(6,4) not null,
    ISO_4217            varchar(3) not null,
    UNIQUE (fecha, ISO_4217)
);
/

