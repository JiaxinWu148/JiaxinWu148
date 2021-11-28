package edu.rice.comp504.model.message;

import java.sql.Timestamp;

public class ImageMessage extends AMessage {
    private String text;

    /**
     * Constructor.
     * @param timestamp The time message sent.
     * @param id The message's unique id.
     * @param senderId The user who sent the message.
     * @param receiverId The user/room that receives the message.
     */
    public ImageMessage(Timestamp timestamp, int id, int senderId, int roomId, int receiverId, String text) {
        super(timestamp, id, senderId, roomId, receiverId);
        this.text = text;
        this.type = "image";
    }

    /**
     * @return Return url.
     */
    public String getText() {
        return text;
    }

    /**
     * @param text Set url.
     */
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean detectHateSpeech() {
        return false;
    }
}
