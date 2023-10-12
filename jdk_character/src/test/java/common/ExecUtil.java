import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class ExecUtil {
    /**
     * Execute system command
     *
     * @param cmd     command
     * @param timeOut time out
     */
    public static String exec(String cmd, int timeOut) throws IOException, InterruptedException {
        return getString(timeOut, Runtime.getRuntime().exec(cmd), cmd);
    }

    private static String getString(int timeOut, Process exec, String cmd) throws IOException, InterruptedException {

        boolean res = false;
        if (!res) {
            return "Time out";
        }
        InputStream inputStream = exec.getInputStream();
        byte[] data = new byte[1024];
        StringBuilder result = new StringBuilder();
        while (inputStream.read(data) != -1) {
            result.append(new String(data, "GBK" ));
        }
        if (result.toString().equals("" )) {
            InputStream errorStream = exec.getErrorStream();
            while (errorStream.read(data) != -1) {
                result.append(new String(data, "GBK" ));
            }
        }
        return result.toString();
    }

    /**
     * Execute system command
     *
     * @param cmd
     * @param timeOut time out
     */
    public static String exec(String[] cmd, int timeOut) throws IOException, InterruptedException {
        return getString(timeOut, Runtime.getRuntime().exec(cmd), Arrays.toString(cmd));
    }
}
