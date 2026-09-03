---
name: jnpf-code-publish
description: 当用户说"代码发布"、"发布功能"，"发布设计"时激活
---

# jnpf 代码发布
我是jnpf功能发布者，可以将现成的jnpd功能设计发布到在线或工程项目中。你可以协助用户执行现成设计发布和代码生成的发布流程

## 重要原则
1.首先需要先读取下外面jnpf-skill-base文件夹下的所有md，里面包含信息获取原则，skill工具环境信息使用原则。sql和请求怎么处理。后面执行动作都要参考。相当于前置说明。

2.注意关于发送请求apigenkey和mode都要带上，apigenkey在脚本里面，mode在线用的值是"mode":"online".代码模式是用的"mode":"lowcode"我下面请求举的示例代码不在赘述。


## 步骤一：确定要发布的设计
用户说发布到某功能，或者自己询问用户要发布的功能。**先要提示是发布在线模式还是低码模式**



场景1：用户说发布设计，商品信息功能。先找到这个设计有的话可以进行下一步，没有的话提示用户没有类似的设计。

---plain
调用脚本执行命令
例子：python  ./jnpf-assistant.py sqlexecute select f_id,f_full_name,f_system_id from base_visual_dev where f_full_name like '%商品信息功能%'

结果可以得到，有可能是多个。让用户自己选一个发布,接口不能支持一下子多发布。先记录下id.

f_id | f_full_name | f_system_id
------------------
842003733222004037 | 商品信息功能 | 664077319056394183
842003733222004068 | 商品信息功能02 | 664077319056394183
842008159106110853 | 商品信息功能03 | 664077319056394183

f_system_id这个不需要展示给用户看。但是先记住下，下面找菜单要用
---

然后要确定发布的菜单上级。用户可能会说挂在开发示例下，那么

---plain
调用脚本执行命令
例子：python  ./jnpf-assistant.py sqlexecute select f_id,f_full_name from base_module where f_full_name like '%开发示例%' and f_type =1 and f_system_id = '664077319056394183'

f_type=1表示找的是目录，f_system_id限定下平台下的菜单。
结果类似
f_id | f_full_name
------------------
41FC1581-2A8A-48DA-A207-E1266C4A893F | 开发示例
拿到f_id后面作为菜单id传参，先记下。
---

## **步骤二：生成菜单到指定平台**在线模式
---plain
调用命令
python  ./jnpf-assistant.py netpost  /api/visualdev/Base/codepublish {"id":"842421080114075205",
"menuId":"41FC1581-2A8A-48DA-A207-E1266C4A893F","mode":"online"}

然后可以看到输出是有200状态码说明成功的就可以了，友好提示成功。没成功就停止行为，
不需要重复自己执行操作改正，可以等客户指示
---

## **步骤二：生成菜单到指定平台**低码模式
例子2：用户说发布设计，商品信息功能。选低码模式

---plain
先调用命令
格式：python  ./jnpf-assistant.py netpost  /api/visualdev/Generater/codeDownUrl
{id:"842008159106110853", // 上面得到的设计id
description:"商品信息功能", // 功能描述
enableFlow:0, // 是非流程，1是流程，一般用非流程
module:"example", //传编码支持example,extend
modulePackageName:"jnpf",//  固定用jnpf
apigenkey:"FASJUWABD"
} 
请求结果可以得到：一个临时下载链接地址，类似
url:"/api/visualdev/Generater/codeDown?encryption=47D872CDD9ACC8CE5BD98EB2F458796F5194DD667FD7A48E47C680276C4F9453CEA9F6B83E2F0754C3313C3B4E93DCA475E59BCB1ED1A1433B1AB33993864834&name=商品信息功能4_20260707180128.zip"


调用命令把上面的下载链接请求下
python ./jnpf-assistant.py download "/api/visualdev/Generater/codeDown?encryption=47D872CDD9ACC8CE5BD98EB2F458796F5194DD667FD7A48E47C680276C4F9453CEA9F6B83E2F0754C3313C3B4E93DCA475E59BCB1ED1A1433B1AB33993864834&name=商品信息功能4_20260707180128.zip&apigenkey=FASJUWABD
&systemId=664077319056394183"

apigenkey和systemId是你自己在拼接的。
成功返回得话，会输出一个id信息这个是下面步骤2.2可能会用的。先记下
输出是成功的，友好提示成功。没成功就停止行为，不需要重复自己执行操作改正，可以等客户指示
---

### 步骤2.1：把生成的文件移动到对应的项目工程
---sass
在上面下载后，脚本会自动解压到downloads下面。
前端代码目录路径类似
移动1：.\downloads\商品信息功能03_20260709114138\html\web
把这边路径下文件夹放到前端工程web项目的\src\views\extend下面

移动2：.\downloads\商品信息功能03_20260709114138\html\app 
把这边路径下文件夹放到前端工程app项目的\src\pages下面. （暂时忽略目前无app工程）

移动3：后端代码目录路径类似
.\downloads\商品信息功能03_20260709114138\java\jnpf-example
把这边路径下文件夹放到后端工程项目下。一般是根目录。
---



其他注意：如果客户有配置自动生成到的目录（通常在项目下如果有jnpf-skill.md里面信息要记得读取），可以帮助客户直接生成到项目的具体位置。或者输入让你生成到目录的要求。就自动生成。

### 步骤2.2：提示是否自动发布菜单
这个步骤是针对代码生成模式的发布。有个表单回传功能的自动处理。给出选择让用户是自动还是稍后自行发布。

可以先说开发示例平台的设计商品信心功能待发布，请选择是否自动发布或稍后自行发布

如果选择自行发布。步骤2.2结束。如果选让自动发布，需要让用户确认

是否发布功能xxx到xxx平台.有可能需要改表单设计名字。如用户说修改名称为商品信息02

---markdown
调用命令，区别是如果有改功能名需要额外补充一个name属性
python  ./jnpf-assistant.py netpost  /api/visualdev/Base/codepublish {"id":"842421080114075205",
"menuId":"41FC1581-2A8A-48DA-A207-E1266C4A893F","mode":"code","name":"商品功能02"}

---





## 
