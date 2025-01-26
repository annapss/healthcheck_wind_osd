# healthcheck_wind_osd

Nova versão do sistema de Monitoramento de Saúde de Dispositivos de Embarcações utilizando o Spring Boot

Nesta versão o projeto está organizado no package src/main/java/com/example/demo em três serviços principais:
- dataGenerator: Serviço que gera os dados que seriam enviados pela embarcação
- dataVerification: Serviço que recebe os dados gerados e identifica se há erros e quais erros
- relatorioGenerator: Gera os relatórios do NavTI e da Inovação (no momento, está gerando somente do NavTI)
- zabbix: Package que contém os arquivos com todas as configurações do zabbix

O package databaseTables que está no mesmo package que os serviços acima diz respeito somente as classes criadas para fazer a interação com o banco de dados.

Para rodar a aplicação, é necessário abrir a pasta "demo" como um projeto Java e executar o arquivo DemoApplication.java que está no package src/main/java/com/example/demo

É importante ressaltar que ao final do projeto o único serviço que será uma api é o dataGenerator. Os serviços de relatório e dataVerification no momento estão com endpoint somente para que possam ser realizados testes com a ferramenta k6.
