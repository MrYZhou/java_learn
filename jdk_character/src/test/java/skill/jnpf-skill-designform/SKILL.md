---
name: jnpf-skill-designform
description: 用于 JNPF 低代码平台表单设计、代码生成、API 调试等任务。当用户提到“设计表单”、“JNPF”、“生成代码”时激活。如说""创建模块"、"新增功能"、"建表"、"新增设计"，"创建设计"时激活
---

# jnpf 设计生成器
将自然语言需求转换为 jnpf功能设计。

## 重要原则
1.首先需要先读取下外面jnpf-skill-base文件夹下的所有md，里面包含信息获取原则，skill工具环境信息使用原则。sql和请求怎么处理。后面执行动作都要参考。相当于前置说明。
2.注意关于发送请求apigenkey和mode都要带上，apigenkey在脚本里面，mode在线用的值是online.我下面请求举的示例代码不在赘述

## 步骤一：提示用户创建一个表单
如果没有找到项目信息，需要先提示用户告诉前端项目路径，和后端项目路径方便代码生成。



输出：用户你好，欢迎使用ai代码生成器，你可以输入 “新增设计，商品信息功能”。

## 步骤二：输出表结构信息
理解步骤一的需求，给出建议的字段信息按照表格展示给客户。字段需要带上前缀f_,需要提示用户表结构信息让用户确定。输出一个表格形式给用户判断，下面是一个参考表格形式。

| 字段 | 控件标识 | 数据库类型 | 说明 |
| --- | --- | --- | --- |
| f_goods_name | input | varchar | 商品名称 |
| f_goods_detail | textarea | text | 商品描述 |
| f_price | inputnumber | int | 价格 |


后续创表脚本要默认加主键f_id 数据库类型是varchar(50),但是不展示



总之此步骤要让用户确定表结构是否符合要求。然后问要不要帮助执行，客户说可以，执行sql，同时不管要不要自动执行，都需要生成sql脚本到桌面，方便后续更新。



执行完成后，结果信息给用户确认后才可以下一步。可以是客户自己执行，也可以是自动执行。

自动执行需要使用上面的脚本功能

---
python  ../jnpf-skill-base/jnpf-assistant.py sqlexecute 你生成的新建表sql.
---

## 步骤三：生成代码生成的表单json配置
先生成fields 的数组，里面有各个字段属性。



如果遇到类型是，下拉选择，需要额外补充生成一些信息。

---python
{
    "dataType":'', // 数据源类型
    "dictionaryType": "", // 字典id
    "propsUrl": "", // 数据接口的id
    "propsName": "", // 数据接口名称
    "props": {
        "label": "fullName",
        "value": "id"
    },  // props默认是这样，除非用户有说改掉。
    "options":[
    
    ] // 数据下拉项
}
---

---python
例子1：有个性别字段，类型是单选框组，那么需要给我这些
"dataType": "static",
"dictionaryType": "",
"propsUrl": "",
"propsName": "",
"props": {
    "label": "fullName",
    "value": "id"
},
"options":[
    {
        "fullName": "男",
        "id": "1"
    },
    {
        "fullName": "女",
        "id": "2"
    }
]
静态类型然后id一般就是数字就好简单点，这个是不一样的就好。1，2，3顺序下来就可以。

例子2：有个字典类型
如果用户有说用什么字典可以去查询下数据库。
查询逻辑：用户某个字段说用数据字典物料属性。
执行命令 "python  ../jnpf-skill-base/jnpf-assistant.py sqlexecute select f_id,f_full_name from base_dictionary_type WHERE f_full_name like '%物料属性%' "
物料属性是客户说的。如果数据库找到有就推荐给用户确认。没有提示用户没有类似
的字典类型，不能自己随意创建。如果找到多条数据，需要让用户确定用哪一条。
返回的格式是获取到f_id的值，配置如下。
"dataType": "dictionary",
"dictionaryType": "773861723890270917",  // 把得到的f_id值填到dictionaryType
"propsUrl": "",  
"propsName": "",
"props": {
    "label": "fullName",
    "value": "id"
},

例子3：有个数据接口
如果用户有说用什么数据接口可以去查询下数据库。
查询逻辑：用户某个字段说用数据接口查询树形物料分组列表，这个是接口名。
执行命令 "python  ../jnpf-skill-base/jnpf-assistant.py sqlexecute select f_id,f_full_name from base_data_interface where f_full_name like '%查询树形物料分组列表%'"
其他的数据忽略，找到f_id | f_full_name  2列

返回的格式是获取到f_id的值，配置如下。
"dataType": "dynamic",
"dictionaryType": "",
"propsUrl": "765599931758019525",  // 把得到的f_id值填到propsUrl
"propsName": "物料分组：查询树形物料分组列表",  // 把这个f_full_name填充到这个propsName属性
"props": {
    "label": "fullName",
    "value": "id"
},
如果数据库找到有就推荐给用户确认。没有提示用户没有类似
的数据接口，不能自己随意创建。如果找到多条数据，需要让用户确定用哪一条。


---



这个步骤按照上面的类型判断，基本格式是如下，我只是列举了一些但是具体还得补充上去

---sass
{
    "fields": [
        {
            "jnpfKey": "input",
            "controlTag": "JnpfInput",
            "label": "商品名",
            "vModel": "f_goods_name"
        },
        {
          "jnpfKey": "radio",
          "controlTag": "JnpfRadio",
          "label": "性别",
          "vModel": "f_sex",
          "dataType": "static",
          "dictionaryType": "",
          "propsUrl": "",
          "propsName": "",
          "props": {
              "label": "fullName",
              "value": "id"
          },
          "options":[
              {
                  "fullName": "男",
                  "id": "1"
              },
              {
                  "fullName": "女",
                  "id": "2"
              }
          ]
        }
    ],
    "fullName": "商品信息功能"
}

上面描述了字段列表，和功能名称信息，字段列表里面有罗列了 input类型，和数据源类型的3种来源，
静态，字典，和数据接口的方式。
---

---python
python  ../jnpf-skill-base/jnpf-assistant.py netpost /api/visualdev/Base/codegen python  ../jnpf-skill-base/jnpf-assistant.py netpost /api/visualdev/Base/codegen {\"fields\":[{\"jnpfKey\":\"radio\",\"controlTag\":\"JnpfRadio\",\"label\":\"性别\",\"vModel\":\"f_sex\",\"dataType\":\"static\",\"dictionaryType\":\"\",\"propsUrl\":\"\",\"propsName\":\"\",\"options\":[{\"fullName\":\"男\",\"id\":\"1\"},{\"fullName\":\"女\",\"id\":\"2\"}],}],\"fullName\":\"商品信息功能\",\"mode\":\"online\",\"apigenkey\":\"在脚本里面\"}
---

最后可以输出提示用户

✅ 代码表单配置初始化完成



### 生成的结构有子表信息
如果你判断某个功能有子表存在，或用户指定了子表则需要补充子表的结果信息

---sass
子表的结构类似如下

{
  "tables":[
  [{
            "jnpfKey": "input",
            "controlTag": "JnpfInput",
            "label": "商品名",
            "vModel": "f_goods_name"
        }],
  [{
            "jnpfKey": "input",
            "controlTag": "JnpfInput",
            "label": "别名",
            "vModel": "f_alias_name"
        }]
  ]
}
假设有2个子表就是一个二维数组，里面的内容结构和上面提到的fields一样。  
---



## 步骤四：调用接口生成设计
上面步骤二完成后应该可以得到一个数据结构，把这个结构调用后端post请求.

---
大概结构是执行 python  ../jnpf-skill-base/jnpf-assistant.py" netpost [请求地址] [内容参数]
内容参数大致如下：
{
  fields:[{jnpfKey:'控制标识',controlTag:'控件前端tag',label:'字段含义',vModel:'字段数据库key'}]
  fullName:'', 
}
需要把上一个步骤你生成的数据填充上面的参数，fullName是生成功能名称。如客户说生成一个商品信息功能
fullName可以是商品信息功能,注意传的参数要注意json转义。是传的json字符串带转义。
类似：
{\"fields\":[{\"jnpfKey\":\"input\",\"controlTag\":\"JnpfInput\",\"label\":\"商品名\",\"vModel\":\"f_goods_name\"},{\"jnpfKey\":\"radio\",\"controlTag\":\"JnpfRadio\",\"label\":\"性别\",\"vModel\":\"f_sex\",\"dataType\":\"static\",\"dictionaryType\":\"\",\"propsUrl\":\"\",\"propsName\":\"\",\"options\":[{\"fullName\":\"男\",\"id\":\"1\"},{\"fullName\":\"女\",\"id\":\"2\"}],}],\"fullName\":\"商品信息功能\"}

---

下面是调用命令，

---
下面可能是一个比较实际的后面你需要帮我执行的请求。
python  ../jnpf-skill-base/jnpf-assistant.py netpost /api/visualdev/Base/codegen  {\"fields\":[{\"jnpfKey\":\"input\",\"controlTag\":\"JnpfInput\",\"label\":\"商品名\",\"vModel\":\"f_goods_name\"}],\"fullName\":\"商品信息功能\"}


解析回来的结果，如果出现200，提示用户生成成功，否则失败，失败不能继续执行下面步骤。成功的话可以
得到一个id数据。记住这个id.
---

## 步骤五：生成菜单到指定平台（可选）
设计完成后，可以说提示用户是否要生成在平台。如果客户选稍后自行发布，则流程结束，如果选要发布，那么请参考 jnpf-code-publish 的skill内容继续执行。

## jnpf-skill.md文件
---sass
这个文件是辅助用的，里面的信息也很关键通常可能会记录环境信息，和一些行为的补充，如果项目工程目录
下有的吗要读取下理解意思

---

