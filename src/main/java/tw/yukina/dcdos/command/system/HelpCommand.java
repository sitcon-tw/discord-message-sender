package tw.yukina.dcdos.command.system;

import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import tw.yukina.dcdos.command.AbstractAssistantCommand;
import tw.yukina.dcdos.constants.Role;
import tw.yukina.dcdos.util.MessageSupplier;


@Component
@Command(name = "help")
public class HelpCommand extends AbstractAssistantCommand implements Runnable{

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.GUEST};
    }

    @Override
    public void run() {
        String message = "help me plz";

        getTelegramConfig().sendMessage(MessageSupplier.getMarkdownFormatBuilder()
                .chatId(String.valueOf(getChatId()))
                .text(message)
                .build());
    }
}
