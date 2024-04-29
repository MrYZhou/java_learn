package excel.delaycompute;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
public class ExcelHelper {
    boolean initPre = false;
    boolean initPost = false;
    private Workbook workbook;
    private List<ExcelFunction> preHandleFunctions = new ArrayList<>();
    private List<ExcelFunction> postHandleFunctions = new ArrayList<>();

    public void init(Workbook workbook) {
        this.workbook = workbook;
    }
    // 初始化前置处理
    private void initPreHandle() {
        ExcelPreHandle pre = new ExcelCommentHandle();
        addPreHandle(pre::execute);
        this.initPre = true;
    }

    // 初始化后置处理
    private void initPostHandle() {
        addPostHandle((data, params) -> {
            System.out.println(2);
        });
        this.initPost = true;
    }

    public void doPreHandle() {
        if (!initPre) {
            this.initPreHandle();
        }
        preHandleFunctions.forEach(item -> item.apply(this, new HashMap<>()));
    }

    public void doPostHandle() {
        if(!initPost){
            this.initPostHandle();
        }
        postHandleFunctions.forEach(item -> item.apply(this, new HashMap<>()));
    }

    public void addPreHandle(ExcelFunction... functions) {
        Collections.addAll(preHandleFunctions, functions);
    }
    public void addPostHandle(ExcelFunction... functions) {
        Collections.addAll(postHandleFunctions, functions);
    }
}
