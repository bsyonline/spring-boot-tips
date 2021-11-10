import com.rolex.tips.command.RejectTestCommand;
import com.rolex.tips.model.User;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class RejectTest {
    public static void main(String[] args) throws InterruptedException {
        /*
            线程池coreSize=10，maxSize=10，queueSize=10。
            25个请求，1-10hang住，11-20在队列中等待,21-25reject。

            tips:
            如果线程池coreSize=5,maxSize=10，queueSize=10。
            25个请求，1-5hang住，6-15在队列中等待,16-20reject,21-25hang住。
         */
        for (int i = 1; i <= 10; i++) {
            Thread.sleep(100);
            int finalI = i;
            new Thread(() -> {
                RejectTestCommand command = new RejectTestCommand(new Long(finalI));
                User user = command.execute();
                System.out.println("--第" + finalI + "次请求，" + user);
            }).start();
        }
        Thread.sleep(1000);
        for (int i = 11; i <= 20; i++) {
            Thread.sleep(100);
            int finalI = i;
            new Thread(() -> {
                RejectTestCommand command = new RejectTestCommand(new Long(finalI));
                User user = command.execute();
                System.out.println("==第" + finalI + "次请求，" + user);
            }).start();
        }
        Thread.sleep(1000);
        for (int i = 21; i <= 25; i++) {
            Thread.sleep(100);
            int finalI = i;
            new Thread(() -> {
                RejectTestCommand command = new RejectTestCommand(new Long(finalI));
                User user = command.execute();
                System.out.println("##第" + finalI + "次请求，" + user);
            }).start();
        }
    }

}
