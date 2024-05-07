<div align="center">
<img src="https://static.rtast.cn/static/icon/yesmotd-icon.png" alt="SDKIcon">

<h3>Made By <a href="https://github.com/RTAkland">RTAkland</a></h3>

<img src="https://static.rtast.cn/static/kotlin/made-with-kotlin.svg" alt="MadeWithKotlin">

<br>
<img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/DangoTown/YeeeesMOTD/main.yml">
<img alt="Kotlin Version" src="https://img.shields.io/badge/Kotlin-1.9.23-pink?logo=kotlin">
<img alt="GitHub" src="https://img.shields.io/github/license/DangoTown/YeeeesMOTD?logo=apache">

</div>

# 概述

> 可以让你的服务器信息个性化,包括:ip指纹、 随机MOTD描述信息、 虚假的在线玩家数、虚假的最大玩家数、随机服务器icon



# 使用

> 本项目为`fabric`模组, 仅在服务端生效, 可以在releases中找到对应的游戏版本, 
> 使用本模组需要安装`fabric api` 以及 `fabric-language-kotlin`

# 使用展示
>ip指纹可以记录玩家登陆游戏的ip在下次玩家使用这个ip ping服务器的时候就会有如下效果(将玩家皮肤的头作为服务器的icon显示给玩家):

<img src="https://static.rtast.cn/static/Minecraft%201.20.6%202024_5_7%2013_37_13.png" alt="showcase">

> 随机服务器的icon需要提前准备好多张64x64像素大小的图片放入服务器`config/yeeeesMotd/icons`文件夹内且图片格式必须为`png`

> 使用`yesmotd reload`可以热重载服务器随机icon列表

# 开源

- 本项目以[Apache-2.0](./LICENSE)许可开源, 即:
    - 你可以直接使用该项目提供的功能, 无需任何授权
    - 你可以在**注明来源版权信息**的情况下对源代码进行任意分发和修改以及衍生

# 鸣谢

<div>

<img src="https://static.rtast.cn/static/other/jetbrains.png" alt="JetBrainsIcon" width="128">

<a href="https://www.jetbrains.com/opensource/"><code>JetBrains Open Source</code></a> 提供的强大IDE支持

</div>