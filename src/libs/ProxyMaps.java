package libs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/**
 * Created by hbfit14bannasch on 11.02.16.
 */
public class ProxyMaps extends JDialog{
    public void setProxy(final String proxyhost, final String proxyport, final String username, final String pw){
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                if (getRequestorType() == RequestorType.PROXY) {
                    String prot = getRequestingProtocol().toLowerCase();
                    String host = System.getProperty(prot + ".proxyHost", proxyhost);
                    String port = System.getProperty(prot + ".proxyPort", proxyport);
                    String user = System.getProperty(prot + ".proxyUser", username);
                    String password = System.getProperty(prot + ".proxyPassword", pw);

                    if (getRequestingHost().toLowerCase().equals(host.toLowerCase())) {
                        if (Integer.parseInt(port) == getRequestingPort()) {
                            // Seems to be OK.
                            return new PasswordAuthentication(user, password.toCharArray());
                        }
                    }
                }
                return null;
            }
        });
    }
}
