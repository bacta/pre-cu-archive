package com.ocdsoft.bacta.swg.precu.chat;


public class EstablishChatServerConnectionTask implements Runnable {

    private final ChatServerAgent agent;
    private final String password;

    public EstablishChatServerConnectionTask(ChatServerAgent agent, String password) {
        this.agent = agent;
        this.password = password;
    }

    @Override
    public void run() {
        try {

            agent.connect();
            agent.login(password);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
