package loadBalancer;

import loadbalancer.LoadBalancer;
import loadbalancer.RandomLoadBalancer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import provider.Provider;

import java.util.List;

public class RandomLoadBalancerTest {
    private LoadBalancer randomLoadBalancer = new RandomLoadBalancer();

    @Test
    public void shouldGetProvider() {
        Provider provider = new Provider();

        //When
        randomLoadBalancer.registerProviders(List.of(provider));
        Provider result = randomLoadBalancer.get();

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getInstanceId(), provider.getInstanceId());
        Assertions.assertEquals(result.getHealthCheckNumber(), 0);
    }
}
