use serde_json;
use std::fs;
use std::io::{BufRead, BufReader, Error, ErrorKind, Result};
use std::os::unix::net::{UnixListener, UnixStream};
use std::path::Path;

use crate::models::events::window::MyWindowEvent;

use super::receiver_handler::handle_received_event;

pub struct SocketServer {
    listener: UnixListener,
}

impl SocketServer {
    pub fn new(socket_path: &str) -> Result<Self> {
        if Path::new(socket_path).exists() {
            match UnixStream::connect(socket_path) {
                Ok(_) => {
                    println!("Socket already in use.");
                    return Err(Error::new(ErrorKind::AddrInUse, "Socket already in use"));
                }
                Err(_) => {
                    fs::remove_file(socket_path)?;
                }
            }
        }

        let listener = UnixListener::bind(socket_path)?;
        Ok(SocketServer { listener })
    }

    pub fn start(&self) -> std::io::Result<()> {
        loop {
            match self.listener.accept() {
                Ok((socket, _)) => {
                    let reader = BufReader::new(&socket);
                    for line in reader.lines() {
                        let buf = line?;
                        let event: MyWindowEvent = serde_json::from_str(&buf).unwrap();
                        handle_received_event(event);
                    }
                }
                Err(error) => {
                    println!("error listening to: {:?}", error);
                }
            }
        }
    }
}
