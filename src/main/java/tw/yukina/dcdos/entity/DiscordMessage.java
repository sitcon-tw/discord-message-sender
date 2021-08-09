package tw.yukina.dcdos.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DiscordMessage extends AbstractEntity {

    @NonNull
    @Column(unique = true)
    private int sheetIndex;

    private String username;
    private String avatarUrl;
    private String content;
    private boolean delay;
}
