use serde_json;
use std::fs;
use std::io::{BufRead, BufReader, Error, ErrorKind, Result};
use std::os::unix::net::{UnixListener, UnixStream};
use std::path::Path;

use super::receiver_handler::handle_received_event;
use crate::models::events::window::MyWindowEvent;
use std::sync::atomic::{AtomicBool, Ordering};
use std::sync::Arc;

pub struct SocketServer {
    listener: UnixListener,
    running: Arc<AtomicBool>,
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
        let running = Arc::new(AtomicBool::new(true));
        Ok(SocketServer { listener, running })
    }

    pub fn stop(&self) {
        self.running.store(false, Ordering::SeqCst);
    }

    pub fn start(&self) -> std::io::Result<()> {
        while self.running.load(Ordering::SeqCst) {
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
        Ok(())
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::models::events::key::MyKey;
    use std::io::Write;
    use std::thread;
    use tempfile::TempDir;

    #[test]
    fn test_socket_server() {
        let temp_dir = TempDir::new().unwrap();
        let socket_path = temp_dir.path().join("test.sock");
        let server = SocketServer::new(socket_path.to_str().unwrap()).unwrap();
        let server_ref = Arc::new(server);
        let server_test = server_ref.clone();

        let handle = thread::spawn(move || {
            server_test.start().unwrap();
        });

        let mut client = UnixStream::connect(socket_path).unwrap();
        let test_event = MyWindowEvent::MyKeyPressed(MyKey::MyA);
        let test_event_json = serde_json::to_string(&test_event).unwrap();

        writeln!(client, "{}", test_event_json).unwrap();

        server_ref.stop();
        handle.join().unwrap();
    }
}
