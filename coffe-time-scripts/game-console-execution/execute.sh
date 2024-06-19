#!/bin/bash

# function to show the loading animation
show_loading() {
    # loading animation
    for i in {1..100}; do
        tput sc
        echo -n "Loading... [$(tput setaf 1)$i%$(tput sgr0)]"
        sleep 0.009
        tput rc
    done
    printf "\n"
}

#function to init a background process for a given script
start_process() {
    local cmd=$1
    local cmd=$2
    echo "Initializing $msg ..."
    #execute command and unpair from terminal
    ($cmd >&1 & disown)
    show_loading
}

# init socket-server
start_process "./socket-server.sh"
echo "socket-server initialized..."

#init screen
start_process "./screen-server.sh" 
echo "screen initialized..."

#init game with library
java -Djava.library.path=console/ -jar game.jar
show_loading

# success message
echo "All processes successfully initialized. :)"
