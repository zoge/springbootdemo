FROM mcr.microsoft.com/mssql/server:2019-latest

ENV ACCEPT_EULA=Y
ENV SA_PASSWORD=YourStrong@Passw0rd
ENV MSSQL_PID=Developer

COPY ./init-db /docker-entrypoint-initdb.d/
COPY ./entrypoint.sh /entrypoint.sh
#RUN chmod +x /entrypoint.sh

EXPOSE 1433

#CMD ["/opt/mssql/bin/sqlservr", "/entrypoint.sh"]
ENTRYPOINT ["/entrypoint.sh"]
