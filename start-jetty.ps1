# Jetty Start Script for Windows PowerShell
# Change to the script's directory, then up to project root
Set-Location $PSScriptRoot
Set-Location ..

# Set environment variables
$env:JAVA_HOME = "$PWD\environments\jdk-25"
$env:PATH = "$env:JAVA_HOME\bin;${env:PATH}"
$env:JETTY_BASE = "$PWD\jetty-base"

# Start Jetty
& "$PWD\jetty-home-12.1.5\bin\jetty.sh" start
