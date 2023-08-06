package ru.brikster.chatty.chat.message.transform.stage.early;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.brikster.chatty.api.chat.message.context.MessageContext;
import ru.brikster.chatty.api.chat.message.strategy.MessageTransformStrategy;
import ru.brikster.chatty.api.chat.message.strategy.result.MessageTransformResult;
import ru.brikster.chatty.api.chat.range.Ranges;
import ru.brikster.chatty.chat.message.transform.result.MessageTransformResultBuilder;

import javax.inject.Singleton;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public final class RangeLimiterStrategy implements MessageTransformStrategy<String> {

    @Override
    public @NotNull MessageTransformResult<String> handle(MessageContext<String> context) {
        Set<Player> recipients = Bukkit.getOnlinePlayers()
                .stream()
                .filter(recipient -> Ranges.isApplicable(context.getSender(), recipient, context.getChat().getRange()))
                .collect(Collectors.toSet());
        return MessageTransformResultBuilder.<String>fromContext(context)
                .withRecipients(recipients)
                .build();
    }

    @Override
    public @NotNull Stage getStage() {
        return Stage.EARLY;
    }

}