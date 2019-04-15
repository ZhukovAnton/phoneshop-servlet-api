package com.es.phoneshop.model.delivery;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DeliveryMode {
    public enum EnumDelModes {
        COURIER(BigDecimal.valueOf(5)), POST_OFFICE(BigDecimal.valueOf(3)), STORE_PICKUP(BigDecimal.valueOf(0));

        private BigDecimal deliveryCost;

        EnumDelModes(BigDecimal deliveryCost) {
            this.deliveryCost = deliveryCost;
        }

        public BigDecimal getDeliveryCost() {
            return deliveryCost;
        }
    }

    private List<EnumDelModes> modes;

    public List<EnumDelModes> getModes() {
        return modes;
    }

    public static EnumDelModes getDeliveryModeFromString(String str) {
        switch(str){
            case "COURIER" :
                return EnumDelModes.COURIER;
            case "POST_OFFICE" :
                return EnumDelModes.POST_OFFICE;
            default: return EnumDelModes.STORE_PICKUP;
        }
    }

    public DeliveryMode() {
        modes = new ArrayList<>();
        modes.add(EnumDelModes.COURIER);
        modes.add(EnumDelModes.POST_OFFICE);
        modes.add(EnumDelModes.STORE_PICKUP);
    }
}
