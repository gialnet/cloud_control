--
-- Datos del gestor de clientes de la operadora del sistema
--

CREATE KEYSPACE customers WITH replication = {'class': 'SimpleStrategy', 'replication_factor':'1'};


--
-- Los distintos tipos de clientes en función de su residencia y consideraciones
-- especiales tributarias
--
CREATE TABLE customers.countries
(
    ISO_3166_1      Text PRIMARY KEY,
    descripcion     Text,
    cuenta_cliente  Text,
    cuenta_ventas   Text
);


INSERT INTO customers.countries (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('ESPAÑA',     'ES','43001' ,'70001');
INSERT INTO customers.countries (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('ARGENTINA',  'AR','43002' ,'70002');
INSERT INTO customers.countries (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('BOLIVIA',    'BO','43003' ,'70003');
INSERT INTO customers.countries (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('CHILE',      'CL','43004' ,'70004');
INSERT INTO customers.countries (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('COLOMBIA',   'CO','43005' ,'70005');
INSERT INTO customers.countries (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('COSTA RICA', 'CR','43006' ,'70006');
INSERT INTO customers.countries (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('CUBA',       'CU','43007' ,'70007');
INSERT INTO customers.countries (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('REPUBLICA DOMINICANA', 'DO','43008' ,'70008');
INSERT INTO customers.countries (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('ECUADOR',    'EC','43009' ,'70009');
INSERT INTO customers.countries (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('EL SALVADOR', 'SV','43010' ,'70010');
INSERT INTO customers.countries (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('GUINEA ECUATORIAL', 'GQ','43011' ,'70011');
INSERT INTO customers.countries (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('HONDURAS',   'HN','43012' ,'70012');
INSERT INTO customers.countries (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('MEXICO',     'MX','43013' ,'70013');
INSERT INTO customers.countries (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('NICARAGUA',  'NI','43014' ,'70014');
INSERT INTO customers.countries (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('PANAMA',     'PA','43015' ,'70015');
INSERT INTO customers.countries (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('PARAGUAY',   'PY','43016' ,'70016');
INSERT INTO customers.countries (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('PERU',       'PE','43017' ,'70017');
INSERT INTO customers.countries (DESCRIPCION,ISO_3166_1,cuenta_cliente,cuenta_ventas) VALUES ('VENEZUELA',  'VE','43018' ,'70018');
/

--
-- Tipos de cuentas
--
CREATE TABLE customers.PoliticaCuentas
(
    id Int PRIMARY KEY,
    nombre          Text,
    precio          decimal,
    duracion        Text, -- mensual, anual, trimestral
    servicios       map<text, text>
);
/

--
-- Tipos de cuentas
--
INSERT INTO customers.PoliticaCuentas ( id, nombre, precio, duracion, servicios) VALUES ( 1, 'Chanquete Free', 0, 'year',
{   'Contabilidad': 'no',
    'Impuestos': 'yes',
    'Nominas': 'yes',
    'NominasApp': 'no',
    'Firma': 'yes',
    'Burofax':  'no',
    'Almacenamiento': 'no',
    'Indexacion':  'no',
    'myHD': 'yes',
    'LimiteUsuarios': '1'
}
);

INSERT INTO customers.PoliticaCuentas ( id, nombre, precio, duracion, servicios) VALUES ( 2, 'Anchovy Premiun', 60, 'year',
{   'Contabilidad': 'yes', 
    'Impuestos': 'yes',
    'Nominas': 'yes',
    'NominasApp': 'no',
    'Firma': 'yes',
    'Burofax':  'no',
    'Almacenamiento': 'no',
    'Indexacion':  'no',
    'myHD': 'yes',
    'LimiteUsuarios': '1'
}
);

INSERT INTO customers.PoliticaCuentas ( id, nombre, precio, duracion, servicios) VALUES ( 3, 'Snapper Enterprise', 32, 'month',
{   'Contabilidad': 'yes', 
    'Impuestos': 'yes',
    'Nominas': 'yes',
    'NominasApp': 'no',
    'Firma': 'yes',
    'Burofax':  'yes',
    'Almacenamiento': 'yes',
    'Indexacion':  'yes',
    'myHD': 'yes',
    'LimiteUsuarios': 'no'
}
);

INSERT INTO customers.PoliticaCuentas ( id, nombre, precio, duracion, servicios) VALUES ( 4, 'Tuna Adviser', 35, 'month',
{   'Contabilidad': 'yes', 
    'Impuestos': 'yes',
    'Nominas': 'yes',
    'NominasApp': 'yes',
    'Firma': 'yes',
    'Burofax':  'no',
    'Almacenamiento': 'no',
    'Indexacion':  'no',
    'myHD': 'yes',
    'LimiteUsuarios': 'no'
}
);
/

--
-- Clientes
--

CREATE TABLE customers.customers
(
    customer_id               Text,
    ISO_3166_1                Text,
    id_tipocuenta             int,
    pod                       map<text, text>,
   primary key (customer_id)
);
/

--
-- Usuarios autorizados por el administrador de un cliente
--
CREATE TABLE customers.customers_users
(
    mail                    Text,
    customer_id             Text,
    rol                     Text, -- Administrador, Socio, Empleado directivo, Empleado plantilla,Asesor Fiscal,Asesor Laboral, Asesor Juridico
    nif                     Text,
    nombre                  Text,
    direccion               Text, -- Avenida Europa, 21
    objeto                  Text, -- bloque A 2ºD
    poblacion               Text, -- 18690 Almuñécar Granada
    movil                   Text,
    saldo                   decimal,
    estado                  Text,
    passwd                  Text,
    IP                      inet,
    url_wellcome            Text,
    url_site                Text,
    api_token               text,
    api_key                 text,
    labels                  map<text, text>,
   primary key (mail)
);

--
-- Vista de tipos de cuentas y los servicios asociados
--
/*
create or replace view TiposCuentas (mail,idcliente,tipocuenta,descripcion, servicios) AS
SELECT customers_users.mail,customers_users.id_customers, customers.id_tipocuenta, PoliticaCuentas.nombre, PoliticaCuentas.servicios
    FROM customers_users, customers, PoliticaCuentas 
    WHERE customers.id_tipocuenta = PoliticaCuentas.id
    AND customers_users.id_customers = customers.id;
*/

--
-- Usuarios externos autorizados por el administrador de un cliente
--
CREATE TABLE customers.customers_users_adviser
(
    id_customers            Text,
    mail                    Text, -- donde se encuentra el certificado del ASESOR en la tabla de customers_users
    rol                     Text, -- Asesor Fiscal,Asesor Laboral, Asesor Juridico
   primary key (id_customers,mail)
);



-- Relación de Servicios

CREATE TABLE customers.Servicios
(
    id              INT,
    nombre          Text,
    precio          decimal,
    duracion        Text, -- mensual, anual, trimestral
    primary key (id)
);


-- Servicios de valor añadido software
INSERT INTO customers.Servicios ( id, nombre, precio, duracion) VALUES ( 1, 'Facturacion', 1, 'mes');
INSERT INTO customers.Servicios ( id, nombre, precio, duracion) VALUES ( 2, 'Contabilidad', 1, 'mes');
INSERT INTO customers.Servicios ( id, nombre, precio, duracion) VALUES ( 3, 'Nóminas', 1, 'mes');
INSERT INTO customers.Servicios ( id, nombre, precio, duracion) VALUES ( 4, 'Firma transfronteriza europea', 1, 'hito');
INSERT INTO customers.Servicios ( id, nombre, precio, duracion) VALUES ( 5, 'eMail & Burofax', 1, 'hito');
INSERT INTO customers.Servicios ( id, nombre, precio, duracion) VALUES ( 6, 'Almacenamineto a largo plazo', 1, 'mes');
INSERT INTO customers.Servicios ( id, nombre, precio, duracion) VALUES ( 7, 'Indexación y recuperación', 2, 'mes');

-- Servicios de Telefonia
INSERT INTO customers.Servicios ( id, nombre, precio, duracion) VALUES (  8, 'Número de teléfono', 2, 'mes');
INSERT INTO customers.Servicios ( id, nombre, precio, duracion) VALUES (  9, 'llamadas teléfono', 2, 'mes');
INSERT INTO customers.Servicios ( id, nombre, precio, duracion) VALUES ( 10, 'SMS', 1, 'hito');


-- Servicios Consultoría Fiscal
INSERT INTO customers.Servicios ( id, nombre, precio, duracion) VALUES (11, 'Presentación telemática de impuestos', 7, 'hito');
INSERT INTO customers.Servicios ( id, nombre, precio, duracion) VALUES (12, 'Cuentas anuales', 7, 'año');
INSERT INTO customers.Servicios ( id, nombre, precio, duracion) VALUES (13, 'Registro Mercantil', 25, 'año');
INSERT INTO customers.Servicios ( id, nombre, precio, duracion) VALUES (14, 'Consultoría Fiscal Telefonica', 25, 'hora');

-- Servicios Consultoría Laboral
INSERT INTO customers.Servicios ( id, nombre, precio, duracion) VALUES (15, 'Nóminas trabajdores', 6, 'hito');
INSERT INTO customers.Servicios ( id, nombre, precio, duracion) VALUES (16, 'Nóminas presentación de TC', 6, 'hito');
INSERT INTO customers.Servicios ( id, nombre, precio, duracion) VALUES (17, 'Consultoría Laboral Telefonica', 25, 'hora');

-- Servicios Consultoría Jurídica
INSERT INTO customers.Servicios ( id, nombre, precio, duracion) VALUES (18, 'Consultoría Jurídica Telefonica', 25, 'hora');



--
-- Relación usuarios servicios
--

CREATE TABLE customers.UsuariosServicios
(
    customer_id         Text,
    id_servicio         Int,
    estado              Text, -- activo, baja, moroso, pruebas, etc.
    fecha_alta          timestamp,
    fecha_limite_uso    timestamp, -- hasta cuando tiene permiso de uso
    fecha_baja          timestamp,
    causa_baja          Text,
    primary key (customer_id,id_servicio,estado)
);


--
-- Transacciones de nuestros clientes con repercusión económica
--
CREATE TABLE customers.VentasServiciosFacturables
(
    customer_id         Text,
    id_servicio         Int,
    estado              Text, -- pendiente/pagado
    Fecha               TIMESTAMP,
    primary key (customer_id,id_servicio,estado,Fecha)
);




--
-- Transacciones de la pasarela de pago
--

-- TransactionId - The transaction ID for this transaction.
-- 2. TransactionStatus - The status of the payment transaction.
-- 3. ResponseMetatdata - The request ID of the Pay action request.
-- You should store the transaction ID and request ID in your database and cross-reference them
-- to the caller reference value you provided in the Pay request.

CREATE TABLE customers.CobrosElectronicos
(
    Fecha               timestamp,
    id_cliente          Text,
    id_servicio         int,
    TransactionId       Text,
    TransactionStatus   Text,
    ResponseMetatdata   Text,
    primary key (fecha,id_cliente,id_servicio,TransactionId)
);

--
-- Datos de facturación
--

CREATE TABLE customers.customers_facturacion
(
    nif                 Text,
    id_customers        Text,
    forma_juridica      Text,
    IBAN                Text, -- los dos primeros digitos indican el país ES codigo para españa
    CNAE                Text, -- ejemplo: CNAE J6311: Proceso de datos, hosting y actividades relacionadas
    fecha_constitucion  date, -- fecha de constitución de la sociedad
    tipo_actividad      Text, -- empresarial o profesional
    nombre              Text,
    direccion           Text, -- Avenida Europa, 21
    objeto              Text, -- bloque A 2ºD
    poblacion           Text, -- 18690 Almuñécar Granada
    movil               Text,
    mail                Text,
    primary key (nif)
);


--
-- Tabla de cambios Banco Central Europeo
--

CREATE TABLE customers.Reference_rates_ECB
(
    fecha               timestamp ,
    rate                decimal,
    ISO_4217            Text,
    Primary key (fecha, ISO_4217)
);

