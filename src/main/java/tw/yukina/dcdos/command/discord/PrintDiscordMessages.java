package tw.yukina.dcdos.command.discord;

import picocli.CommandLine;
import tw.yukina.dcdos.command.AbstractDiscordSubCommand;
import tw.yukina.dcdos.entity.DiscordMessage;

@SuppressWarnings("DuplicatedCode")
@CommandLine.Command(name = "print", description = "將給定範圍的訊息回覆至指定傳送者的 Telegram 私訊", subcommands = {CommandLine.HelpCommand.class})
public class PrintDiscordMessages extends AbstractDiscordSubCommand implements Runnable{

    @CommandLine.Parameters(index = "0", paramLabel = "<start>", description = "從哪裡開始傳送訊息，直接輸入左側行數即可，不需考慮陣列從零開始等問題")
    private int start;

    @CommandLine.Parameters(index = "1", paramLabel = "<end>", description = "傳到哪一行結束，如果只傳一個訊息請重複 start 參數")
    private int end;

    @Override
    public void run() {
        UpdateDiscordMessages.checkInit(parentCommand);

        for(int i = start; i <= end; i++){
            DiscordMessage discordMessage = parentCommand.getDiscordMessageRepository().findBySheetIndex(i);
            if(discordMessage == null){
                parentCommand.sendMessageToChatId("DiscordMessage at sheet's index " + i + " could not be found");
                break;
            }
            parentCommand.sendMessageToChatId(discordMessage.toString());
        }
    }
}
