
# Runs the Java Swing app (compile + run) with PostgreSQL JDBC
# Usage:  powershell -ExecutionPolicy Bypass -File .\run.ps1

$ErrorActionPreference = 'Stop'

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $root

$srcRoot = Join-Path $root 'src'
$binRoot = Join-Path $root 'bin'

# Build classpath from all JARs in lib/
$libDir = Join-Path $root 'lib'
$jars = Get-ChildItem -Path $libDir -Filter '*.jar' | ForEach-Object { $_.FullName }
$cpJars = ($jars -join ';')

if (-not (Test-Path $srcRoot)) { throw "Missing src folder: $srcRoot" }
if (-not (Test-Path $libDir)) { throw "Missing lib folder: $libDir" }
if (-not ($jars | Where-Object { $_ -like '*postgresql*.jar' })) {
  throw "Missing JDBC jar: postgresql-*.jar in $libDir (expected PostgreSQL JDBC driver)" 
}
if (-not ($jars | Where-Object { $_ -like '*jackson-databind*.jar' })) {
  Write-Host "Warning: jackson-databind JAR not found in lib/. Please add required Jackson JARs." -ForegroundColor Yellow
}

if (-not (Test-Path $binRoot)) {
  New-Item -ItemType Directory -Path $binRoot | Out-Null
}

Write-Host "Compiling Java sources..." -ForegroundColor Cyan
$javaFiles = Get-ChildItem -Path $srcRoot -Recurse -Filter '*.java' | ForEach-Object { $_.FullName }
if (-not $javaFiles -or $javaFiles.Count -eq 0) { throw "No .java files found under $srcRoot" }

# Compile and show errors if any
Write-Host "Compiling all Java sources (showing errors if any)..." -ForegroundColor Yellow
$fullCp = "$cpJars;$srcRoot;./Java/"
$javacOutput = & javac -encoding UTF-8 -cp $fullCp -d $binRoot @javaFiles 2>&1
if ($LASTEXITCODE -ne 0) {
  Write-Host "Javac compilation failed:" -ForegroundColor Red
  Write-Host $javacOutput -ForegroundColor Red
  exit 1
}
Write-Host $javacOutput

# Start the Swing app
Write-Host "Starting Swing app (Concordia.MainDriver)..." -ForegroundColor Cyan
$cp = "$binRoot;$cpJars;./Java/"
java -cp $cp Concordia.MainDriver
