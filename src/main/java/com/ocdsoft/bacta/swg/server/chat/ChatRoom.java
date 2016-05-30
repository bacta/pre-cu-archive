package com.ocdsoft.bacta.swg.server.chat;

import com.ocdsoft.bacta.swg.shared.chat.ChatRoomData;
import com.ocdsoft.bacta.swg.shared.chat.ChatRoomVisibility;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatAvatarId;
import lombok.Getter;

import java.util.*;

/**
 * Created by crush on 5/28/2016.
 */
public final class ChatRoom {
    public static final int UNLIMITED_SIZE = 0;

    @Getter
    private final int id;
    @Getter
    private final ChatAvatarId creator;
    @Getter
    private final Date created;
    @Getter
    private final String name; //TODO: Should we allow this to be mutable?
    @Getter
    private ChatAvatarId owner;
    /**
     * Attributes set on the room. See {@link ChatRoomAttributes}.
     */
    private int attributes;
    @Getter
    private String topic;
    @Getter
    private String password;
    @Getter
    private int maxSize;

    private final Set<ChatAvatarId> bans;
    private final Set<ChatAvatarId> admins;
    /**
     * Avatars presently in the room. Should not be persisted.
     */
    private transient final Set<ChatAvatarId> avatars;
    private final Set<ChatAvatarId> invitees;
    private final Set<ChatAvatarId> moderators;

    public ChatRoom(final int id,
                    final ChatAvatarId creator,
                    final String name,
                    final String topic,
                    final int attributes) {
        this(id, creator, name, topic, attributes, UNLIMITED_SIZE);
    }

    public ChatRoom(final int id,
                    final ChatAvatarId creator,
                    final String name,
                    final String topic,
                    final int attributes,
                    final int maxSize) {

        this.id = id;
        this.creator = creator;
        this.owner = creator;
        this.created = new Date();
        this.name = name;
        this.topic = topic;
        this.attributes = attributes;
        this.maxSize = maxSize;

        this.bans = new HashSet<>();
        this.admins = new HashSet<>();
        this.avatars = new HashSet<>();
        this.invitees = new HashSet<>();
        this.moderators = new HashSet<>();
    }

    public boolean isPrivate() {
        return ChatRoomAttributes.isSet(attributes, ChatRoomAttributes.PRIVATE);
    }

    public boolean isPublic() {
        return !isPrivate();
    }

    public boolean isModerated() {
        return ChatRoomAttributes.isSet(attributes, ChatRoomAttributes.MODERATED);
    }

    public boolean isCreator(final ChatAvatarId avatarId) {
        return creator.equals(avatarId);
    }

    public boolean isOwner(final ChatAvatarId avatarId) {
        return owner.equals(avatarId);
    }

    public boolean isBanned(final ChatAvatarId avatarId) {
        return bans.contains(avatarId);
    }

    public boolean banAvatar(final ChatAvatarId avatarId) {
        return bans.add(avatarId);
    }

    public boolean unbanAvatar(final ChatAvatarId avatarId) {
        return bans.remove(avatarId);
    }

    public Set<ChatAvatarId> getBannedAvatars() {
        return Collections.unmodifiableSet(bans);
    }

    public Iterator<ChatAvatarId> getBannedAvatarsIterator() {
        return bans.iterator();
    }

    /**
     * Checks to see if an avatar has administrative privileges in a chat room.
     *
     * @param avatarId The avatar that is being checked.
     * @return True if they are an administrator. Otherwise, false.
     */
    public boolean isAdmin(final ChatAvatarId avatarId) {
        return admins.contains(avatarId);
    }

    /**
     * Adds an avatar to the room as an admin. The avatar does not need to be present in order to be added as an
     * admin. When they join the room at a later time, they will have the administrative permissions. This operation
     * does not add the avatar to the room.
     *
     * @param avatarId The avatar that is being added as a room administrator.
     * @return True if the admin was added. Otherwise, false, including if they were already an admin.
     * @see #addAvatar(ChatAvatarId) To add an avatar to the room.
     */
    public boolean addAdmin(final ChatAvatarId avatarId) {
        return admins.add(avatarId);
    }

    /**
     * Removes an avatar as an administrator of the room.
     *
     * @param avatarId The avatar to remove as an administrator.
     * @return True if administrative privileges were removed from the avatar. Otherwise, false.
     * @see #removeAvatar(ChatAvatarId) To remove an avatar from the chat room.
     */
    public boolean removeAdmin(final ChatAvatarId avatarId) {
        return admins.remove(avatarId);
    }

    public Set<ChatAvatarId> getAdmins() {
        return Collections.unmodifiableSet(admins);
    }

    public Iterator<ChatAvatarId> getAdminsIterator() {
        return admins.iterator();
    }

    public boolean isInRoom(final ChatAvatarId avatarId) {
        return avatars.contains(avatarId);
    }

    public boolean addAvatar(final ChatAvatarId avatarId) {
        return avatars.add(avatarId);
    }

    public boolean removeAvatar(final ChatAvatarId avatarId) {
        return avatars.remove(avatarId);
    }

    public Set<ChatAvatarId> getAvatars() {
        return Collections.unmodifiableSet(avatars);
    }

    public Iterator<ChatAvatarId> getAvatarsIterator() {
        return avatars.iterator();
    }

    public boolean isInvited(final ChatAvatarId avatarId) {
        return invitees.contains(avatarId);
    }

    public boolean addAvatarInvitation(final ChatAvatarId avatarId) {
        return invitees.add(avatarId);
    }

    public boolean removeAvatarInvitation(final ChatAvatarId avatarId) {
        return invitees.remove(avatarId);
    }

    public Set<ChatAvatarId> getInvitedAvatars() {
        return Collections.unmodifiableSet(invitees);
    }

    public Iterator<ChatAvatarId> getInvitedAvatarsIterator() {
        return invitees.iterator();
    }

    public boolean isModerator(final ChatAvatarId avatarId) {
        return moderators.contains(avatarId);
    }

    public boolean addModerator(final ChatAvatarId avatarId) {
        return moderators.add(avatarId);
    }

    public boolean removeModerator(final ChatAvatarId avatarId) {
        return moderators.remove(avatarId);
    }

    public Set<ChatAvatarId> getModerators() {
        return Collections.unmodifiableSet(moderators);
    }

    public Iterator<ChatAvatarId> getModeratorsIterator() {
        return moderators.iterator();
    }


    /**
     * Creates a {@link ChatRoomData} object that is like a view model of this ChatRoom to be sent to the client.
     *
     * @return
     */
    public ChatRoomData createChatRoomData() {
        //TODO: Should we cache this object, and invalidate it on sets?
        final ChatRoomVisibility visibility = ChatRoomAttributes.isSet(attributes, ChatRoomAttributes.PRIVATE)
                ? ChatRoomVisibility.PRIVATE
                : ChatRoomVisibility.PUBLIC;

        return new ChatRoomData(
                id,
                visibility,
                name,
                owner,
                creator,
                topic,
                moderators,
                invitees,
                ChatRoomAttributes.isSet(attributes, ChatRoomAttributes.MODERATED));
    }
}
