# Execução local:
1) Abrir CMD
2) Acessar o diretório onde consta o jar "h2-2.3.232.jar"
3) Executar o comando "java -cp h2-2.3.232.jar org.h2.tools.Server -tcp -tcpAllowOthers -ifNotExists"
4) Swagger estará disponível em: http://localhost:8084/saude-ja-posto-saude-service/swagger-ui/index.html
<br><br>obs.: a aplicação faz parte do projeto saude-ja (https://github.com/stars/PedroLuizQuessada/lists/sa%C3%BAde-j%C3%A1)

# Alterações para ambientes corporativos:
1) Adição do arquivo src/main/resources/application-jpa.properties no .gitignore
2) Adição do arquivo src/main/resources/application-restclient.properties no .gitignore
3) Adição dos arquivos da pasta src/main/resources/keys no .gitignore