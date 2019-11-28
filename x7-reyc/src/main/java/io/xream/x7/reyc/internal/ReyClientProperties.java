package io.xream.x7.reyc.internal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReyClientProperties {

    @Value("${x7.reyclient.remote-exception:'RemoteException'}")
    private String remoteException;

    public String getRemoteException() {
        return remoteException;
    }

    public void setRemoteException(String remoteException) {
        this.remoteException = remoteException;
    }
}
