package com.Nacho;

import com.Nacho.Event.Queue;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;

public class Listeners extends ListenerAdapter {

    @Override
    public void onButtonInteraction (@NotNull ButtonInteractionEvent event) {
        if(!event.getButton().getId().equals("verify")) {
            if (event.getButton().getId().equals("next-button")) {
                if (Queue.queuePos < Queue.queuePosEnd) {
                    Queue.queuePos++;
                }
            } else if (event.getButton().getId().equals("previous-button")) {
                if (Queue.queuePos > 0) {
                    Queue.queuePos--;
                }
            } else if (event.getButton().getId().equals("first-button")) {
                Queue.queuePos = 0;
            } else if (event.getButton().getId().equals("last-button")) {
                Queue.queuePos = Queue.queuePosEnd;
            }

            MessageCreateData message = new MessageCreateBuilder()
                    .setEmbeds(Queue.arrayEmbed.get(Queue.queuePos))
                    .setComponents(ActionRow.of(Queue.first, Queue.previous, Queue.next, Queue.last))
                    .build();
            event.reply(message).queue();
        }

        if (event.getButton().getId().equals("verify")) {
            Member member = event.getMember();
            Guild guild = event.getGuild();
            if (member == null) {
                System.out.println("Unable to find member.");
                return;
            }
            Role commoner = guild.getRoleById(1102155034650759259L);
            if (commoner == null) {
                System.out.println("Unable to find role.");
                return;
            }
            if (!member.hasPermission(Permission.VIEW_CHANNEL)) {
                guild.addRoleToMember(member, commoner).queue();
                event.reply("Access Granted.").setEphemeral(true).queue();
            }
            else {
                event.reply("You already have access.").setEphemeral(true).queue();
            }
        }

    }
}

