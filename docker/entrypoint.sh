#!/bin/bash
/opt/mssql/bin/sqlservr &

echo "Waiting for SQL Server to start..."
sleep 20

echo "Running initialization scripts..."
for f in $(ls /docker-entrypoint-initdb.d/*.sql | sort);
do
  echo "Running $f"
  /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P "$SA_PASSWORD" -i "$f" -C
done

echo "Initialization completed!"
wait
