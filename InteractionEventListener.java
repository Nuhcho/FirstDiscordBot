package com.Nacho.Event;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class InteractionEventListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);
        System.out.println("Interaction");
        switch (event.getName()) {
            case "slash-cmd":
                event.reply("1 On Apush").setEphemeral(true).queue();
                break;
        }

    }
}
