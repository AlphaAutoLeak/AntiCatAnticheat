# AntiCatAnticheat
- [演示视频](https://b23.tv/ltnquRW)
这是反`定制猫反`的软件源代码,支持2.7.5版本以下。
- 不支持更新最新版本
- 最新版本已和谐此方法
## How to work (`如何工作`)
- 通过安全管理器替换dll路径
- Hook发包方法,进行数据修改

# 配置文件使用说明
`配置文件(动态配置文件夹里,每个用户名字都不同)`
只在反猫反以下版本出现
* (1.12.2 高于0.5版本)
* (1.7.10 高于0.8版本)

----

## 配置文件生成

``` 
将反猫反放入mods文件夹启动游戏,即可在D盘`acacxxxx`文件夹下生成文件。
```

## 注意

``` 
在游戏时,删除配置文件后.系统无法读取自定义数据.
可能会出现被服务器t出的问题
配置文件修改一次,每次发包时都会读取系统自定义数据
```

----

## 配置文件解析
- 配置文件
```
{
  "autoMode": false,
  "qqList": [
    9203328310,
    9285038638,
    4786471524
  ],
  "excludeList": [
    "chentg",
    "alphaautoleak",
    "AZRAELPROTECTFANCHENPROTECT"
  ],
  "cancelleScreenShot": false,
  "enableCustomScreenShotText": true,
  "screenShotText": "qq951397728",
  "macInfo": "Realtek PCIe GBE Family Controller",
  "mac": [
    49,
    33,
    71,
    42,
    17,
    24
  ]
}
```
- 参数的解释

|            参数             |   可填入类型   |        含义        |    默认值    |
| :------------------------: | :-----------: | :----------------: | ------------ |
|          automode          | false 与 true | false(否) true(是) | false(否)    |
|           qqList           |    数字类型    |      伪造的QQ       | 随机10位数字 |
|        excludeList         |    字符类型    |    排除禁止的mod    | 如上所示     |
|     cancelleScreenShot     | false 与 true | false(否) true(是) | false(否)    |
| enableCustomScreenShotText | false 与 true | false(否) true(是) | false(否)    |
|       screenShotText       |    字符类型    |  自定义假截图的水印  | 宣传qq群     |
|          macInfo           | 数字与字符类型  |     假网卡信息      | 如上所示     |
|            mac             |    数字类型    |   数字类型网卡地址   | 如上所示     |

----

## AutoMode
```
"autoMode": true,
```
当你填入以上时

- 系统会不读取配置文件中的自定义信息,而是随机生成


```
"autoMode": false,
```
当你填入以上时

- 系统会读取配置文件中的自定义信息,进行发送你自己的信息

----

## QQList
- 腐竹无法获取你的完整qq号，但可以从加密后字符来判断

以下为后台数据
![image](https://github.com/AlphaAutoLeak/AntiCatAnticheat/blob/master/img/364182202245189.png)

- 修改前
```
  "qqList": [
    9203328310,
    9285038638,
    4786471524
  ],
```

- 添加qq
```
  "qqList": [
    9203328310,
    9285038638,
    4786471524,
    1236548900,
    2531674258,
    9430593543
  ],
```  

- 修改qq
```
  "qqList": [
    9203666310,
    9277778638,
    4786471994
  ],
```
----
## CancelleScreenShot

**这个功能打开可以让你拦截猫反的截图**

```
"cancelleScreenShot": true,
```
当你填入以上时

- 对截图包进行拦截,并发送假截图


```
"cancelleScreenShot": false,
```
当你填入以上时

- 不对截图包进行拦截,发送的为猫反原本的截图

---

## EnableCustomScreenShotText

**打开自定义截图水印( 需要将CancelleScreenShot设置为 `true` )**

```
"enableCustomScreenShotText": true,
```
当你填入以上时
![image](https://github.com/AlphaAutoLeak/AntiCatAnticheat/blob/master/img/68212002247687.png)
- 对水印绘制进行拦截,修改为你自己所定义的内容(可能存在`编码问题`,自行解决`文件使用ANSI编码`)


```
"enableCustomScreenShotText": false,
```
当你填入以上时

![image](https://github.com/AlphaAutoLeak/AntiCatAnticheat/blob/master/img/308121902232203.png)
- 不启用拦截水印绘制

---

## ScreenShotText

> (可能存在`编码问题`,自行解决: `文件使用ANSI编码`)

**自定义的截图水印内容(需启用 `enableCustomScreenShotText` 与 `CancelleScreenShot` )**

- 默认值

```
"screenShotText": "qq951397728",
```

- 自定义内容

```
"screenShotText": "NMSL",
```

或

```
"screenShotText": "123NMSL",
```


--- 

## MacInfo & Mac

> 网卡信息,用于 `机器码封禁`

- 修改原则
- 数值组合不得超过`6组`.
- 重在修改mac中的`数值`(可以`减少`或者`增加`各位数)
- 次在macInfo的`字符`,因为这一串字符,腐竹可以在后台看见。

后台数据
![image](https://github.com/AlphaAutoLeak/AntiCatAnticheat/blob/master/img/4301702236449.png)

- 修改前
```
"macInfo": "Realtek PCIe GBE Family Controller",
  "mac": [
    49,
    33,
    71,
    42,
    17,
    24
  ]
```

- 修改后

```
"macInfo": "Intel Corporaton 82545E Gigabit Ethenet Cntoller",
  "mac": [
    42,
    34,
    76,
    48,
    10,
    21
  ]
```

- 或者

```
"macInfo": "Realtek PCIe GBE Family Controller123",
  "mac": [
    44,
    31,
    75,
    42,
    17,
    24
  ]
```

### 错误修改演示
```
"macInfo": "Realtek PCIe GBE Family Controller123",
  "mac": [
    44,
    31,
    75,
    42,
    17,
    24,
    44,
  ]
```
