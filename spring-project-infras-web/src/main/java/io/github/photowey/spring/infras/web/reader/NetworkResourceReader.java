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
package io.github.photowey.spring.infras.web.reader;

import io.github.photowey.spring.infras.common.function.consumer.NoOpsConsumer;
import io.github.photowey.spring.infras.core.getter.BeanFactoryGetter;
import io.github.photowey.spring.infras.core.reader.ResourceReader;
import io.github.photowey.spring.infras.web.getter.RestTemplateGetter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * {@code NetworkResourceReader}
 *
 * @author photowey
 * @version 1.4.0
 * @since 2024/05/15
 */
public class NetworkResourceReader extends ResourceReader implements
        RestTemplateGetter, BeanFactoryGetter, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;
    private final ConcurrentHashMap<Class<?>, RestTemplate> ctx = new ConcurrentHashMap<>(2);

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public BeanFactory beanFactory() {
        return this.beanFactory;
    }

    @Override
    public RestTemplate restTemplate() {
        return this.ctx.computeIfAbsent(RestTemplate.class, (x) -> this.tryRestTemplate());
    }

    public void register(RestTemplate restTemplate) {
        this.ctx.computeIfAbsent(RestTemplate.class, (x) -> restTemplate);
    }

    public Resource tryNetworkRead(String uri) {
        return this.tryNetworkRead(uri, NoOpsConsumer::accept);
    }

    public Resource tryNetworkRead(String uri, Consumer<UriComponentsBuilder> fx) {
        return this.tryNetworkRead(uri, fx, NoOpsConsumer::accept);
    }

    public Resource tryNetworkRead(String uri, Consumer<UriComponentsBuilder> fx, Consumer<UriComponents> fn) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
        fx.accept(builder);
        UriComponents components = builder.build();
        // expand or ...
        fn.accept(components);

        java.net.URI remote = components.toUri();

        return this.tryNetworkRead(remote);
    }

    public Resource tryNetworkRead(java.net.URI uri) {
        return this.tryNetworkRead(uri, HttpMethod.GET);
    }

    public Resource tryNetworkRead(java.net.URI uri, HttpMethod method) {
        return this.tryNetworkRead(uri, method, null);
    }

    public Resource tryNetworkRead(java.net.URI uri, RequestEntity<?> request) {
        return this.tryNetworkRead(uri, HttpMethod.GET, request);
    }

    public Resource tryNetworkRead(java.net.URI uri, HttpMethod method, RequestEntity<?> request) {
        if (null == this.restTemplate()) {
            return null;
        }
        ResponseEntity<Resource> response = this.restTemplate().exchange(uri, method, request, Resource.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        return null;
    }

    // ----------------------------------------------------------------

    private RestTemplate tryRestTemplate() {
        try {
            return this.beanFactory.getBean(RestTemplate.class);
        } catch (BeansException e) {
            return this.ctx.computeIfAbsent(RestTemplate.class, this::init);
        }
    }

    private RestTemplate init(Class<?> ignored) {
        return new RestTemplate();
    }
}