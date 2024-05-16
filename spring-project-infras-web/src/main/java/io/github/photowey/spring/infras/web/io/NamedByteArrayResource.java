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

import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * {@code NamedByteArrayResource}
 *
 * @author photowey
 * @version 1.4.0
 * @since 2024/05/09
 */
public class NamedByteArrayResource extends ByteArrayResource {

    private final String filename;

    public NamedByteArrayResource(byte[] bytes, String filename) {
        super(bytes);
        this.filename = filename;
    }

    public NamedByteArrayResource(InputStream input, String filename) {
        super(convert(input));
        this.filename = filename;
    }

    public NamedByteArrayResource(MultipartFile file) {
        super(convert(file), file.getName());
        this.filename = file.getOriginalFilename();
    }

    @Override
    public String getFilename() {
        return this.filename;
    }

    private static byte[] convert(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read MultipartFile.", e);
        }
    }

    private static byte[] convert(InputStream input) {
        try {
            return FileCopyUtils.copyToByteArray(input);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read InputStream.", e);
        }
    }
}