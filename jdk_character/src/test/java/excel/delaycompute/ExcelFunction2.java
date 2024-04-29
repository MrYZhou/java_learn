package excel.delaycompute;

import java.util.Map;

@FunctionalInterface
interface ExcelFunction2 {
    void apply(Index.ExcelHelper compute, Map<String, Object> params);
}
