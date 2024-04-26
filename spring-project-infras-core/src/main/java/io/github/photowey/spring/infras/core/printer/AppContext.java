/*
 * Copyright © 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.photowey.spring.infras.core.printer;

import java.io.Serializable;

/**
 * {@code AppContext}
 *
 * @author photowey
 * @version 1.1.0
 * @since 2024/04/26
 */
public class AppContext implements Serializable {

    private static final long serialVersionUID = 1238866251476668122L;

    private String protocol;
    private String app;
    private String host;
    private String port;
    private String profileActive;
    private String contextPath;
    private String healthContextPath;

    // ----------------------------------------------------------------

    private String swaggerPath;
    private boolean swaggerEnabled;

    public static AppContextBuilder defaultAppContext() {
        return AppContext.builder().swaggerEnabled(false);
    }

    public static AppContextBuilder swaggerAppContext() {
        return swaggerAppContext("doc.html");
    }

    public static AppContextBuilder swaggerAppContext(String swaggerPath) {
        return AppContext.builder()
                .swaggerEnabled(true)
                .swaggerPath(swaggerPath);
    }

    public static AppContextBuilder builder() {
        return new AppContextBuilder();
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getApp() {
        return this.app;
    }

    public String getHost() {
        return this.host;
    }

    public String getPort() {
        return this.port;
    }

    public String getProfileActive() {
        return this.profileActive;
    }

    public String getContextPath() {
        return this.contextPath;
    }

    public String getHealthContextPath() {
        return this.healthContextPath;
    }

    public String getSwaggerPath() {
        return this.swaggerPath;
    }

    public boolean isSwaggerEnabled() {
        return this.swaggerEnabled;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setProfileActive(String profileActive) {
        this.profileActive = profileActive;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public void setHealthContextPath(String healthContextPath) {
        this.healthContextPath = healthContextPath;
    }

    public void setSwaggerPath(String swaggerPath) {
        this.swaggerPath = swaggerPath;
    }

    public void setSwaggerEnabled(boolean swaggerEnabled) {
        this.swaggerEnabled = swaggerEnabled;
    }

    public AppContext() {}

    public AppContext(String protocol, String app, String host, String port, String profileActive, String contextPath, String healthContextPath, String swaggerPath, boolean swaggerEnabled) {
        this.protocol = protocol;
        this.app = app;
        this.host = host;
        this.port = port;
        this.profileActive = profileActive;
        this.contextPath = contextPath;
        this.healthContextPath = healthContextPath;
        this.swaggerPath = swaggerPath;
        this.swaggerEnabled = swaggerEnabled;
    }

    public static class AppContextBuilder {

        private String protocol;
        private String app;
        private String host;
        private String port;
        private String profileActive;
        private String contextPath;
        private String healthContextPath;
        private String swaggerPath;
        private boolean swaggerEnabled;

        AppContextBuilder() {}

        public AppContextBuilder protocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        public AppContextBuilder app(String app) {
            this.app = app;
            return this;
        }

        public AppContextBuilder host(String host) {
            this.host = host;
            return this;
        }

        public AppContextBuilder port(String port) {
            this.port = port;
            return this;
        }

        public AppContextBuilder profileActive(String profileActive) {
            this.profileActive = profileActive;
            return this;
        }

        public AppContextBuilder contextPath(String contextPath) {
            this.contextPath = contextPath;
            return this;
        }

        public AppContextBuilder healthContextPath(String healthContextPath) {
            this.healthContextPath = healthContextPath;
            return this;
        }

        public AppContextBuilder swaggerPath(String swaggerPath) {
            this.swaggerPath = swaggerPath;
            return this;
        }

        public AppContextBuilder swaggerEnabled(boolean swaggerEnabled) {
            this.swaggerEnabled = swaggerEnabled;
            return this;
        }

        public AppContext build() {
            return new AppContext(this.protocol, this.app, this.host, this.port, this.profileActive, this.contextPath, this.healthContextPath, this.swaggerPath, this.swaggerEnabled);
        }

        public String toString() {
            return "AppContext.AppContextBuilder(protocol=" + this.protocol + ", app=" + this.app + ", host=" + this.host + ", port=" + this.port + ", profileActive=" + this.profileActive + ", contextPath=" + this.contextPath + ", healthContextPath=" + this.healthContextPath + ", swaggerPath=" + this.swaggerPath + ", swaggerEnabled=" + this.swaggerEnabled + ")";
        }
    }
}
