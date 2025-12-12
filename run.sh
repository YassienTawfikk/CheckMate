#!/bin/bash
# Compile and Run script for CheckMate

# Create bin directory for compiled classes
mkdir -p bin

# Compile source files
# Output to bin directory
# Source path is src/main/java
# Main file is src/main/java/com/checkmate/core/Main.java
echo "Compiling..."
javac -d bin -sourcepath src src/com/checkmate/core/Main.java

if [ $? -eq 0 ]; then
    echo "Running CheckMate..."
    # Run from bin directory, including resources in classpath
    # Main class is com.checkmate.core.Main
    java -cp bin:src/resources com.checkmate.core.Main
else
    echo "Compilation failed."
fi
