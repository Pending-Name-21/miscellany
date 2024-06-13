use serde::{Deserialize, Serialize};

use super::key::MyKey;

#[derive(Serialize, Deserialize, Clone, Debug, PartialEq)]
pub enum MyWindowEvent {
    MyKeyPressed(MyKey)
}