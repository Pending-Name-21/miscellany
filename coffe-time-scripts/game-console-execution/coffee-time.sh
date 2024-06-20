#!/bin/bash

#socket path
SOCKET_PATH= "/tmp/events-socket.sock"

#logs directory
LOG_DIR="logs"


#function to init a background process for a given script
start_process() {
    local cmd=$1
    local log_file="$LOG_DIR/$2.log"

    mkdir -p "$LOG_DIR"

    #execute command, unpair from terminal and save logs
    ($cmd >"$log_file" 2>&1 & disown)

}

# init socket-server
start_process "${SOCKET_PATH}/socket-server.sh" "socket-server"
echo -n "Initializing... [$(tput setaf 1)Socket_Server$(tput sgr0)]"
printf "\n"

#init screen
start_process "${SOCKET_PATH}/screen-server.sh" "screen-server"
echo -n "Initializing... [$(tput setaf 1)Screen_Server$(tput sgr0)]"
printf "\n"

#init game with library
java -Djava.library.path=console/ -jar HelloWorld.jar >"$LOG_DIR/game.log" 2>&1 &
echo -n "Initializing... [$(tput setaf 1)Game$(tput sgr0)]"
printf "\n"

# success message
echo "All processes successfully initialized. :)"