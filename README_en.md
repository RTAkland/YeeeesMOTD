<div align="center">
<img src="https://static.rtast.cn/static/icon/yesmotd-icon.png" alt="SDKIcon">

<h3>Made By <a href="https://github.com/RTAkland">RTAkland</a></h3>

<img src="https://static.rtast.cn/static/kotlin/made-with-kotlin.svg" alt="MadeWithKotlin">

<br>
<img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/DangoTown/YeeeesMOTD/main.yml">
<img alt="Kotlin Version" src="https://img.shields.io/badge/Kotlin-1.9.24-pink?logo=kotlin">
<img alt="GitHub" src="https://img.shields.io/github/license/RTAkland/YeeeesMOTD?logo=apache">

</div>

* 中文文档 [README](./README.md)

# Overview

> You can personalize your server information, including: IP fingerprint, random MOTD description information, false
> number of online players, false maximum number of players, random server icon

# Usage

> This project is a [`Velocity`](https://velocitypowered.com/) plug-in,
> You can download the plug-in in [`Releases`](https://github.com/RTAkland/YeeeesMOTD/releases/), and put the plug-in
> into the plugins folder to use it

# Showcase

> IP fingerprint can record the IP of the player logging into the game. The next time the player uses this IP to ping
> the server, it will have the following effect (
> Display the player skin's head as the server's icon to the player):

<img src="./images/description.png" alt="showcase">

> The icon of the random server needs to prepare multiple `64x64` pixel size pictures in advance and put them into the
> server `plugins/YeeeesMotd/icons`
> In the folder and the image format must be `png`

> Use `yesmotd reload` to hot reload the server's random icon list

## Random MOTD

> You need to start the files required for velocity initialization first, which you can find in
> the `plugins/YeeeesMotd/` folder
> `descriptions.json` file, use any text editor (for example: Notepad that comes with Windows, vi, vim, etc.)
> The following effects need to be achieved:

<img src="./images/description.png" alt="description">

> You need to open `description.json`. After opening, you can see the following content, which can be modified or added
> for more MOTD information.
> Among them, `line1` represents the first line, `line2` represents the second line, and the syntax
> supports [MiniMessage](https://github.com/KyoriPowered/adventure)
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

## MiniMessage

> Here we will show some usage of MiniMessage format

```json
[
  {
    "line1": "<yellow><bold>YeeeesMOTD",
    // Here it means yellow and bold
    "line2": "<light_purple>test</light_purple>"
    // You can also use pairs of tags to control exactly which ones require color
  },
  {
    "line1": "<#00ff00><italic>DangoTown 团子小镇 生电服务器欢迎你",
    // You can also directly use RGB hexadecimal numbers to represent colors
    "line2": "<yellow><bold>https://dgtmc.top"
  }
]
```

> For more usage, please go to [MiniMessage Docs](https://docs.advntr.dev/minimessage/format.html#standard-tags)

# Precautions

* This plugin only works properly with `Velocity`.
* Can only be used on servers with online mode turned on. Offline servers cannot be used.
* This plugin only takes effect on servers with public IP addresses.

# Open Source

- This project is open source under the [Apache-2.0](./LICENSE) license, that is:
    - You can directly use the functions provided by the project without any authorization
    - You can distribute, modify and derive the source code at will provided that you indicate the source copyright
      information**

# Thanks

<div>

<img src="https://static.rtast.cn/static/other/jetbrains.png" alt="JetBrainsIcon" width="128">

Thanks to the powerful IDE support by the <a href="https://www.jetbrains.com/opensource/"><code>JetBrains Open Source</code></a> project

</div>
