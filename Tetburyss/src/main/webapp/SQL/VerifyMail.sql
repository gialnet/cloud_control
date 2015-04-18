
--
-- Verificar una cuenta de correo del formulario de alta
--

CREATE OR REPLACE FUNCTION VerifyMail( xURL in varchar ) 
returns integer
AS
$body$
DECLARE

    xIDLibre integer := NULL;
    xIDDataBases_Servidor varchar(20);

BEGIN

-- xURL ID + - + url aleatoria
-- extraer el ID de DataBases_Servidor

xIDDataBases_Servidor := substr(xURL, 0, position('-' in xURL));

    -- Activar la base de datos
    -- Buscar una base de datos disponible
    -- ocupar la base de datos

    WITH databaseFree AS (

    Update DataBases_Servidor SET Estado = 'ocupada' 
    WHERE id = xIDDataBases_Servidor :: integer  and Estado = 'pendiente'
    returning ID
    )
    select id into xIDLibre from databaseFree;

    if xIDLibre is null then
        return -1;
    end if;


    -- activar el usuario

    WITH activa_cuenta AS (

    Update customers_users Set estado='activa' where url_wellcome=xURL and estado='pendiente verificacion mail'    
    returning ID
    )
    select id into xIDLibre from activa_cuenta;

    if xIDLibre is null then
       return -2;
    else
       return 0;
    end if;

END;
$body$
LANGUAGE 'plpgsql'
VOLATILE
SECURITY INVOKER
COST 100;

