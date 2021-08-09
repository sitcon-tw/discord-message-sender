package tw.yukina.dcdos.command.discord;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import tw.yukina.dcdos.command.AbstractAssistantCommand;
import tw.yukina.dcdos.constants.Role;
import tw.yukina.dcdos.repository.DiscordMessageRepository;

@Getter
@Component
@CommandLine.Command(name = "/discord", subcommands = {CommandLine.HelpCommand.class, UpdateDiscordMessages.class
                    , PrintDiscordMessages.class, PushDiscordMessages.class})
public class DiscordCommand extends AbstractAssistantCommand {

    @Autowired
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    private DiscordMessageRepository discordMessageRepository;

    @Value("${discord.gap.url}")
    private String gapBaseUrl;

    @Value("${discord.webhook}")
    private String discordWebhookDefault;

    @Override
    public String getCommandName() {
        return "discord";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.GUEST};
    }

}
