package tw.yukina.dcdos.command.discord;

import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
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

import java.util.Objects;

@Component
@Command(name = "pushMessage", description = "手動傳送訊息至 Discord 訊息頻道", subcommands = {CommandLine.HelpCommand.class})
public class DiscordWebhook extends AbstractAssistantCommand implements Runnable {

    @CommandLine.Parameters(description = "使用者名稱")
    private String username;

    @CommandLine.Parameters(description = "推送內容")
    private String content;

    @CommandLine.Option(names = {"-avatar", "--avatarUrl"}, paramLabel = "<avatarUrl>", defaultValue = "", description = "頭像圖片網址")
    private String avatarUrl;

    @CommandLine.Option(names = {"-hook", "--DiscordWebhook"}, paramLabel = "<DiscordWebhook>", defaultValue = "")
    private String discordWebhook;

    @Value("${discord.webhook}")
    private String discordWebhookDefault;

    @Value("${discord.gap.url}")
    private String gapBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    @SneakyThrows
    public void run() {
        if(Objects.equals(discordWebhook, ""))discordWebhook = discordWebhookDefault;
        pushMessage();
    }

    private void pushMessage(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> requestEntity =
                new HttpEntity<>("{\n" +
                        "  \"username\": \"" + username + "\",\n" +
                        "  \"avatar_url\": \"" + avatarUrl + "\",\n" +
                        "  \"content\": \"" + content + "\"\n" +
                        "}", headers);
        restTemplate.exchange("https://discord.com/api/webhooks/" + discordWebhook, HttpMethod.POST, requestEntity,
                String.class);
    }

    @Override
    public String getCommandName() {
        return "pushMessage";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.GUEST};
    }
}
