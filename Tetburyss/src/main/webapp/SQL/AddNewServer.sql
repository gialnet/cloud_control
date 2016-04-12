
--
-- Añadir un nuevo servidor
--
-- Se tiene que añadir tantos usuarios nuevos como el número de bases de datos
-- que permite en nuevo servidor
--
-- select AddNewServer('81.45.18.210','www.myempresa.eu',1,30,30,1,'Telefonica',1);
-- select AddNewServer('37.153.108.155','www.myempresa.eu',2,50,30,1,'Joyent',1);
-- select AddNewServer('81.45.22.58','www.loexisasesores.es',1,30,1,35,'Telefonica',1);
--
CREATE OR REPLACE FUNCTION AddNewServer(
    xIP in varchar,
    xServerName in varchar,
    xRAM in numeric,
    xHD in numeric,
    xNumDB in integer,
    xSemilla in integer,
    xProveedor_Cloud in varchar,
    xPais in integer
) 
returns void
AS
$body$
DECLARE

    xDataBaseName varchar(30);
    xPassDataBase varchar(30);
    xIDCustomers integer;
    xIDServidores integer;
    xCertificado varchar(15);

BEGIN

    -- Insertar el nuevo servidor en la base de datos de servidores

    WITH Servidores_ins AS (
        INSERT INTO Servidores (IP,ServerName,RAM, HD, Proveedor_Cloud, NumeroDB, id_pais) 
        VALUES (xIP, xServerName, xRAM, xHD, xProveedor_Cloud, xNumDB, xPais)
        returning ID
    )
    select id into xIDServidores from Servidores_ins;


-- Añadir tantos clientes como número de bases de datos


FOR i IN 1..xNumDB LOOP

    raise notice 'valor de la semilla : %',xSemilla;
    xDataBaseName := 'jdbc/myEmpresa' || upper(lpad(to_hex(xSemilla),2,'0'));
    xPassDataBase := 'PassMaquina1';
    xCertificado := upper(lpad(to_hex(xSemilla),2,'0')); -- OJO para cuando pasemos de dos digitos
    

    -- Inserta un potencial cliente
    -- id_tipocuenta por defecto a Free
    -- desde el panel se ajustará este valor
    WITH customers_ins AS (
        INSERT INTO customers ( id_customers_pais, id_servidor, id_tipocuenta )
        VALUES ( xPais, xIDServidores, 1)
        returning ID
    )
    select id into xIDCustomers from customers_ins;

    -- AÑADIR el usuario administrador
    INSERT INTO customers_users (id_customers, databasename, passdatabase, IP, certificado, rol) 
                        VALUES (xIDCustomers, xDataBaseName, xPassDataBase, xIP, pg_read_binary_file(xCertificado||'.p12'), 'Administrador');

    -- inserta una base de datos disponible

    INSERT INTO DataBases_Servidor (id_servidor,id_cliente) VALUES (xIDServidores, xIDCustomers);

    xSemilla:=xSemilla+1;

END LOOP;


END;
$body$
LANGUAGE 'plpgsql'
VOLATILE
SECURITY INVOKER
COST 100;