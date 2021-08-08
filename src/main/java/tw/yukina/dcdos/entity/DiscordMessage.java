package tw.yukina.dcdos.entity;

import lombok.Data;

@Data
public class DiscordMessage {
    private String username;
    private String avatarUrl;
    private String content;
    private boolean delay;
}
