package common;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.twelvemonkeys.lang.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MatchTest {
    @Test
    public void test() {
        String a = """
                121""";
        String name = "xiao";
        int ret = switch (name) {
            case "ai" -> 1;
            case "xiao", "xian" ->{
                System.out.println(1);
                yield 1;
            }
            default -> 0;
        };
        System.out.println(ret);
    }

    @Test
    public void test1() {
        String sql = "SELECT\n" +
                "  c.f_id,\n" +
                "  c.customer_name,\n" +
                "  c.customer_code,\n" +
                "  us.f_real_name\n" +
                "FROM\n" +
                "  mes_xs_customer c\n" +
                "LEFT JOIN public.mes_xs_customer_address ca \n" +
                "    ON c.f_id = ca.customer_id \n" +
                "    -- 只匹配默认地址（无则地址字段为NULL）\n" +
                "    AND ca.is_moren = '1'\n" +
                "    LEFT JOIN public.base_user us \n" +
                "    ON c.salesman = us.f_id\n" +
                "WHERE\n" +
                "  c.@showKey = @showValue";
        String result = sql.replaceFirst("=(\\s*@showValue)", " in $1");
        System.out.println(result);
    }
    @Test
    public void test1221(){
        List<GenFieldModel> fieldModels = new ArrayList<>();
        GenFieldModel genFieldModel = new GenFieldModel();
        genFieldModel.setLabel("商品信息");
        genFieldModel.setVModel("f_name");
        genFieldModel.setControlTag("JnpfInput");
        genFieldModel.setJnpfKey("input");
        fieldModels.add(genFieldModel);
        String formDataString =
                """
                {
                    "formRef": "formRef",
                    "formModel": "dataForm",
                    "size": "middle",
                    "labelPosition": "right",
                    "labelWidth": 100,
                    "labelSuffix": "",
                    "formRules": "rules",
                    "popupType": "general",
                    "generalWidth": "600px",
                    "fullScreenWidth": "100%",
                    "drawerWidth": "600px",
                    "gutter": 15,
                    "disabled": false,
                    "span": 24,
                    "colon": false,
                    "hasCopyBtn": false,
                    "copyButtonText": "复制",
                    "copyButtonTextI18nCode": "common.copyText",
                    "hasCancelBtn": true,
                    "cancelButtonText": "取消",
                    "cancelButtonTextI18nCode": "common.cancelText",
                    "hasConfirmBtn": true,
                    "confirmButtonText": "确定",
                    "confirmButtonTextI18nCode": "common.okText",
                    "hasConfirmAndAddBtn": true,
                    "hasPrintBtn": false,
                    "printButtonText": "打印",
                    "printButtonTextI18nCode": "common.printText",
                    "customBtns": [],
                    "appCustomBtns": [],
                    "primaryKeyPolicy": 1,
                    "concurrencyLock": false,
                    "logicalDelete": false,
                    "dataLog": false,
                    "useBusinessKey": false,
                    "businessKeyList": [],
                    "businessKeyTip": "数据已存在，请勿重复提交！",
                    "printId": "",
                    "formStyle": "",
                    "classNames": [],
                    "className": [],
                    "classJson": "",
                    "detailExtraList": [],
                    "funcs": {
                        "onLoad": "({ formData, setFormData, setShowOrHide, setRequired, setDisabled, onlineUtils }) => {\\n    // 在此编写代码\\n    \\n}",
                        "beforeSubmit": "({ formData, setFormData, setShowOrHide, setRequired, setDisabled, onlineUtils }) => {\\n    return new Promise((resolve, reject) => {\\n        // 在此编写代码\\n        \\n        // 继续执行\\n        resolve()\\n    })\\n}",
                        "afterSubmit": "({ formData, setFormData, setShowOrHide, setRequired, setDisabled, onlineUtils }) => {\\n    // 在此编写代码\\n    \\n}"
                    },
                    "fields": ${fields}
                }
                """;
        String filedStr = """
                {
                        "__config__": {
                            "jnpfKey": "${jnpfKey}",
                            "label": "${label}",
                            "tipLabel": "",
                            "showLabel": true,
                            "tag": "${controlTag}",
                            "tagIcon": "icon-ym icon-ym-generator-input",
                            "tableAlign": "left",
                            "tableFixed": "none",
                            "className": [],
                            "required": false,
                            "layout": "colFormItem",
                            "span": 24,
                            "dragDisabled": false,
                            "visibility": ["pc", "app"],
                            "tableName": "",
                            "noShow": false,
                            "noShowAdd": true,
                            "noShowEdit": true,
                            "noShowDetail": true,
                            "regList": [],
                            "trigger": "blur",
                            "formId": "${formId}",
                            "renderKey": ${renderKey}
                        },
                        "on": {
                            "change": "({ data, rowIndex, formData, setFormData, setShowOrHide, setRequired, setDisabled, onlineUtils }) => {\\n    // 在此编写代码\\n    \\n}",
                            "blur": "({ data, rowIndex, formData, setFormData, setShowOrHide, setRequired, setDisabled, onlineUtils }) => {\\n    // 在此编写代码\\n    \\n}"
                        },
                        "style": {"width": "100%"},
                        "placeholder": "请输入",
                        "useScan": false,
                        "useMask": false,
                        "maskConfig": {
                            "filler": "*",
                            "maskType": 1,
                            "prefixType": 1,
                            "prefixLimit": 0,
                            "prefixSpecifyChar": "",
                            "suffixType": 1,
                            "suffixLimit": 0,
                            "suffixSpecifyChar": "",
                            "ignoreChar": "",
                            "useUnrealMask": false,
                            "unrealMaskLength": 1
                        },
                        "clearable": true,
                        "addonBefore": "",
                        "addonAfter": "",
                        "prefixIcon": "",
                        "suffixIcon": "",
                        "maxlength": null,
                        "showCount": false,
                        "showPassword": false,
                        "readonly": false,
                        "disabled": false,
                        "__vModel__": ${vModel}
                    }
                """;
        List<String> fields= new ArrayList();
        for (GenFieldModel fieldModel : fieldModels) {
            String newField = filedStr.replace("${vModel}",fieldModel.getVModel());
            newField = newField.replace("${jnpfKey}",fieldModel.getJnpfKey());
            newField = newField.replace("${label}",fieldModel.getLabel());
            newField = newField.replace("${controlTag}",fieldModel.getControlTag());

            newField = newField.replace("${formId}","formItem"+ 123);
            newField = newField.replace("${renderKey}", String.valueOf(System.currentTimeMillis()));
            fields.add(newField);
        }
        String replace = formDataString.replace("${fields}", fields.toString());
        System.out.println(replace);

    }

    @Test
    public void test122(){
        String aa = """
                {
                  "tables":[
                        [{"jnpfKey": "input",
                            "controlTag": "JnpfInput",
                            "label": "商品名",
                            "vModel": "f_goods_name"}]
                  ]
                }
                """;
        Map map = JSONUtil.toBean(aa, Map.class);
        List<List> tables = JSONUtil.toList(map.get("tables").toString(), List.class);
        for (List table : tables) {
            List mtable = JSONUtil.toList(table.toString(), List.class);
            System.out.println(mtable);

        }

//        List<List> list = JSONUtil.toList(aa, List.class);
    }
    @Test
    public void test121(){
        ArrayList<Object> list = new ArrayList<>();
        list.add("""
                {
                        "__config__": {
                            "jnpfKey": "input",
                            "label": "单行输入",
                            "tipLabel": "",
                            "showLabel": true,
                            "tag": "JnpfInput",
                            "tagIcon": "icon-ym icon-ym-generator-input",
                            "tableAlign": "left",
                            "tableFixed": "none",
                            "className": [],
                            "required": false,
                            "layout": "colFormItem",
                            "span": 24,
                            "dragDisabled": false,
                            "visibility": ["pc", "app"],
                            "tableName": "",
                            "noShow": false,
                            "noShowAdd": true,
                            "noShowEdit": true,
                            "noShowDetail": true,
                            "regList": [],
                            "trigger": "blur",
                            "formId": "formItem5d4336",
                            "renderKey": 1782113676199
                        },
                        "on": {
                            "change": "({ data, rowIndex, formData, setFormData, setShowOrHide, setRequired, setDisabled, onlineUtils }) => {\\n    // 在此编写代码\\n    \\n}",
                            "blur": "({ data, rowIndex, formData, setFormData, setShowOrHide, setRequired, setDisabled, onlineUtils }) => {\\n    // 在此编写代码\\n    \\n}"
                        },
                        "style": {"width": "100%"},
                        "placeholder": "请输入",
                        "useScan": false,
                        "useMask": false,
                        "maskConfig": {
                            "filler": "*",
                            "maskType": 1,
                            "prefixType": 1,
                            "prefixLimit": 0,
                            "prefixSpecifyChar": "",
                            "suffixType": 1,
                            "suffixLimit": 0,
                            "suffixSpecifyChar": "",
                            "ignoreChar": "",
                            "useUnrealMask": false,
                            "unrealMaskLength": 1
                        },
                        "clearable": true,
                        "addonBefore": "",
                        "addonAfter": "",
                        "prefixIcon": "",
                        "suffixIcon": "",
                        "maxlength": null,
                        "showCount": false,
                        "showPassword": false,
                        "readonly": false,
                        "disabled": false,
                        "__vModel__": "f_name"
                    }
                """);
        String formDataString = """
                {
                    "formRef": "formRef",
                    "formModel": "dataForm",
                    "size": "middle",
                    "labelPosition": "right",
                    "labelWidth": 100,
                    "labelSuffix": "",
                    "formRules": "rules",
                    "popupType": "general",
                    "generalWidth": "600px",
                    "fullScreenWidth": "100%",
                    "drawerWidth": "600px",
                    "gutter": 15,
                    "disabled": false,
                    "span": 24,
                    "colon": false,
                    "hasCopyBtn": false,
                    "copyButtonText": "复制",
                    "copyButtonTextI18nCode": "common.copyText",
                    "hasCancelBtn": true,
                    "cancelButtonText": "取消",
                    "cancelButtonTextI18nCode": "common.cancelText",
                    "hasConfirmBtn": true,
                    "confirmButtonText": "确定",
                    "confirmButtonTextI18nCode": "common.okText",
                    "hasConfirmAndAddBtn": true,
                    "hasPrintBtn": false,
                    "printButtonText": "打印",
                    "printButtonTextI18nCode": "common.printText",
                    "customBtns": [],
                    "appCustomBtns": [],
                    "primaryKeyPolicy": 1,
                    "concurrencyLock": false,
                    "logicalDelete": false,
                    "dataLog": false,
                    "useBusinessKey": false,
                    "businessKeyList": [],
                    "businessKeyTip": "数据已存在，请勿重复提交！",
                    "printId": "",
                    "formStyle": "",
                    "classNames": [],
                    "className": [],
                    "classJson": "",
                    "detailExtraList": [],
                    "funcs": {
                        "onLoad": "({ formData, setFormData, setShowOrHide, setRequired, setDisabled, onlineUtils }) => {\\n    // 在此编写代码\\n    \\n}",
                        "beforeSubmit": "({ formData, setFormData, setShowOrHide, setRequired, setDisabled, onlineUtils }) => {\\n    return new Promise((resolve, reject) => {\\n        // 在此编写代码\\n        \\n        // 继续执行\\n        resolve()\\n    })\\n}",
                        "afterSubmit": "({ formData, setFormData, setShowOrHide, setRequired, setDisabled, onlineUtils }) => {\\n    // 在此编写代码\\n    \\n}"
                    },
                    "fields": ${fields}
                }
                """;
        formDataString = formDataString.replace("${fields}",list.toString());
        System.out.println(formDataString);

    }

    @Test
    public void test21(){
        String aaa="aaa";
        String userTag = StringUtils.substringBetween(aaa, "${", "}");
        System.out.println(StringUtil.isEmpty(userTag));
    }
}
