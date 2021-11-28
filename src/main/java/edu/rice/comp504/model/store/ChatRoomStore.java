package edu.rice.comp504.model.store;

import edu.rice.comp504.model.User;
import edu.rice.comp504.model.chatroom.AChatRoom;
import edu.rice.comp504.model.chatroom.NullChatRoom;
import edu.rice.comp504.model.factory.ChatRoomFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoomStore {
    public static int chatroomCount = 0;
    private static ChatRoomStore ONLY;
    private static ChatRoomFactory roomFac;
    ConcurrentHashMap<Integer, AChatRoom> chatRoomMap;

    /**
     * Constructor.
     */
    ChatRoomStore() {
        chatRoomMap = new ConcurrentHashMap<>();
    }

    /**
     * @return Singleton store.
     */
    public static ChatRoomStore make() {
        if (ONLY == null) {
            ONLY = new ChatRoomStore();
        }
        return ONLY;
    }

    /**
     * getOnlyRoomFac.
     */
    public static ChatRoomFactory getOnlyRoomFac() {
        if (roomFac == null) {
            roomFac = new ChatRoomFactory();
        }
        return roomFac;
    }

    /**
     * Create a chat room.
     */
    public AChatRoom createChatRoom(User admins, int size, String description,
                                    String name, String type, String password) {
        AChatRoom newChatRoom = getOnlyRoomFac().make(chatroomCount, admins,
                size, description, name, type, password);
        newChatRoom.addUser(admins);
        chatRoomMap.put(chatroomCount++, newChatRoom);
        return newChatRoom;
    }

    /**
     * Get a room.
     */
    public AChatRoom getRoom(int id) {
        if (id >= 0) {
            return chatRoomMap.get(id);
        } else {
            return NullChatRoom.make();
        }
    }

    /**
     * public String deleteRoom(int roomId, int userId) {
     * if (!chatRoomMap.containsKey(roomId))
     * return "wrong room id";
     * AChatRoom room = chatRoomMap.get(roomId);
     * if (!room.getAdmin().contains(userId))
     * return "no permission";
     * chatRoomMap.remove(roomId);
     * return "success";
     * }
     */

    /**
     * deleteRoomCheck.
     * @param roomId roomId
     * @param userId user id
     */
    public String deleteRoomCheck(int roomId, int userId) {
        if (!chatRoomMap.containsKey(roomId)) {
            return "wrong room id";
        }
        AChatRoom room = chatRoomMap.get(roomId);
        if (!room.getAdmin().contains(userId)) {
            return "no permission";
        }
        return "success";
    }

    /**
     * deleteRoom.
     */
    public void deleteRoom(int roomId) {
        chatRoomMap.remove(roomId);
    }

    /**
     * getUserRoom.
     */
    public List<AChatRoom> getUserRoom(int userId) {
        List<AChatRoom> list = new ArrayList<>();
        for (int key : chatRoomMap.keySet()) {
            if (chatRoomMap.get(key).checkUser(userId)) {
                list.add(chatRoomMap.get(key));
            }
        }
        return list;
    }

    /**
     * getJoinRoom.
     */
    public List<AChatRoom> getJoinRoom(int userId) {
        List<AChatRoom> list = new ArrayList<>();
        for (int key : chatRoomMap.keySet()) {
            if (!chatRoomMap.get(key).checkUser(userId)) {
                list.add(chatRoomMap.get(key));
            }
        }
        return list;
    }

    /**
     * checkRoom.
     */
    public boolean checkRoom(int roomId) {
        return chatRoomMap.containsKey(roomId);
    }


}
