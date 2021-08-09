package tw.yukina.dcdos.command;

import lombok.Getter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import tw.yukina.dcdos.command.discord.DiscordCommand;
import tw.yukina.dcdos.command.system.user.UserCommand;

@Command
@Getter
public abstract class AbstractDiscordSubCommand {
    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    public boolean usageHelpRequested;

    @ParentCommand
    public DiscordCommand parentCommand;
}
