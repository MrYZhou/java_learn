package excel.delaycompute;

import java.util.Map;

public interface ExcelPreHandle {
     void execute(ExcelHelper compute, Map<String, Object> params);
}
