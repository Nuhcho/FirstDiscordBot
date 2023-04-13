package com.Nacho.Event;

import com.Nacho.ICommand;
import com.Nacho.LavaPlayer.GuildMusicManager;
import com.Nacho.LavaPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class Repeat implements ICommand {
    @Override
    public String getName() {
        return "repeat";
    }

    @Override
    public String getDescription() {
        return "Turns on repeating songs";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();
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
        boolean isRepeat = !guildMusicManager.getTrackScheduler().isRepeat();
        guildMusicManager.getTrackScheduler().setRepeat(isRepeat);
        event.reply("Repeat is now " + isRepeat).queue();

    }
}
