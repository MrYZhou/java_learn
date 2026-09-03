---
name: jnpf-code-edit
description: 当用户说"代码修改"、"修改功能"类似字样激活或用户说使用编辑功能时激活
---





# jnpf 代码修改
可以将现成的jnpf工程项目中代码进行修改。协助对已经生成到项目的代码进行新增和编辑。

## 重要原则
首先需要先读取下外面jnpf-skill-base文件夹下的所有md，里面包含信息获取原则，skill工具环境信息使用原则。sql和请求怎么处理。后面执行动作都要参考。相当于前置说明。

## 步骤一
如果不知道修改的文件内容在哪里需要和用户问一下功能的位置，前端和后端代码位置后才能依据来判断。判断下是新增还是编辑字段。新增是增加对应的代码和json配置，修改是对现有的json配置编辑修改。下面所有涉及json对象的修改要注意添加前要注意先对前面内容或属性补一个逗号。避免报错



后端的修改是根据表来生成代码。比如表f_main_test，则生成文件名规则

F_main_testController.java, F_main_testForm.java,F_main_testPagination.java

F_main_testServiceImpl.java, F_main_testEntity.java. 但是结构是xxxServiceImpl.java。

但是要知道xxx的内容就是具体表，只是现在举例的是f_main_test。



所以用户可能有几种方式来问

方式一：基于代码发布后在修改

代码修改商品信息功能

---
这种方式需要基于代码发布功能skill生成的。因为是基于代码发布skill生成发布的，ai发布的可以
从对话中找到对应的文件和生成位置信息
---

方式二：关键字方式

代码修改，前端extend目录，后端模块jnpf-example下，关键字f_main_test

---
这时候就按照规则找前端默认是src/views/extend 下的f_main_test文件夹
后端默认是对应jnpf-example模块的位置下的f_main_test前缀开头的文件。这种可以基于现有表的方式。
---



然后就可以进行修改了。重要规则：需要参考下面的字段类型的说明进行代码修改

最后总结下，提示完成。

## 步骤二：
这个步骤可以把上面新增的和修改删除的数据库字段生成sql语句输入，同时问用户是否需要生成变更的sql。按照数据库的类型进行生成。然后默认是生成文件到桌面。除非用户有说，或者其他的文件md里面有提供说明。类型判断参考下面的控件类型使用方式



完成后提示完成，需要让用户进行重启后端应用程序。有生成脚本提示输出脚本的位置



## 字段类型
重要规则：

规则1.下面有关于json配置的如果新增要判断现有的json配置如果有的话，要对上一个json配置加逗号后在插入新的配置。

规则2：下面描述中可能出现

f_selectProps:{"label":"fullName","value":"id", labelType: 'default' },

xxxProps的格式是对存在数据源类型的控件需要配置的。默认显示label用的key是fullName，存储的value值是用的key是id。除非用户有说明否则生成都用这个。否则要替换。

规则3：json配置中出现类型下面结构要分析自动替换，一个是显示标题的key，一个是存的value的key.

"props":{"labelType":"default","label":"fullName","value":"id"}, 如用户说某字段label用的key为name, 存的value的key为encode.那么要这么改

"props":{"labelType":"default","label":"name","value":"encode"},



规则4：存在替换的这个注意转义是我需要的有2个\\ 是正常的不是错误不需要删除。

tt.replaceFirst("\\[","[[]");



下面假设对的f_main_test功能进行修改。

### 单行输入
生成了一个新字段名称是单行输入，并且字段key为f_input，则如下规则添加

修改前端文件Detail.vue

修改处1，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
<a-col :span="24" class="ant-col-item">
    <a-form-item
        name="f_input">
      <template #label>单行输入
      </template>
      <JnpfInput v-model:value="dataForm.f_input"
                 placeholder="请输入" disabled
                 detailed allowClear :style='{"width":"100%"}' :maskConfig="maskConfig.f_input">
      </JnpfInput>
    </a-form-item>
  </a-col>
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_input":[]}, 
---

修改处3

---
掩码配置maskConfig:{}对象下添加
maskConfig:{f_input: {"prefixType":1,"useUnrealMask":false,"maskType":1,"unrealMaskLength":1,"prefixLimit":0,"suffixLimit":0,"filler":"*","prefixSpecifyChar":"","suffixType":1,"ignoreChar":"","suffixSpecifyChar":""}
---

修改前端文件Form.vue

修改处1

---
dataForm:{} 对象下添加
dataForm: {
  f_input:undefined, 
},
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_input":[]}, 
---

修改处3

---
掩码配置maskConfig:{}对象下添加
maskConfig:{f_input: {"prefixType":1,"useUnrealMask":false,"maskType":1,"unrealMaskLength":1,"prefixLimit":0,"suffixLimit":0,"filler":"*","prefixSpecifyChar":"","suffixType":1,"ignoreChar":"","suffixSpecifyChar":""}
---

修改处4

---
// 设置默认值
      state.dataForm={
        f_input:undefined,
      };
---

修改处5，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---

<a-col :span="24" class="ant-col-item">
    <a-form-item
        name="f_input">
      <template #label>单行输入
      </template>
      <JnpfInput v-model:value="dataForm.f_input"
                 placeholder="请输入" disabled
                 detailed allowClear :style='{"width":"100%"}' :maskConfig="maskConfig.f_input">
      </JnpfInput>
    </a-form-item>
  </a-col>
---

修改文件columnList.ts，注意插入时候看规则1，后面不在赘述

修改处1，增加json配置

---
{
		"useScan":false,
		"suffixIcon":"",
		"fullNameI18nCode":[
			""
		],
		"align":"left",
		"headerAlign":"left",
		"showCount":false,
		"__config__":{
			"jnpfKey":"input",
			"dataType":null,
			"isSubTable":false,
			"label":"单行输入",
			"propsUrl":null,
			"labelI18nCode":null
		},
		"readonly":false,
		"prop":"f_input2",
		"__vModel__":"f_input2",
		"disabled":false,
		"id":"f_input2",
		"placeholder":"请输入",
		"addonBefore":"",
		"clearable":true,
		"maxlength":null,
		"jnpfKey":"input",
		"fullName":"单行输入",
		"label":"单行输入",
		"sortable":false,
		"addonAfter":"",
		"filter":false,
		"maskConfig":{
			"prefixType":1,
			"useUnrealMask":false,
			"maskType":1,
			"unrealMaskLength":1,
			"prefixLimit":0,
			"suffixLimit":0,
			"filler":"*",
			"prefixSpecifyChar":"",
			"suffixType":1,
			"ignoreChar":"",
			"suffixSpecifyChar":""
		},
		"width":null,
		"useMask":false,
		"showPassword":false,
		"fixed":"none",
		"prefixIcon":"",
		"labelI18nCode":""
	},
---

修改文件searchList.ts，注意插入时候看规则1，后面不在赘述

修改处1添加到数组中

---
{
		"useScan":false,
		"suffixIcon":"",
		"fullNameI18nCode":[
			""
		],
		"showCount":false,
		"__config__":{
			"jnpfKey":"input",
			"defaultValue":null,
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"单行输入",
			"propsUrl":null,
			"templateJson":null,
			"labelI18nCode":null
		},
		"readonly":false,
		"prop":"f_input",
		"__vModel__":"f_input",
		"searchMultiple":false,
		"disabled":false,
		"id":"f_input",
		"addonBefore":"",
		"clearable":true,
		"searchType":2,
		"maxlength":null,
		"jnpfKey":"input",
		"fullName":"单行输入",
		"label":"单行输入",
		"addonAfter":"",
		"sourceType":2,
		"maskConfig":{
			"prefixType":1,
			"useUnrealMask":false,
			"maskType":1,
			"unrealMaskLength":1,
			"prefixLimit":0,
			"suffixLimit":0,
			"filler":"*",
			"prefixSpecifyChar":"",
			"suffixType":1,
			"ignoreChar":"",
			"suffixSpecifyChar":""
		},
		"isKeyword":false,
		"useMask":false,
		"showPassword":false,
		"prefixIcon":"",
		"labelI18nCode":""
	},
---

修改文件superQueryJson.ts，注意插入时候看规则1，后面不在赘述

修改处1

---
{
		"clearable":true,
		"maxlength":null,
		"useScan":false,
		"suffixIcon":"",
		"fullName":"单行输入",
		"fullNameI18nCode":[
			""
		],
		"addonAfter":"",
		"showCount":false,
		"__config__":{
			"relationTable":null,
			"jnpfKey":"input",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"单行输入",
			"propsUrl":null,
			"templateJson":null,
			"tableName":"f_main_test"
		},
		"readonly":false,
		"maskConfig":{
			"prefixType":1,
			"useUnrealMask":false,
			"maskType":1,
			"unrealMaskLength":1,
			"prefixLimit":0,
			"suffixLimit":0,
			"filler":"*",
			"prefixSpecifyChar":"",
			"suffixType":1,
			"ignoreChar":"",
			"suffixSpecifyChar":""
		},
		"__vModel__":"f_input",
		"useMask":false,
		"showPassword":false,
		"disabled":false,
		"id":"f_input",
		"prefixIcon":"",
		"addonBefore":""
	},
---

修改文件f_main_testServiceImpl.java.

//普通查询下面添加代码

---
if(ObjectUtil.isNotEmpty(f_main_testPagination.getFinput())){
                String value = f_main_testPagination.getFinput() instanceof List ?
                    JsonUtil.getObjectToString(f_main_testPagination.getFinput()) :
                    String.valueOf(f_main_testPagination.getFinput());
                wrapper.like(F_main_testEntity::getFinput,value);
            }

 if(queryNormalParams != null && ObjectUtil.isNotEmpty(queryNormalParams.get("f_input"))){
                String value = queryNormalParams.get("f_input") instanceof List ?
                    JsonUtil.getObjectToString(queryNormalParams.get("f_input")) :
                    String.valueOf(queryNormalParams.get("f_input"));
                wrapper.like(F_main_testEntity::getFinput,value);
            }            
---

修改文件F_main_testEntity.java.

---
@TableField(value = "f_input" , updateStrategy = FieldStrategy.ALWAYS)
    @JSONField(name = "f_input")
    private String finput;
---

修改文件F_main_testForm.java



---
   /** 单行输入 **/
    @Schema(description = "单行输入")
    @JsonProperty("f_input")
    @JSONField(name = "f_input")
    private String finput;
---

修改文件 F_main_testPagination

---
 /** 单行输入 */
  @Schema(description = "单行输入")
  @JsonProperty("f_input")
  @JSONField(name = "finput")
  private Object finput;
---

修改文件F_main_testJson.json，注意插入时候看规则1，后面不在赘述

修改1：补充第一处"fields":[] 的json对象的那个字段内容。

---
{
			"modelId":"",
			"templateJson":"[]",
			"searchMultiple":false,
			"id":"",
			"vModel":"f_input",
			"multiple":false,
			"config":{
				"jnpfKey":"input",
				"isFromParam":false,
				"label":"单行输入",
				"templateJson":[],
				"required":false,
				"tableName":"f_main_test",
				"useCache":false,
				"unique":false,
				"regList":[]
			}
		},
---

修改2：补充tableList对象下的fields对象增加，注意插入时候看规则1，后面不在赘述

---
{
  "fieldName":"单行输入",
  "field":"f_input",
  "dataType":"varchar",
  "primaryKey":0
}
---

### 多行输入
如地址，详情信息字段可以用。假设生成了一个新字段名称是多行输入，并且字段key为f_textarea，则如下规则添加

修改前端文件Detail.vue

修改处1，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
 <a-col :span="24" class="ant-col-item"  >
      <a-form-item  name="f_textarea" >
        <template #label>多行输入</template> 
        <p>{{dataForm.f_textarea}}</p>
      </a-form-item>
</a-col>
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes: {"f_textarea":[]},
---



修改前端文件Form.vue

修改处1

---
dataForm:{} 对象下添加
dataForm: {
  f_textarea:undefined,
},
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_textarea":[]}, 
---

修改处3

---
// 设置默认值
      state.dataForm={
        f_textarea:undefined,
      };
---

修改处4，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
<a-col :span="24" class="ant-col-item">
        <a-form-item   
  name="f_textarea" >
<template #label>多行输入 
</template> <JnpfTextarea    v-model:value="dataForm.f_textarea"  @change="changeData('f_textarea', -1 )"  
 placeholder="请输入"   :allowClear='true'  :style='{"width":"100%"}' :autoSize='{"minRows":4,"maxRows":4}'  :showCount = "false"         >
</JnpfTextarea>
        </a-form-item>
    </a-col>
---

修改文件columnList.ts

修改处1，增加json配置

---
{
		"clearable":true,
		"maxlength":null,
		"jnpfKey":"textarea",
		"fullName":"多行输入",
		"fullNameI18nCode":[
			""
		],
		"label":"多行输入",
		"sortable":false,
		"align":"left",
		"filter":false,
		"autoSize":{
			"minRows":4,
			"maxRows":4
		},
		"headerAlign":"left",
		"showCount":false,
		"__config__":{
			"jnpfKey":"textarea",
			"dataType":null,
			"isSubTable":false,
			"label":"多行输入",
			"propsUrl":null,
			"labelI18nCode":null
		},
		"readonly":false,
		"prop":"f_textarea",
		"width":null,
		"__vModel__":"f_textarea",
		"fixed":"none",
		"disabled":false,
		"id":"f_textarea",
		"placeholder":"请输入",
		"labelI18nCode":""
	}
---

修改文件searchList.ts

修改处1添加到数组中

---
{
		"clearable":true,
		"searchType":2,
		"maxlength":null,
		"jnpfKey":"textarea",
		"fullName":"多行输入",
		"fullNameI18nCode":[
			""
		],
		"label":"多行输入",
		"autoSize":{
			"minRows":4,
			"maxRows":4
		},
		"showCount":false,
		"__config__":{
			"jnpfKey":"textarea",
			"defaultValue":null,
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"多行输入",
			"propsUrl":null,
			"templateJson":null,
			"labelI18nCode":null
		},
		"readonly":false,
		"sourceType":2,
		"prop":"f_textarea",
		"__vModel__":"f_textarea",
		"searchMultiple":false,
		"isKeyword":false,
		"disabled":false,
		"id":"f_textarea",
		"labelI18nCode":""
	}
---

修改文件superQueryJson.ts

修改处1

---
{
		"clearable":true,
		"maxlength":null,
		"fullName":"多行输入",
		"fullNameI18nCode":[
			""
		],
		"autoSize":{
			"minRows":4,
			"maxRows":4
		},
		"showCount":false,
		"__config__":{
			"relationTable":null,
			"jnpfKey":"textarea",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"多行输入",
			"propsUrl":null,
			"templateJson":null,
			"tableName":"f_main_test"
		},
		"readonly":false,
		"__vModel__":"f_textarea",
		"disabled":false,
		"id":"f_textarea"
	}
---

修改文件f_main_testServiceImpl.java.

//普通查询下面添加代码

---
if(ObjectUtil.isNotEmpty(f_main_testPagination.getFtextarea())){
                String value = f_main_testPagination.getFtextarea() instanceof List ?
                    JsonUtil.getObjectToString(f_main_testPagination.getFtextarea()) :
                    String.valueOf(f_main_testPagination.getFtextarea());
                wrapper.like(F_main_testEntity::getFtextarea,value);
            }


if(queryNormalParams != null && ObjectUtil.isNotEmpty(queryNormalParams.get("f_textarea"))){
                String value = queryNormalParams.get("f_textarea") instanceof List ?
                    JsonUtil.getObjectToString(queryNormalParams.get("f_textarea")) :
                    String.valueOf(queryNormalParams.get("f_textarea"));
                wrapper.like(F_main_testEntity::getFtextarea,value);
            }         
---

修改文件F_main_testEntity.java.

---
 @TableField(value = "f_textarea" , updateStrategy = FieldStrategy.ALWAYS)
    @JSONField(name = "f_textarea")
    private String ftextarea;
---

修改文件F_main_testForm.java



---
 /** 多行输入 **/
    @Schema(description = "多行输入")
    @JsonProperty("f_textarea")
    @JSONField(name = "f_textarea")
    private String ftextarea;
---

修改文件 F_main_testPagination

---
/** 多行输入 */
    @Schema(description = "多行输入")
    @JsonProperty("f_textarea")
    @JSONField(name = "ftextarea")
    private Object ftextarea;
---

修改文件F_main_testJson.json

修改1：补充第一处"fields":[] 的json对象的那个字段内容。

---
{
			"modelId":"",
			"templateJson":"[]",
			"searchMultiple":false,
			"id":"",
			"vModel":"f_textarea",
			"multiple":false,
			"config":{
				"jnpfKey":"textarea",
				"isFromParam":false,
				"label":"多行输入",
				"templateJson":[],
				"required":false,
				"tableName":"f_main_test",
				"useCache":false,
				"unique":false,
				"regList":[]
			}
		}
---

修改2：补充tableList对象下的fields对象增加

---
{
					"fieldName":"多行输入",
					"field":"f_textarea",
					"dataType":"text",
					"primaryKey":0
				},
---

### 数字输入
数字类型的使用，假设生成了一个新字段名称是数字输入，并且字段key为f_inputnum，则如下规则添加

修改前端文件Detail.vue

修改处1，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
  <a-col :span="24" class="ant-col-item"  >
        <a-form-item    
  name="f_inputnum" >
<template #label>数字输入 
</template> 
    <JnpfInputNumber    v-model:value="dataForm.f_inputnum"
 placeholder="请输入"   disabled
 detailed  :style='{"width":"100%"}' :step="1"  :controls="false"     >
    </JnpfInputNumber>
        </a-form-item>
    </a-col>
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_inputnum":[]}, 
---

修改前端文件Form.vue

修改处1

---
dataForm:{} 对象下添加
dataForm: {
  f_inputnum:undefined, 
},
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_inputnum":[]}, 
---

修改处3

---
// 设置默认值
      state.dataForm={
        f_inputnum:undefined,
      };
---

修改处4，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
 <a-col :span="24" class="ant-col-item"
>
        <a-form-item   
  name="f_inputnum" >
<template #label>数字输入 
</template> <JnpfInputNumber    v-model:value="dataForm.f_inputnum"  @change="changeData('f_inputnum', -1 )"  
 placeholder="请输入"   :style='{"width":"100%"}' :step="1"  :controls="false"         >
</JnpfInputNumber>
        </a-form-item>
    </a-col>
---

修改文件columnList.ts

修改处1，增加json配置

---
{
		"controls":false,
		"fullNameI18nCode":[
			""
		],
		"align":"left",
		"isAmountChinese":false,
		"headerAlign":"left",
		"__config__":{
			"jnpfKey":"inputNumber",
			"dataType":null,
			"isSubTable":false,
			"label":"数字输入",
			"propsUrl":null,
			"labelI18nCode":null
		},
		"prop":"f_inputnum",
		"__vModel__":"f_inputnum",
		"disabled":false,
		"id":"f_inputnum",
		"placeholder":"请输入",
		"addonBefore":"",
		"jnpfKey":"inputNumber",
		"fullName":"数字输入",
		"label":"数字输入",
		"sortable":false,
		"thousands":false,
		"addonAfter":"",
		"filter":false,
		"width":null,
		"fixed":"none",
		"step":1,
		"labelI18nCode":""
	}
---

修改文件searchList.ts

修改处1添加到数组中

---
{
		"controls":false,
		"searchType":3,
		"jnpfKey":"inputNumber",
		"fullName":"数字输入",
		"fullNameI18nCode":[
			""
		],
		"label":"数字输入",
		"thousands":false,
		"isAmountChinese":false,
		"addonAfter":"",
		"__config__":{
			"jnpfKey":"inputNumber",
			"defaultValue":null,
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"数字输入",
			"propsUrl":null,
			"templateJson":null,
			"labelI18nCode":null
		},
		"sourceType":2,
		"prop":"f_inputnum",
		"__vModel__":"f_inputnum",
		"searchMultiple":false,
		"isKeyword":false,
		"step":1,
		"disabled":false,
		"id":"f_inputnum",
		"value":[],
		"addonBefore":"",
		"labelI18nCode":""
	}
---

修改文件superQueryJson.ts

修改处1

---
{
		"controls":false,
		"fullName":"数字输入",
		"fullNameI18nCode":[
			""
		],
		"thousands":false,
		"isAmountChinese":false,
		"addonAfter":"",
		"__config__":{
			"relationTable":null,
			"jnpfKey":"inputNumber",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"数字输入",
			"propsUrl":null,
			"templateJson":null,
			"tableName":"f_main_test"
		},
		"__vModel__":"f_inputnum",
		"step":1,
		"disabled":false,
		"id":"f_inputnum",
		"addonBefore":""
	}
---

修改文件f_main_testServiceImpl.java.

//普通查询下面添加代码

---
if(ObjectUtil.isNotEmpty(f_main_testPagination.getFinputnum())){
                List<String> jsonList =  JsonUtil.getJsonToList(f_main_testPagination.getFinputnum(),String.class);
                for(int i=0;i<jsonList.size();i++){
                    String id = String.valueOf(jsonList.get(i));
                    boolean idAll = StringUtil.isNotEmpty(id) && !id.equals("null");
                    if(idAll){
                        Object b= new BigDecimal(id);
                        if(i==0){
                            wrapper.ge(F_main_testEntity::getFinputnum,b);
                        }else{
                            wrapper.le(F_main_testEntity::getFinputnum,b);
                        }
                    }
                }
            }


  if(queryNormalParams != null && ObjectUtil.isNotEmpty(queryNormalParams.get("f_inputnum"))){
                List<String> jsonList =  JsonUtil.getJsonToList(queryNormalParams.get("f_inputnum"),String.class);
                for(int i=0;i<jsonList.size();i++){
                    String id = String.valueOf(jsonList.get(i));
                    boolean idAll = StringUtil.isNotEmpty(id) && !id.equals("null");
                    if(idAll){
                        Object b= new BigDecimal(id);
                        if(i==0){
                            wrapper.ge(F_main_testEntity::getFinputnum,b);
                        }else{
                            wrapper.le(F_main_testEntity::getFinputnum,b);
                        }
                    }
                }
            }
            
---

修改文件F_main_testEntity.java.

---
@TableField(value = "f_inputnum" , updateStrategy = FieldStrategy.ALWAYS)
    @JSONField(name = "f_inputnum")
    private Integer finputnum;
---

修改文件F_main_testForm.java

---
/** 数字输入 **/
    @Schema(description = "数字输入")
    @JsonProperty("f_inputnum")
    @JSONField(name = "f_inputnum")
    private BigDecimal finputnum;

---

修改文件 F_main_testPagination

---
/** 数字输入 */
    @Schema(description = "数字输入")
    @JsonProperty("f_inputnum")
    @JSONField(name = "finputnum")
    private Object finputnum;
---

修改文件F_main_testJson.json

修改1：补充第一处"fields":[] 的json对象的那个字段内容。

---
{
			"modelId":"",
			"templateJson":"[]",
			"searchMultiple":false,
			"id":"",
			"vModel":"f_inputnum",
			"multiple":false,
			"config":{
				"jnpfKey":"inputNumber",
				"isFromParam":false,
				"label":"数字输入",
				"templateJson":[],
				"required":false,
				"tableName":"f_main_test",
				"useCache":false,
				"unique":false,
				"regList":[]
			}
		}
---

修改2：补充tableList对象下的fields对象增加

---
{
  "fieldName":"数字输入",
  "field":"f_inputnum",
  "dataType":"int4",
  "primaryKey":0
},
---

### 开关类型
假设生成了一个新字段名称是开关类型，并且字段key为f_switch，则如下规则添加

修改前端文件Detail.vue

修改处1，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
   <a-col :span="24" class="ant-col-item"  >
        <a-form-item    
  name="f_switch" >
<template #label>开关 
</template> 
            <p>{{dataForm.f_switch}}</p>
        </a-form-item>
    </a-col>
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_switch":[]}, 
---



修改前端文件Form.vue

修改处1

---
dataForm:{} 对象下添加
dataForm: {
  f_switch:0,
},
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_switch":[]}, 
---

修改处3

---
// 设置默认值
      state.dataForm={
       f_switch:0,
      };
---

修改处5，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
 <a-col :span="24" class="ant-col-item"
>
        <a-form-item   
  name="f_switch" >
<template #label>开关 
</template> <JnpfSwitch    v-model:value="dataForm.f_switch"  @change="changeData('f_switch', -1 )"  
        >
</JnpfSwitch>
        </a-form-item>
    </a-col>
---

修改文件columnList.ts

修改处1，增加json配置

---
{
		"activeValue":1,
		"jnpfKey":"switch",
		"inactiveValue":0,
		"fullName":"开关",
		"inactiveTxt":"关",
		"fullNameI18nCode":[
			""
		],
		"label":"开关",
		"sortable":false,
		"align":"left",
		"filter":false,
		"headerAlign":"left",
		"__config__":{
			"jnpfKey":"switch",
			"dataType":null,
			"isSubTable":false,
			"label":"开关",
			"propsUrl":null,
			"labelI18nCode":null
		},
		"prop":"f_switch",
		"width":null,
		"__vModel__":"f_switch",
		"fixed":"none",
		"disabled":false,
		"activeTxt":"开",
		"id":"f_switch",
		"labelI18nCode":""
	}
---

修改文件superQueryJson.ts

修改处1

---
{
		"__config__":{
			"relationTable":null,
			"jnpfKey":"switch",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"开关",
			"propsUrl":null,
			"templateJson":null,
			"tableName":"f_main_test"
		},
		"activeValue":1,
		"inactiveValue":0,
		"__vModel__":"f_switch",
		"fullName":"开关",
		"inactiveTxt":"关",
		"fullNameI18nCode":[
			""
		],
		"disabled":false,
		"activeTxt":"开",
		"id":"f_switch"
	}
---



修改文件F_main_testEntity.java.

---
@TableField(value = "f_switch" , updateStrategy = FieldStrategy.ALWAYS)
    @JSONField(name = "f_switch")
    private Integer fswitch;
---

修改文件F_main_testForm.java



---
/** 开关 **/
    @Schema(description = "开关")
    @JsonProperty("f_switch")
    @JSONField(name = "f_switch")
    private Integer fswitch;
---



修改文件F_main_testJson.json

修改1：补充第一处"fields":[] 的json对象的那个字段内容。

---
{
			"modelId":"",
			"inactiveTxt":"关",
			"templateJson":"[]",
			"searchMultiple":false,
			"id":"",
			"vModel":"f_switch",
			"multiple":false,
			"activeTxt":"开",
			"config":{
				"jnpfKey":"switch",
				"isFromParam":false,
				"label":"开关",
				"templateJson":[],
				"required":false,
				"tableName":"f_main_test",
				"useCache":false,
				"unique":false,
				"regList":[]
			}
		}
---

修改2：补充tableList对象下的fields对象增加

---
{
  "fieldName":"开关",
  "field":"f_switch",
  "dataType":"int4",
  "primaryKey":0
},
---

### 单选框组
假设生成了一个新字段名称是单选框组，并且字段key为f_radio，则如下规则添加

修改前端文件Detail.vue

修改处1，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
<a-col :span="24" class="ant-col-item"  >
        <a-form-item    
  name="f_radio" >
<template #label>单选框组 
</template> 
        <p>{{ dataForm.f_radio }} </p>
        </a-form-item>
    </a-col>
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_radio":[]}, 
---

修改前端文件Form.vue

修改处1

---
dataForm:{} 对象下添加
dataForm: {
  f_radio:undefined, 
},
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_radio":[]}, 
---

修改处3

---
optionsObj对象处添加

optionsObj:{
    f_radioOptions:[{"fullName":"选项一","id":"1"},{"fullName":"选项二","id":"2"}],
    f_radioProps:{"label":"fullName","value":"id" },
},

---

修改处4

---
// 设置默认值
      state.dataForm={
        f_radio:undefined,
      };
---

修改处5，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
<a-col :span="24" class="ant-col-item"
>
        <a-form-item   
  name="f_radio" >
<template #label>单选框组 
</template> <JnpfRadio    v-model:value="dataForm.f_radio"  @change="changeData('f_radio', -1 )"  
 :templateJson="state.interfaceRes.f_radio" :style='{"width":"100%"}' size="default"              :options="optionsObj.f_radioOptions"          :fieldNames="optionsObj.f_radioProps"
 direction="horizontal"  optionType="default"     >
</JnpfRadio>
        </a-form-item>
    </a-col>
---

修改文件columnList.ts

修改处1，增加json配置

---
{
		"jnpfKey":"radio",
		"buttonStyle":"solid",
		"fullName":"单选框组",
		"fullNameI18nCode":[
			""
		],
		"label":"单选框组",
		"sortable":false,
		"align":"left",
		"props":{
			"label":"fullName",
			"value":"id"
		},
		"filter":false,
		"optionType":"default",
		"headerAlign":"left",
		"__config__":{
			"jnpfKey":"radio",
			"dataType":"static",
			"isSubTable":false,
			"label":"单选框组",
			"propsUrl":"",
			"labelI18nCode":null
		},
		"size":"default",
		"prop":"f_radio",
		"width":null,
		"options":[
			{
				"fullName":"选项一",
				"id":"1"
			},
			{
				"fullName":"选项二",
				"id":"2"
			}
		],
		"__vModel__":"f_radio",
		"fixed":"none",
		"disabled":false,
		"id":"f_radio",
		"labelI18nCode":"",
		"direction":"horizontal"
	}
---

修改文件searchList.ts

修改处1添加到数组中

---
{
		"searchType":1,
		"jnpfKey":"radio",
		"buttonStyle":"solid",
		"fullName":"单选框组",
		"fullNameI18nCode":[
			""
		],
		"label":"单选框组",
		"props":{
			"label":"fullName",
			"value":"id"
		},
		"optionType":"default",
		"__config__":{
			"jnpfKey":"radio",
			"defaultValue":null,
			"dataType":"static",
			"dictionaryType":"",
			"isSubTable":false,
			"label":"单选框组",
			"propsUrl":"",
			"templateJson":[],
			"labelI18nCode":null
		},
		"size":"default",
		"sourceType":2,
		"prop":"f_radio",
		"options":[
			{
				"fullName":"选项一",
				"id":"1"
			},
			{
				"fullName":"选项二",
				"id":"2"
			}
		],
		"__vModel__":"f_radio",
		"searchMultiple":false,
		"isKeyword":false,
		"disabled":false,
		"id":"f_radio",
		"labelI18nCode":"",
		"direction":"horizontal"
	}
---

修改文件superQueryJson.ts

修改处1

---
{
		"buttonStyle":"solid",
		"fullName":"单选框组",
		"fullNameI18nCode":[
			""
		],
		"props":{
			"label":"fullName",
			"value":"id"
		},
		"optionType":"default",
		"__config__":{
			"relationTable":null,
			"jnpfKey":"radio",
			"dataType":"static",
			"dictionaryType":"",
			"isSubTable":false,
			"label":"单选框组",
			"propsUrl":"",
			"templateJson":[],
			"tableName":"f_main_test"
		},
		"size":"default",
		"options":[
			{
				"fullName":"选项一",
				"id":"1"
			},
			{
				"fullName":"选项二",
				"id":"2"
			}
		],
		"__vModel__":"f_radio",
		"disabled":false,
		"id":"f_radio",
		"direction":"horizontal"
	}
---

修改文件f_main_testServiceImpl.java.

//普通查询下面添加代码

---
if(ObjectUtil.isNotEmpty(f_main_testPagination.getFradio())){
                wrapper.eq(F_main_testEntity::getFradio,f_main_testPagination.getFradio());
            }


  if(queryNormalParams != null && ObjectUtil.isNotEmpty(queryNormalParams.get("f_radio"))){
              wrapper.eq(F_main_testEntity::getFradio,queryNormalParams.get("f_radio"));
          }

      }            
---

修改文件F_main_testEntity.java.

---
@TableField(value = "f_radio" , updateStrategy = FieldStrategy.ALWAYS)
    @JSONField(name = "f_radio")
    private String fradio;
---

修改文件F_main_testForm.java



---
 /** 单选框组 **/
    @Schema(description = "单选框组")
    @JsonProperty("f_radio")
    @JSONField(name = "f_radio")
    private String fradio;
---

修改文件 F_main_testPagination

---
/** 单选框组 */
    @Schema(description = "单选框组")
    @JsonProperty("f_radio")
    @JSONField(name = "fradio")
    private Object fradio;
---

修改文件F_main_testJson.json

修改1：补充第一处"fields":[] 的json对象的那个字段内容。

---
{
			"modelId":"",
			"templateJson":"[]",
			"options":"[{\"fullName\":\"选项一\",\"id\":\"1\"},{\"fullName\":\"选项二\",\"id\":\"2\"}]",
			"searchMultiple":false,
			"id":"",
			"vModel":"f_radio",
			"multiple":false,
			"props":{
				"children":"",
				"multiple":false,
				"label":"fullName",
				"value":"id"
			},
			"config":{
				"jnpfKey":"radio",
				"dataType":"static",
				"dictionaryType":"",
				"isFromParam":false,
				"label":"单选框组",
				"propsUrl":"",
				"templateJson":[],
				"required":false,
				"tableName":"f_main_test",
				"useCache":true,
				"unique":false,
				"regList":[]
			}
		}
---

修改2：补充tableList对象下的fields对象增加

---
{
    "fieldName":"单选框组",
    "field":"f_radio",
    "dataType":"varchar",
    "primaryKey":0
  },
---

### 多选框组
假设生成了一个新字段名称是多选框组类型，并且字段key为f_checkbox，则如下规则添加

修改前端文件Detail.vue 

修改处1，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
  <a-col :span="24" class="ant-col-item"  >
        <a-form-item    
  name="f_checkbox" >
<template #label>多选框组 
</template> 
        <p>{{ dataForm.f_checkbox }} </p>
        </a-form-item>
    </a-col>
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_checkbox":[]}, 
---

修改前端文件Form.vue

修改处1

---
dataForm:{} 对象下添加
dataForm: {
  f_checkbox:undefined, 
},
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_checkbox":[]}, 
---

修改处3

---
optionsObj对象处添加

optionsObj:{
    f_checkboxOptions:[{"fullName":"选项一","id":"1"},{"fullName":"选项二","id":"2"}],
    f_checkboxProps:{"label":"fullName","value":"id" },
},
---

修改处4

---
// 设置默认值
      state.dataForm={
        f_checkbox:[],
      };
---

修改处5，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
<a-col :span="24" class="ant-col-item">
        <a-form-item   
  name="f_checkbox" >
<template #label>多选框组 
</template> <JnpfCheckbox    v-model:value="dataForm.f_checkbox"  @change="changeData('f_checkbox', -1 )"  
 :templateJson="state.interfaceRes.f_checkbox" :style='{"width":"100%"}'             :options="optionsObj.f_checkboxOptions"          :fieldNames="optionsObj.f_checkboxProps"
 direction="horizontal"    >
</JnpfCheckbox>
        </a-form-item>
    </a-col>
---

修改文件columnList.ts

修改处1，增加json配置

---
{
		"jnpfKey":"checkbox",
		"fullName":"多选框组",
		"fullNameI18nCode":[
			""
		],
		"label":"多选框组",
		"sortable":false,
		"align":"left",
		"props":{
			"label":"fullName",
			"value":"id"
		},
		"filter":false,
		"headerAlign":"left",
		"__config__":{
			"jnpfKey":"checkbox",
			"dataType":"static",
			"isSubTable":false,
			"label":"多选框组",
			"propsUrl":"",
			"labelI18nCode":null
		},
		"prop":"f_checkbox",
		"width":null,
		"options":[
			{
				"fullName":"选项一",
				"id":"1"
			},
			{
				"fullName":"选项二",
				"id":"2"
			}
		],
		"__vModel__":"f_checkbox",
		"fixed":"none",
		"disabled":false,
		"id":"f_checkbox",
		"labelI18nCode":"",
		"direction":"horizontal"
	}
---

修改文件searchList.ts

修改处1添加到数组中

---
{
		"searchType":1,
		"jnpfKey":"checkbox",
		"fullName":"多选框组",
		"fullNameI18nCode":[
			""
		],
		"label":"多选框组",
		"props":{
			"label":"fullName",
			"value":"id"
		},
		"__config__":{
			"jnpfKey":"checkbox",
			"defaultValue":"[]",
			"dataType":"static",
			"dictionaryType":"",
			"isSubTable":false,
			"label":"多选框组",
			"propsUrl":"",
			"templateJson":[],
			"labelI18nCode":null
		},
		"sourceType":2,
		"prop":"f_checkbox",
		"options":[
			{
				"fullName":"选项一",
				"id":"1"
			},
			{
				"fullName":"选项二",
				"id":"2"
			}
		],
		"__vModel__":"f_checkbox",
		"searchMultiple":false,
		"isKeyword":false,
		"disabled":false,
		"id":"f_checkbox",
		"labelI18nCode":"",
		"direction":"horizontal"
	}
---

修改文件superQueryJson.ts

修改处1

---
{
		"__config__":{
			"relationTable":null,
			"jnpfKey":"checkbox",
			"dataType":"static",
			"dictionaryType":"",
			"isSubTable":false,
			"label":"多选框组",
			"propsUrl":"",
			"templateJson":[],
			"tableName":"f_main_test"
		},
		"options":[
			{
				"fullName":"选项一",
				"id":"1"
			},
			{
				"fullName":"选项二",
				"id":"2"
			}
		],
		"__vModel__":"f_checkbox",
		"fullName":"多选框组",
		"fullNameI18nCode":[
			""
		],
		"disabled":false,
		"id":"f_checkbox",
		"props":{
			"label":"fullName",
			"value":"id"
		},
		"direction":"horizontal"
	}
---

修改文件f_main_testServiceImpl.java.

//普通查询下面添加代码

---
if(ObjectUtil.isNotEmpty(f_main_testPagination.getFcheckbox())){
                List<String> idList = new ArrayList<>();
                try {
                    String[][] fcheckbox = JsonUtil.getJsonToBean(f_main_testPagination.getFcheckbox(),String[][].class);
                    for(int i=0;i<fcheckbox.length;i++){
                        if(fcheckbox[i].length>0){
                            idList.add(JsonUtil.getObjectToString(Arrays.asList(fcheckbox[i])));
                        }
                    }
                }catch (Exception e1){
                    try {
                        List<String> fcheckbox = JsonUtil.getJsonToList(f_main_testPagination.getFcheckbox(),String.class);
                        if(!fcheckbox.isEmpty()){
                            idList.addAll(fcheckbox);
                        }
                    }catch (Exception e2){
                        idList.add(String.valueOf(f_main_testPagination.getFcheckbox()));
                    }
                }
                wrapper.and(t->{
                    idList.forEach(tt->{
                        if(StringUtil.isNotEmpty(tt) && "Microsoft SQL Server".equalsIgnoreCase(databaseName)){
                            tt = tt.replaceFirst("\\[","[[]");
                        }
                        t.like(F_main_testEntity::getFcheckbox, tt).or();
                    });
                });
            }


第二处
 if(queryNormalParams != null && ObjectUtil.isNotEmpty(queryNormalParams.get("f_checkbox"))){
                List<String> idList = new ArrayList<>();
                try {
                    String[][] fcheckbox = JsonUtil.getJsonToBean(queryNormalParams.get("f_checkbox"),String[][].class);
                    for(int i=0;i<fcheckbox.length;i++){
                        if(fcheckbox[i].length>0){
                            idList.add(JsonUtil.getObjectToString(Arrays.asList(fcheckbox[i])));
                        }
                    }
                }catch (Exception e1){
                    try {
                        List<String> fcheckbox = JsonUtil.getJsonToList(queryNormalParams.get("f_checkbox"),String.class);
                        if(!fcheckbox.isEmpty()){
                            idList.addAll(fcheckbox);
                        }
                    }catch (Exception e2){
                        idList.add(String.valueOf(queryNormalParams.get("f_checkbox")));
                    }
                }
                wrapper.and(t->{
                    idList.forEach(tt->{
                        if(StringUtil.isNotEmpty(tt) && "Microsoft SQL Server".equalsIgnoreCase(databaseName)){
                            tt = tt.replaceFirst("\\[","[[]");
                        }
                        t.like(F_main_testEntity::getFcheckbox, tt).or();
                    });
                });
            }

---

修改文件F_main_testEntity.java.

---
@TableField(value = "f_checkbox" , updateStrategy = FieldStrategy.ALWAYS)
    @JSONField(name = "f_checkbox")
    private String fcheckbox;
---

修改文件F_main_testForm.java

---
/** 多选框组 **/
    @Schema(description = "多选框组")
    @JsonProperty("f_checkbox")
    @JSONField(name = "f_checkbox")
    private Object fcheckbox;
---

修改文件 F_main_testPagination

---
/** 多选框组 */
    @Schema(description = "多选框组")
    @JsonProperty("f_checkbox")
    @JSONField(name = "fcheckbox")
    private Object fcheckbox;
---

修改文件F_main_testJson.json

修改1：补充第一处"fields":[] 的json对象的那个字段内容。

---
{
			"modelId":"",
			"templateJson":"[]",
			"options":"[{\"fullName\":\"选项一\",\"id\":\"1\"},{\"fullName\":\"选项二\",\"id\":\"2\"}]",
			"searchMultiple":false,
			"id":"",
			"vModel":"f_checkbox",
			"multiple":false,
			"props":{
				"children":"",
				"multiple":false,
				"label":"fullName",
				"value":"id"
			},
			"config":{
				"jnpfKey":"checkbox",
				"dataType":"static",
				"dictionaryType":"",
				"isFromParam":false,
				"label":"多选框组",
				"propsUrl":"",
				"templateJson":[],
				"required":false,
				"tableName":"f_main_test",
				"useCache":true,
				"unique":false,
				"regList":[]
			}
		}
---

修改2：补充tableList对象下的fields对象增加

---
{
					"fieldName":"多选框组",
					"field":"f_checkbox",
					"dataType":"varchar",
					"primaryKey":0
				},
---

### 下拉选择
假设生成了一个新字段名称是下拉选择，并且字段key为f_select，则如下规则添加。

修改前端文件Detail.vue

修改处1，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
 <a-col :span="24" class="ant-col-item"  >
        <a-form-item    
  name="f_select" >
<template #label>下拉选择 
</template> 
        <p>{{ dataForm.f_select }} </p>
        </a-form-item>
    </a-col>
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_select":[]}, 
---

修改前端文件Form.vue

修改处1

---
dataForm:{} 对象下添加
dataForm: {
  f_select:undefined, 
},
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_select":[]}, 
---

修改处3

---
optionsObj对象处添加

第一种情况:静态数据源
optionsObj:{
   f_selectOptions:[{"fullName":"选项一","id":"1"},{"fullName":"选项二","id":"2"}],
   f_selectProps:{"label":"fullName","value":"id", labelType: 'default' },
},

第二种情况:数据字典，根据下面数据源说明的规则来得到需要的id等信息
optionsObj:{
   f_selectOptions:[],
}
f_selectOptions里面要是有东西可以清空

第三种情况:数据接口，根据下面数据源说明的规则来得到需要的id等信息


---

修改处4

---
// 设置默认值
      state.dataForm={
        f_select:'',
      };
---

修改处5，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
 <a-col :span="24" class="ant-col-item"
>
        <a-form-item   
  name="f_select" >
<template #label>下拉选择 
</template> <JnpfSelect    v-model:value="dataForm.f_select"  @change="changeData('f_select', -1 )"  
 placeholder="请选择"   :templateJson="state.interfaceRes.f_select" :allowClear='true'  :style='{"width":"100%"}' :showSearch='false'              :options="optionsObj.f_selectOptions"          :fieldNames="optionsObj.f_selectProps"
    >
</JnpfSelect>
        </a-form-item>
    </a-col>
---

修改处6

---
情况1：如果是静态数据源
跳过此处修改

情况2：数据字典 

搜索文件区域 "初始化options" 下面增加方法getXXXOptions() ,xxx是替换为属性key。
getf_selectOptions();

然后在getInfo(id).then((res) => {state.dataForm = res.data || {}; 处，也
就是getInfo里面补充方法调用，类似

getInfo(id).then((res) => {
      state.dataForm = res.data || {};
 +     getf_selectOptions();
....
}

在文件的js区域补充方法，963255a34ea64a2584c5d1ba269c1fe6这个是数据源获取到的字典id

  //数据选项--数据字典初始化方法
    function getf_selectOptions() {
        getDictionaryDataSelector('963255a34ea64a2584c5d1ba269c1fe6').then(res => {
            state.optionsObj.f_selectOptions = res.data.list
        })
    }


情况3：数据接口

搜索文件区域 "初始化options" 下面增加方法getXXXOptions() ,xxx是替换为属性key。
getf_selectOptions();

然后在getInfo(id).then((res) => {state.dataForm = res.data || {}; 处，也
就是getInfo里面补充方法调用，类似

getInfo(id).then((res) => {
      state.dataForm = res.data || {};
 +     getf_selectOptions();
....
}

  //数据选项--远端数据初始化方法
    function getf_selectOptions(isClear = false) {
        const index = state.childIndex
        let templateJsonList = JSON.parse(JSON.stringify(state.interfaceRes.f_select))
        for (let i = 0; i < templateJsonList.length; i++) {
            let json = templateJsonList[i];
            if(json.relationField&&json.sourceType ==1){
                let relationFieldAll = json.relationField.split("-");
                let val = json.defaultValue;
                if(relationFieldAll.length>1 && index>-1){
                    if(relationFieldAll[0].endsWith("List")){
                        val = state.dataForm[relationFieldAll[0]]&&state.dataForm[relationFieldAll[0]].length?
                                state.dataForm[relationFieldAll[0]][index][relationFieldAll[1]]:''
                    }else{
                        val = state.dataForm[relationFieldAll[0]+'List']&&state.dataForm[relationFieldAll[0]+'List'].length?
                                state.dataForm[relationFieldAll[0]+'List'][index][relationFieldAll[1]]:''
                    }
                }else {
                    val = state.dataForm[relationFieldAll]
                }
                json.defaultValue = val ? val : '';
            }
        }
        let template ={
            paramList:templateJsonList
        }
        getDataInterfaceRes('829969813769032389',template).then(res => {
            let data = res.data
            state.optionsObj.f_selectOptions = data
                        if(isClear){
                changeDataFormData(1,'List','f_select',index,'')
            }
        })
    }    
---

修改文件columnList.ts

修改处1，增加json配置

---
{
		"tagColor":"",
		"fullNameI18nCode":[
			""
		],
		"align":"left",
		"headerAlign":"left",
		"__config__":{
			"jnpfKey":"select",
			"dataType":"static",
			"isSubTable":false,
			"label":"下拉选择",
			"propsUrl":"",
			"labelI18nCode":null
		},
		"prop":"f_select",
		"options":[
			{
				"tagColor":null,
				"fullName":"选项一",
				"id":"1"
			},
			{
				"tagColor":null,
				"fullName":"选项二",
				"id":"2"
			}
		],
		"__vModel__":"f_select",
		"disabled":false,
		"id":"f_select",
		"placeholder":"请选择",
		"filterable":false,
		"clearable":true,
		"jnpfKey":"select",
		"multiple":false,
		"fullName":"下拉选择",
		"label":"下拉选择",
		"sortable":false,
		"props":{
			"labelType":"default",
			"label":"fullName",
			"value":"id"
		},
		"filter":false,
		"width":null,
		"fixed":"none",
		"labelI18nCode":""
	}

第二种情况：数据字典
	"__config__":{
			"jnpfKey":"select",
			"dataType":"static",
把上面的"dataType":"static", 换成   "dataType":"dictionary",     
把上面的options内容换成空的，"options":[],  


第三种情况：数据接口
把上面的"dataType":"static", 换成   "dataType":"dynamic",     
把上面的propsUrl 内容填充从请求获取到的id，"propsUrl":"829969813769032389",  
把上面的options内容换成空的，"options":[],  
---

修改文件searchList.ts

修改处1添加到数组中

---
{
		"filterable":false,
		"clearable":true,
		"searchType":1,
		"tagColor":"",
		"jnpfKey":"select",
		"multiple":false,
		"fullName":"下拉选择",
		"fullNameI18nCode":[
			""
		],
		"label":"下拉选择",
		"props":{
			"labelType":"default",
			"label":"fullName",
			"value":"id"
		},
		"__config__":{
			"jnpfKey":"select",
			"defaultValue":"",
			"dataType":"static",
			"dictionaryType":"",
			"isSubTable":false,
			"label":"下拉选择",
			"propsUrl":"",
			"templateJson":[],
			"labelI18nCode":null
		},
		"sourceType":2,
		"prop":"f_select",
		"options":[
			{
				"tagColor":null,
				"fullName":"选项一",
				"id":"1"
			},
			{
				"tagColor":null,
				"fullName":"选项二",
				"id":"2"
			}
		],
		"__vModel__":"f_select",
		"searchMultiple":true,
		"isKeyword":false,
		"disabled":false,
		"id":"f_select",
		"labelI18nCode":""
	}

第二种：数据字典
参考上面的结构，然后替换。
替换1：   "dataType":"dictionary",
替换2：  "dictionaryType":"963255a34ea64a2584c5d1ba269c1fe6", 
替换3： "options":[],

第三种情况：数据接口
参考上面的结构，然后替换。
替换1：   "dataType":"dynamic",
替换2：  "dictionaryType":"",  //有值需要置空 
替换3： "options":[],
替换4： "propsUrl":"829969813769032389", //添加对应数据接口id
---

修改文件superQueryJson.ts

修改处1

---
{
		"filterable":false,
		"clearable":true,
		"tagColor":"",
		"multiple":false,
		"fullName":"下拉选择",
		"fullNameI18nCode":[
			""
		],
		"props":{
			"labelType":"default",
			"label":"fullName",
			"value":"id"
		},
		"__config__":{
			"relationTable":null,
			"jnpfKey":"select",
			"dataType":"static",
			"dictionaryType":"",
			"isSubTable":false,
			"label":"下拉选择",
			"propsUrl":"",
			"templateJson":[],
			"tableName":"f_main_test"
		},
		"options":[
			{
				"tagColor":null,
				"fullName":"选项一",
				"id":"1"
			},
			{
				"tagColor":null,
				"fullName":"选项二",
				"id":"2"
			}
		],
		"__vModel__":"f_select",
		"disabled":false,
		"id":"f_select"
	}

第二种：数据字典
参考上面的结构，然后替换。
替换1：   "dataType":"dictionary",
替换2：  "dictionaryType":"963255a34ea64a2584c5d1ba269c1fe6", 
替换3： "options":[],  

第三种情况：数据接口
参考上面的结构，然后替换。
替换1：   "dataType":"dynamic",
替换2：  "dictionaryType":"",  //有值需要置空 
替换3： "options":[],
替换4： "propsUrl":"829969813769032389", //添加对应数据接口id
---

修改文件f_main_testServiceImpl.java.

//普通查询下面添加代码

---
if(ObjectUtil.isNotEmpty(f_main_testPagination.getFselect())){
                List<String> idList = new ArrayList<>();
                try {
                    String[][] fselect = JsonUtil.getJsonToBean(f_main_testPagination.getFselect(),String[][].class);
                    for(int i=0;i<fselect.length;i++){
                        if(fselect[i].length>0){
                            idList.add(JsonUtil.getObjectToString(Arrays.asList(fselect[i])));
                        }
                    }
                }catch (Exception e1){
                    try {
                        List<String> fselect = JsonUtil.getJsonToList(f_main_testPagination.getFselect(),String.class);
                        if(!fselect.isEmpty()){
                            idList.addAll(fselect);
                        }
                    }catch (Exception e2){
                        idList.add(String.valueOf(f_main_testPagination.getFselect()));
                    }
                }
                wrapper.and(t->{
                    idList.forEach(tt->{
                        if(StringUtil.isNotEmpty(tt) && "Microsoft SQL Server".equalsIgnoreCase(databaseName)){
                            tt = tt.replaceFirst("\\[","[[]");
                        }
                        t.like(F_main_testEntity::getFselect, tt).or();
                    });
                });
            }

还有一处
 if(queryNormalParams != null && ObjectUtil.isNotEmpty(queryNormalParams.get("f_select"))){
                List<String> idList = new ArrayList<>();
                try {
                    String[][] fselect = JsonUtil.getJsonToBean(queryNormalParams.get("f_select"),String[][].class);
                    for(int i=0;i<fselect.length;i++){
                        if(fselect[i].length>0){
                            idList.add(JsonUtil.getObjectToString(Arrays.asList(fselect[i])));
                        }
                    }
                }catch (Exception e1){
                    try {
                        List<String> fselect = JsonUtil.getJsonToList(queryNormalParams.get("f_select"),String.class);
                        if(!fselect.isEmpty()){
                            idList.addAll(fselect);
                        }
                    }catch (Exception e2){
                        idList.add(String.valueOf(queryNormalParams.get("f_select")));
                    }
                }
                wrapper.and(t->{
                    idList.forEach(tt->{
                        if(StringUtil.isNotEmpty(tt) && "Microsoft SQL Server".equalsIgnoreCase(databaseName)){
                            tt = tt.replaceFirst("\\[","[[]");
                        }
                        t.like(F_main_testEntity::getFselect, tt).or();
                    });
                });
            }
---

修改文件F_main_testEntity.java.

---
@TableField(value = "f_select" , updateStrategy = FieldStrategy.ALWAYS)
    @JSONField(name = "f_select")
    private String fselect;
---

修改文件F_main_testForm.java



---
/** 下拉选择 **/
    @Schema(description = "下拉选择")
    @JsonProperty("f_select")
    @JSONField(name = "f_select")
    private Object fselect;
---

修改文件 F_main_testPagination

---
/** 下拉选择 */
    @Schema(description = "下拉选择")
    @JsonProperty("f_select")
    @JSONField(name = "fselect")
    private Object fselect;
---

修改文件F_main_testJson.json

修改1：补充第一处"fields":[] 的json对象的那个字段内容。

---
{
			"modelId":"",
			"tagColor":"",
			"templateJson":"[]",
			"options":"[{\"fullName\":\"选项一\",\"id\":\"1\"},{\"fullName\":\"选项二\",\"id\":\"2\"}]",
			"searchMultiple":false,
			"id":"",
			"vModel":"f_select",
			"multiple":false,
			"props":{
				"children":"",
				"labelType":"default",
				"multiple":false,
				"label":"fullName",
				"value":"id"
			},
			"config":{
				"jnpfKey":"select",
				"dataType":"static",
				"dictionaryType":"",
				"isFromParam":false,
				"label":"下拉选择",
				"propsUrl":"",
				"templateJson":[],
				"required":false,
				"tableName":"f_main_test",
				"useCache":true,
				"unique":false,
				"regList":[]
			}
		}

第二种：数据字典
参考上面的结构，然后替换。
替换1：   "dataType":"dictionary",
替换2：  "dictionaryType":"963255a34ea64a2584c5d1ba269c1fe6",   

第三种情况：数据接口
参考上面的结构，然后替换。
替换1：   "dataType":"dynamic",
替换2：  "dictionaryType":"",  //有值需要置空 
替换3： "options":[],
替换4： "propsUrl":"829969813769032389", //添加对应数据接口id
---

修改2：补充tableList对象下的fields对象增加

---
{
					"fieldName":"下拉选择",
					"field":"f_select",
					"dataType":"varchar",
					"primaryKey":0
				},
---

### 日期选择
假设生成了一个新字段名称是日期选择，并且字段key为f_date，则如下规则添加

修改前端文件Detail.vue

修改处1，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
<a-col :span="24" class="ant-col-item"  >
        <a-form-item    
  name="f_date" >
<template #label>日期选择 
</template> 
        <p>{{ dataForm.f_date }} </p>
        </a-form-item>
    </a-col>
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_date":[]}, 
---



修改前端文件Form.vue

修改处1

---
dataForm:{} 对象下添加
dataForm: {
  f_date:undefined, 
},
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_date":[]}, 
---



修改处3

---
// 设置默认值
      state.dataForm={
        f_date:undefined,
      };
---

修改处4，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
<a-col :span="24" class="ant-col-item"
>
        <a-form-item   
  name="f_date" >
<template #label>日期选择 
</template> <JnpfDatePicker    v-model:value="dataForm.f_date"  @change="changeData('f_date', -1 )"  
 placeholder="请选择"   :allowClear='true'  :style='{"width":"100%"}' format="yyyy-MM-dd"  :startTime="getRelationDate(false,1,1,'','')"  :endTime="getRelationDate(false,1,1,'','')"         >
</JnpfDatePicker>
        </a-form-item>
    </a-col>
---

修改文件columnList.ts

修改处1，增加json配置

---
{
		"clearable":true,
		"jnpfKey":"datePicker",
		"format":"yyyy-MM-dd",
		"fullName":"日期选择",
		"fullNameI18nCode":[
			""
		],
		"label":"日期选择",
		"sortable":false,
		"align":"left",
		"filter":false,
		"headerAlign":"left",
		"__config__":{
			"jnpfKey":"datePicker",
			"dataType":null,
			"isSubTable":false,
			"label":"日期选择",
			"propsUrl":null,
			"labelI18nCode":null
		},
		"prop":"f_date",
		"width":null,
		"__vModel__":"f_date",
		"fixed":"none",
		"startTime":null,
		"disabled":false,
		"id":"f_date",
		"placeholder":"请选择",
		"endTime":null,
		"labelI18nCode":""
	}
---

修改文件searchList.ts

修改处1添加到数组中

---
{
		"clearable":true,
		"searchType":3,
		"jnpfKey":"datePicker",
		"format":"yyyy-MM-dd",
		"fullName":"日期选择",
		"fullNameI18nCode":[
			""
		],
		"label":"日期选择",
		"__config__":{
			"jnpfKey":"datePicker",
			"defaultValue":null,
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"日期选择",
			"propsUrl":null,
			"templateJson":null,
			"labelI18nCode":null
		},
		"sourceType":2,
		"prop":"f_date",
		"__vModel__":"f_date",
		"searchMultiple":false,
		"isKeyword":false,
		"startTime":null,
		"disabled":false,
		"id":"f_date",
		"endTime":null,
		"value":[],
		"labelI18nCode":""
	}
---

修改文件superQueryJson.ts

修改处1

---
{
		"clearable":true,
		"format":"yyyy-MM-dd",
		"fullName":"日期选择",
		"fullNameI18nCode":[
			""
		],
		"__config__":{
			"relationTable":null,
			"jnpfKey":"datePicker",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"日期选择",
			"propsUrl":null,
			"templateJson":null,
			"tableName":"f_main_test"
		},
		"__vModel__":"f_date",
		"startTime":null,
		"disabled":false,
		"id":"f_date",
		"endTime":null
	},
---

修改文件f_main_testServiceImpl.java.

//普通查询下面添加代码

---
if(ObjectUtil.isNotEmpty(f_main_testPagination.getFdate())){
    List<String> jsonList =  JsonUtil.getJsonToList(f_main_testPagination.getFdate(),String.class);
    for(int i=0;i<jsonList.size();i++){
        String id = String.valueOf(jsonList.get(i));
        boolean idAll = StringUtil.isNotEmpty(id) && !id.equals("null");
        if(idAll){
            Object b= new Date(Long.valueOf(id));
            if(i==0){
                wrapper.ge(F_main_testEntity::getFdate,b);
            }else{
                wrapper.le(F_main_testEntity::getFdate,b);
            }
        }
    }
}


 if(queryNormalParams != null && ObjectUtil.isNotEmpty(queryNormalParams.get("f_date"))){
                List<String> jsonList =  JsonUtil.getJsonToList(queryNormalParams.get("f_date"),String.class);
                for(int i=0;i<jsonList.size();i++){
                    String id = String.valueOf(jsonList.get(i));
                    boolean idAll = StringUtil.isNotEmpty(id) && !id.equals("null");
                    if(idAll){
                        Object b= new Date(Long.valueOf(id));
                        if(i==0){
                            wrapper.ge(F_main_testEntity::getFdate,b);
                        }else{
                            wrapper.le(F_main_testEntity::getFdate,b);
                        }
                    }
                }
            }
---

修改文件F_main_testEntity.java.

---
 @TableField(value = "f_date" , updateStrategy = FieldStrategy.ALWAYS)
    @JSONField(name = "f_date")
    private Date fdate;
---

修改文件F_main_testForm.java

---
/** 日期选择 **/
    @Schema(description = "日期选择")
    @JsonProperty("f_date")
    @JSONField(name = "f_date")
    private String fdate;
---

修改文件 F_main_testPagination

---
/** 日期选择 */
    @Schema(description = "日期选择")
    @JsonProperty("f_date")
    @JSONField(name = "fdate")
    private Object fdate;
---

修改文件F_main_testJson.json

修改1：补充第一处"fields":[] 的json对象的那个字段内容。

---
{
			"modelId":"",
			"templateJson":"[]",
			"searchMultiple":false,
			"id":"",
			"vModel":"f_date",
			"format":"yyyy-MM-dd",
			"multiple":false,
			"config":{
				"jnpfKey":"datePicker",
				"isFromParam":false,
				"label":"日期选择",
				"templateJson":[],
				"required":false,
				"tableName":"f_main_test",
				"useCache":false,
				"unique":false,
				"regList":[]
			}
		},
---

修改2：补充tableList对象下的fields对象增加

---
{
					"fieldName":"日期选择",
					"field":"f_date",
					"dataType":"date",
					"primaryKey":0
				},
---

### 时间选择
假设生成了一个新字段名称是时间选择，并且字段key为f_time，则如下规则添加

修改前端文件Detail.vue

修改处1，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
 <a-col :span="24" class="ant-col-item"  >
        <a-form-item    
  name="f_time" >
<template #label>时间选择 
</template> 
            <p>{{dataForm.f_time}}</p>
        </a-form-item>
    </a-col>
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_time":[]}, 
---

修改前端文件Form.vue

修改处1

---
dataForm:{} 对象下添加
dataForm: {
  f_time:undefined, 
},
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_time":[]}, 
---

修改处3

---
// 设置默认值
      state.dataForm={
        f_time:undefined,
      };
---

修改处4，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
 <a-col :span="24" class="ant-col-item"
>
        <a-form-item   
  name="f_time" >
<template #label>时间选择 
</template> <JnpfTimePicker    v-model:value="dataForm.f_time"  @change="changeData('f_time', -1 )"  
 placeholder="请选择"   :allowClear='true'  :style='{"width":"100%"}' format="HH:mm:ss"  :startTime="getRelationTime(false,1,1,'','HH:mm:ss','')"  :endTime="getRelationTime(false,1,1,'','HH:mm:ss','')"         >
</JnpfTimePicker>
        </a-form-item>
    </a-col>
---

修改文件columnList.ts

修改处1，增加json配置

---
{
		"clearable":true,
		"jnpfKey":"timePicker",
		"format":"HH:mm:ss",
		"fullName":"时间选择",
		"fullNameI18nCode":[
			""
		],
		"label":"时间选择",
		"sortable":false,
		"align":"left",
		"filter":false,
		"headerAlign":"left",
		"__config__":{
			"jnpfKey":"timePicker",
			"dataType":null,
			"isSubTable":false,
			"label":"时间选择",
			"propsUrl":null,
			"labelI18nCode":null
		},
		"prop":"f_time",
		"width":null,
		"__vModel__":"f_time",
		"fixed":"none",
		"startTime":null,
		"disabled":false,
		"id":"f_time",
		"placeholder":"请选择",
		"endTime":null,
		"labelI18nCode":""
	}
---

修改文件searchList.ts

修改处1添加到数组中

---
{
		"clearable":true,
		"searchType":3,
		"jnpfKey":"timePicker",
		"format":"HH:mm:ss",
		"fullName":"时间选择",
		"fullNameI18nCode":[
			""
		],
		"label":"时间选择",
		"__config__":{
			"jnpfKey":"timePicker",
			"defaultValue":null,
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"时间选择",
			"propsUrl":null,
			"templateJson":null,
			"labelI18nCode":null
		},
		"sourceType":2,
		"prop":"f_time",
		"__vModel__":"f_time",
		"searchMultiple":false,
		"isKeyword":false,
		"startTime":null,
		"disabled":false,
		"id":"f_time",
		"endTime":null,
		"value":[],
		"labelI18nCode":""
	}
---

修改文件superQueryJson.ts

修改处1

---
{
		"clearable":true,
		"format":"HH:mm:ss",
		"fullName":"时间选择",
		"fullNameI18nCode":[
			""
		],
		"__config__":{
			"relationTable":null,
			"jnpfKey":"timePicker",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"时间选择",
			"propsUrl":null,
			"templateJson":null,
			"tableName":"f_main_test"
		},
		"__vModel__":"f_time",
		"startTime":null,
		"disabled":false,
		"id":"f_time",
		"endTime":null
	}
---

修改文件f_main_testServiceImpl.java.

//普通查询下面添加代码

---
if(ObjectUtil.isNotEmpty(f_main_testPagination.getFtime())){
                List<String> jsonList =  JsonUtil.getJsonToList(f_main_testPagination.getFtime(),String.class);
                for(int i=0;i<jsonList.size();i++){
                    String id = String.valueOf(jsonList.get(i));
                    boolean idAll = StringUtil.isNotEmpty(id) && !id.equals("null");
                    if(idAll){
                        Object b= id;
                        if(i==0){
                            wrapper.ge(F_main_testEntity::getFtime,b);
                        }else{
                            wrapper.le(F_main_testEntity::getFtime,b);
                        }
                    }
                }
            }


 if(queryNormalParams != null && ObjectUtil.isNotEmpty(queryNormalParams.get("f_time"))){
                List<String> jsonList =  JsonUtil.getJsonToList(queryNormalParams.get("f_time"),String.class);
                for(int i=0;i<jsonList.size();i++){
                    String id = String.valueOf(jsonList.get(i));
                    boolean idAll = StringUtil.isNotEmpty(id) && !id.equals("null");
                    if(idAll){
                        Object b= id;
                        if(i==0){
                            wrapper.ge(F_main_testEntity::getFtime,b);
                        }else{
                            wrapper.le(F_main_testEntity::getFtime,b);
                        }
                    }
                }
            }
---

修改文件F_main_testEntity.java.

---
@TableField("f_time")
    @JSONField(name = "f_time")
    private String ftime;
---

修改文件F_main_testForm.java



---
/** 时间选择 **/
    @Schema(description = "时间选择")
    @JsonProperty("f_time")
    @JSONField(name = "f_time")
    private String ftime;
---

修改文件 F_main_testPagination

---
 /** 时间选择 */
    @Schema(description = "时间选择")
    @JsonProperty("f_time")
    @JSONField(name = "ftime")
    private Object ftime;
---

修改文件F_main_testJson.json

修改1：补充第一处"fields":[] 的json对象的那个字段内容。

---
{
			"modelId":"",
			"templateJson":"[]",
			"searchMultiple":false,
			"id":"",
			"vModel":"f_time",
			"format":"HH:mm:ss",
			"multiple":false,
			"config":{
				"jnpfKey":"timePicker",
				"isFromParam":false,
				"label":"时间选择",
				"templateJson":[],
				"required":false,
				"tableName":"f_main_test",
				"useCache":false,
				"unique":false,
				"regList":[]
			}
		}
---

修改2：补充tableList对象下的fields对象增加

---
{
					"fieldName":"时间选择",
					"field":"f_time",
					"dataType":"varchar",
					"primaryKey":0
				},
---

### 文件上传
假设生成了一个新字段名称是文件上传，并且字段key为f_file，则如下规则添加

如果遇到字段可能是文件直接用这个

修改前端文件Detail.vue

修改处1，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
 <a-col :span="24" class="ant-col-item"  >
        <a-form-item    
  name="f_file" >
<template #label>文件上传 
</template> 
    <JnpfUploadFile    v-model:value="dataForm.f_file"
 disabled
 detailed  :fileSize="10"  sizeUnit="MB"  :limit="9"  pathType="defaultPath"  timeFormat="YYYY"  buttonText="点击上传"     >
    </JnpfUploadFile>
        </a-form-item>
    </a-col>
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_file":[]}, 
---

修改前端文件Form.vue

修改处1

---
dataForm:{} 对象下添加
dataForm: {
  f_file:[], 
},
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_file":[]}, 
---

修改处3

---
// 设置默认值
      state.dataForm={
        f_file:[],
      };
---

修改处4，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
 <a-col :span="24" class="ant-col-item"
>
        <a-form-item   
  name="f_file" >
<template #label>文件上传 
</template> <JnpfUploadFile    v-model:value="dataForm.f_file"  @change="changeData('f_file', -1 )"  
 :fileSize="10"    sizeUnit="MB"  :limit="9"  pathType="defaultPath"  showType="button"  timeFormat="YYYY"  buttonText="点击上传"         >
</JnpfUploadFile>
        </a-form-item>
    </a-col>
---

修改文件columnList.ts

修改处1，增加json配置

---
{
		"fullNameI18nCode":[
			""
		],
		"align":"left",
		"headerAlign":"left",
		"__config__":{
			"jnpfKey":"uploadFile",
			"dataType":null,
			"isSubTable":false,
			"label":"文件上传",
			"propsUrl":null,
			"labelI18nCode":null
		},
		"prop":"f_file",
		"limit":9,
		"__vModel__":"f_file",
		"sizeUnit":"MB",
		"showType":"button",
		"disabled":false,
		"id":"f_file",
		"buttonText":"点击上传",
		"jnpfKey":"uploadFile",
		"fullName":"文件上传",
		"label":"文件上传",
		"sortable":false,
		"pathType":"defaultPath",
		"accept":"",
		"filter":false,
		"folder":"",
		"fileSize":10,
		"sortRule":[],
		"timeFormat":"YYYY",
		"width":null,
		"tipText":"",
		"fixed":"none",
		"labelI18nCode":""
	}
---

修改文件superQueryJson.ts

修改处1

---
{
		"buttonText":"点击上传",
		"fullName":"文件上传",
		"fullNameI18nCode":[
			""
		],
		"pathType":"defaultPath",
		"accept":"",
		"__config__":{
			"relationTable":null,
			"jnpfKey":"uploadFile",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"文件上传",
			"propsUrl":null,
			"templateJson":null,
			"tableName":"f_main_test"
		},
		"folder":"",
		"fileSize":10,
		"sortRule":[],
		"timeFormat":"YYYY",
		"limit":9,
		"__vModel__":"f_file",
		"sizeUnit":"MB",
		"tipText":"",
		"showType":"button",
		"disabled":false,
		"id":"f_file"
	}
---



修改文件F_main_testEntity.java.

---
@TableField(value = "f_file" , updateStrategy = FieldStrategy.ALWAYS)
    @JSONField(name = "f_file")
    private String ffile;
---

修改文件F_main_testForm.java



---
/** 文件上传 **/
    @Schema(description = "文件上传")
    @JsonProperty("f_file")
    @JSONField(name = "f_file")
    private Object ffile;

---



修改文件F_main_testJson.json

修改1：补充第一处"fields":[] 的json对象的那个字段内容。

---
{
			"modelId":"",
			"templateJson":"[]",
			"searchMultiple":false,
			"id":"",
			"vModel":"f_file",
			"multiple":false,
			"config":{
				"jnpfKey":"uploadFile",
				"isFromParam":false,
				"label":"文件上传",
				"templateJson":[],
				"required":false,
				"tableName":"f_main_test",
				"useCache":false,
				"unique":false,
				"regList":[]
			}
		}
---

修改2：补充tableList对象下的fields对象增加

---
{
    "fieldName":"文件上传",
    "field":"f_file",
    "dataType":"varchar",
    "primaryKey":0
  },
---

### 图片上传
假设生成了一个新字段名称是图片上传，并且字段key为f_img，则如下规则添加

如果遇到字段可能是图片直接用这个

修改前端文件Detail.vue

修改处1，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
   <a-col :span="24" class="ant-col-item"  >
        <a-form-item    
  name="f_img" >
<template #label>图片上传 
</template> 
    <JnpfUploadImg    v-model:value="dataForm.f_img"
 disabled
 detailed  :fileSize="10"  sizeUnit="MB"  :limit="9"  pathType="defaultPath"  timeFormat="YYYY"  buttonText="点击上传"     >
    </JnpfUploadImg>
        </a-form-item>
    </a-col>
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_img":[]}, 
---

修改前端文件Form.vue

修改处1

---
dataForm:{} 对象下添加
dataForm: {
  f_img:undefined, 
},
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_img":[]}, 
---

修改处3

---
// 设置默认值
      state.dataForm={
        f_img:undefined,
      };
---

修改处4，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
 <a-col :span="24" class="ant-col-item"
>
        <a-form-item   
  name="f_img" >
<template #label>图片上传 
</template> <JnpfUploadImg    v-model:value="dataForm.f_img"  @change="changeData('f_img', -1 )"  
 :fileSize="10"    sizeUnit="MB"  :limit="9"  pathType="defaultPath"  showType="card"  timeFormat="YYYY"  buttonText="点击上传"         >
</JnpfUploadImg>
        </a-form-item>
    </a-col>
---

修改文件columnList.ts

修改处1，增加json配置

---
{
		"fullNameI18nCode":[
			""
		],
		"align":"left",
		"headerAlign":"left",
		"__config__":{
			"jnpfKey":"uploadImg",
			"dataType":null,
			"isSubTable":false,
			"label":"图片上传",
			"propsUrl":null,
			"labelI18nCode":null
		},
		"prop":"f_img",
		"limit":9,
		"__vModel__":"f_img",
		"sizeUnit":"MB",
		"showType":"card",
		"disabled":false,
		"id":"f_img",
		"buttonText":"点击上传",
		"jnpfKey":"uploadImg",
		"fullName":"图片上传",
		"label":"图片上传",
		"sortable":false,
		"pathType":"defaultPath",
		"filter":false,
		"folder":"",
		"fileSize":10,
		"sortRule":[],
		"timeFormat":"YYYY",
		"width":null,
		"tipText":"",
		"fixed":"none",
		"labelI18nCode":""
	}
---

修改文件F_main_testEntity.java.

---
 @TableField(value = "f_img" , updateStrategy = FieldStrategy.ALWAYS)
    @JSONField(name = "f_img")
    private String fimg;
---

修改文件F_main_testForm.java



---
 /** 图片上传 **/
    @Schema(description = "图片上传")
    @JsonProperty("f_img")
    @JSONField(name = "f_img")
    private Object fimg;
---

修改文件F_main_testJson.json

修改1：补充第一处"fields":[] 的json对象的那个字段内容。

---
{
			"modelId":"",
			"templateJson":"[]",
			"searchMultiple":false,
			"id":"",
			"vModel":"f_img",
			"multiple":false,
			"config":{
				"jnpfKey":"uploadImg",
				"isFromParam":false,
				"label":"图片上传",
				"templateJson":[],
				"required":false,
				"tableName":"f_main_test",
				"useCache":false,
				"unique":false,
				"regList":[]
			}
		}
---

修改2：补充tableList对象下的fields对象增加

---
{
					"fieldName":"图片上传",
					"field":"f_img",
					"dataType":"varchar",
					"primaryKey":0
				},
---

### 组织选择
假设生成了一个新字段名称是组织选择，并且字段key为f_organize，则如下规则添加

修改前端文件Detail.vue

修改处1，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
<a-col :span="24" class="ant-col-item"  >
        <a-form-item    
  name="f_organize" >
<template #label>组织选择 
</template> 
            <p>{{dataForm.f_organize}}</p>
        </a-form-item>
    </a-col>
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_organize":[]}, 
---



修改前端文件Form.vue

修改处1

---
dataForm:{} 对象下添加
dataForm: {
  f_organize:'', 
},
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_organize":[]}, 
---

修改处3

---
// 设置默认值
      state.dataForm={
        f_organize:'',
      };
---

修改处4，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
 <a-col :span="24" class="ant-col-item"
>
        <a-form-item   
  name="f_organize" >
<template #label>组织选择 
</template> <JnpfOrganizeSelect    v-model:value="dataForm.f_organize"  @change="changeData('f_organize', -1 )"  
 placeholder="请选择"   :allowClear='true'  :style='{"width":"100%"}' :showSearch='false'  selectType="all"         >
</JnpfOrganizeSelect>
        </a-form-item>
    </a-col>
---

修改文件columnList.ts

修改处1，增加json配置

---
{
		"filterable":false,
		"clearable":true,
		"jnpfKey":"organizeSelect",
		"ableIds":[],
		"multiple":false,
		"fullName":"组织选择",
		"fullNameI18nCode":[
			""
		],
		"label":"组织选择",
		"sortable":false,
		"align":"left",
		"filter":false,
		"headerAlign":"left",
		"__config__":{
			"jnpfKey":"organizeSelect",
			"dataType":null,
			"isSubTable":false,
			"label":"组织选择",
			"propsUrl":null,
			"labelI18nCode":null
		},
		"prop":"f_organize",
		"width":null,
		"__vModel__":"f_organize",
		"fixed":"none",
		"selectType":"all",
		"disabled":false,
		"id":"f_organize",
		"placeholder":"请选择",
		"labelI18nCode":""
	}
---

修改文件searchList.ts

修改处1添加到数组中

---
{
		"filterable":false,
		"clearable":true,
		"searchType":1,
		"jnpfKey":"organizeSelect",
		"ableIds":[],
		"multiple":false,
		"fullName":"组织选择",
		"fullNameI18nCode":[
			""
		],
		"label":"组织选择",
		"__config__":{
			"jnpfKey":"organizeSelect",
			"defaultValue":"",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"组织选择",
			"propsUrl":null,
			"templateJson":null,
			"labelI18nCode":null
		},
		"sourceType":2,
		"prop":"f_organize",
		"__vModel__":"f_organize",
		"searchMultiple":true,
		"isKeyword":false,
		"selectType":"all",
		"disabled":false,
		"id":"f_organize",
		"labelI18nCode":""
	}
---

修改文件superQueryJson.ts

修改处1

---
{
		"filterable":false,
		"clearable":true,
		"ableIds":[],
		"multiple":false,
		"fullName":"组织选择",
		"fullNameI18nCode":[
			""
		],
		"__config__":{
			"relationTable":null,
			"jnpfKey":"organizeSelect",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"组织选择",
			"propsUrl":null,
			"templateJson":null,
			"tableName":"f_main_test"
		},
		"__vModel__":"f_organize",
		"selectType":"all",
		"disabled":false,
		"id":"f_organize"
	}
---

修改文件f_main_testServiceImpl.java.

//普通查询下面添加代码

---
if(ObjectUtil.isNotEmpty(f_main_testPagination.getForganize())){
                List<String> idList = new ArrayList<>();
                try {
                    String[][] forganize = JsonUtil.getJsonToBean(f_main_testPagination.getForganize(),String[][].class);
                    for(int i=0;i<forganize.length;i++){
                        if(forganize[i].length>0){
                            idList.add(JsonUtil.getObjectToString(Arrays.asList(forganize[i])));
                        }
                    }
                }catch (Exception e1){
                    try {
                        List<String> forganize = JsonUtil.getJsonToList(f_main_testPagination.getForganize(),String.class);
                        if(!forganize.isEmpty()){
                            idList.addAll(forganize);
                        }
                    }catch (Exception e2){
                        idList.add(String.valueOf(f_main_testPagination.getForganize()));
                    }
                }
                wrapper.and(t->{
                    idList.forEach(tt->{
                        if(StringUtil.isNotEmpty(tt) && "Microsoft SQL Server".equalsIgnoreCase(databaseName)){
                            tt = tt.replaceFirst("\\[","[[]");
                        }
                        t.like(F_main_testEntity::getForganize, tt).or();
                    });
                });
            }


if(queryNormalParams != null && ObjectUtil.isNotEmpty(queryNormalParams.get("f_organize"))){
                List<String> idList = new ArrayList<>();
                try {
                    String[][] forganize = JsonUtil.getJsonToBean(queryNormalParams.get("f_organize"),String[][].class);
                    for(int i=0;i<forganize.length;i++){
                        if(forganize[i].length>0){
                            idList.add(JsonUtil.getObjectToString(Arrays.asList(forganize[i])));
                        }
                    }
                }catch (Exception e1){
                    try {
                        List<String> forganize = JsonUtil.getJsonToList(queryNormalParams.get("f_organize"),String.class);
                        if(!forganize.isEmpty()){
                            idList.addAll(forganize);
                        }
                    }catch (Exception e2){
                        idList.add(String.valueOf(queryNormalParams.get("f_organize")));
                    }
                }
                wrapper.and(t->{
                    idList.forEach(tt->{
                        if(StringUtil.isNotEmpty(tt) && "Microsoft SQL Server".equalsIgnoreCase(databaseName)){
                            tt = tt.replaceFirst("\\[","[[]");
                        }
                        t.like(F_main_testEntity::getForganize, tt).or();
                    });
                });
            }            
---

修改文件F_main_testEntity.java.

---
@TableField(value = "f_organize" , updateStrategy = FieldStrategy.ALWAYS)
    @JSONField(name = "f_organize")
    private String forganize;
---

修改文件F_main_testForm.java



---
 /** 组织选择 **/
    @Schema(description = "组织选择")
    @JsonProperty("f_organize")
    @JSONField(name = "f_organize")
    private Object forganize;
---

修改文件 F_main_testPagination

---
/** 组织选择 */
    @Schema(description = "组织选择")
    @JsonProperty("f_organize")
    @JSONField(name = "forganize")
    private Object forganize;
---

修改文件F_main_testJson.json

修改1：补充第一处"fields":[] 的json对象的那个字段内容。

---
	{
			"modelId":"",
			"templateJson":"[]",
			"searchMultiple":false,
			"selectType":"all",
			"id":"",
			"vModel":"f_organize",
			"multiple":false,
			"config":{
				"jnpfKey":"organizeSelect",
				"isFromParam":false,
				"label":"组织选择",
				"templateJson":[],
				"required":false,
				"tableName":"f_main_test",
				"useCache":false,
				"unique":false,
				"regList":[]
			}
		}
---

修改2：补充tableList对象下的fields对象增加

---
{
					"fieldName":"组织选择",
					"field":"f_organize",
					"dataType":"varchar",
					"primaryKey":0
				},
---

### 岗位选择
假设生成了一个新字段名称是岗位选择，并且字段key为<font style="color:rgb(31, 35, 40);background-color:rgb(171, 242, 188);">f_position</font>，则如下规则添加

修改前端文件Detail.vue

修改处1，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
 <a-col :span="24" class="ant-col-item"  >
        <a-form-item    
  name="f_position" >
<template #label>岗位选择 
</template> 
            <p>{{dataForm.f_position}}</p>
        </a-form-item>
    </a-col>
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_position":[]}, 
---



修改前端文件Form.vue

修改处1

---
dataForm:{} 对象下添加
dataForm: {
  f_position:undefined, 
},
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_position":[]}, 
---



修改处3

---
// 设置默认值
      state.dataForm={
        f_position:undefined,
      };
---

修改处4，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
 <a-col :span="24" class="ant-col-item"
>
        <a-form-item   
  name="f_position" >
<template #label>岗位选择 
</template> <JnpfPosSelect    v-model:value="dataForm.f_position"  @change="changeData('f_position', -1 )"  
 placeholder="请选择"   :allowClear='true'  :style='{"width":"100%"}' :showSearch='false'  selectType="all"         >
</JnpfPosSelect>
        </a-form-item>
    </a-col>
---

修改文件columnList.ts

修改处1，增加json配置

---
{
		"filterable":false,
		"clearable":true,
		"jnpfKey":"posSelect",
		"ableIds":[],
		"multiple":false,
		"fullName":"岗位选择",
		"fullNameI18nCode":[
			""
		],
		"label":"岗位选择",
		"sortable":false,
		"align":"left",
		"filter":false,
		"headerAlign":"left",
		"__config__":{
			"jnpfKey":"posSelect",
			"dataType":null,
			"isSubTable":false,
			"label":"岗位选择",
			"propsUrl":null,
			"labelI18nCode":null
		},
		"prop":"f_position",
		"width":null,
		"__vModel__":"f_position",
		"fixed":"none",
		"selectType":"all",
		"disabled":false,
		"id":"f_position",
		"placeholder":"请选择",
		"labelI18nCode":""
	}
---

修改文件searchList.ts

修改处1添加到数组中

---
{
		"filterable":false,
		"clearable":true,
		"searchType":1,
		"jnpfKey":"posSelect",
		"ableIds":[],
		"multiple":false,
		"fullName":"岗位选择",
		"fullNameI18nCode":[
			""
		],
		"label":"岗位选择",
		"__config__":{
			"jnpfKey":"posSelect",
			"defaultValue":null,
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"岗位选择",
			"propsUrl":null,
			"templateJson":null,
			"labelI18nCode":null
		},
		"sourceType":2,
		"prop":"f_position",
		"__vModel__":"f_position",
		"searchMultiple":true,
		"isKeyword":false,
		"selectType":"all",
		"disabled":false,
		"id":"f_position",
		"labelI18nCode":""
	}
---

修改文件superQueryJson.ts

修改处1

---
{
		"filterable":false,
		"clearable":true,
		"ableIds":[],
		"multiple":false,
		"fullName":"岗位选择",
		"fullNameI18nCode":[
			""
		],
		"__config__":{
			"relationTable":null,
			"jnpfKey":"posSelect",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"岗位选择",
			"propsUrl":null,
			"templateJson":null,
			"tableName":"f_main_test"
		},
		"__vModel__":"f_position",
		"selectType":"all",
		"disabled":false,
		"id":"f_position"
	}
---

修改文件f_main_testServiceImpl.java.

//普通查询下面添加代码

---
if(ObjectUtil.isNotEmpty(f_main_testPagination.getFposition())){
                List<String> idList = new ArrayList<>();
                try {
                    String[][] fposition = JsonUtil.getJsonToBean(f_main_testPagination.getFposition(),String[][].class);
                    for(int i=0;i<fposition.length;i++){
                        if(fposition[i].length>0){
                            idList.add(JsonUtil.getObjectToString(Arrays.asList(fposition[i])));
                        }
                    }
                }catch (Exception e1){
                    try {
                        List<String> fposition = JsonUtil.getJsonToList(f_main_testPagination.getFposition(),String.class);
                        if(!fposition.isEmpty()){
                            idList.addAll(fposition);
                        }
                    }catch (Exception e2){
                        idList.add(String.valueOf(f_main_testPagination.getFposition()));
                    }
                }
                wrapper.and(t->{
                    idList.forEach(tt->{
                        if(StringUtil.isNotEmpty(tt) && "Microsoft SQL Server".equalsIgnoreCase(databaseName)){
                            tt = tt.replaceFirst("\\[","[[]");
                        }
                        t.like(F_main_testEntity::getFposition, tt).or();
                    });
                });
            }


 if(queryNormalParams != null && ObjectUtil.isNotEmpty(queryNormalParams.get("f_position"))){
                List<String> idList = new ArrayList<>();
                try {
                    String[][] fposition = JsonUtil.getJsonToBean(queryNormalParams.get("f_position"),String[][].class);
                    for(int i=0;i<fposition.length;i++){
                        if(fposition[i].length>0){
                            idList.add(JsonUtil.getObjectToString(Arrays.asList(fposition[i])));
                        }
                    }
                }catch (Exception e1){
                    try {
                        List<String> fposition = JsonUtil.getJsonToList(queryNormalParams.get("f_position"),String.class);
                        if(!fposition.isEmpty()){
                            idList.addAll(fposition);
                        }
                    }catch (Exception e2){
                        idList.add(String.valueOf(queryNormalParams.get("f_position")));
                    }
                }
                wrapper.and(t->{
                    idList.forEach(tt->{
                        if(StringUtil.isNotEmpty(tt) && "Microsoft SQL Server".equalsIgnoreCase(databaseName)){
                            tt = tt.replaceFirst("\\[","[[]");
                        }
                        t.like(F_main_testEntity::getFposition, tt).or();
                    });
                });
            }
---

修改文件F_main_testEntity.java.

---
@TableField(value = "f_position" , updateStrategy = FieldStrategy.ALWAYS)
    @JSONField(name = "f_position")
    private String fposition;
---

修改文件F_main_testForm.java



---
/** 岗位选择 **/
    @Schema(description = "岗位选择")
    @JsonProperty("f_position")
    @JSONField(name = "f_position")
    private Object fposition;
---

修改文件 F_main_testPagination

---
/** 岗位选择 */
    @Schema(description = "岗位选择")
    @JsonProperty("f_position")
    @JSONField(name = "fposition")
    private Object fposition;
---

修改文件F_main_testJson.json

修改1：补充第一处"fields":[] 的json对象的那个字段内容。

---
{
			"modelId":"",
			"templateJson":"[]",
			"searchMultiple":false,
			"selectType":"all",
			"id":"",
			"vModel":"f_position",
			"multiple":false,
			"config":{
				"jnpfKey":"posSelect",
				"isFromParam":false,
				"label":"岗位选择",
				"templateJson":[],
				"required":false,
				"tableName":"f_main_test",
				"useCache":false,
				"unique":false,
				"regList":[]
			}
		}
---

修改2：补充tableList对象下的fields对象增加

---
{
					"fieldName":"岗位选择",
					"field":"f_position",
					"dataType":"varchar",
					"primaryKey":0
				},
---

### 用户选择
假设生成了一个新字段名称是用户选择，并且字段key为f_user，则如下规则添加

修改前端文件Detail.vue

修改处1，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
<a-col :span="24" class="ant-col-item"  >
        <a-form-item    
  name="f_user" >
<template #label>用户选择 
</template> 
            <p>{{dataForm.f_user}}</p>
        </a-form-item>
    </a-col>
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_user":[]}, 
---



修改前端文件Form.vue

修改处1

---
dataForm:{} 对象下添加
dataForm: {
  f_user:undefined, 
},
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_user":[]}, 
---



修改处3

---
// 设置默认值
      state.dataForm={
        f_user:undefined,
      };
---

修改处4，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
<a-col :span="24" class="ant-col-item"
>
        <a-form-item   
  name="f_user" >
<template #label>用户选择 
</template> <JnpfUserSelect    v-model:value="dataForm.f_user"  @change="changeData('f_user', -1 )"  
 placeholder="请选择"   :allowClear='true'  :style='{"width":"100%"}' selectType="all"         >
</JnpfUserSelect>
        </a-form-item>
    </a-col>
---

修改文件columnList.ts

修改处1，增加json配置

---
{
		"ableRelationIds":[],
		"fullNameI18nCode":[
			""
		],
		"align":"left",
		"headerAlign":"left",
		"__config__":{
			"jnpfKey":"userSelect",
			"dataType":null,
			"isSubTable":false,
			"label":"用户选择",
			"propsUrl":null,
			"labelI18nCode":null
		},
		"prop":"f_user",
		"__vModel__":"f_user",
		"selectType":"all",
		"disabled":false,
		"id":"f_user",
		"placeholder":"请选择",
		"clearable":true,
		"jnpfKey":"userSelect",
		"ableIds":[],
		"multiple":false,
		"fullName":"用户选择",
		"label":"用户选择",
		"sortable":false,
		"relationField":"",
		"filter":false,
		"width":null,
		"fixed":"none",
		"labelI18nCode":""
	}
---

修改文件searchList.ts

修改处1添加到数组中

---
{
		"ableRelationIds":[],
		"clearable":true,
		"searchType":1,
		"jnpfKey":"userSelect",
		"ableIds":[],
		"multiple":false,
		"fullName":"用户选择",
		"fullNameI18nCode":[
			""
		],
		"label":"用户选择",
		"relationField":"",
		"__config__":{
			"jnpfKey":"userSelect",
			"defaultValue":null,
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"用户选择",
			"propsUrl":null,
			"templateJson":null,
			"labelI18nCode":null
		},
		"sourceType":2,
		"prop":"f_user",
		"__vModel__":"f_user",
		"searchMultiple":true,
		"isKeyword":false,
		"selectType":"all",
		"disabled":false,
		"id":"f_user",
		"labelI18nCode":""
	}
---

修改文件superQueryJson.ts

修改处1

---
{
		"ableRelationIds":[],
		"clearable":true,
		"ableIds":[],
		"multiple":false,
		"fullName":"用户选择",
		"fullNameI18nCode":[
			""
		],
		"relationField":"",
		"__config__":{
			"relationTable":null,
			"jnpfKey":"userSelect",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"用户选择",
			"propsUrl":null,
			"templateJson":null,
			"tableName":"f_main_test"
		},
		"__vModel__":"f_user",
		"selectType":"all",
		"disabled":false,
		"id":"f_user"
	}
---

修改文件f_main_testServiceImpl.java.

//普通查询下面添加代码

---
if(ObjectUtil.isNotEmpty(f_main_testPagination.getFuser())){
      List<String> idList = new ArrayList<>();
      try {
          String[][] fuser = JsonUtil.getJsonToBean(f_main_testPagination.getFuser(),String[][].class);
          for(int i=0;i<fuser.length;i++){
              if(fuser[i].length>0){
                  idList.add(JsonUtil.getObjectToString(Arrays.asList(fuser[i])));
              }
          }
      }catch (Exception e1){
          try {
              List<String> fuser = JsonUtil.getJsonToList(f_main_testPagination.getFuser(),String.class);
              if(!fuser.isEmpty()){
                  idList.addAll(fuser);
              }
          }catch (Exception e2){
              idList.add(String.valueOf(f_main_testPagination.getFuser()));
          }
      }
      wrapper.and(t->{
          idList.forEach(tt->{
              if(StringUtil.isNotEmpty(tt) && "Microsoft SQL Server".equalsIgnoreCase(databaseName)){
                  tt = tt.replaceFirst("\\[","[[]");
              }
              t.like(F_main_testEntity::getFuser, tt).or();
          });
      });
  }

 if(queryNormalParams != null && ObjectUtil.isNotEmpty(queryNormalParams.get("f_user"))){
                List<String> idList = new ArrayList<>();
                try {
                    String[][] fuser = JsonUtil.getJsonToBean(queryNormalParams.get("f_user"),String[][].class);
                    for(int i=0;i<fuser.length;i++){
                        if(fuser[i].length>0){
                            idList.add(JsonUtil.getObjectToString(Arrays.asList(fuser[i])));
                        }
                    }
                }catch (Exception e1){
                    try {
                        List<String> fuser = JsonUtil.getJsonToList(queryNormalParams.get("f_user"),String.class);
                        if(!fuser.isEmpty()){
                            idList.addAll(fuser);
                        }
                    }catch (Exception e2){
                        idList.add(String.valueOf(queryNormalParams.get("f_user")));
                    }
                }
                wrapper.and(t->{
                    idList.forEach(tt->{
                        if(StringUtil.isNotEmpty(tt) && "Microsoft SQL Server".equalsIgnoreCase(databaseName)){
                            tt = tt.replaceFirst("\\[","[[]");
                        }
                        t.like(F_main_testEntity::getFuser, tt).or();
                    });
                });
            }

---

修改文件F_main_testEntity.java.

---
@TableField(value = "f_user" , updateStrategy = FieldStrategy.ALWAYS)
    @JSONField(name = "f_user")
    private String fuser;
---

修改文件F_main_testForm.java

---
 /** 用户选择 **/
    @Schema(description = "用户选择")
    @JsonProperty("f_user")
    @JSONField(name = "f_user")
    private Object fuser;
---

修改文件 F_main_testPagination

---
/** 用户选择 */
    @Schema(description = "用户选择")
    @JsonProperty("f_user")
    @JSONField(name = "fuser")
    private Object fuser;
---

修改文件F_main_testJson.json

修改1：补充第一处"fields":[] 的json对象的那个字段内容。

---
{
			"modelId":"",
			"templateJson":"[]",
			"searchMultiple":false,
			"selectType":"all",
			"id":"",
			"vModel":"f_user",
			"multiple":false,
			"relationField":"",
			"config":{
				"jnpfKey":"userSelect",
				"isFromParam":false,
				"label":"用户选择",
				"templateJson":[],
				"required":false,
				"tableName":"f_main_test",
				"useCache":false,
				"unique":false,
				"regList":[]
			}
		}
---

修改2：补充tableList对象下的fields对象增加

---
{
					"fieldName":"用户选择",
					"field":"f_user",
					"dataType":"varchar",
					"primaryKey":0
				},
---

### 角色选择
假设生成了一个新字段名称是角色选择，并且字段key为f_role，则如下规则添加

修改前端文件Detail.vue

修改处1，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
 <a-col :span="24" class="ant-col-item"  >
        <a-form-item    
  name="f_role" >
<template #label>角色选择 
</template> 
            <p>{{dataForm.f_role}}</p>
        </a-form-item>
    </a-col>
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_role":[]}, 
---

修改前端文件Form.vue

修改处1

---
dataForm:{} 对象下添加
dataForm: {
  f_role:undefined, 
},
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_role":[]}, 
---

修改处3

---
// 设置默认值
      state.dataForm={
        f_role:undefined,
      };
---

修改处4，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
<a-col :span="24" class="ant-col-item"
>
        <a-form-item   
  name="f_role" >
<template #label>角色选择 
</template> <JnpfRoleSelect    v-model:value="dataForm.f_role"  @change="changeData('f_role', -1 )"  
 placeholder="请选择"   :allowClear='true'  :style='{"width":"100%"}' selectType="all"         >
</JnpfRoleSelect>
        </a-form-item>
    </a-col>
---

修改文件columnList.ts

修改处1，增加json配置

---
{
		"clearable":true,
		"jnpfKey":"roleSelect",
		"ableIds":[],
		"multiple":false,
		"fullName":"角色选择",
		"fullNameI18nCode":[
			""
		],
		"label":"角色选择",
		"sortable":false,
		"align":"left",
		"filter":false,
		"headerAlign":"left",
		"__config__":{
			"jnpfKey":"roleSelect",
			"dataType":null,
			"isSubTable":false,
			"label":"角色选择",
			"propsUrl":null,
			"labelI18nCode":null
		},
		"prop":"f_role",
		"width":null,
		"__vModel__":"f_role",
		"fixed":"none",
		"selectType":"all",
		"disabled":false,
		"id":"f_role",
		"placeholder":"请选择",
		"labelI18nCode":""
	}
---

修改文件searchList.ts

修改处1添加到数组中

---
{
		"clearable":true,
		"searchType":1,
		"jnpfKey":"roleSelect",
		"ableIds":[],
		"multiple":false,
		"fullName":"角色选择",
		"fullNameI18nCode":[
			""
		],
		"label":"角色选择",
		"__config__":{
			"jnpfKey":"roleSelect",
			"defaultValue":null,
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"角色选择",
			"propsUrl":null,
			"templateJson":null,
			"labelI18nCode":null
		},
		"sourceType":2,
		"prop":"f_role",
		"__vModel__":"f_role",
		"searchMultiple":true,
		"isKeyword":false,
		"selectType":"all",
		"disabled":false,
		"id":"f_role",
		"labelI18nCode":""
	}
---

修改文件superQueryJson.ts

修改处1

---
{
		"clearable":true,
		"ableIds":[],
		"multiple":false,
		"fullName":"角色选择",
		"fullNameI18nCode":[
			""
		],
		"__config__":{
			"relationTable":null,
			"jnpfKey":"roleSelect",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"角色选择",
			"propsUrl":null,
			"templateJson":null,
			"tableName":"f_main_test"
		},
		"__vModel__":"f_role",
		"selectType":"all",
		"disabled":false,
		"id":"f_role"
	}
---

修改文件f_main_testServiceImpl.java.

//普通查询下面添加代码

---
if(ObjectUtil.isNotEmpty(f_main_testPagination.getFrole())){
                List<String> idList = new ArrayList<>();
                try {
                    String[][] frole = JsonUtil.getJsonToBean(f_main_testPagination.getFrole(),String[][].class);
                    for(int i=0;i<frole.length;i++){
                        if(frole[i].length>0){
                            idList.add(JsonUtil.getObjectToString(Arrays.asList(frole[i])));
                        }
                    }
                }catch (Exception e1){
                    try {
                        List<String> frole = JsonUtil.getJsonToList(f_main_testPagination.getFrole(),String.class);
                        if(!frole.isEmpty()){
                            idList.addAll(frole);
                        }
                    }catch (Exception e2){
                        idList.add(String.valueOf(f_main_testPagination.getFrole()));
                    }
                }
                wrapper.and(t->{
                    idList.forEach(tt->{
                        if(StringUtil.isNotEmpty(tt) && "Microsoft SQL Server".equalsIgnoreCase(databaseName)){
                            tt = tt.replaceFirst("\\[","[[]");
                        }
                        t.like(F_main_testEntity::getFrole, tt).or();
                    });
                });
            }
            
  if(queryNormalParams != null && ObjectUtil.isNotEmpty(queryNormalParams.get("f_role"))){
                List<String> idList = new ArrayList<>();
                try {
                    String[][] frole = JsonUtil.getJsonToBean(queryNormalParams.get("f_role"),String[][].class);
                    for(int i=0;i<frole.length;i++){
                        if(frole[i].length>0){
                            idList.add(JsonUtil.getObjectToString(Arrays.asList(frole[i])));
                        }
                    }
                }catch (Exception e1){
                    try {
                        List<String> frole = JsonUtil.getJsonToList(queryNormalParams.get("f_role"),String.class);
                        if(!frole.isEmpty()){
                            idList.addAll(frole);
                        }
                    }catch (Exception e2){
                        idList.add(String.valueOf(queryNormalParams.get("f_role")));
                    }
                }
                wrapper.and(t->{
                    idList.forEach(tt->{
                        if(StringUtil.isNotEmpty(tt) && "Microsoft SQL Server".equalsIgnoreCase(databaseName)){
                            tt = tt.replaceFirst("\\[","[[]");
                        }
                        t.like(F_main_testEntity::getFrole, tt).or();
                    });
                });
            }

---

修改文件F_main_testEntity.java.

---
@TableField(value = "f_role" , updateStrategy = FieldStrategy.ALWAYS)
    @JSONField(name = "f_role")
    private String frole;
---

修改文件F_main_testForm.java



---
 /** 角色选择 **/
    @Schema(description = "角色选择")
    @JsonProperty("f_role")
    @JSONField(name = "f_role")
    private Object frole;
---

修改文件 F_main_testPagination

---
 /** 角色选择 */
    @Schema(description = "角色选择")
    @JsonProperty("f_role")
    @JSONField(name = "frole")
    private Object frole;
---

修改文件F_main_testJson.json

修改1：补充第一处"fields":[] 的json对象的那个字段内容。

---
{
			"modelId":"",
			"templateJson":"[]",
			"searchMultiple":false,
			"selectType":"all",
			"id":"",
			"vModel":"f_role",
			"multiple":false,
			"config":{
				"jnpfKey":"roleSelect",
				"isFromParam":false,
				"label":"角色选择",
				"templateJson":[],
				"required":false,
				"tableName":"f_main_test",
				"useCache":false,
				"unique":false,
				"regList":[]
			}
		}
---

修改2：补充tableList对象下的fields对象增加

---
{
					"fieldName":"角色选择",
					"field":"f_role",
					"dataType":"varchar",
					"primaryKey":0
				},
---

### 省市区
假设生成了一个新字段名称是省市区，并且字段key为f_area，则如下规则添加

修改前端文件Detail.vue

修改处1，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
<a-col :span="24" class="ant-col-item"  >
        <a-form-item    
  name="f_area" >
<template #label>省市区 
</template> 
            <p>{{dataForm.f_area}}</p>
        </a-form-item>
    </a-col>
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_area":[]}, 
---

修改前端文件Form.vue

修改处1

---
dataForm:{} 对象下添加
dataForm: {
  f_area:[], 
},
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_area":[]}, 
---

修改处3

---
// 设置默认值
      state.dataForm={
        f_area:[],
      };
---

修改处4，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
<a-col :span="24" class="ant-col-item"
>
        <a-form-item   
  name="f_area" >
<template #label>省市区 
</template> <JnpfAreaSelect    v-model:value="dataForm.f_area"  @change="changeData('f_area', -1 )"  
 placeholder="请选择"   :allowClear='true'  :style='{"width":"100%"}' :showSearch='false'  :level=2         >
</JnpfAreaSelect>
        </a-form-item>
    </a-col>
---

修改文件columnList.ts

修改处1，增加json配置

---
{
		"filterable":false,
		"clearable":true,
		"level":2,
		"jnpfKey":"areaSelect",
		"multiple":false,
		"fullName":"省市区",
		"fullNameI18nCode":[
			""
		],
		"label":"省市区",
		"sortable":false,
		"align":"left",
		"filter":false,
		"headerAlign":"left",
		"__config__":{
			"jnpfKey":"areaSelect",
			"dataType":null,
			"isSubTable":false,
			"label":"省市区",
			"propsUrl":null,
			"labelI18nCode":null
		},
		"prop":"f_area",
		"width":null,
		"__vModel__":"f_area",
		"fixed":"none",
		"disabled":false,
		"id":"f_area",
		"placeholder":"请选择",
		"labelI18nCode":""
	}
---

修改文件searchList.ts

修改处1添加到数组中

---
{
		"filterable":false,
		"clearable":true,
		"searchType":1,
		"level":2,
		"jnpfKey":"areaSelect",
		"multiple":false,
		"fullName":"省市区",
		"fullNameI18nCode":[
			""
		],
		"label":"省市区",
		"__config__":{
			"jnpfKey":"areaSelect",
			"defaultValue":"[]",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"省市区",
			"propsUrl":null,
			"templateJson":null,
			"labelI18nCode":null
		},
		"sourceType":2,
		"prop":"f_area",
		"__vModel__":"f_area",
		"searchMultiple":false,
		"isKeyword":false,
		"disabled":false,
		"id":"f_area",
		"value":[],
		"labelI18nCode":""
	}
---

修改文件superQueryJson.ts

修改处1

---
{
		"filterable":false,
		"clearable":true,
		"level":2,
		"multiple":false,
		"fullName":"省市区",
		"fullNameI18nCode":[
			""
		],
		"__config__":{
			"relationTable":null,
			"jnpfKey":"areaSelect",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"省市区",
			"propsUrl":null,
			"templateJson":null,
			"tableName":"f_main_test"
		},
		"__vModel__":"f_area",
		"disabled":false,
		"id":"f_area"
	}
---

修改文件f_main_testServiceImpl.java.

//普通查询下面添加代码

---
if(ObjectUtil.isNotEmpty(f_main_testPagination.getFarea())){
                List<String> idList = new ArrayList<>();
                try {
                    String[][] farea = JsonUtil.getJsonToBean(f_main_testPagination.getFarea(),String[][].class);
                    for(int i=0;i<farea.length;i++){
                        if(farea[i].length>0){
                            idList.add(JsonUtil.getObjectToString(Arrays.asList(farea[i])));
                        }
                    }
                }catch (Exception e1){
                    try {
                        List<String> farea = JsonUtil.getJsonToList(f_main_testPagination.getFarea(),String.class);
                        if(!farea.isEmpty()){
                            idList.add(JsonUtil.getObjectToString(farea));
                        }
                    }catch (Exception e2){
                        idList.add(String.valueOf(f_main_testPagination.getFarea()));
                    }
                }
                wrapper.and(t->{
                    idList.forEach(tt->{
                        if(StringUtil.isNotEmpty(tt) && "Microsoft SQL Server".equalsIgnoreCase(databaseName)){
                            tt = tt.replaceFirst("\\[","[[]");
                        }
                        t.like(F_main_testEntity::getFarea, tt).or();
                    });
                });
            }

   if(queryNormalParams != null && ObjectUtil.isNotEmpty(queryNormalParams.get("f_area"))){
                List<String> idList = new ArrayList<>();
                try {
                    String[][] farea = JsonUtil.getJsonToBean(queryNormalParams.get("f_area"),String[][].class);
                    for(int i=0;i<farea.length;i++){
                        if(farea[i].length>0){
                            idList.add(JsonUtil.getObjectToString(Arrays.asList(farea[i])));
                        }
                    }
                }catch (Exception e1){
                    try {
                        List<String> farea = JsonUtil.getJsonToList(queryNormalParams.get("f_area"),String.class);
                        if(!farea.isEmpty()){
                            idList.add(JsonUtil.getObjectToString(farea));
                        }
                    }catch (Exception e2){
                        idList.add(String.valueOf(queryNormalParams.get("f_area")));
                    }
                }
                wrapper.and(t->{
                    idList.forEach(tt->{
                        if(StringUtil.isNotEmpty(tt) && "Microsoft SQL Server".equalsIgnoreCase(databaseName)){
                            tt = tt.replaceFirst("\\[","[[]");
                        }
                        t.like(F_main_testEntity::getFarea, tt).or();
                    });
                });
            }            
---

修改文件F_main_testEntity.java.

---
 @TableField(value = "f_area" , updateStrategy = FieldStrategy.ALWAYS)
    @JSONField(name = "f_area")
    private String farea;
---

修改文件F_main_testForm.java



---
/** 省市区 **/
    @Schema(description = "省市区")
    @JsonProperty("f_area")
    @JSONField(name = "f_area")
    private Object farea;
---

修改文件 F_main_testPagination

---
/** 省市区 */
    @Schema(description = "省市区")
    @JsonProperty("f_area")
    @JSONField(name = "farea")
    private Object farea;
---

修改文件F_main_testJson.json

修改1：补充第一处"fields":[] 的json对象的那个字段内容。

---
{
			"modelId":"",
			"templateJson":"[]",
			"searchMultiple":false,
			"id":"",
			"vModel":"f_area",
			"level":2,
			"multiple":false,
			"config":{
				"jnpfKey":"areaSelect",
				"isFromParam":false,
				"label":"省市区",
				"templateJson":[],
				"required":false,
				"tableName":"f_main_test",
				"useCache":false,
				"unique":false,
				"regList":[]
			}
		}
---

修改2：补充tableList对象下的fields对象增加

---
{
					"fieldName":"省市区",
					"field":"f_area",
					"dataType":"varchar",
					"primaryKey":0
				},
---

### 创建人
假设生成了一个新字段名称是创建人，并且字段key为f_create_by，则如下规则添加

修改前端文件Detail.vue

修改处1，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
<a-col :span="24" class="ant-col-item"  >
        <a-form-item    
  name="f_create_by" >
<template #label>创建人 
</template> 
            <p>{{dataForm.f_create_by}}</p>
        </a-form-item>
    </a-col>
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_create_by":[]}, 
---

修改前端文件Form.vue

修改处1

---
dataForm:{} 对象下添加
dataForm: {
  f_create_by:'', 
},
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_create_by":[]}, 
---



修改处3

---
// 设置默认值
      state.dataForm={
        f_create_by:'',
      };
---

修改处4，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
<a-col :span="24" class="ant-col-item"
>
        <a-form-item   
  name="f_create_by" >
<template #label>创建人 
</template> <JnpfOpenData    v-model:value="dataForm.f_create_by"  @change="changeData('f_create_by', -1 )"  
 readonly  :style='{"width":"100%"}' type="currUser"         >
</JnpfOpenData>
        </a-form-item>
    </a-col>
---

修改文件columnList.ts

修改处1，增加json配置

---
{
		"jnpfKey":"createUser",
		"fullName":"创建人",
		"fullNameI18nCode":[
			""
		],
		"label":"创建人",
		"sortable":false,
		"align":"left",
		"type":"currUser",
		"filter":false,
		"headerAlign":"left",
		"__config__":{
			"jnpfKey":"createUser",
			"dataType":null,
			"isSubTable":false,
			"label":"创建人",
			"propsUrl":null,
			"labelI18nCode":null
		},
		"readonly":true,
		"prop":"f_create_by",
		"width":null,
		"__vModel__":"f_create_by",
		"fixed":"none",
		"id":"f_create_by",
		"placeholder":"",
		"labelI18nCode":""
	}
---

修改文件searchList.ts

修改处1添加到数组中

---
{
		"searchType":1,
		"jnpfKey":"createUser",
		"fullName":"创建人",
		"fullNameI18nCode":[
			""
		],
		"label":"创建人",
		"type":"currUser",
		"__config__":{
			"jnpfKey":"createUser",
			"defaultValue":"",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"创建人",
			"propsUrl":null,
			"templateJson":null,
			"labelI18nCode":null
		},
		"readonly":true,
		"sourceType":2,
		"prop":"f_create_by",
		"__vModel__":"f_create_by",
		"searchMultiple":false,
		"isKeyword":false,
		"id":"f_create_by",
		"labelI18nCode":""
	}
---

修改文件superQueryJson.ts

修改处1

---
{
		"__config__":{
			"relationTable":null,
			"jnpfKey":"createUser",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"创建人",
			"propsUrl":null,
			"templateJson":null,
			"tableName":"f_main_test"
		},
		"readonly":true,
		"__vModel__":"f_create_by",
		"fullName":"创建人",
		"fullNameI18nCode":[
			""
		],
		"id":"f_create_by",
		"type":"currUser"
	}
---

修改文件f_main_testServiceImpl.java.

//普通查询下面添加代码

---
if(ObjectUtil.isNotEmpty(f_main_testPagination.getFcreateBy())){
                wrapper.eq(F_main_testEntity::getFcreateBy,f_main_testPagination.getFcreateBy());
            }
 if(queryNormalParams != null && ObjectUtil.isNotEmpty(queryNormalParams.get("f_create_by"))){
                wrapper.eq(F_main_testEntity::getFcreateBy,queryNormalParams.get("f_create_by"));
            }            
---

修改文件F_main_testEntity.java.

---
 @TableField("f_create_by")
    @JSONField(name = "f_create_by")
    private String fcreateBy;
---

修改文件F_main_testForm.java



---
 /** 创建人 **/
    @Schema(description = "创建人")
    @JsonProperty("f_create_by")
    @JSONField(name = "f_create_by")
    private String fcreateBy;
---

修改文件 F_main_testPagination

---
/** 创建人 */
    @Schema(description = "创建人")
    @JsonProperty("f_create_by")
    @JSONField(name = "fcreateBy")
    private Object fcreateBy;
---

修改文件F_main_testJson.json

修改1：补充第一处"fields":[] 的json对象的那个字段内容。

---
{
			"modelId":"",
			"templateJson":"[]",
			"searchMultiple":false,
			"id":"",
			"vModel":"f_create_by",
			"multiple":false,
			"config":{
				"jnpfKey":"createUser",
				"isFromParam":false,
				"label":"创建人",
				"templateJson":[],
				"required":false,
				"tableName":"f_main_test",
				"useCache":false,
				"unique":false
			}
		}
---

修改2：补充tableList对象下的fields对象增加

---
{
					"fieldName":"创建人",
					"field":"f_create_by",
					"dataType":"varchar",
					"primaryKey":0
				},
---

### 创建时间
假设生成了一个新字段名称是创建时间，并且字段key为f_create_time，则如下规则添加

修改前端文件Detail.vue

修改处1，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
 <a-col :span="24" class="ant-col-item"  >
        <a-form-item    
  name="f_create_time" >
<template #label>创建时间 
</template> 
            <p>{{dataForm.f_create_time}}</p>
        </a-form-item>
    </a-col>
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_create_time":[]}, 
---



修改前端文件Form.vue

修改处1

---
dataForm:{} 对象下添加
dataForm: {
  f_create_time:'', 
},
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_create_time":[]}, 
---



修改处3

---
// 设置默认值
      state.dataForm={
        f_create_time:'',
      };
---

修改处4，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
<a-col :span="24" class="ant-col-item">
        <a-form-item   
  name="f_create_time" >
<template #label>创建时间 
</template> <JnpfOpenData    v-model:value="dataForm.f_create_time"  @change="changeData('f_create_time', -1 )"  
 readonly  :style='{"width":"100%"}' type="currTime"         >
</JnpfOpenData>
        </a-form-item>
    </a-col>
---

修改文件columnList.ts

修改处1，增加json配置

---
{
		"jnpfKey":"createTime",
		"fullName":"创建时间",
		"fullNameI18nCode":[
			""
		],
		"label":"创建时间",
		"sortable":false,
		"align":"left",
		"type":"currTime",
		"filter":false,
		"headerAlign":"left",
		"__config__":{
			"jnpfKey":"createTime",
			"dataType":null,
			"isSubTable":false,
			"label":"创建时间",
			"propsUrl":null,
			"labelI18nCode":null
		},
		"readonly":true,
		"prop":"f_create_time",
		"width":null,
		"__vModel__":"f_create_time",
		"fixed":"none",
		"id":"f_create_time",
		"placeholder":"",
		"labelI18nCode":""
	}
---

修改文件searchList.ts

修改处1添加到数组中

---
{
		"searchType":3,
		"jnpfKey":"createTime",
		"fullName":"创建时间",
		"fullNameI18nCode":[
			""
		],
		"label":"创建时间",
		"type":"currTime",
		"__config__":{
			"jnpfKey":"createTime",
			"defaultValue":"",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"创建时间",
			"propsUrl":null,
			"templateJson":null,
			"labelI18nCode":null
		},
		"readonly":true,
		"sourceType":2,
		"prop":"f_create_time",
		"__vModel__":"f_create_time",
		"searchMultiple":false,
		"isKeyword":false,
		"id":"f_create_time",
		"labelI18nCode":""
	}
---

修改文件superQueryJson.ts

修改处1

---
{
		"__config__":{
			"relationTable":null,
			"jnpfKey":"createTime",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"创建时间",
			"propsUrl":null,
			"templateJson":null,
			"tableName":"f_main_test"
		},
		"readonly":true,
		"__vModel__":"f_create_time",
		"fullName":"创建时间",
		"fullNameI18nCode":[
			""
		],
		"id":"f_create_time",
		"type":"currTime"
	}
---

修改文件f_main_testServiceImpl.java.

//普通查询下面添加代码

---
if(ObjectUtil.isNotEmpty(f_main_testPagination.getFcreateTime())){
                List<String> jsonList =  JsonUtil.getJsonToList(f_main_testPagination.getFcreateTime(),String.class);
                for(int i=0;i<jsonList.size();i++){
                    String id = String.valueOf(jsonList.get(i));
                    boolean idAll = StringUtil.isNotEmpty(id) && !id.equals("null");
                    if(idAll){
                        Object b= new Date(Long.valueOf(id));
                        if(i==0){
                            wrapper.ge(F_main_testEntity::getFcreateTime,b);
                        }else{
                            wrapper.le(F_main_testEntity::getFcreateTime,b);
                        }
                    }
                }
            }


 if(queryNormalParams != null && ObjectUtil.isNotEmpty(queryNormalParams.get("f_create_time"))){
                List<String> jsonList =  JsonUtil.getJsonToList(queryNormalParams.get("f_create_time"),String.class);
                for(int i=0;i<jsonList.size();i++){
                    String id = String.valueOf(jsonList.get(i));
                    boolean idAll = StringUtil.isNotEmpty(id) && !id.equals("null");
                    if(idAll){
                        Object b= new Date(Long.valueOf(id));
                        if(i==0){
                            wrapper.ge(F_main_testEntity::getFcreateTime,b);
                        }else{
                            wrapper.le(F_main_testEntity::getFcreateTime,b);
                        }
                    }
                }
            }            
---

修改文件F_main_testEntity.java.

---
@TableField("f_create_time")
    @JSONField(name = "f_create_time")
    private Date fcreateTime;
---

修改文件F_main_testForm.java



---
/** 创建时间 **/
    @Schema(description = "创建时间")
    @JsonProperty("f_create_time")
    @JSONField(name = "f_create_time")
    private String fcreateTime;
---

修改文件 F_main_testPagination

---
/** 创建时间 */
    @Schema(description = "创建时间")
    @JsonProperty("f_create_time")
    @JSONField(name = "fcreateTime")
    private Object fcreateTime;
---

修改文件F_main_testJson.json

修改1：补充第一处"fields":[] 的json对象的那个字段内容。

---
{
			"modelId":"",
			"templateJson":"[]",
			"searchMultiple":false,
			"id":"",
			"vModel":"f_create_time",
			"multiple":false,
			"config":{
				"jnpfKey":"createTime",
				"isFromParam":false,
				"label":"创建时间",
				"templateJson":[],
				"required":false,
				"tableName":"f_main_test",
				"useCache":false,
				"unique":false
			}
		}
---

修改2：补充tableList对象下的fields对象增加

---
{
					"fieldName":"创建时间",
					"field":"f_create_time",
					"dataType":"date",
					"primaryKey":0
				},
---

### 修改人员
修改人员就是变更人员的意思。如果判断是需要字段是记录修改的人员使用。假设生成了一个新字段名称是修改人员，并且字段key为f_update_by，则如下规则添加

修改前端文件Detail.vue

修改处1，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
  <a-col :span="24" class="ant-col-item"  >
        <a-form-item    
  name="f_update_by" >
<template #label>修改人员 
</template> 
            <p>{{dataForm.f_update_by}}</p>
        </a-form-item>
    </a-col>
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_update_by":[]}, 
---



修改前端文件Form.vue

修改处1

---
dataForm:{} 对象下添加
dataForm: {
  f_update_by:'', 
},
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_update_by":[]}, 
---

修改处3

---
// 设置默认值
      state.dataForm={
        f_update_by:'',
      };
---

修改处4，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
<a-col :span="24" class="ant-col-item"
>
        <a-form-item   
  name="f_update_by" >
<template #label>修改人员 
</template> <JnpfInput    v-model:value="dataForm.f_update_by"  @change="changeData('f_update_by', -1 )"  
 placeholder="系统自动生成"   readonly  :style='{"width":"100%"}'        >
</JnpfInput>
        </a-form-item>
    </a-col>
---

修改文件columnList.ts

修改处1，增加json配置

---
{
		"jnpfKey":"modifyUser",
		"fullName":"修改人员",
		"fullNameI18nCode":[
			""
		],
		"label":"修改人员",
		"sortable":false,
		"align":"left",
		"filter":false,
		"headerAlign":"left",
		"__config__":{
			"jnpfKey":"modifyUser",
			"dataType":null,
			"isSubTable":false,
			"label":"修改人员",
			"propsUrl":null,
			"labelI18nCode":null
		},
		"readonly":true,
		"prop":"f_update_by",
		"width":null,
		"__vModel__":"f_update_by",
		"fixed":"none",
		"id":"f_update_by",
		"placeholder":"系统自动生成",
		"labelI18nCode":""
	}
---

修改文件searchList.ts

修改处1添加到数组中

---
{
		"searchType":1,
		"jnpfKey":"modifyUser",
		"fullName":"修改人员",
		"fullNameI18nCode":[
			""
		],
		"label":"修改人员",
		"__config__":{
			"jnpfKey":"modifyUser",
			"defaultValue":"",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"修改人员",
			"propsUrl":null,
			"templateJson":null,
			"labelI18nCode":null
		},
		"readonly":true,
		"sourceType":2,
		"prop":"f_update_by",
		"__vModel__":"f_update_by",
		"searchMultiple":false,
		"isKeyword":false,
		"id":"f_update_by",
		"labelI18nCode":""
	}
---

修改文件superQueryJson.ts

修改处1

---
{
		"__config__":{
			"relationTable":null,
			"jnpfKey":"modifyUser",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"修改人员",
			"propsUrl":null,
			"templateJson":null,
			"tableName":"f_main_test"
		},
		"readonly":true,
		"__vModel__":"f_update_by",
		"fullName":"修改人员",
		"fullNameI18nCode":[
			""
		],
		"id":"f_update_by"
	}
---

修改文件f_main_testServiceImpl.java.

//普通查询下面添加代码

---
if(ObjectUtil.isNotEmpty(f_main_testPagination.getFupdateBy())){
                wrapper.eq(F_main_testEntity::getFupdateBy,f_main_testPagination.getFupdateBy());
            }

  if(queryNormalParams != null && ObjectUtil.isNotEmpty(queryNormalParams.get("f_update_by"))){
                wrapper.eq(F_main_testEntity::getFupdateBy,queryNormalParams.get("f_update_by"));
            }            
---

修改文件F_main_testEntity.java.

---
@TableField("f_update_by")
    @JSONField(name = "f_update_by")
    private String fupdateBy;
---

修改文件F_main_testForm.java



---
 /** 修改人员 **/
    @Schema(description = "修改人员")
    @JsonProperty("f_update_by")
    @JSONField(name = "f_update_by")
    private String fupdateBy;
---

修改文件 F_main_testPagination

---
 /** 修改人员 */
    @Schema(description = "修改人员")
    @JsonProperty("f_update_by")
    @JSONField(name = "fupdateBy")
    private Object fupdateBy;
---

修改文件F_main_testJson.json

修改1：补充第一处"fields":[] 的json对象的那个字段内容。

---
{
			"modelId":"",
			"templateJson":"[]",
			"searchMultiple":false,
			"id":"",
			"vModel":"f_update_by",
			"multiple":false,
			"config":{
				"jnpfKey":"modifyUser",
				"isFromParam":false,
				"label":"修改人员",
				"templateJson":[],
				"required":false,
				"tableName":"f_main_test",
				"useCache":false,
				"unique":false
			}
		}
---

修改2：补充tableList对象下的fields对象增加

---
{
					"fieldName":"修改人员",
					"field":"f_update_by",
					"dataType":"varchar",
					"primaryKey":0
				},
---

### 修改时间
假设生成了一个新字段名称是修改时间，并且字段key为f_update_time，则如下规则添加

修改前端文件Detail.vue

修改处1，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
<a-col :span="24" class="ant-col-item"  >
        <a-form-item    
  name="f_update_time" >
<template #label>修改时间 
</template> 
            <p>{{dataForm.f_update_time}}</p>
        </a-form-item>
    </a-col>
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_update_time":[]}, 
---

修改前端文件Form.vue

修改处1

---
dataForm:{} 对象下添加
dataForm: {
  f_update_time:'', 
},
---

修改处2

---
interfaceRes：{}对象下添加
interfaceRes:  {"f_update_time":[]}, 
---

修改处3

---
// 设置默认值
      state.dataForm={
        f_update_time:'',
      };
---

修改处4，找到 <!-- 具体表单 --> 部分区域，然后补充在下面。

---
 <a-col :span="24" class="ant-col-item"
>
        <a-form-item   
  name="f_update_time" >
<template #label>修改时间 
</template> <JnpfInput    v-model:value="dataForm.f_update_time"  @change="changeData('f_update_time', -1 )"  
 placeholder="系统自动生成"   readonly  :style='{"width":"100%"}'        >
</JnpfInput>
        </a-form-item>
    </a-col>
---

修改文件columnList.ts

修改处1，增加json配置

---
{
		"jnpfKey":"modifyTime",
		"fullName":"修改时间",
		"fullNameI18nCode":[
			""
		],
		"label":"修改时间",
		"sortable":false,
		"align":"left",
		"filter":false,
		"headerAlign":"left",
		"__config__":{
			"jnpfKey":"modifyTime",
			"dataType":null,
			"isSubTable":false,
			"label":"修改时间",
			"propsUrl":null,
			"labelI18nCode":null
		},
		"readonly":true,
		"prop":"f_update_time",
		"width":null,
		"__vModel__":"f_update_time",
		"fixed":"none",
		"id":"f_update_time",
		"placeholder":"系统自动生成",
		"labelI18nCode":""
	}
---

修改文件searchList.ts

修改处1添加到数组中

---
{
		"searchType":3,
		"jnpfKey":"modifyTime",
		"fullName":"修改时间",
		"fullNameI18nCode":[
			""
		],
		"label":"修改时间",
		"__config__":{
			"jnpfKey":"modifyTime",
			"defaultValue":"",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"修改时间",
			"propsUrl":null,
			"templateJson":null,
			"labelI18nCode":null
		},
		"readonly":true,
		"sourceType":2,
		"prop":"f_update_time",
		"__vModel__":"f_update_time",
		"searchMultiple":false,
		"isKeyword":false,
		"id":"f_update_time",
		"labelI18nCode":""
	}
---

修改文件superQueryJson.ts

修改处1

---
{
		"__config__":{
			"relationTable":null,
			"jnpfKey":"modifyTime",
			"dataType":null,
			"dictionaryType":null,
			"isSubTable":false,
			"label":"修改时间",
			"propsUrl":null,
			"templateJson":null,
			"tableName":"f_main_test"
		},
		"readonly":true,
		"__vModel__":"f_update_time",
		"fullName":"修改时间",
		"fullNameI18nCode":[
			""
		],
		"id":"f_update_time"
	}
---

修改文件f_main_testServiceImpl.java.

//普通查询下面添加代码

---
if(ObjectUtil.isNotEmpty(f_main_testPagination.getFupdateTime())){
                List<String> jsonList =  JsonUtil.getJsonToList(f_main_testPagination.getFupdateTime(),String.class);
                for(int i=0;i<jsonList.size();i++){
                    String id = String.valueOf(jsonList.get(i));
                    boolean idAll = StringUtil.isNotEmpty(id) && !id.equals("null");
                    if(idAll){
                        Object b= new Date(Long.valueOf(id));
                        if(i==0){
                            wrapper.ge(F_main_testEntity::getFupdateTime,b);
                        }else{
                            wrapper.le(F_main_testEntity::getFupdateTime,b);
                        }
                    }
                }
            }

  if(queryNormalParams != null && ObjectUtil.isNotEmpty(queryNormalParams.get("f_update_time"))){
                List<String> jsonList =  JsonUtil.getJsonToList(queryNormalParams.get("f_update_time"),String.class);
                for(int i=0;i<jsonList.size();i++){
                    String id = String.valueOf(jsonList.get(i));
                    boolean idAll = StringUtil.isNotEmpty(id) && !id.equals("null");
                    if(idAll){
                        Object b= new Date(Long.valueOf(id));
                        if(i==0){
                            wrapper.ge(F_main_testEntity::getFupdateTime,b);
                        }else{
                            wrapper.le(F_main_testEntity::getFupdateTime,b);
                        }
                    }
                }
            }            

---

修改文件F_main_testEntity.java.

---
@TableField("f_update_time")
    @JSONField(name = "f_update_time")
    private Date fupdateTime;
---

修改文件F_main_testForm.java



---
/** 修改时间 **/
    @Schema(description = "修改时间")
    @JsonProperty("f_update_time")
    @JSONField(name = "f_update_time")
    private String fupdateTime;
---

修改文件 F_main_testPagination

---
/** 修改时间 */
    @Schema(description = "修改时间")
    @JsonProperty("f_update_time")
    @JSONField(name = "fupdateTime")
    private Object fupdateTime;
---

修改文件F_main_testJson.json

修改1：补充第一处"fields":[] 的json对象的那个字段内容。

---
{
			"modelId":"",
			"templateJson":"[]",
			"searchMultiple":false,
			"id":"",
			"vModel":"f_update_time",
			"multiple":false,
			"config":{
				"jnpfKey":"modifyTime",
				"isFromParam":false,
				"label":"修改时间",
				"templateJson":[],
				"required":false,
				"tableName":"f_main_test",
				"useCache":false,
				"unique":false
			}
		}
---

修改2：补充tableList对象下的fields对象增加

---
{
					"fieldName":"修改时间",
					"field":"f_update_time",
					"dataType":"date",
					"primaryKey":0
				},
---

## 数据源说明
数据源情况说明，存在3种数据源情况。第一种情况:静态数据源，第二种数据字典，第三种是数据接口。类型中有可能是需要数据源的控件，如单选框，多选框，下拉。目前文档的案例只是举例默认用的静态数据源。

数据字典方式：

---
查询逻辑：用户某个字段说用数据字典物料属性。
执行命令 "python  ../jnpf-skill-base/jnpf-assistant.py sqlexecute select f_id,f_full_name from base_dictionary_type WHERE f_full_name like '%物料属性%' "
物料属性是客户说的。如果数据库找到有就推荐给用户确认。没有提示用户没有类似
的字典类型，不能自己随意创建。如果找到多条数据，需要让用户确定用哪一条。
返回的格式是获取到f_id的值记住，待会修改会用
---

数据接口方式：

---
查询逻辑：用户某个字段说用数据接口查询树形物料分组列表，这个是接口名。
执行命令 "python  ../jnpf-skill-base/jnpf-assistant.py sqlexecute select f_id,f_full_name from base_data_interface where f_full_name like '%查询树形物料分组列表%'"
其他的数据忽略，找到f_id | f_full_name  2列

返回的格式是获取到f_id的值，f_full_name也记住，待会修改会用
如果数据库找到有就推荐给用户确认。没有提示用户没有类似
的数据接口，不能自己随意创建。如果找到多条数据，需要让用户确定用哪一条。
---

如果用户有需要修改数据源类型字典或数据接口需要参考下拉选择类型的规则说明来改。把上面得取到的id给填到对应的位置。

## 控件类型使用方式
控件有类型，基础类型。高级控件，系统类型。下面的表格可以参考，数据库示例是mysql.其他库自己转换

| 字段 | 控件标识 | 前端标识 | 数据库类型 | 说明 |
| --- | --- | --- | --- | --- |
| ai自己判断生成 | input | JnpfInput | varchar | 使用input输入字符串类型，如名字，账号 |
| ai自己判断生成 | textarea | JnpfTextarea | text | 多行输入使用，如地址信息字段 |
| ai自己判断生成 | inputNumber | JnpfInputNumber | int | 数字输入，数字类型的使用 |
| ai自己判断生成 | switch | JnpfSwitch | int | 开关类型 |
| ai自己判断生成 | radio | JnpfRadio | varchar | 单选框组 |
| ai自己判断生成 | checkbox | JnpfCheckbox | varchar | 多选框组 |
| ai自己判断生成 | select | JnpfSelect | varchar | 下拉选择 |
| ai自己判断生成 | datePicker | JnpfDatePicker | datatime | 日期选择 |
| ai自己判断生成 | timePicker | JnpfTimePicker | datatime | 时间选择 |
| ai自己判断生成 | uploadFile | JnpfUploadFile | varchar | 文件上传，如果遇到字段可能是文件直接用这个 |
| ai自己判断生成 | uploadImg | JnpfUploadImg |  | 图片上传，如果遇到字段可能是图片直接用这个 |
| ai自己判断生成 | organizeSelect | JnpfOrganizeSelect |  | 组织选择 |
| ai自己判断生成 | posSelect | JnpfPosSelect |  | 岗位选择 |
| ai自己判断生成 | userSelect | JnpfUserSelect |  | 用户选择 |
| ai自己判断生成 | roleSelect | JnpfRoleSelect |  | 角色选择 |
| ai自己判断生成 | areaSelect | JnpfAreaSelect |  | 省市区 |
| `f_create_by` | createUser | JnpfOpenData |  | 创建人 |
| `f_create_time` | createTime | JnpfOpenData |  | 创建时间 |
| `f_update_by` | modifyUser | JnpfInput |  | 更新人 |
| `f_update_time` | modifyTime | JnpfInput |  | 更新时间 |






## 
