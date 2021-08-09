package tw.yukina.dcdos.command.discord;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import picocli.CommandLine;
import tw.yukina.dcdos.command.AbstractDiscordSubCommand;
import tw.yukina.dcdos.entity.DiscordMessage;
import tw.yukina.dcdos.repository.DiscordMessageRepository;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("DuplicatedCode")
@CommandLine.Command(name = "update", description = "從 Google 試算表下載並重新整理訊息列表")
public class UpdateDiscordMessages extends AbstractDiscordSubCommand implements Runnable{

    private final RestTemplate restTemplate = new RestTemplate();

    public static boolean hasInit = false;

    public static void checkInit(DiscordCommand discordCommand){
        if(!UpdateDiscordMessages.hasInit){
            List<String> parameter = new ArrayList<>();
            parameter.add(0, "--ChatId");
            parameter.add(1, String.valueOf(discordCommand.getChatId()));
            parameter.add("update");
            String[] args = parameter.toArray(new String[0]);
            new CommandLine(discordCommand).setUsageHelpWidth(100).execute(args);
        }

    }

    @Override
    public void run() {
        DiscordMessageRepository discordMessageRepository = parentCommand.getDiscordMessageRepository();
        discordMessageRepository.deleteAll();

        List<DiscordMessage> discordMessageList = new ArrayList<>();

        JSONArray jsonArray = getDiscordMessages();
        for (int i = 0; i < jsonArray.length(); i++) {
            discordMessageList.add(packageDiscordMessage(jsonArray.getJSONObject(i), i));
        }

        discordMessageRepository.saveAll(discordMessageList);
        UpdateDiscordMessages.hasInit = true;
        parentCommand.sendMessageToChatId("Done");
    }

    private DiscordMessage packageDiscordMessage(JSONObject jsonObject, int arrayIndex){

        DiscordMessage discordMessage = new DiscordMessage();
        discordMessage.setSheetIndex(arrayIndex + 2);
        discordMessage.setUsername(jsonObject.getString("username"));
        discordMessage.setAvatarUrl(jsonObject.getString("avatarUrl"));
        discordMessage.setContent(jsonObject.getString("content"));

        if(jsonObject.has("hasDelay")) discordMessage.setDelay(jsonObject.getBoolean("hasDelay"));
        return discordMessage;
    }


    private JSONArray getDiscordMessages(){
        ResponseEntity<String> response =
                restTemplate.getForEntity(
                        parentCommand.getGapBaseUrl() + "?script=MessageLoader&"
                                + "start=" + 2,
                        String.class);

        return new JSONArray(response.getBody());
    }
}
