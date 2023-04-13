package com.Nacho.Event;

import com.Nacho.ICommand;
import com.Nacho.LavaPlayer.GuildMusicManager;
import com.Nacho.LavaPlayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Queue implements ICommand {

    public static int queuePos;
    public static int queuePosEnd;
    public static ArrayList<MessageEmbed> arrayEmbed = new ArrayList<MessageEmbed>();
    public static Button next;
    public static Button first;
    public static Button last;
    public static Button previous;
    @Override
    public String getName() {
        return "queue";
    }

    @Override
    public String getDescription() {
        return "Will display the current queue";
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
        List<AudioTrack> queue = new ArrayList<>(guildMusicManager.getTrackScheduler().getQueue());
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Current Queue");
        if(queue.isEmpty()) {
            embedBuilder.setDescription("Queue is empty");
        }
        else if(queue.size() < 11) {
            for (int i = 0; i < queue.size(); i++) {
                AudioTrackInfo info = queue.get(i).getInfo();
                embedBuilder.addField(i + 1 + ":", info.title, false);
            }
            event.replyEmbeds(embedBuilder.build()).queue();
        }

        else {
            int queueSize = queue.size();
            int lastEmbedSize = queueSize % 10;
            if (lastEmbedSize == 0) {
                lastEmbedSize = 10;
            }

            for (int i = 0; i < queueSize; i++) {
                AudioTrackInfo info = queue.get(i).getInfo();
                embedBuilder.addField((i + 1) + ":", info.title, false);
                if ((i + 1) % 10 == 0) {
                    arrayEmbed.add(embedBuilder.build());
                    embedBuilder.clear();
                }
            }

            if (lastEmbedSize > 0) {
                embedBuilder.clear();
                for (int i = queueSize - lastEmbedSize; i < queueSize; i++) {
                    AudioTrackInfo info = queue.get(i).getInfo();
                    embedBuilder.addField((i + 1) + ":", info.title, false);
                }
                arrayEmbed.add(embedBuilder.build());
            }
            queuePos = 0;
            queuePosEnd = arrayEmbed.size()-1;
            next = Button.primary("next-button", ">");
            previous = Button.primary("previous-button", "<");
            first = Button.primary("first-button", "<<");
            last = Button.primary("last-button", ">>");
            MessageCreateData message = new MessageCreateBuilder()
                    .setEmbeds(arrayEmbed.get(queuePos))
                    .setComponents(ActionRow.of(first, previous, next, last))
                    .build();
            event.reply(message).queue();
        }


    }

}
