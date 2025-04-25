<div align="center">
<img src="https://static.rtast.cn/static/icon/yesmotd-icon.png" alt="SDKIcon">

<h3>Made By <a href="https://github.com/RTAkland">RTAkland</a></h3>

<img src="https://static.rtast.cn/static/kotlin/made-with-kotlin.svg" alt="MadeWithKotlin">

<br>
<img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/DangoTown/YeeeesMOTD/main.yml">
<img alt="Kotlin Version" src="https://img.shields.io/badge/Kotlin-2.0.0-pink?logo=kotlin">
<img alt="GitHub" src="https://img.shields.io/github/license/RTAkland/YeeeesMOTD?logo=apache">

</div>

* 中文文档 [README](./README.md)

# Overview

This plugin can modify the server info like favicon, server motd. It has many features

1. Ip fingering (Use player's head as server favicon, not all player use one head, instead players will see themselves
   head as favicon)
2. Random motd (You can set the random motd list or use API to get random sentence)
3. Fake online players count
4. Fake max player count
5. Random favicon (Set the image by yourself)
6. Anti bot(Simple but it works)
7. Random version protocol version and description
8. Fake online players list

# Usage

## IP fingerprint

> This feature requires the server to enable online mode and have a public IP address`
> The IP fingerprint can record the player's login IP address in the game. The next time a player uses this IP to ping
> the server, the following effects will occur:（
> Display the head of the player's skin as the server icon to the player:

![showcase](./images/description.png)

[!IMPORTANT]
All platforms need java 21 to run expecting Bungeecord and Velocity They just need java 17 to run.

# Feature completeness

<details>
<summary>Expand</summary>

|                   Feature/Platform                   | Velocity | Paper(Folia Supported) | Spigot & Bukkit | BungeeCord |
|:----------------------------------------------------:|:--------:|:----------------------:|:---------------:|:----------:|
|                     IP Fingering                     |    ✅     |           ✅            |        ✅        |     ✅      |
|                Fake max players count                |    ✅     |           ✅            |        ✅        |     ✅      |
|               Fake online player count               |    ✅     |           ❌            |        ❌        |     ✅      |
|                     Random motd                      |    ✅     |           ✅            |        ✅        |     ✅      |
|                    Random favicon                    |    ✅     |           ✅            |        ✅        |     ✅      |
|                       Anti bot                       |    ✅     |           ✅            |        ✅        |     ✅      |
|                       hitokoto                       |    ✅     |           ✅            |        ✅        |     ✅      |
| Fake version protocol version number and description |    ✅     |           ❌            |        ❌        |     ✅      |
|                   Fake player list                   |    ✅     |           ❌            |        ❌        |     ✅      |

</details>

# Quickstart

Just select the correct platform of jar and put it in the plugins folder, start the server, then enjoy it !

> YeeeesMOTD Supported `Spigot` `Paper` `Purpur` `Velocity` `Bukkit` `Bungeecord` `Luminol` `Folia` `Leaves`
> and it forks/branch, the following server core is tested by me.

# Configure

## IP fingering

This feature needs to enable online-mode with public ip

It records the ip address of player, if once the player joined the server, the ip address will
be recorded, in the next player ping the server, then they can see the avatar of their skin as server favicon

To enable this feature you need to find the `ipFingerprint/enabled` and set it to true

![showcase](./images/description.png)

## Random server favicon

Before start, you need to prepare some png image and put them in the `plugins/YeeeesMotd/icons`
folder. The image size is not must be 64x64, if not plugin will auto resize the image to 64x64

> Use `/yeeeesmotd reload` to hot-reload the image cache

## Random motd

Opening the config file and find these lines

```json
 "descriptions": [
{
"line1": "<yellow><bold>YeeeesMOTD",
"line2": "<light_purple><bold>Powered by RTAkland: https://github.com/RTAkland"
},
{
"line1": "<#00ff00><bold>DangoTown 团子小镇 生电服务器欢迎你",
"line2": "<yellow><bold>https://dgtmc.top"
},
{
"line1": "<#00FFFF><bold>团子小镇是一个历史悠久的服务器",
"line2": "<#00FFFF><bold>服务器于2016年开服至今"
}
]
```

You can edit them by yourself.

# Fake max players and fake online players count

> Note: This feature is only works on velocity or bungeecord

Find these lines, and set it.

```json
"maximumPlayer": -1,
"onlinePlayer": -1,
"clearSamplePlayer": true
```

## Fake online player list

This feature can generate some fake player name and respond it to client

![fake_sample_player](./images/fake_sample_player.png)

```json
"fakeSamplePlayer": {
"enabled": true,
"fakePlayersCount": 10
},
```

**DO NOT set the value of fakePlayersCount over 400!**

If you want to use this feature, please set `clearSamplePlayer` to false

## Anti bot

This feature is a simple anti-bot feature, It requires ping the server first before 
join the server

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

# Fake version protocol number and description

This feature will respond the fake protocol number and its description to client,
but not really prevent player to join

![fake_protocol.png](images/fake_protocol.png)

```json
"fakeProtocol": {
"enabled": false,
"protocolNumberPool": [],
"protocolNamePool": [],
"alwaysInvalidProtocolNumber": false
},
```