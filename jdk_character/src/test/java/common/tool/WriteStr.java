package tool;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WriteStr {
    @Test
    public void wewq() throws IOException {
        String dirPath = "D:/Users/JNPF/Desktop/project/java/java_learn/jdk_character/src/test/java/common/tool/com.txt";
        File dir = new File(dirPath);

        String content = FileUtils.readFileToString(dir);

        String content2 = "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<template>\n" +
                "\n" +
                "    <div class=\"JNPF-common-layout\">\n" +
                "\n" +
                "        <div class=\"JNPF-common-layout-center\">\n" +
                "            <el-row class=\"JNPF-common-search-box\" :gutter=\"16\">\n" +
                "                <el-form @submit.native.prevent>\n" +
                "\n" +
                "                    <el-col :span=\"6\">\n" +
                "                        <el-form-item>\n" +
                "                            <el-button type=\"primary\" icon=\"el-icon-search\" @click=\"search()\">{{$t(\"common.search\")}}</el-button>\n" +
                "                            <el-button icon=\"el-icon-refresh-right\" @click=\"reset()\">重置</el-button>\n" +
                "                        </el-form-item>\n" +
                "                    </el-col>\n" +
                "                </el-form>\n" +
                "            </el-row>\n" +
                "</template>\n" +
                "\n" +
                "<script>\n" +
                "\n" +
                "        methods: {\n" +
                "\n" +
                "                    treeInterfaceId:\"\",\n" +
                "                    treeTemplateJson:[]\n" +
                "                }\n" +
                "                if (config.treeInterfaceId) {\n" +
                "                    //这里是为了拿到参数中关联的字段的值，后端自行拿\n" +
                "                    if (config.treeTemplateJson && config.treeTemplateJson.length) {\n" +
                "                        for (let i = 0; i < config.treeTemplateJson.length; i++) {\n" +
                "                            const element = config.treeTemplateJson[i];\n" +
                "                            element.defaultValue = nodeData[element.relationField] || ''\n" +
                "                        }\n" +
                "                    }\n" +
                "                    //参数\n" +
                "                    let query = {\n" +
                "                        paramList: config.treeTemplateJson || [],\n" +
                "                    }\n" +
                "                    //接口\n" +
                "                    getDataInterfaceRes(config.treeInterfaceId, query).then(res => {\n" +
                "                        let data = res.data\n" +
                "                        if (Array.isArray(data)) {\n" +
                "                            resolve(data);\n" +
                "                        } else {\n" +
                "                            resolve([]);\n" +
                "                        }\n" +
                "                    })\n" +
                "                }\n" +
                "            },\n" +
                "                         getColumnList() {\n" +
                "         // 没有开启权限\n" +
                "         this.columnOptions = this.transformColumnList(this.columnList)\n" +
                "             },\n" +
                "             transformColumnList(columnList) {\n" +
                "                 let list = []\n" +
                "                 for (let i = 0; i < columnList.length; i++) {\n" +
                "                     const e = columnList[i];\n" +
                "                     if (!e.prop.includes('-')) {\n" +
                "                         list.push(e)\n" +
                "                     } else {\n" +
                "                         let prop = e.prop.split('-')[0]\n" +
                "                         let label = e.label.split('-')[0]\n" +
                "                         let vModel = e.prop.split('-')[1]\n" +
                "                         let newItem = {\n" +
                "                             align: \"center\",\n" +
                "                             jnpfKey: \"table\",\n" +
                "                             prop,\n" +
                "                             label,\n" +
                "                             children: []\n" +
                "                         }\n" +
                "                         e.vModel = vModel\n" +
                "                         if (!this.expandObj.hasOwnProperty(`${prop}Expand`)) this.$set(this.expandObj, `${prop}Expand`, false)\n" +
                "                         if (!list.some(o => o.prop === prop)) list.push(newItem)\n" +
                "                         for (let i = 0; i < list.length; i++) {\n" +
                "                             if (list[i].prop === prop) {\n" +
                "                                 list[i].children.push(e)\n" +
                "                                 break\n" +
                "                             }\n" +
                "                         }\n" +
                "                     }\n" +
                "                 }\n" +
                "                 this.getMergeList(list)\n" +
                "                 this.getExportList(list)\n" +
                "                 return list\n" +
                "             },\n" +
                "             arraySpanMethod({ column }) {\n" +
                "                 for (let i = 0; i < this.mergeList.length; i++) {\n" +
                "                     if (column.property == this.mergeList[i].prop) {\n" +
                "                         return [this.mergeList[i].rowspan, this.mergeList[i].colspan]\n" +
                "                     }\n" +
                "                 }\n" +
                "             },\n" +
                "             getMergeList(list) {\n" +
                "                 let newList = JSON.parse(JSON.stringify(list))\n" +
                "                 newList.forEach(item => {\n" +
                "                     if (item.children && item.children.length) {\n" +
                "                         let child = {\n" +
                "                             prop: item.prop + '-child-first'\n" +
                "                         }\n" +
                "                         item.children.unshift(child)\n" +
                "                     }\n" +
                "                 })\n" +
                "                 newList.forEach(item => {\n" +
                "\n" +
                "                     if (item.children && item.children.length ) {\n" +
                "                         item.children.forEach((child, index) => {\n" +
                "                             if (index == 0) {\n" +
                "                                 this.mergeList.push({\n" +
                "                                     prop: child.prop,\n" +
                "                                     rowspan: 1,\n" +
                "                                     colspan: item.children.length\n" +
                "                                 })\n" +
                "                             } else {\n" +
                "                                 this.mergeList.push({\n" +
                "                                     prop: child.prop,\n" +
                "                                     rowspan: 0,\n" +
                "                                     colspan: 0\n" +
                "                                 })\n" +
                "                             }\n" +
                "                         })\n" +
                "                     } else {\n" +
                "                         this.mergeList.push({\n" +
                "                             prop: item.prop,\n" +
                "                             rowspan: 1,\n" +
                "                             colspan: 1\n" +
                "                         })\n" +
                "                     }\n" +
                "                 })\n" +
                "             },\n" +
                "            getExportList(list) {\n" +
                "                let exportList = []\n" +
                "                for (let i = 0; i < list.length; i++) {\n" +
                "                    if (list[i].jnpfKey === 'table') {\n" +
                "                        for (let j = 0; j < list[i].children.length; j++) {\n" +
                "                            exportList.push(list[i].children[j])\n" +
                "                        }\n" +
                "                    } else {\n" +
                "                        exportList.push(list[i])\n" +
                "                    }\n" +
                "                }\n" +
                "                this.exportList = exportList\n" +
                "            },\n" +
                "            goDetail(id){\n" +
                "                this.detailVisible = true\n" +
                "                this.$nextTick(() => {\n" +
                "                    this.$refs.Detail.init(id)\n" +
                "                })\n" +
                "            },\n" +
                "            sortChange({column, prop, order}) {\n" +
                "                this.listQuery.sort = order == 'ascending' ? 'asc' : 'desc'\n" +
                "                this.listQuery.sidx = !order ? '' : prop\n" +
                "                this.initData()\n" +
                "            },\n" +
                "            async initSearchDataAndListData() {\n" +
                "                await this.initSearchData()\n" +
                "                this.initData()\n" +
                "            },\n" +
                "            async initSearchData() {\n" +
                "            },\n" +
                "            initData() {\n" +
                "                this.listLoading = true;\n" +
                "                let _query = {\n" +
                "                    ...this.listQuery,\n" +
                "                    ...this.query,\n" +
                "             keyword: this.keyword,\n" +
                "             dataType: 0,\n" +
                "                menuId:this.menuId,\n" +
                "                moduleId:'433547625472488453'\n" +
                "                };\n" +
                "                request({\n" +
                "                    url: `/api/example/Test_position/getList`,\n" +
                "                    method: 'post',\n" +
                "                    data: _query\n" +
                "                }).then(res => {\n" +
                "                    var _list =[];\n" +
                "                    for(let i=0;i<res.data.list.length;i++){\n" +
                "                        let _data = res.data.list[i];\n" +
                "                        _list.push(_data)\n" +
                "                    }\n" +
                "                                       this.list = _list.map(o => ({\n" +
                "                       ...o,\n" +
                "                       ...this.expandObj,\n" +
                "                   }))\n" +
                "                        this.total = res.data.pagination.total\n" +
                "                    this.listLoading = false\n" +
                "                })\n" +
                "            },\n" +
                "            handleDel(id) {\n" +
                "                this.$confirm('此操作将永久删除该数据, 是否继续?', '{{this.$t(\"common.tip\")}}', {\n" +
                "                    type: 'warning'\n" +
                "                }).then(() => {\n" +
                "                    request({\n" +
                "                        url: `/api/example/Test_position/${id}`,\n" +
                "                        method: 'DELETE'\n" +
                "                    }).then(res => {\n" +
                "                        this.$message({\n" +
                "                            type: 'success',\n" +
                "                            message: res.msg,\n" +
                "                            onClose: () => {\n" +
                "                                this.initData()\n" +
                "                            }\n" +
                "                        });\n" +
                "                    })\n" +
                "                }).catch(() => {\n" +
                "                });\n" +
                "            },\n" +
                "            handelUpload(){\n" +
                "                this.uploadBoxVisible = true\n" +
                "                this.$nextTick(() => {\n" +
                "                    this.$refs.UploadBox.init(\"\",\"example/Test_position\")\n" +
                "                })\n" +
                "            },\n" +
                "\n" +
                "                openSuperQuery() {\n" +
                "                    this.superQueryVisible = true\n" +
                "                    this.$nextTick(() => {\n" +
                "                        this.$refs.SuperQuery.init()\n" +
                "                    })\n" +
                "                },\n" +
                "                superQuery(queryJson) {\n" +
                "                    this.listQuery.superQueryJson = queryJson\n" +
                "                    this.listQuery.currentPage = 1\n" +
                "                    this.initData()\n" +
                "                },\n" +
                "            addOrUpdateHandle(id, isDetail) {\n" +
                "                this.formVisible = true\n" +
                "                this.$nextTick(() => {\n" +
                "                    this.$refs.JNPFForm.init(id, isDetail,this.list)\n" +
                "                })\n" +
                "            },\n" +
                "            exportData() {\n" +
                "                this.exportBoxVisible = true\n" +
                "                this.$nextTick(() => {\n" +
                "                    this.$refs.ExportBox.init(this.exportList)\n" +
                "                })\n" +
                "            },\n" +
                "            download(data) {\n" +
                "                let query = {...data, ...this.listQuery, ...this.query,menuId:this.menuId}\n" +
                "                request({\n" +
                "                    url: `/api/example/Test_position/Actions/Export`,\n" +
                "                    method: 'post',\n" +
                "                    data: query\n" +
                "                }).then(res => {\n" +
                "                    if (!res.data.url) return\n" +
                "                    this.jnpf.downloadFile(res.data.url)\n" +
                "                    this.$refs.ExportBox.visible = false\n" +
                "                    this.exportBoxVisible = false\n" +
                "                })\n" +
                "            },\n" +
                "            search() {\n" +
                "                this.listQuery.currentPage=1\n" +
                "                this.listQuery.pageSize=20\n" +
                "                this.listQuery.sort=\"desc\"\n" +
                "                this.listQuery.sidx=\"\"\n" +
                "                this.initData()\n" +
                "            },\n" +
                "            refresh(isrRefresh) {\n" +
                "                this.formVisible = false\n" +
                "                if (isrRefresh) this.reset()\n" +
                "            },\n" +
                "            reset() {\n" +
                "                for (let key in this.query) {\n" +
                "                    this.query[key] = undefined\n" +
                "                }\n" +
                "                this.search()\n" +
                "            },\n" +
                "          // 展开折叠方法\n" +
                "        }\n" +
                "    }\n" +
                "</script>\n";

        Matcher matcher;
//        content = content.replaceAll("\r\n","\n");
        String regex = "<template>(.|/r/n)*?</template>";
        regex = "<template>(.*?)\n+?<script>";
        regex = "<template>(.)*?<script>";

//        regex = "<template>.*?</template>";
        Pattern pattern = Pattern.compile(regex);
//        System.out.println(content.split("<script>")[0]);
//        System.out.println(content.split("<script>"+"<script>")[1]);
        matcher = pattern.matcher(content);
        if (matcher.find()) {
            String group = matcher.group();
            System.out.println(group);
        }

    }

    @Test
    public void wrapTag() throws IOException {
        String leftWrap = "{{this.$t(\"";
        String rightWrap = "\")}}";
        String dirPath = "D:/Users/JNPF/Desktop/project/java/java_learn/jdk_character/src/test/java/common/tool/com.txt";

        Map<String, String> source = new HashMap<>();
        String str1 = "提示";
        String str2 = "没有更多数据";
        source.put(str1, leftWrap + "common.tip" + rightWrap);
        source.put(str2, leftWrap + "common.search2" + rightWrap);

        File dir = new File(dirPath);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                this.replaceStr(file, source);
            }
        } else {
            this.replaceStr(dir, source);
        }
    }
    public void replaceStr(File file, Map<String, String> source) throws IOException {
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                replaceStr(subFile, source);
            }
        } else {
            String content = FileUtils.readFileToString(file, "UTF-8");
            // 切割布局和script内容，由于国际化包裹方式的不同

            String html = content.split("<script>")[0];
            String scriptStr = content.split("<script>" + "<script>")[1];

            for (Map.Entry<String, String> entry : source.entrySet()) {
                String oldStr = entry.getKey();
                String newStr = entry.getValue();
                html = html.replace(oldStr, newStr);
            }

            for (Map.Entry<String, String> entry : source.entrySet()) {
                String oldStr = entry.getKey();
                String newStr = entry.getValue();
                scriptStr = scriptStr.replace(oldStr, newStr);
            }
            content = html + scriptStr;
            FileUtils.writeStringToFile(file, content, "UTF-8");
        }
    }

    /**
     * 获取文件中的中文
     *
     * @param args
     */
    @Test
    public void testrepstr() {
        String filePath = "D:\\Users\\JNPF\\Downloads\\i18-dev1685957692914\\html\\web";

        HashSet<String> chineseStrings = new HashSet<>();
        this.collectStr(filePath, chineseStrings);
        chineseStrings.forEach(System.out::println);
    }

    private void collectStr(String directory, HashSet<String> chineseStrings) {
        File dir = new File(directory);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files == null) {
                System.err.println("Error reading files in " + dir.getAbsolutePath());
                return;
            }
            files = files[0].listFiles();

            for (File file : files) {
                findFileChinese(file, chineseStrings);
            }

        } else {
            findFileChinese(dir, chineseStrings);

        }
    }

    private void findFileChinese(File file, HashSet<String> chineseStrings) {
        if (file.isDirectory()) return;
        Pattern pattern;
        Matcher matcher;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                pattern = Pattern.compile("\"[\u4e00-\u9fa5]+\"");
                matcher = pattern.matcher(line);
                while (matcher.find()) {
                    chineseStrings.add(matcher.group()); // 将中文字符串添加到HashSet中
                }

                pattern = Pattern.compile("'[\u4e00-\u9fa5]+'");
                matcher = pattern.matcher(line);
                while (matcher.find()) {
                    chineseStrings.add(matcher.group()); // 将中文字符串添加到HashSet中
                }

                pattern = Pattern.compile(">[\u4e00-\u9fa5].*<");
                matcher = pattern.matcher(line);
                while (matcher.find()) {
                    chineseStrings.add(matcher.group()); // 将中文字符串添加到HashSet中
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
