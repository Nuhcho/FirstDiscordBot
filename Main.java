package com.Nacho;

import com.Nacho.Event.*;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException, RateLimitedException {

        final String TOKEN = "MTA5NDg4NDYzMjA5NDMyMjczOA.GittYR.KNm8kVqicVU-fsRI6uiAdOE_jkQcyUKuuZm9z0";
        JDABuilder jdaBuilder = JDABuilder.createDefault(TOKEN);
        JDA jda = jdaBuilder
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES)
                .addEventListeners(new ReadyEventsListener(), new MessageEventListener(), new InteractionEventListener(), new Listeners())
                .build();
        jda.upsertCommand("slash-cmd", "This is a slash command").setGuildOnly(true).queue();
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
        jda.addEventListener(manager);
    }
}

