#!/usr/bin/env bash
# Jetty Start Script for Git Bash

# Set project root (directory containing this script)
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$SCRIPT_DIR"

# Set environment variables
export JAVA_HOME="$PROJECT_ROOT/environments/jdk-25"
export PATH="$JAVA_HOME/bin:$PATH"
export JETTY_BASE="$PROJECT_ROOT/jetty-base"

# Start Jetty
"$PROJECT_ROOT/jetty-home-12.1.5/bin/jetty.sh" start
