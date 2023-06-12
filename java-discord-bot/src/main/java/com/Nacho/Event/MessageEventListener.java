package com.Nacho.Event;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.ArrayList;

public class MessageEventListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);
        System.out.println("User sent: " + event.getMessage().getContentDisplay());
        
/*
//For when user is mentioned
        String getEvent = event.getMessage().getContentRaw();
        if(getEvent.startsWith("<@") && getEvent.endsWith(">")) {
            getEvent = getEvent.substring(getEvent.indexOf(">"));
        }
//Fun stuff
        if(getEvent.contains("1") && !event.getAuthor().isBot()) {
            event.getChannel().sendMessage("1 on apush lol").queue();
        }
        else if(getEvent.contains("5") && !event.getAuthor().isBot()) {
            event.getChannel().sendMessage("combined 5 lol").queue();
        }
    */

        ArrayList<String> bannableWords = new ArrayList<String>();
        bannableWords.add("nigger");
        bannableWords.add("nigga");
        bannableWords.add("fag");
        bannableWords.add("chink");
        bannableWords.add("coon");

        for(int i = 0; i < bannableWords.size(); i++) {
            if(event.getMessage().getContentRaw().equals(bannableWords.get(i).toLowerCase())) {
                event.getMessage().delete().reason("No no word").queue();
            }
        }


    }
}
