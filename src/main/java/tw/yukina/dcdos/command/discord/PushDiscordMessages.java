package tw.yukina.dcdos.command.discord;

import lombok.SneakyThrows;
import org.json.JSONArray;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import picocli.CommandLine;
import tw.yukina.dcdos.command.AbstractDiscordSubCommand;
import tw.yukina.dcdos.entity.DiscordMessage;
import tw.yukina.dcdos.repository.DiscordMessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("DuplicatedCode")
@CommandLine.Command(name = "push", description = "推送指定範圍的訊息至 Discord 訊息頻道", subcommands = {CommandLine.HelpCommand.class})
public class PushDiscordMessages extends AbstractDiscordSubCommand implements Runnable{

    @CommandLine.Parameters(index = "0", paramLabel = "<start>", description = "從哪裡開始傳送訊息，直接輸入左側行數即可，不需考慮陣列從零開始等問題")
    private int start;

    @CommandLine.Parameters(index = "1", paramLabel = "<end>", description = "傳到哪一行結束，如果只傳一個訊息請重複 start 參數")
    private int end;

    @CommandLine.Option(names = {"-hook", "--DiscordWebhook"}, paramLabel = "<DiscordWebhook>", defaultValue = "")
    private String discordWebhook;

    @CommandLine.Option(names = {"-delay"}, paramLabel = "<DelayMillisecond>", description = "每一則訊息的傳送延遲，預設為每個字 0.5 秒（500）", defaultValue = "1000")
    private int delay;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    @SneakyThrows
    @SuppressWarnings("BusyWait")
    public void run() {
        UpdateDiscordMessages.checkInit(parentCommand);

        if(Objects.equals(discordWebhook, ""))discordWebhook = parentCommand.getDiscordWebhookDefault();
        DiscordMessageRepository discordMessageRepository = parentCommand.getDiscordMessageRepository();

        for(int i = start; i <= end; i++){
            DiscordMessage discordMessage = discordMessageRepository.findBySheetIndex(i);
            if(discordMessage == null){
                parentCommand.sendMessageToChatId("DiscordMessage at sheet's index " + i + " could not be found");
                break;
            }

            if( i != 2 && discordMessage.isDelay()) {
                int messageDelay = discordMessageRepository.findBySheetIndex(i - 1).getContent().length() * delay;
                Thread.sleep(messageDelay);
            }

            pushMessage(discordMessage);
        }
    }

    private void pushMessage(DiscordMessage discordMessage){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> requestEntity =
                new HttpEntity<>("{\n" +
                        "  \"username\": \"" + discordMessage.getUsername() + "\",\n" +
                        "  \"avatar_url\": \"" + discordMessage.getAvatarUrl() + "\",\n" +
                        "  \"content\": \"" + discordMessage.getContent() + "\"\n" +
                        "}", headers);
        restTemplate.exchange("https://discord.com/api/webhooks/" + discordWebhook, HttpMethod.POST, requestEntity,
                String.class);
    }
}
