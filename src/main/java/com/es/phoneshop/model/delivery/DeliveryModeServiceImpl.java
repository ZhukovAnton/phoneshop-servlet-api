package com.es.phoneshop.model.delivery;

public class DeliveryModeServiceImpl implements DeliveryModeService {
    private static volatile DeliveryModeService instance;
    private DeliveryMode delivery;

    public static DeliveryModeService getInstance() {
        if (instance == null) {
            synchronized (DeliveryModeServiceImpl.class) {
                if (instance == null) {
                    instance = new DeliveryModeServiceImpl();
                }
            }
        }
        return instance;
    }

    private DeliveryModeServiceImpl() {
        delivery = new DeliveryMode();
    }

    @Override
    public DeliveryMode getDeliveryMode() {
        return delivery;
    }
}
