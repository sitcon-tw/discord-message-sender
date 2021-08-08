package tw.yukina.dcdos.command.system;

import lombok.SneakyThrows;
import net.bytebuddy.implementation.bytecode.Throw;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import tw.yukina.dcdos.command.AbstractAssistantCommand;
import tw.yukina.dcdos.constants.Role;
import tw.yukina.dcdos.entity.DiscordMessage;
import tw.yukina.dcdos.manager.telegram.TelegramUserInfoManager;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@Command(name = "discordMessage", description = "", subcommands = {CommandLine.HelpCommand.class})
public class DiscordWebhook extends AbstractAssistantCommand implements Runnable {

    @CommandLine.Parameters(index = "0", paramLabel = "<start>", description = "從哪裡開始傳送訊息，直接輸入左側行數即可，不需考慮陣列從零開始等問題")
    private int start;

    @CommandLine.Parameters(index = "1", paramLabel = "<end>", description = "傳到哪一行結束，如果只傳一個訊息請重複 start 參數")
    private int end;

    @CommandLine.Option(names = {"-hook", "--DiscordWebhook"}, paramLabel = "<DiscordWebhook>", defaultValue = "")
    private String discordWebhook;

    @CommandLine.Option(names = {"-delay"}, paramLabel = "<DelayMillisecond>", description = "每一則訊息的傳送延遲，預設為每個字 1 秒（1000）", defaultValue = "1000")
    private int delay;

    @Value("${discord.webhook}")
    private String discordWebhookDefault;

    @Value("${discord.gap.url}")
    private String gapBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    @SneakyThrows
    public void run() {
        if(Objects.equals(discordWebhook, ""))discordWebhook = discordWebhookDefault;

        JSONArray jsonArray = getDiscordMessage();
        for (int i = 0; i < jsonArray.length(); i++) {
            if (i != 0) {
                int messageDelay = packageDiscordMessage(jsonArray.getJSONObject(i - 1)).getContent().length() * delay;
                Thread.sleep(messageDelay);
            }
            pushMessage(packageDiscordMessage(jsonArray.getJSONObject(i)));
        }
    }

    private DiscordMessage packageDiscordMessage(JSONObject jsonObject){
        DiscordMessage discordMessage = new DiscordMessage();
        discordMessage.setUsername(jsonObject.getString("username"));
        discordMessage.setAvatarUrl(jsonObject.getString("avatarUrl"));
        discordMessage.setContent(jsonObject.getString("content"));
        return discordMessage;
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

    private JSONArray getDiscordMessage(){
        ResponseEntity<String> response =
                restTemplate.getForEntity(
                         gapBaseUrl + "?script=MessageLoader&"
                            + "start=" + start
                            + "&end=" + end,
                        String.class);

        return new JSONArray(response.getBody());
    }

    @Override
    public String getCommandName() {
        return "discordMessage";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.GUEST};
    }
}
