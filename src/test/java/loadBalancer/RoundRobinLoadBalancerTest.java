package loadBalancer;

import loadbalancer.LoadBalancer;
import loadbalancer.RoundRobinLoadBalancer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import provider.Provider;

import java.util.List;

public class RoundRobinLoadBalancerTest {
    private LoadBalancer roundRobinLoadBalancer = new RoundRobinLoadBalancer();

    @Test
    public void shouldGetProvider() {
        Provider provider = new Provider();

        //When
        roundRobinLoadBalancer.registerProviders(List.of(provider));
        Provider result = roundRobinLoadBalancer.get();

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getInstanceId(), provider.getInstanceId());
        Assertions.assertEquals(result.getHealthCheckNumber(), 0);
    }
}
