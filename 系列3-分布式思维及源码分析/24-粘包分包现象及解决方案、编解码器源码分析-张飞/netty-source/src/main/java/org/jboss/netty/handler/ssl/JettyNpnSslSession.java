/*
 * Copyright 2014 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.jboss.netty.handler.ssl;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;
import java.security.Principal;
import java.security.cert.Certificate;

final class JettyNpnSslSession implements SSLSession {

    private final SSLEngine engine;
    private volatile String applicationProtocol;

    JettyNpnSslSession(SSLEngine engine) {
        this.engine = engine;
    }

    void setApplicationProtocol(String applicationProtocol) {
        if (applicationProtocol != null) {
            applicationProtocol = applicationProtocol.replace(':', '_');
        }
        this.applicationProtocol = applicationProtocol;
    }

    public String getProtocol() {
        final String protocol = unwrap().getProtocol();
        final String applicationProtocol = this.applicationProtocol;

        if (applicationProtocol == null) {
            if (protocol != null) {
                return protocol.replace(':', '_');
            } else {
                return null;
            }
        }

        final StringBuilder buf = new StringBuilder(32);
        if (protocol != null) {
            buf.append(protocol.replace(':', '_'));
            buf.append(':');
        } else {
            buf.append("null:");
        }
        buf.append(applicationProtocol);
        return buf.toString();
    }

    private SSLSession unwrap() {
        return engine.getSession();
    }

    public byte[] getId() {
        return unwrap().getId();
    }

    public SSLSessionContext getSessionContext() {
        return unwrap().getSessionContext();
    }

    public long getCreationTime() {
        return unwrap().getCreationTime();
    }

    public long getLastAccessedTime() {
        return unwrap().getLastAccessedTime();
    }

    public void invalidate() {
        unwrap().invalidate();
    }

    public boolean isValid() {
        return unwrap().isValid();
    }

    public void putValue(String s, Object o) {
        unwrap().putValue(s, o);
    }

    public Object getValue(String s) {
        return unwrap().getValue(s);
    }

    public void removeValue(String s) {
        unwrap().removeValue(s);
    }

    public String[] getValueNames() {
        return unwrap().getValueNames();
    }

    public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
        return unwrap().getPeerCertificates();
    }

    public Certificate[] getLocalCertificates() {
        return unwrap().getLocalCertificates();
    }

    public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
        return unwrap().getPeerCertificateChain();
    }

    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        return unwrap().getPeerPrincipal();
    }

    public Principal getLocalPrincipal() {
        return unwrap().getLocalPrincipal();
    }

    public String getCipherSuite() {
        return unwrap().getCipherSuite();
    }

    public String getPeerHost() {
        return unwrap().getPeerHost();
    }

    public int getPeerPort() {
        return unwrap().getPeerPort();
    }

    public int getPacketBufferSize() {
        return unwrap().getPacketBufferSize();
    }

    public int getApplicationBufferSize() {
        return unwrap().getApplicationBufferSize();
    }
}
