package excel.delaycompute;

import java.util.Map;

public interface ExcelPostHandle {
     void execute(ExcelHelper compute, Map<String, Object> params);
}
