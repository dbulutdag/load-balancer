package loadbalancer;

import provider.Provider;

import java.util.Random;
import java.util.logging.Logger;

public class RandomLoadBalancer extends LoadBalancer {
    private static final Logger log = Logger.getLogger(RoundRobinLoadBalancer.class.getName());

    public RandomLoadBalancer() {
        super();
    }

    @Override
    public Provider get() {
        Random randomIndex = new Random();
        Provider provider = getAliveProviders().get(randomIndex.nextInt(getAliveProviders().size()));
        log.info(String.format("Instance id: %s", provider.getInstanceId()));
        return provider;
    }
}
