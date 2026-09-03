---
name: jnpf-code-edit
description: 当用户说"代码修改"、"修改功能"类似字样激活或用户说使用编辑功能时激活
---

# jnpf 代码修改
可以将现成的jnpf工程项目中代码进行修改。协助对已经生成到项目的代码进行新增和编辑。

## 重要原则
首先需要先读取下外面jnpf-skill-base文件夹下的所有md，里面包含信息获取原则，skill工具环境信息使用原则。sql和请求怎么处理。后面执行动作都要参考。相当于前置说明。

## git操作规范
<font style="color:rgb(15, 17, 21);">在执行前，请确认以下两点：</font>

1. **<font style="color:rgb(15, 17, 21);">确保你在 Git 仓库根目录</font>**<font style="color:rgb(15, 17, 21);">（执行</font><font style="color:rgb(15, 17, 21);"> </font>`<font style="color:rgb(15, 17, 21);background-color:rgb(235, 238, 242);">git rev-parse --show-toplevel</font>`<font style="color:rgb(15, 17, 21);"> </font><font style="color:rgb(15, 17, 21);">检查）。</font>
2. **<font style="color:rgb(15, 17, 21);">确保没有正在进行中的合并/变基操作</font>**<font style="color:rgb(15, 17, 21);">（执行 </font>`<font style="color:rgb(15, 17, 21);background-color:rgb(235, 238, 242);">git status</font>`<font style="color:rgb(15, 17, 21);"> 确认，状态应为 </font>`<font style="color:rgb(15, 17, 21);background-color:rgb(235, 238, 242);">clean</font>`<font style="color:rgb(15, 17, 21);"> 或显示有未暂存改动，但不能有 </font>`<font style="color:rgb(15, 17, 21);background-color:rgb(235, 238, 242);">rebase in progress</font>`<font style="color:rgb(15, 17, 21);"> 等提示）</font>

如果环境没问题，则可以进行处理代码更新的操作。

## 步骤一：确认操作位置
如果不知道修改的文件内容在哪里需要和用户问一下功能的位置，前端和后端代码位置后才能依据来判断。判断下是新增还是编辑字段。新增是增加对应的代码和json配置，修改是对现有的json配置编辑修改。下面所有涉及json对象的修改要注意添加前要注意先对前面内容或属性补一个逗号。避免报错



后端的修改是根据表来生成代码。比如表f_main_test，则生成文件名规则

F_main_testController.java, F_main_testForm.java,F_main_testPagination.java

F_main_testServiceImpl.java, F_main_testEntity.java. 但是结构是xxxServiceImpl.java。

但是要知道xxx的内容就是具体表，只是现在举例的是f_main_test。



用户可以通过关键字方式来引导ai定位代码编辑的位置

如：用户说代码修改，前端extend目录，后端模块jnpf-example下，关键字f_main_test。

这时候就按照规则找前端默认是src/views/extend 下的f_main_test文件夹

后端默认是对应jnpf-example模块的位置下的f_main_test前缀开头的文件。



然后就可以进行修改了。修改需要注意每次新的修改周期都要遵循git diff修改步骤。

## 步骤二：对项目代码git在暂存
执行步骤二需要先查看git操作规范。

此步骤目的是<font style="color:rgb(15, 17, 21);">创建临时提交，</font>先在项目的根目录执行命令

`<font style="color:rgb(15, 17, 21);background-color:rgb(235, 238, 242);">git add -A && git commit -m "temporary checkpoint for AI diff" --no-verify</font>`<font style="color:rgb(15, 17, 21);"> 。注意前后端都需要进行。</font>

### <font style="color:rgb(15, 17, 21);">步骤三：生成代码逻辑替换到项目中</font>
这个步骤需要调用技能jnpf-code-publish。里面的步骤二，生成菜单到指定平台低码模式。

最终完成效果是，把现成的某设计的代码生成到项目中。

## 步骤四：对比代码
`<font style="color:rgb(15, 17, 21);background-color:rgb(235, 238, 242);">执行git diff HEAD</font>`<font style="color:rgb(15, 17, 21);background-color:rgb(235, 238, 242);">查看差异进行比对后更新代码到项目。注意如果是用户自己新加的代码不用删除。</font>

## <font style="color:rgb(15, 17, 21);background-color:rgb(235, 238, 242);">步骤五：还原git暂存代码</font>
1. `**<font style="color:rgb(15, 17, 21);background-color:rgb(235, 238, 242);">git reset --soft HEAD~1</font>**`<font style="color:rgb(15, 17, 21);"> </font><font style="color:rgb(15, 17, 21);">（</font>**<font style="color:rgb(15, 17, 21);">这一步必须带</font>****<font style="color:rgb(15, 17, 21);"> </font>**`**<font style="color:rgb(15, 17, 21);background-color:rgb(235, 238, 242);">--soft HEAD~1</font>**`<font style="color:rgb(15, 17, 21);">，撤销提交并回到原位）</font>
2. <font style="color:rgb(15, 17, 21);">执行 </font>`<font style="color:rgb(15, 17, 21);background-color:rgb(235, 238, 242);">git reset</font>`<font style="color:rgb(15, 17, 21);"> 把它们放回工作区。</font>



