```
Usage: /discord -h COMMAND
   -h, --help   display this help message
 Commands:
   help    Displays help information about the specified command
   update  從 Google 試算表下載並重新整理訊息列表
   print   將給定範圍的訊息回覆至指定傳送者的 Telegram 私訊
   push    推送指定範圍的訊息至 Discord 訊息頻道
```
```
Usage: /discord update -h
從 Google 試算表下載並重新整理訊息列表
```
```
Usage: /discord print -h <start> <end> COMMAND
將給定範圍的訊息回覆至指定傳送者的 Telegram 私訊
      <start>   從哪裡開始傳送訊息，直接輸入左側行數即可，不需考慮陣列從零開始等問題
      <end>     傳到哪一行結束，如果只傳一個訊息請重複 start 參數
  -h, --help    display this help message
Commands:
  help  Displays help information about the specified command
```
```
Usage: /discord push -h -delay=<DelayMillisecond> -hook=<DiscordWebhook> <start> <end>
                     COMMAND
推送指定範圍的訊息至 Discord 訊息頻道
      <start>   從哪裡開始傳送訊息，直接輸入左側行數即可，不需考慮陣列從零開始等問題
      <end>     傳到哪一行結束，如果只傳一個訊息請重複 start 參數
      -delay=<DelayMillisecond>
                每一則訊息的傳送延遲，預設為每個字 0.5 秒（500）
  -h, --help    display this help message
      -hook, --DiscordWebhook=<DiscordWebhook>

Commands:
  help  Displays help information about the specified command
```

```
Usage: pushMessage -h -avatar=<avatarUrl> -hook=<DiscordWebhook> <username> <content>
                    COMMAND
手動傳送訊息至 Discord 訊息頻道
      <username>   使用者名稱
      <content>    推送內容
      -avatar, --avatarUrl=<avatarUrl>
                   頭像圖片網址
  -h, --help       display this help message
      -hook, --DiscordWebhook=<DiscordWebhook>

Commands:
  help  Displays help information about the specified command
```