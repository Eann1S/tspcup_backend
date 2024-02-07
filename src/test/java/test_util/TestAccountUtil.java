package test_util;

import org.example.entity.Account;
import org.instancio.Instancio;
import org.instancio.TargetSelector;

public class TestAccountUtil {

    public static <V> Account createAccountWithField(TargetSelector targetSelector, V value) {
        return Instancio.of(Account.class)
                .set(targetSelector, value)
                .create();
    }

    public static <V1, V2> Account createAccountWithFields(
            TargetSelector targetSelector1, V1 value1,
            TargetSelector targetSelector2, V2 value2
    ) {
        return Instancio.of(Account.class)
                .set(targetSelector1, value1)
                .set(targetSelector2, value2)
                .create();
    }
}
