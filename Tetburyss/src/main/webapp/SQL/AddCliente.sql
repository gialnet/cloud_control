

--
-- Añadir o modificar los datos de un cliente
--
-- Mantiene sincronizadas las tablas DatosPer de cada cuenta de cliente con la
-- central de facturación
--select AddCliente('{"id":1,"forma_juridica":"SL","iban":null,"cnae":"null","fecha_constitucion":null,"tipo_actividad":"empresarial","carga_impositiva":20.00,"sociedades":"NO","capital_social":0.00,"income_account":null,"fiscal_year":"2013","periodo":"4 ","irpf_profesionales":21.00,"irpf_alquileres":21.00,"otras_reglas":null,"nif":"B18456962 ","nombre":"Gialnet Servicios","direccion":"C/ Darrillo de la Magdalena 7","objeto":"3ºA","poblacion":"18010 Granada","movil":"          ","mail":"info@gialnet.com","plantilla_factura":null,"plantilla_albaran":null,"plantilla_proforma":null,"escrituras_consti":null,"cero36":null,"tipo_de_cuenta":1}') 
--

/*  DatosPerJSON:=
    {
        "id":1,
        "forma_juridica":"SL",
        "iban":null,
        "cnae":"null",
        "fecha_constitucion":null,
        "tipo_actividad":"empresarial",
        "carga_impositiva":20.00,
        "sociedades":"NO",
        "capital_social":0.00,
        "income_account":null,
        "fiscal_year":"2013",
        "periodo":"4 ",
        "irpf_profesionales":21.00,
        "irpf_alquileres":21.00,
        "otras_reglas":null,
        "nif":"B18456962 ",
        "nombre":"Gialnet Servicios",
        "direccion":"C/ Darrillo de la Magdalena 7",
        "objeto":"3ºA",
        "poblacion":"18010 Granada",
        "movil":"          ",
        "mail":"info@gialnet.com",
        "plantilla_factura":null,
        "plantilla_albaran":null,
        "plantilla_proforma":null,
        "escrituras_consti":null,
        "cero36":null,
        "tipo_de_cuenta":1
    }
*/

CREATE OR REPLACE FUNCTION AddCliente(DatosPerJSON in varchar, email in varchar) 
returns void
AS
$body$
DECLARE

    xVar json;
    cuantos integer;
    xid_customers integer;

BEGIN

    xVar := DatosPerJSON::json;


    -- buscamos a que cuenta está asociado el cliente
    select id_customers into xid_customers from customers_users where mail=email;


    -- Comprobar si existe
    select count(*) into cuantos from customers_facturacion where id_customers = xid_customers;

    if cuantos = 0 then
        -- insertar los datos del cliente
        INSERT INTO customers_facturacion (
            id_customers,
            nif,
            forma_juridica,
            IBAN ,
            CNAE ,
            fecha_constitucion ,
            tipo_actividad ,
            nombre ,
            direccion ,
            objeto ,
            poblacion ,
            movil ,
            mail ) 
    values (xid_customers,
            Upper(xVar::json->>'nif'), 
            xVar::json->>'forma_juridica',
            Upper(xVar::json->>'iban'), 
            Upper(xVar::json->>'cnae'),
            to_date(xVar::json->>'fecha_constitucion','YYYY-MM-DD'),
            xVar::json->>'tipo_actividad',
            xVar::json->>'nombre',
            xVar::json->>'direccion',
            xVar::json->>'objeto',
            xVar::json->>'poblacion',
            xVar::json->>'movil',
            email
        );
    else
        -- actualizar los datos

        UPDATE customers_facturacion SET 
            forma_juridica = xVar::json->>'forma_juridica',
            IBAN = Upper(xVar::json->>'iban'),
            CNAE = Upper(xVar::json->>'cnae'),
            fecha_constitucion = to_date(xVar::json->>'fecha_constitucion','YYYY-MM-DD'),
            tipo_actividad = xVar::json->>'tipo_actividad',
            nombre = xVar::json->>'nombre',
            direccion = xVar::json->>'direccion',
            objeto = xVar::json->>'objeto',
            poblacion = xVar::json->>'poblacion',
            movil = xVar::json->>'movil',
            mail = email,
            nif = Upper(xVar::json->>'nif')
        WHERE id_customers = xid_customers;

    end if;
    

END;
$body$
LANGUAGE 'plpgsql'
VOLATILE
SECURITY INVOKER
COST 100;

