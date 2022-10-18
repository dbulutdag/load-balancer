package loadbalancer;

import provider.Provider;

import java.util.logging.Logger;

public class RoundRobinLoadBalancer extends LoadBalancer {
    private static final Logger log = Logger.getLogger(RoundRobinLoadBalancer.class.getName());
    private static Integer position = 0;

    public RoundRobinLoadBalancer() {
        super();
    }

    @Override
    public Provider get() {
        Provider provider;
        synchronized (position) {
            if (position > getAliveProviders().size() - 1) {
                position = 0;
            }
            provider = getAliveProviders().get(position);
            position++;
        }
        log.info(String.format("Instance id: %s", provider.getInstanceId()));
        return provider;
    }
}
