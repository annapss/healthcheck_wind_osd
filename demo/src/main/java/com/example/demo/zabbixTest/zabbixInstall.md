# Como instalar zabbix

- Instalar versão 14 do PostgreSQL https://www.postgresql.org/download/linux/ubuntu/
	sudo apt install -y postgresql-common
	sudo /usr/share/postgresql-common/pgdg/apt.postgresql.org.sh
	sudo apt install curl ca-certificates
	sudo install -d /usr/share/postgresql-common/pgdg
	sudo curl -o /usr/share/postgresql-common/pgdg/apt.postgresql.org.asc --fail https://www.postgresql.org/media/keys/ACCC4CF8.asc
	sudo sh -c 'echo "deb [signed-by=/usr/share/postgresql-common/pgdg/apt.postgresql.org.asc] 	https://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list'
	sudo apt update
	sudo apt -y install postgresql
- Iniciar o PostgreSQL: service postgresql start
- Verificar se o seu wsl está com o systemd habilitado
	cd /
	vim /etc/wsl.conf
	- Se tiver como false, mude para true e faça um reboot no wsl no cmd do Windows -> wsl.exe --shutdown
- Configurar o locale no wls
	sudo vim /etc/locale.gen -> Descomentar todos que começam com en_US
	sudo locale-gen
- Acessar https://www.zabbix.com/download?zabbix=7.2&os_distribution=ubuntu&os_version=22.04&components=server_frontend_agent&db=pgsql&ws=apache e verificar se estão com as opções
	Zabbix version: 7.2
	OS Distribution: Ubuntu
	OS Version: 22.04 Jammy
	Zabbix Component: Server, Frontend, Agent
	Database: PostgreSQL
	Web Server: Apache
<!-- Continua ...>