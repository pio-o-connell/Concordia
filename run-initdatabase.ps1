# PowerShell script to compile and run InitDatabase.java
# Make sure the PostgreSQL JDBC jar is in the lib/ folder

$srcDir = "src/Concordia"
$libDir = "lib"
$classpath = "$libDir/*;$srcDir"

# Compile all required Java files
javac -cp "$libDir/*" $srcDir/*.java

# Run InitDatabase to create and populate tables
java -cp "$classpath" Concordia.InitDatabase
