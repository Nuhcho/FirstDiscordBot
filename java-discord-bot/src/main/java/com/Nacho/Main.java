package com.Nacho;

import com.Nacho.Event.*;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException, InterruptedException {

        final String TOKEN = "MTA5NDg4NDYzMjA5NDMyMjczOA.GKVZhN.RnO2Wlo7t-DF0JpcsuFBpRfEMXATlFftdxAmEo";
        JDABuilder jdaBuilder = JDABuilder.createDefault(TOKEN);
        jdaBuilder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS);
        jdaBuilder.addEventListeners(new ReadyEventsListener(), new MessageEventListener(), new Listeners());
        JDA jda = jdaBuilder.build();



        CommandManager manager = new CommandManager();
        manager.add(new Play());
        manager.add(new Queue());
        manager.add(new NowPlaying());
        manager.add(new Skip());
        manager.add(new Stop());
        manager.add(new Repeat());
        manager.add(new Clear());
        manager.add(new ClearAll());
        manager.add(new Unban());
        manager.add(new Ban());
        manager.add(new SkipAll());
        jda.addEventListener(manager);
        jda.awaitReady();

        
        //Verification
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Verification");
        embedBuilder.setDescription("Click the green check mark to verify yourself");
        Guild guild = jda.getGuildById(1102155034650759258L); // replace GUILD_ID with your guild ID
        if (guild == null) {
            System.out.println("Unable to find the guild.");
            return;
        }
        TextChannel channel = guild.getTextChannelById(1102155035321835551L); // replace channel ID with your channel ID
        if (channel == null) {
            System.out.println("Unable to find the text channel.");
            return;
        }
        Button verifyButton = Button.primary("verify", Emoji.fromUnicode("\u2705"));
        MessageCreateData message = new MessageCreateBuilder()
                .setEmbeds(embedBuilder.build())
                .setComponents(ActionRow.of(verifyButton))
                .build();
        channel.sendMessage(message).queue();
        


    }
}

