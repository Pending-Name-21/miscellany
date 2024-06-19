use socket_server::receiver::socket_server::SocketServer;
use std::io::Result;

fn main() -> Result<()> {
    let server = SocketServer::new("/tmp/events-socket.sock")?;
    server.start()?;
    Ok(())
}
