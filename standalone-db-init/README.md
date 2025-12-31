# Create Database

### psql -U postgres -h 127.0.0.1 -p 5432 -d concordia password password
* Use existing database.class to generate the data
* the data must persist
* all the java classes in repository layer use jdbc



### and using
* using ./swing-app/src/main/java/Concordia/infrastructure/ApplicationBootstra.java
* using ./swing-app/src/main/java/Concordia/Database.java to 
* invokes setup creating users
* invokes resetAndPopulateGardeningTestData()
* invokes updateHistoryTransintoDatabase()


### Invoke by- 
java -cp "standalone-db-init/bin;standalone-db-init/lib/postgresql-42.7.3.jar" concordia.Main




### Bullet list
