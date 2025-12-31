# Download PostgreSQL JDBC Driver
# Run this in PowerShell to download the latest driver to the lib/ directory

Invoke-WebRequest -Uri "https://jdbc.postgresql.org/download/postgresql-42.7.3.jar" -OutFile "lib/postgresql-42.7.3.jar"
