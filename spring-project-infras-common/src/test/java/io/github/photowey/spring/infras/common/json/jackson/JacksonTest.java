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
package io.github.photowey.spring.infras.common.json.jackson;

import com.fasterxml.jackson.annotation.JsonView;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

/**
 * {@code JacksonTest}
 *
 * @author photowey
 * @version 1.3.0
 * @since 2024/04/28
 */
class JacksonTest {

    @Test
    void testToJsonString() {
        Long now = 1714314630000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        String json = Jackson.toJsonString(student);
        DocumentContext ctx = JsonPath.parse(json);

        Assertions.assertEquals(student.getId(), ctx.read("$.id"));
        Assertions.assertEquals(student.getName(), ctx.read("$.name"));
        Assertions.assertEquals(student.getAge(), ctx.read("$.age"));
    }

    @Test
    void testToJsonString_with_view() {
        Long now = 1714314630000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        String json = Jackson.toJsonString(student, View.Public.class);
        DocumentContext ctx = JsonPath.parse(json);

        Assertions.assertEquals(student.getId(), ctx.read("$.id"));
        Assertions.assertEquals(student.getName(), ctx.read("$.name"));

        Assertions.assertThrows(PathNotFoundException.class, () -> ctx.read("$.age"));
    }

    @Test
    void testToJsonString_pretty_json() {
        Long now = 1714314630000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        String json = Jackson.toJsonString(student);
        Assertions.assertFalse(json.contains("\n"));

        String prettyJson = Jackson.toPrettyJson(json);
        Assertions.assertTrue(prettyJson.contains("\n"));
    }

    public interface View {
        interface Public {}
    }

    public static class Student implements Serializable {

        private static final long serialVersionUID = -9189460711272727208L;

        @JsonView(View.Public.class)
        private Long id;
        @JsonView(View.Public.class)
        private String name;

        private Integer age;

        public static StudentBuilder builder() {
            return new StudentBuilder();
        }

        public Long getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public Integer getAge() {
            return this.age;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String toString() {
            return "Student(id=" + this.getId() + ", name=" + this.getName() + ", age=" + this.getAge() + ")";
        }

        public Student() {}

        public Student(Long id, String name, Integer age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public static class StudentBuilder {

            private Long id;
            private String name;
            private Integer age;

            StudentBuilder() {
            }

            public StudentBuilder id(Long id) {
                this.id = id;
                return this;
            }

            public StudentBuilder name(String name) {
                this.name = name;
                return this;
            }

            public StudentBuilder age(Integer age) {
                this.age = age;
                return this;
            }

            public Student build() {
                return new Student(this.id, this.name, this.age);
            }

            public String toString() {
                return "Student.StudentBuilder(id=" + this.id + ", name=" + this.name + ", age=" + this.age + ")";
            }
        }
    }
}