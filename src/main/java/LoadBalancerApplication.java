import loadbalancer.LoadBalancer;
import loadbalancer.RoundRobinLoadBalancer;
import provider.Provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class LoadBalancerApplication {
    private static final Logger log = Logger.getLogger(LoadBalancerApplication.class.getName());

    public static void main(String[] args) {
        List<Provider> registeredProviders = new ArrayList<>();
        LoadBalancerApplication loadBalancerApplication = new LoadBalancerApplication();

        for (int i = 0; i < 3; i++) {
            registeredProviders.add(new Provider());
            log.info(String.format("Provider %s, %s", i, registeredProviders.get(i).getInstanceId()));
        }

        LoadBalancer loadBalancer = new RoundRobinLoadBalancer();
        //LoadBalancer loadBalancer = new RandomLoadBalancer();
        loadBalancer.registerProviders(registeredProviders);

        new Timer().schedule(loadBalancer, 0, 5000);

        loadBalancerApplication.sendParallelRequests(loadBalancer, 15);

        Provider provider = registeredProviders.get(0);
        loadBalancer.excludeProvider(provider);
        loadBalancer.includeProvider(provider);

        loadBalancer.get();
        loadBalancer.get();
        loadBalancer.get();
        loadBalancer.get();
        loadBalancer.get();
    }

    private void sendParallelRequests(LoadBalancer loadBalancer, int numOfCalls) {
        IntStream
                .range(0, numOfCalls)
                .parallel()
                .forEach(i -> {
                                loadBalancer.get().get();
                                System.out.println(" --- Request from client: " + i  + " --- [Thread: " + Thread.currentThread().getName() + "]");
                        }
                );
    }
}
