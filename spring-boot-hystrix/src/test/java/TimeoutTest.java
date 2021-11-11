import com.rolex.tips.command.TimeoutTestCommand;
import com.rolex.tips.model.User;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class TimeoutTest {
    public static void main(String[] args) {
        TimeoutTestCommand command = new TimeoutTestCommand(1L);
        User user = command.execute();
        System.out.println(user);
    }
}
