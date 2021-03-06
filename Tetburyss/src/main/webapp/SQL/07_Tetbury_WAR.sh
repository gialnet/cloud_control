#!/bin/sh

clear

#ejecuta el script

echo "Actualizar los fuentes proyecto Glassfish Secure Tetbury desde Dilar a Secure Tetbury"

echo "movernos a la carpeta de los fuentes"
cd /home/antonio/NetBeansProjects/cloud_control/Tetburyss

echo "comprimir los fuentes del proyecto Glassfish"
time zip -r -FS /home/antonio/Certificados/GF4Tetbury * --exclude "target/*" "src/main/webapp/SQL/*" "src/main/webapp/js/*" "src/main/webapp/css/*" "src/main/webapp/img/*" "src/main/webapp/images/*"
echo "Se excluye el código SQL,js y el WAR de despliegue"

cd /home/antonio/Certificados

echo "borrar la versión actual"
time ssh root@37.153.108.155 'rm -rf /root/gf4/*'

echo "copiando GF4Tetbury.zip a www.myempresa.eu Beta1 en 37.153.108.155"
time scp GF4Tetbury.zip root@37.153.108.155:/root/gf4

echo "descomprimir el código de la nueva versión"
time ssh root@37.153.108.155 'cd /root/gf4; unzip GF4Tetbury.zip;'

echo "compilar el proyecto con maven"
# para crear el WAR
# mvn clean install -U
#
time ssh root@37.153.108.155 'source .profile; cd /root/gf4; mvn clean install;'

echo "desplegar el archivo WAR"

echo "Terminado"
echo "cambiar el propietario del WAR de root a glafish"
echo "Copiar el WAR de despliegue en el servidor de Glassfish"
echo "desplegar con contraseña Prodacon2016"
ssh root@37.153.108.155