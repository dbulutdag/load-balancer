package provider;

import util.Constants;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Provider {
    private final String instanceId;
    private boolean isActive;
    private int numOfRequest;
    private AtomicInteger healthCheckNumber;
    private static final Logger log = Logger.getLogger(Provider.class.getName());

    public Provider() {
        this.instanceId = String.valueOf(UUID.randomUUID());
        this.isActive = true;
        this.numOfRequest = 0;
        this.healthCheckNumber = new AtomicInteger(0);
    }

    public String get() {
        if (isActive && numOfRequest < Constants.MAX_REQUEST_NUMBER) {
            numOfRequest++;
            log.info(String.format("Provider %s handling %s request right now", instanceId, numOfRequest));
            return instanceId;
        } else {
            isActive = false;
            numOfRequest = 0;
            log.warning(String.format("Provider %s has reached its maximum request capacity!", instanceId));
            return Constants.MAX_CAPACITY_MESSAGE;
        }
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setHealthCheckNumber(int healthCheckNumber) {
        this.healthCheckNumber.set(healthCheckNumber);
    }

    public int getHealthCheckNumber() {
        return healthCheckNumber.get();
    }

    public String getInstanceId() {
        return instanceId;
    }

    public boolean isActive() {
        return isActive;
    }
}
