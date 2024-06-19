#!/bin/bash

#function to init a background process for a given script
start_process() {
    local cmd=$1
    #execute command and unpair from terminal
    $cmd >&1 & disown
}

# init socket-server
start_process "./socket-server.sh"
echo "socket-server initialized..."

#init screen
start_process "./screen-server.sh" 
echo "screen initialized..."

#init game with library
java -Djava.library.path=console/ -jar game.jar
echo "game initilazed..."

# success message
echo "All processes successfully initialized. :)"
