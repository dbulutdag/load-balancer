package loadbalancer;

import provider.Provider;
import util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Logger;

public abstract class LoadBalancer extends TimerTask{
    private static final Logger log = Logger.getLogger(LoadBalancer.class.getName());
    private List<Provider> registeredProviders;
    private List<Provider> aliveProviders;

    public LoadBalancer() {
        this.registeredProviders = new ArrayList<>();
        this.aliveProviders = new ArrayList<>();
    }

    public void registerProviders(List<Provider> providerList) {
        providerList.forEach(provider -> {
            if(registeredProviders.size()< Constants.PROVIDER_LIMIT) {
                registeredProviders.add(provider);
                aliveProviders.add(provider);
            } else {
                log.warning("Provider exceeded it's max capacity");
            }
        });
    }

    public List<Provider> getAliveProviders() {
        return aliveProviders;
    }

    public abstract Provider get();

    public void includeProvider(Provider provider) {
        log.info(String.format("Provider with instance id : %s has been added to alive providers!", provider.getInstanceId()));
        aliveProviders.add(provider);
    }

    public void excludeProvider(Provider provider) {
        log.info(String.format("Provider with instance id : %s has been removed from alive providers!", provider.getInstanceId()));
        aliveProviders.remove(provider);
    }

    public synchronized void run() {
        log.info("Health check has been called!");
        for (Provider provider : registeredProviders) {
            if (!provider.isActive() && provider.getHealthCheckNumber() == 0) {
                provider.setHealthCheckNumber(1);
                excludeProvider(provider);
            } else if (!provider.isActive() && provider.getHealthCheckNumber() == 1) {
                provider.setHealthCheckNumber(2);
            } else if (!provider.isActive() && provider.getHealthCheckNumber() == 2) {
                provider.setActive(true);
                provider.setHealthCheckNumber(0);
                includeProvider(provider);
            }
        }
    }
}
