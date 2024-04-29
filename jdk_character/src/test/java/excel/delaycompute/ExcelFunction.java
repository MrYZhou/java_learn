package excel.delaycompute;

import java.util.Map;

@FunctionalInterface
interface ExcelFunction {
    void apply(ExcelHelper compute, Map<String, Object> params);
}
