<div align="center">
<img src="https://static.rtast.cn/static/icon/yesmotd-icon.png" alt="SDKIcon">

<h3>Made By <a href="https://github.com/RTAkland">RTAkland</a></h3>

<img src="https://static.rtast.cn/static/kotlin/made-with-kotlin.svg" alt="MadeWithKotlin">

<br>
<img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/DangoTown/YeeeesMOTD/main.yml">
<img alt="Kotlin Version" src="https://img.shields.io/badge/Kotlin-1.9.24-pink?logo=kotlin">
<img alt="GitHub" src="https://img.shields.io/github/license/RTAkland/YeeeesMOTD?logo=apache">

</div>

<h1>English document is NOT UPDATE TO DATE! please go to Chinese document and translate into english</h1>

* 中文文档 [README](./README.md)

# Overview

> It can personalize your server information, including: 'IP fingerprint', 'random MOTD description information', 'false
> number of online players',' false maximum number of players', 'random server icon', * anti pressure testing*

> Can be found in [` Releases `]（ https://github.com/RTAkland/YeeeesMOTD/releases/ ）Download the plugin and put it in
> the plugins folder to use it

# Usage

## IP fingerprint

> This feature requires the server to enable online mode and have a public IP address`
> The IP fingerprint can record the player's login IP address in the game. The next time a player uses this IP to ping
> the server, the following effects will occur:（
> Display the head of the player's skin as the server icon to the player:

! [showcase] (./images/description. png)

## Random server icon

> Random server icons need to be prepared in advance with a few 64x64 pixel sized images to be placed on the server's
> plugins/YeeeesMotd/icons`
> Within the folder and the image format must be ` png '`

> Using 'yesmotd reload' allows for hot reloading of server random icon lists

## MOTD information

> You need to start the files required for reverse proxy initialization first, which can be found in the '
> plugins/YeeeesMotd/' folder
> The 'descriptions. json' file, using any text editor (such as Windows built-in Notepad, vi, vim, etc.)
> The following effects need to be achieved:

! [description] (./images/description. png)

> You need to open 'config. json' and find the root of 'descriptions'. You can see the following content and modify the
> content of the corresponding line
> Among them, 'line1' represents the first line, and 'line2' represents the second line. The syntax
> supports [MiniMessage]（ https://github.com/KyoriPowered/adventure ）
> The following is the default description file

```json
[
  {
    "line1": "<yellow><bold>YeeeesMOTD",
    "line2": "<light_purple><bold>Powered by RTAkland: https://github.com/RTAkland"
  },
  {
    "line1": "<green><bold>DangoTown 团子小镇 生电服务器欢迎你",
    "line2": "<yellow><bold>https://dgtmc.top"
  }
]
```

### Online player list

> You need to open 'config. json' and find the words' maximumPlayer ',' onlinePlayer ', and' clearSamplePlayer '
> These three attributes represent: maximum number of players, number of online players, and whether to not display the
> online player list
> The default values for the three attributes are 'maximumPlayer': '-1' | 'onlinePlayer': '-1' | 'clearSamplePlayer': '
> false'`
> You can refer to the following figure:

! [playerlist] (./images/playerlist. png)

## Anti pressure testing

> This feature prevents malicious pressure on the server, so players need to ping the server once before entering,
> If the event of pinging the server and the entry time to the server are too long, it is necessary to ping the server
> again,
> The following is the configuration file

```json
{
  "pingPass": {
    "enabled": false,
    "pingFirstText": "Please ping the server first! / 请先在服务器列表Ping一次服务器",
    "pingAgainText": "Please ping the server again! / 请重新Ping一次服务器",
    "interval": 120
  }
  // ...
}
```

> Among them, 'enabled' indicates whether to enable anti pressure testing, and the default is' false '
> 'pingFirstText' indicates the message prompted when the player enters the server without pinging it first, and
> disconnects the player
> The 'rePingText' indicates a prompt message indicating that the time interval between the player ping the server and
> entering the server is too long
> 'interval' represents the maximum interval time between ping the server and entering the server, in seconds`

### MiniMessage

> Here we will show some usage of MiniMessage format

```json
[
  {
    "line1": "<yellow><bold>YeeeesMOTD",
    "line2": "<light_purple>test</light_purple>"
  },
  {
    // or use rgb color
    "line1": "<#00ff00><italic>DangoTown 团子小镇 生电服务器欢迎你",
    "line2": "<yellow><bold>https://dgtmc.top"
  }
]
```

##

> For more usage of MiniMessage, please go
> to [MiniMessage Documents]（ https://docs.advntr.dev/minimessage/format.html#standard -Tags)

# Precautions

* Can only be used on servers with genuine verification enabled. Offline servers cannot be used due to differences in player UUID calculation methods compared to genuine ones
* This plugin only works on servers with public IP addresses, and cannot be used if using FRP mapping technology

# Open source

- This project is open source under the license of [Apache-2.0] (./LICENSE), which means:
- You can directly use the features provided by this project without any authorization
- You can distribute, modify, and derive the source code at will without specifying the source copyright information

# Thanks

<div>

<img src="https://static.rtast.cn/static/other/jetbrains.png" alt="JetBrainsIcon" width="128">

<a href="https://www.jetbrains.com/opensource/"><code>JetBrains Open Source</code></a> 提供的强大IDE支持

</div>

