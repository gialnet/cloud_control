#!/bin/sh
#
# Crear una base de datos desde la línea de comandos del Shell
#

echo 'crear el usuario'

usuario='us'$1
database='db'$1

#useradd ${usuario}

#usermod -d /usuarios/pgsql ${usuario}

psql -d secure -U postgres -c "CREATE USER ${usuario};"

echo 'crear la base de datos'
psql -d secure -U postgres -c "CREATE DATABASE ${database} OWNER ${usuario};"

# crear las tablas
echo 'crear las tablas'
psql -d ${database} -f CrearTablas.sql -U ${usuario}


# crear las funciones
echo 'crear las funciones'

psql -d ${database} -f ApunteCompras.sql -U ${usuario}
psql -d ${database} -f ApunteContable.sql -U ${usuario}
psql -d ${database} -f ApunteVenta.sql -U ${usuario}
psql -d ${database} -f Balance.sql -U ${usuario}
psql -d ${database} -f BalancesCalculos.sql -U ${usuario}
psql -d ${database} -f BorraPagoCompraBanco.sql -U ${usuario}
psql -d ${database} -f BorrarCobroVentaBanco.sql -U ${usuario}
psql -d ${database} -f CalculaNomina.sql -U ${usuario}
psql -d ${database} -f CalculaSociedades.sql -U ${usuario}
psql -d ${database} -f CalculaSS.sql -U ${usuario}
psql -d ${database} -f CalculaTodo.sql -U ${usuario}
psql -d ${database} -f CalculoIVA.sql -U ${usuario}
psql -d ${database} -f CaluculoIRPF.sql -U ${usuario}
psql -d ${database} -f CargoNomina.sql -U ${usuario}
psql -d ${database} -f CobroVentaBanco.sql -U ${usuario}
psql -d ${database} -f EstadoIngresosGastosReconocidos.sql -U ${usuario}
psql -d ${database} -f getCuentaDebe.sql -U ${usuario}
psql -d ${database} -f getdto.sql -U ${usuario}
psql -d ${database} -f getiva.sql -U ${usuario}
psql -d ${database} -f ImpuestoSociedades.sql -U ${usuario}
psql -d ${database} -f ingresonominas.sql -U ${usuario}
psql -d ${database} -f ingresoSS.sql -U ${usuario}
psql -d ${database} -f newGasto.sql -U ${usuario}
psql -d ${database} -f newHeadFactura.sql -U ${usuario}
psql -d ${database} -f PagoAutonomos.sql -U ${usuario}
psql -d ${database} -f PagoCompraBanco.sql -U ${usuario}
psql -d ${database} -f PagoTributos.sql -U ${usuario}
psql -d ${database} -f PanelFacturacion.sql -U ${usuario}
psql -d ${database} -f PerdidasyGanancias.sql -U ${usuario}
psql -d ${database} -f ReintegrosIngresos.sql -U ${usuario}
psql -d ${database} -f SaldoCuenta.sql -U ${usuario}
psql -d ${database} -f SaveFactPDF.sql -U ${usuario}
psql -d ${database} -f SaveNewCliente.sql -U ${usuario}
psql -d ${database} -f SaveNewProveedor.sql -U ${usuario}
psql -d ${database} -f Socios.sql -U ${usuario}
psql -d ${database} -f UpdateApartado.sql -U ${usuario}
psql -d ${database} -f UpdateAsientoVenta.sql -U ${usuario}
psql -d ${database} -f UpdateCargoNominas.sql -U ${usuario}
psql -d ${database} -f UpdateCliente.sql -U ${usuario}
psql -d ${database} -f UpdateCompra.sql -U ${usuario}
psql -d ${database} -f updateFact.sql -U ${usuario}
psql -d ${database} -f UpdateProveedor.sql -U ${usuario}
psql -d ${database} -f UpdateReintegrosIngresos.sql -U ${usuario}


# crear las vistas
echo 'crear las vistas'
psql -d ${database} -f CrearView.sql -U ${usuario}

echo 'proceso concluido'