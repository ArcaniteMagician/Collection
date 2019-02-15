package com.endymion.common.component;

import android.util.Log;

/**
 * Created by Jim Lee on 2019/2/15.
 */
public class ComponentProxyFactory {

    public static final int WEATHER = 0;
    private static final String[] proxyClassNames = new String[]{
            "com.endymion.weather.WeatherProxy"
    };
    private final ComponentProxyInterface[] componentProxies = new ComponentProxyInterface[proxyClassNames.length];

    private ComponentProxyFactory() {

    }

    public static ComponentProxyFactory getInstance() {
        return Holder.instance;
    }

    private boolean initComponentProxy(int index) {
        try {
            Object obj = Class.forName(proxyClassNames[index]).newInstance();
            if (obj != null) {
                componentProxies[index] = (ComponentProxyInterface) obj;
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException e) {
            e.printStackTrace();
            return false;
        }

        return componentProxies[index] != null;
    }

    public ComponentProxyInterface getComponentProxy(int index) {
        if (componentProxies.length > index) {
            if (componentProxies[index] != null
                    || initComponentProxy(index)) {
                return componentProxies[index];
            }
        } else {
            Log.e("ComponentProxy", "setComponentProxy error, max length is " + componentProxies.length + ", but index = " + index);
        }
        return new EmptyComponentProxy();
    }

    private static class Holder {
        private static final ComponentProxyFactory instance = new ComponentProxyFactory();
    }
}
