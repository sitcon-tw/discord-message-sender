```
Usage: discordMessage -h -delay=<DelayMillisecond> -hook=<DiscordWebhook> <start> <end>

      <start>   從哪裡開始傳送訊息，直接輸入左側行數即可，不需考慮陣列從零開始等問題
      <end>     傳到哪一行結束，如果只傳一個訊息請重複 start 參數
      -delay=<DelayMillisecond>
                每一則訊息的傳送延遲，預設為每個字 1 秒（1000）
  -h, --help    display this help message
      -hook, --DiscordWebhook=<DiscordWebhook>

Commands:
  help  Displays help information about the specified command
```
