package com.integration_testing.t_224;

import CoffeeTime.InputEvents.Keyboard;
import com.bridge.core.exceptions.renderHandlerExceptions.RenderException;
import com.bridge.ipc.Transmitter;
import com.bridge.processinputhandler.IEventSubscriber;
import com.bridge.renderHandler.render.Frame;
import com.bridge.renderHandler.sprite.Coord;
import com.bridge.renderHandler.sprite.Sprite;

import java.util.List;

public class SpriteObject implements IEventSubscriber <Keyboard> {

    Sprite sprite;
    Transmitter transmitter;
    private final int MOVEMENT_DISTANCE = 10;

    SpriteObject(Transmitter transmitter, Sprite sprite ) throws RenderException {
        this.sprite = sprite;
        this.transmitter = transmitter;
        transmitter.send(new Frame(List.of(sprite), List.of()));
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Transmitter getTransmitter() {
        return transmitter;
    }

    @Override
    public void doNotify(Keyboard keyboard) {
        if(keyboard.type().equals("KeyPressed")) {
            Coord currentPosition = sprite.getPosition();
            switch (keyboard.key()) {
                case "Up":
                    try {
                        System.out.println("move in up:" + currentPosition.getX() + " " + currentPosition.getY());
                        Coord newPosition = new Coord(currentPosition.getX(), currentPosition.getY() + MOVEMENT_DISTANCE);
                        sprite.setPosition(newPosition);
                        transmitter.send(new Frame(List.of(sprite), List.of()));
                    } catch (RenderException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                case "Down":
                    try {
                        System.out.println("move in down:" + currentPosition.getX() + " " + currentPosition.getY());
                        Coord newPosition = new Coord(currentPosition.getX(), currentPosition.getY() - MOVEMENT_DISTANCE);
                        sprite.setPosition(newPosition);
                        transmitter.send(new Frame(List.of(sprite), List.of()));
                    } catch (RenderException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                case "Left":
                    try {
                        System.out.println("move in left:" + currentPosition.getX() + " " + currentPosition.getY());
                        Coord newPosition = new Coord(currentPosition.getX() - MOVEMENT_DISTANCE, currentPosition.getY());
                        sprite.setPosition(newPosition);
                        transmitter.send(new Frame(List.of(sprite), List.of()));
                    } catch (RenderException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "Right":
                    try {
                        System.out.println("move in right:" + currentPosition.getX() + " " + currentPosition.getY());
                        Coord newPosition = new Coord(currentPosition.getX() + MOVEMENT_DISTANCE, currentPosition.getY());
                        sprite.setPosition(newPosition);
                        transmitter.send(new Frame(List.of(sprite), List.of()));
                    } catch (RenderException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        }
    }
}
