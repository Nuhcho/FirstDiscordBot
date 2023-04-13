package com.Nacho.Event;

import com.Nacho.ICommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class ClearAll implements ICommand {
    @Override
    public String getName() {
        return "clearall";
    }

    @Override
    public String getDescription() {
        return "Clears all messages in a channel";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        try {
            List<Message> messageList = event.getChannel().getHistory().retrievePast(100).complete();
            event.getChannel().purgeMessages(messageList);
            while (messageList.size() == 100) {
                messageList = event.getChannel().getHistory().retrievePast(100).complete();
                event.getChannel().purgeMessages(messageList);
                Thread.sleep(500);
            }
            event.reply("Deleting all messages.").queue();
        } catch (Exception e) {
            event.reply("Failed to delete all messages.").queue();
            e.printStackTrace();
        }

    }
}
