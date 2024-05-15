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
package io.github.photowey.spring.infras.core.reader;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * {@code ClasspathReader}
 *
 * @author photowey
 * @version 1.4.0
 * @since 2024/05/15
 */
public class ResourceReader implements ResourceLoaderAware {

    protected static final String CLASSPATH = "classpath";
    protected static final String FILE_SYSTEM = "file";
    protected static final String URI = "uri";
    protected static final String PROTOCOL_SEPARATOR = ":";
    protected static final String DOUBLE_SLASH = "//";

    /**
     * file://
     */
    protected static final String FILE_PROTOCOL = FILE_SYSTEM + PROTOCOL_SEPARATOR + DOUBLE_SLASH;
    /**
     * classpath://
     */
    protected static final String CLASS_PROTOCOL = CLASSPATH + PROTOCOL_SEPARATOR + DOUBLE_SLASH;
    /**
     * uri://
     */
    protected static final String URI_PROTOCOL = URI + PROTOCOL_SEPARATOR + DOUBLE_SLASH;

    protected ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    // ----------------------------------------------------------------

    public Resource tryLoaderRead(String location) {
        if (this.determineIsClasspathProtocol(location)) {
            return this.resourceLoader.getResource(location);
        }

        return this.resourceLoader.getResource(CLASSPATH + PROTOCOL_SEPARATOR + location);
    }

    // ----------------------------------------------------------------

    public Resource tryFileSystemRead(String location) {
        // file:///opt/app/cert/dummy.crt
        if (location.startsWith(FILE_PROTOCOL)) {
            location = location.replaceAll(FILE_PROTOCOL, "");
        }

        return new FileSystemResource(location);
    }

    public Resource tryClasspathRead(String location) {
        // classpath://dev/cert/dummy.crt
        if (location.startsWith(CLASSPATH)) {
            location = location.replaceAll(CLASSPATH + PROTOCOL_SEPARATOR, "");
        }

        return new ClassPathResource(location);
    }

    // ----------------------------------------------------------------

    public Resource tryMixedRead(String location) {
        if (this.determineIsFileProtocol(location)) {
            return this.tryFileSystemRead(location);
        }

        return this.tryClasspathRead(location);
    }

    // ----------------------------------------------------------------

    public InputStream tryReadStream(String location) throws IOException {
        Resource resource = this.tryMixedRead(location);
        if (!resource.exists()) {
            throw new FileNotFoundException("Not found resource: [" + location + "]");
        }
        if (resource.isReadable()) {
            return resource.getInputStream();
        }

        throw new UnsupportedOperationException("Resource: [" + location + " ] Unreadable.");
    }

    public String toStr(String location) throws IOException {
        try (InputStream input = this.tryReadStream(location);) {
            if (input != null) {
                return this.toStr(input);
            }
        }

        return null;
    }

    public String toStr(Resource resource) throws IOException {
        if (resource.isReadable()) {
            try (InputStream input = resource.getInputStream()) {
                return this.toStr(input);
            }
        }

        return null;
    }

    public String toStr(InputStream input) throws IOException {
        return toStr(input, Function.identity());
    }

    public String toStr(InputStream input, Function<String, String> mapper) throws IOException {
        return toStr(input, line -> true, mapper);
    }

    public String toStr(InputStream input, Predicate<String> filter, Function<String, String> mapper) throws IOException {
        if (null == input) {
            return null;
        }

        InputStreamReader isr = new InputStreamReader(input);
        BufferedReader br = new BufferedReader(isr);
        List<String> data = new ArrayList<>();
        String line = null;
        while ((line = br.readLine()) != null) {
            data.add(line);
        }

        br.close();
        isr.close();
        input.close();

        return data.stream()
                .filter(filter)
                .map(mapper)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    // ----------------------------------------------------------------

    protected boolean determineIsFileProtocol(String path) {
        return StringUtils.hasText(path) && path.startsWith(FILE_PROTOCOL);
    }

    protected boolean determineIsClasspathProtocol(String path) {
        return StringUtils.hasText(path) && path.startsWith(CLASS_PROTOCOL);
    }

    protected boolean determineIsUriProtocol(String path) {
        return StringUtils.hasText(path) && path.startsWith(URI_PROTOCOL);
    }

}