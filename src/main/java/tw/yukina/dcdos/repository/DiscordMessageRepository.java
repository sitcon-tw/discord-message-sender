package tw.yukina.dcdos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.yukina.dcdos.entity.DiscordMessage;

@Repository
public interface DiscordMessageRepository extends JpaRepository<DiscordMessage, Long> {
    DiscordMessage findBySheetIndex(int sheetIndex);
}
