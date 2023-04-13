package com.Nacho.Event;

import com.Nacho.ICommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class Clear implements ICommand {
    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "Clears a certain amount of messages";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.INTEGER, "amount", "Amount that you want to clear", true));
        return options;
    }

    @Override
    public void execute (SlashCommandInteractionEvent event) {
        int amount = event.getOption("amount").getAsInt();
        List<Message> messageList = event.getChannel().getHistory().retrievePast(amount).complete();
        event.getChannel().purgeMessages(messageList);
        event.reply("Deleting " + amount + " messages.").queue();



    }

}
