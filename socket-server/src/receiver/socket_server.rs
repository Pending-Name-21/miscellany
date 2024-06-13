use serde_json;
use std::io::{BufRead, BufReader};
use std::os::unix::net::UnixListener;

use crate::models::events::window::MyWindowEvent;

use super::receiver_handler::handle_received_event;

pub struct SocketServer {
    listener: UnixListener,
}

impl SocketServer {
    pub fn new(socket_path: &str) -> std::io::Result<Self> {
        let listener = UnixListener::bind(socket_path)?;
        Ok(SocketServer { listener })
    }

    pub fn start(&self) -> std::io::Result<()> {
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
        Ok(())
    }
}
