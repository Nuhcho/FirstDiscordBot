package com.Nacho;

import com.Nacho.Event.Queue;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;

public class Listeners extends ListenerAdapter {

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if(event.getButton().getId().equals("next-button")) {
            Queue a = new Queue();
            MessageCreateData message = new MessageCreateBuilder()
                    .setEmbeds(Queue.arrayEmbed.get(Queue.queuePos + 1))
                    .setComponents(ActionRow.of(Queue.first, Queue.previous, Queue.next, Queue.last))
                    .build();
            Queue.queuePos+=1;
            event.reply(message).queue();

        }
        else if(event.getButton().getId().equals("previous-button")) {
            Queue a = new Queue();
            if (Queue.queuePos == 0) {
                MessageCreateData message = new MessageCreateBuilder()
                        .setEmbeds(Queue.arrayEmbed.get(Queue.queuePos))
                        .setComponents(ActionRow.of(Queue.first, Queue.previous, Queue.next, Queue.last))
                        .build();
                event.reply(message).queue();
            }
            else{
                MessageCreateData message = new MessageCreateBuilder()
                        .setEmbeds(Queue.arrayEmbed.get(Queue.queuePos-1))
                        .setComponents(ActionRow.of(Queue.first, Queue.previous, Queue.next, Queue.last))
                        .build();
                Queue.queuePos-=1;
                event.reply(message).queue();
            }

        }
        else if(event.getButton().getId().equals("first-button")) {
            Queue a = new Queue();
            MessageCreateData message = new MessageCreateBuilder()
                    .setEmbeds(Queue.arrayEmbed.get(0))
                    .setComponents(ActionRow.of(Queue.first, Queue.previous, Queue.next, Queue.last))
                    .build();
            Queue.queuePos = 0;
            event.reply(message).queue();

        }
        else if(event.getButton().getId().equals("last-button")) {
            Queue a = new Queue();
            MessageCreateData message = new MessageCreateBuilder()
                    .setEmbeds(Queue.arrayEmbed.get(Queue.queuePosEnd))
                    .setComponents(ActionRow.of(Queue.first, Queue.previous, Queue.next, Queue.last))
                    .build();
            Queue.queuePos = Queue.queuePosEnd;
            event.reply(message).queue();

        }

    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        if(event.getModalId().equals("person-modal")) {
            ModalMapping nameValue = event.getValue("name-field");
            ModalMapping ageValue = event.getValue("age-field");
            ModalMapping descriptionValue = event.getValue("description-field");

            String name = nameValue.getAsString();
            String description = descriptionValue.getAsString();

            String age;
            if(ageValue.getAsString().isBlank()) {
                age = "N/A";
            } else {
                age = ageValue.getAsString();
            }

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle(name);
            builder.setDescription("The description of " + name);
            builder.addField("Name", name, false);
            builder.addField("Age", age, false);
            builder.addField("Description", description, false);
            event.replyEmbeds(builder.build()).queue();
        }
    }
}

