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
package io.github.photowey.spring.infras.web.io;

import org.springframework.core.io.InputStreamResource;

import java.io.InputStream;

/**
 * {@code NamedInputStreamSource}
 *
 * @author photowey
 * @version 1.4.0
 * @since 2024/05/09
 */
public class NamedInputStreamSource extends InputStreamResource {

    private String filename;

    public NamedInputStreamSource(InputStream inputStream, String filename) {
        super(inputStream);
        this.filename = filename;
    }

    public NamedInputStreamSource(InputStream inputStream, String filename, String description) {
        super(inputStream, description);
        this.filename = filename;
    }

    @Override
    public String getFilename() {
        return this.filename;
    }
}