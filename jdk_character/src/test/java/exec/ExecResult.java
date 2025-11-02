package exec;

import lombok.Data;

@Data
public class ExecResult {
    private final int exitCode;
    private final String output;
    private final String error;

    public ExecResult(int exitCode, String output, String error) {
        this.exitCode = exitCode;
        this.output = output;
        this.error = error;
    }

    public boolean isSuccess() { return exitCode == 0; }

}
