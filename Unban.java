package com.Nacho.Event;

import com.Nacho.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Unban implements ICommand {
    @Override
    public String getName() {
        return "unban";
    }

    @Override
    public String getDescription() {
        return "Will unban user";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.USER, "unban", "The user to unban", true));
        return options;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        if(member.hasPermission(Permission.BAN_MEMBERS)) {
            Member unbanned = event.getOption("unban").getAsMember();
            event.getGuild().unban(unbanned);
            event.reply("Unbanning" + unbanned);
        } else {
            event.reply("You do not have the required permission to execute this command").queue();
        }

    }
}
