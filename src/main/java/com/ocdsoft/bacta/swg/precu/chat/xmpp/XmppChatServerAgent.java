package com.ocdsoft.bacta.swg.precu.chat.xmpp;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.soe.object.chat.ChatAvatarId;
import com.ocdsoft.bacta.soe.object.chat.InvalidChatAvatarIdException;
import com.ocdsoft.bacta.swg.precu.chat.ChatServerAgent;
import com.ocdsoft.bacta.swg.precu.chat.ChatServerAgentHandler;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public final class XmppChatServerAgent extends ChatServerAgent {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final XMPPConnection connection;
    private final ChatAvatarId avatarId;
    private final BactaConfiguration configuration;

    private final Map<String, Chat> chats = new ConcurrentHashMap<>();

    @Inject
    public XmppChatServerAgent(
            BactaConfiguration configuration,
            @Assisted ChatAvatarId avatarId,
            @Assisted ChatServerAgentHandler handler) {

        super(handler);

        this.configuration = configuration;
        this.avatarId = avatarId;

        String host = configuration.getStringWithDefault("Bacta/ChatServer", "Host", "127.0.0.1");
        int port = configuration.getIntWithDefault("Bacta/ChatServer", "Port", 5222);

        ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration(host, port);
        connectionConfiguration.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);

        this.connection = new XMPPTCPConnection(connectionConfiguration);

        logger.info(String.format("Attempting to configure connection to chat server at %s:%d.", host, port));
    }

    @Override
    public void connect() {
        logger.info("Connecting to chat server.");
        try {
            connection.connect();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        logger.info("Disconnecting from chat server.");
        try {
            connection.disconnect();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            //Not connected....so what???
        }
    }

    @Override
    public void login(String password) {
        try {
            connection.login(avatarId.toString(), password);

            registerListeners();

            logger.info("Connected and logged in to remote chat server.");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void register(ChatAvatarId avatarId, String password) {
        try {
            AccountManager.getInstance(connection).createAccount(avatarId.toString(), password);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public ChatAvatarId getAvatarId() {
        return avatarId;
    }

    @Override
    public void addFriend(ChatAvatarId friendId) {
        final String host = configuration.getStringWithDefault("Bacta/ChatServer", "Host", "127.0.0.1");

        try {
            logger.info("Adding friend to roster");
            Roster roster = connection.getRoster();
            roster.createEntry(
                    String.format("%s@%s", friendId.toString(), host),
                    friendId.getName(),
                    null);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        } finally {
            getHandler().onFriendAdded(friendId);
        }
    }

    @Override
    public void removeFriend(ChatAvatarId friendId) {
        try {
            Roster roster = connection.getRoster();
            roster.removeEntry(roster.getEntry(friendId.toString()));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public List<ChatAvatarId> retrieveFriends() {
        try {
            Roster roster = connection.getRoster();

            List<ChatAvatarId> friends = new ArrayList<ChatAvatarId>();

            for (RosterEntry entry : roster.getEntries())
                friends.add(new ChatAvatarId(entry.getUser()));

            return friends;
        } catch (InvalidChatAvatarIdException ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void sendPersistentMessage(ChatAvatarId recipientId, String subject, String body) {
        // TODO Auto-generated method stub
    }

    @Override
    public void sendInstantMessage(final ChatAvatarId recipientId, final String body, final int sequenceId) {
        int errorCode = 0;

        try {
            final String recipient = recipientId.toString();

            logger.info("Sending a message to recipient.");

            if (body.length() > 3000) {
                errorCode = 16; //too long.
            } else {

                //TODO: Implement ignore check. Make sure recipient isn't ignoring this user. Error Code = 9.

                //TODO: Implement online check.

                Chat chat = chats.get(recipient);

                //TODO: Figure out a way so that we can get the host from the connection rather than config.
                String host = configuration.getStringWithDefault("Bacta/ChatServer", "Host", "127.0.0.1");

                if (chat == null)
                    chat = ChatManager.getInstanceFor(connection).createChat(
                            String.format("%s@%s", recipient, host),
                            new ChatMessageListener());

                logger.info("Chat message dispatching.");
                Message message = new Message(recipient, Message.Type.chat);
                message.setBody(body);
                chat.sendMessage(message);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
            errorCode = 1;
        } finally {
            getHandler().onInstantMessageSent(recipientId, body, sequenceId, errorCode);
        }
    }

    @Override
    public void sendRoomMessage(final String roomId, final String body) {
        // TODO Auto-generated method stub

    }

    private void registerListeners() {
        //connection.addConnectionListener(connectionListener); closes or fails.
        //connection.addPacketListener(packetListener, packetFilter); can this handle incoming chat packets?

        ChatManager.getInstanceFor(connection).addChatListener(new ChatManagerListener() {

            @Override
            public void chatCreated(Chat chat, boolean createdLocally) {
                if (!createdLocally) {
                    chat.addMessageListener(new ChatMessageListener());
                    chats.put(chat.getParticipant(), chat);
                }
            }

        });

        connection.getRoster().addRosterListener(new RosterListener() {

            @Override
            public void entriesAdded(Collection<String> addresses) {
                logger.info("Added " + addresses.size() + " entries to roster:");
                for (String address : addresses) {
                    logger.info("-Entry: " + address);
                }
            }

            @Override
            public void entriesUpdated(Collection<String> addresses) {
                // TODO Auto-generated method stub

            }

            @Override
            public void entriesDeleted(Collection<String> addresses) {
                // TODO Auto-generated method stub

            }

            @Override
            public void presenceChanged(Presence presence) {
                try {
                    logger.info("presence changed listener");
                    final ChatAvatarId friendId = new ChatAvatarId(presence.getFrom());
                    final boolean status = connection.getRoster().getPresence(presence.getFrom()).isAvailable();

                    getHandler().onFriendStatusChanged(friendId, status);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            }

        });
    }

    private class ChatMessageListener implements MessageListener {

        @Override
        public void processMessage(Chat chat, Message message) {
            try {
                ChatAvatarId sender = new ChatAvatarId(chat.getParticipant());
                getHandler().onInstantMessageReceived(sender, message.getBody());
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }

    }
}
