package provider;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProviderTest {
    private Provider provider = new Provider();

    @Test
    public void shouldGetProvider() {
        //When
        String instanceId = provider.get();
        //Then
        Assertions.assertNotNull(instanceId);
    }
}
