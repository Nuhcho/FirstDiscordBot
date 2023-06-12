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

public class Ban implements ICommand {
    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public String getDescription() {
        return "Bans user for a certain amount of time";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.USER, "banned", "The user to ban", true));
        options.add(new OptionData(OptionType.INTEGER, "length", "The length to ban", true));
        options.add(new OptionData(OptionType.STRING, "reason", "The reason to ban", false));
        return options;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        try {
            Member member = event.getMember();
            if (member.hasPermission(Permission.BAN_MEMBERS)) {
                Member banned = event.getOption("banned").getAsMember();
                int length = event.getOption("length").getAsInt();
                OptionMapping reason = event.getOption("reason");
                if (reason == null) {
                    banned.ban(length, TimeUnit.DAYS).queue();
                } else {
                    banned.ban(length, TimeUnit.DAYS).reason(reason.getAsString()).queue();
                }
                event.reply("Banned " + banned.getAsMention() + " for " + length + " days.").setEphemeral(true).queue();
            } else {
                event.reply("You do not have the required permission to execute this command").setEphemeral(true).queue();
            }
        } catch (Exception e) {
            event.reply("An error occurred while executing the command: " + e.getMessage()).setEphemeral(true).queue();
        }
    }
}

