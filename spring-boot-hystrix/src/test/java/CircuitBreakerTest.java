import com.rolex.tips.command.CircuitBreakerTestCommand;
import com.rolex.tips.model.User;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class CircuitBreakerTest {
    public static void main(String[] args) throws InterruptedException {
        /*
            前20次请求，9次成功，11次失败，超过50%触发短路器开启
            第21-30次请求直接走fallback。
            等5秒，短路器half-open，第31-40次请求，成功。
         */
        for (int i = 1; i <= 9; i++) {
            CircuitBreakerTestCommand command = new CircuitBreakerTestCommand(new Long(i));
            User user = command.execute();
            System.out.println("第" + i + "次请求，" + user);
        }
        for (int i = 10; i <= 20; i++) {
            CircuitBreakerTestCommand command = new CircuitBreakerTestCommand(new Long(-100));
            User user = command.execute();
            System.out.println("第" + i + "次请求，" + user);
        }
        Thread.sleep(5000);
        for (int i = 21; i <= 30; i++) {
            CircuitBreakerTestCommand command = new CircuitBreakerTestCommand(new Long(-100));
            User user = command.execute();
            System.out.println("第" + i + "次请求，" + user);
        }
        Thread.sleep(5000);
        for (int i = 31; i <= 40; i++) {
            CircuitBreakerTestCommand command = new CircuitBreakerTestCommand(new Long(i));
            User user = command.execute();
            System.out.println("第" + i + "次请求，" + user);
        }
    }

}
