package com.Nacho.Event;

import com.Nacho.ICommand;
import com.Nacho.LavaPlayer.GuildMusicManager;
import com.Nacho.LavaPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class Skip implements ICommand {
    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getDescription() {
        return "Will skip one song";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.STRING, "amount", "The amount to skip", false));
        return options;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        try {
            Member member = event.getMember();
            GuildVoiceState memberVoiceState = member.getVoiceState();
            OptionMapping ifNull = event.getOption("amount");

            if(!memberVoiceState.inAudioChannel()) {
                event.reply("You must be in an audio channel").queue();
                return; //Empty return ends the code since it is in a void method.
            }

            Member self = event.getGuild().getSelfMember();
            GuildVoiceState selfVoiceState = self.getVoiceState();

            if(!selfVoiceState.inAudioChannel()) {
                event.reply("I am not in an audio channel").queue();
                return;
            }
            if(selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
                event.reply("You are not in the same channel as me").queue();
                return;
            }
            GuildMusicManager guildMusicManager = PlayerManager.get().getGuildMusicManager(event.getGuild());
            if(ifNull == null) {
                guildMusicManager.getTrackScheduler().getPlayer().stopTrack();
            }

            else {
                int amount = ifNull.getAsInt();
                for(int i = 0; i < amount; i++) {
                    guildMusicManager.getTrackScheduler().getPlayer().stopTrack();
                }
            }
            event.reply("Skipped").queue();
        } catch (Exception e) {
            event.reply("An error occurred while executing the command: " + e.getMessage()).setEphemeral(true).queue();
        }
    }
}
