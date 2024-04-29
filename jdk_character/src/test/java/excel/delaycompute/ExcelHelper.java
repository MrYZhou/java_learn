package excel.delaycompute;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
public class ExcelHelper{
    private Workbook workbook;
    private List<ExcelFunction> list = new ArrayList<>();
    private List<ExcelFunction> listPost = new ArrayList<>();

    public ExcelHelper(Workbook workbook) {
        this.workbook = workbook;
        this.init();
    }

    private void init() {
        addPreHandle(new ExcelCommentHandle()::execute);
        addPostHandle((data,params)->{
            System.out.println(2);
        });
    }


    public void doPreHandle(){
        list.forEach(item->item.apply(this,new HashMap<>()));
    }
    public ExcelHelper addPreHandle(ExcelFunction item){
        list.add(item);
        return this;
    }
    public void doPostHandle(){
        listPost.forEach(item->item.apply(this,new HashMap<>()));
    }
    public ExcelHelper addPostHandle(ExcelFunction item){
        listPost.add(item);
        return this;
    }



}
