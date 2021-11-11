import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.rolex.tips.command.CollapserTestCommand;
import com.rolex.tips.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class CollapserTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        String ids = "1,1,2,2,3,4";
        List<Future<User>> futures = new ArrayList<>();
        for (String id : ids.split(",")) {
            CollapserTestCommand command1 = new CollapserTestCommand(Long.valueOf(id));
            Future<User> queue1 = command1.queue();
            futures.add(queue1);
        }
        for (Future<User> future : futures) {
            System.out.println("future的结果：" + future.get());
        }
        context.close();
    }
}
