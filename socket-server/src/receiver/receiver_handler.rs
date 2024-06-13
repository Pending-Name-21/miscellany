use crate::models::events::window::MyWindowEvent;

pub fn handle_received_event(event: MyWindowEvent) {
    // TO DO: implement event receiver handler
    println!("{:?}", event);
}
