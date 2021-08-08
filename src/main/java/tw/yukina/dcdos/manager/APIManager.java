package tw.yukina.dcdos.manager;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import tw.yukina.dcdos.config.TelegramConfig;
import tw.yukina.dcdos.util.MessageSupplier;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;

@Service
public class APIManager {

    private APIManager(){
    }

    synchronized public void active(){
    }

    private void checkHibernate(){
    }

    public void hibernate(){
    }

    private void restart(){
    }

    private void sshConnect(String command){

    }
}
