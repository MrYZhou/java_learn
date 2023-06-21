import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DateFormaterTest {
  static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public static String getTime(LocalDateTime time) {

    String format = dateTimeFormatter.format(time);
    return format;
  }

  public static LocalDateTime parseTime(String str) {

    LocalDateTime parse = LocalDateTime.parse(str, dateTimeFormatter);
    return parse;
  }

  public static void main(String[] args) {
    // 多线程调用
    ExecutorService executorService = Executors.newFixedThreadPool(5);

    CompletableFuture.runAsync(
        () -> {
          for (int i = 0; i < 10; i++) {
            LocalDateTime now = LocalDateTime.now();
            String time = getTime(now);
            LocalDateTime localDateTime = parseTime(time);
            System.out.println(localDateTime);
          }
        },
        executorService);
  }
}
