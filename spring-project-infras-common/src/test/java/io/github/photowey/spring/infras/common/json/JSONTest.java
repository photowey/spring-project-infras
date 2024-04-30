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
package io.github.photowey.spring.infras.common.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import io.github.photowey.spring.infras.common.LocalTest;
import io.github.photowey.spring.infras.common.json.jackson.View;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * {@code JSONTest}
 *
 * @author photowey
 * @version 1.3.0
 * @since 2024/04/29
 */
class JSONTest extends LocalTest {

    @Test
    void testJackson_toJSONString() {
        Long now = 1714314630000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        String json = JSON.Jackson.toJSONString(student);
        DocumentContext ctx = JsonPath.parse(json);

        Assertions.assertEquals(student.getId(), ctx.read("$.id"));
        Assertions.assertEquals(student.getName(), ctx.read("$.name"));
        Assertions.assertEquals(student.getAge(), ctx.read("$.age"));
    }

    @Test
    void testJackson_toJSONString_with_view() {
        Long now = 1714314630000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        String json = JSON.Jackson.toJSONString(student, View.Public.class);
        DocumentContext ctx = JsonPath.parse(json);

        Assertions.assertEquals(student.getId(), ctx.read("$.id"));
        Assertions.assertEquals(student.getName(), ctx.read("$.name"));

        Assertions.assertThrows(PathNotFoundException.class, () -> ctx.read("$.age"));
    }

    @Test
    void testJackson_toPrettyString() {
        Long now = 1714314630000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        String json = JSON.Jackson.toJSONString(student);
        Assertions.assertFalse(json.contains("\n"));

        String prettyJson = JSON.Jackson.toPrettyString(json);
        Assertions.assertTrue(prettyJson.contains("\n"));
    }

    @Test
    void testParseArray() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        List<Student> students = new ArrayList<>();
        students.add(student);

        String json = JSON.Jackson.toJSONString(students);
        List<Student> peers = JSON.Jackson.parseArray(json, new TypeReference<List<Student>>() {});

        Assertions.assertNotNull(peers);
        Assertions.assertEquals(1, peers.size());

        Student peer = peers.get(0);
        Assertions.assertEquals(peer.getId(), student.getId());
        Assertions.assertEquals(peer.getName(), student.getName());
        Assertions.assertEquals(peer.getAge(), student.getAge());
    }

    @Test
    void testParseArray_bytes() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        List<Student> students = new ArrayList<>();
        students.add(student);

        String json = JSON.Jackson.toJSONString(students);
        List<Student> peers = JSON.Jackson.parseArray(json.getBytes(StandardCharsets.UTF_8), new TypeReference<List<Student>>() {});

        Assertions.assertNotNull(peers);
        Assertions.assertEquals(1, peers.size());

        Student peer = peers.get(0);
        Assertions.assertEquals(peer.getId(), student.getId());
        Assertions.assertEquals(peer.getName(), student.getName());
        Assertions.assertEquals(peer.getAge(), student.getAge());
    }

    @Test
    void testParseArray_input_stream() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        List<Student> students = new ArrayList<>();
        students.add(student);

        String json = JSON.Jackson.toJSONString(students);
        InputStream input = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        List<Student> peers = JSON.Jackson.parseArray(input, new TypeReference<List<Student>>() {});

        Assertions.assertNotNull(peers);
        Assertions.assertEquals(1, peers.size());

        Student peer = peers.get(0);
        Assertions.assertEquals(peer.getId(), student.getId());
        Assertions.assertEquals(peer.getName(), student.getName());
        Assertions.assertEquals(peer.getAge(), student.getAge());
    }

    @Test
    void testToList() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        List<Student> students = new ArrayList<>();
        students.add(student);

        String json = JSON.Jackson.toJSONString(students);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        InputStream input = new ByteArrayInputStream(bytes);

        // ----------------------------------------------------------------

        List<Student> peers1 = JSON.Jackson.toList(json, new TypeReference<List<Student>>() {});

        Assertions.assertNotNull(peers1);
        Assertions.assertEquals(1, peers1.size());

        Student peer1 = peers1.get(0);
        Assertions.assertEquals(peer1.getId(), student.getId());
        Assertions.assertEquals(peer1.getName(), student.getName());
        Assertions.assertEquals(peer1.getAge(), student.getAge());

        // ----------------------------------------------------------------

        List<Student> peers2 = JSON.Jackson.toList(bytes, new TypeReference<List<Student>>() {});
        Assertions.assertNotNull(peers2);
        Assertions.assertEquals(1, peers2.size());

        Student peer2 = peers2.get(0);
        Assertions.assertEquals(peer2.getId(), student.getId());
        Assertions.assertEquals(peer2.getName(), student.getName());
        Assertions.assertEquals(peer2.getAge(), student.getAge());

        // ----------------------------------------------------------------

        List<Student> peers3 = JSON.Jackson.toList(input, new TypeReference<List<Student>>() {});
        Assertions.assertNotNull(peers3);
        Assertions.assertEquals(1, peers3.size());

        Student peer3 = peers3.get(0);
        Assertions.assertEquals(peer3.getId(), student.getId());
        Assertions.assertEquals(peer3.getName(), student.getName());
        Assertions.assertEquals(peer3.getAge(), student.getAge());
    }

    @Test
    void testToSet() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        List<Student> students = new ArrayList<>();
        students.add(student);

        String json = JSON.Jackson.toJSONString(students);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        InputStream input = new ByteArrayInputStream(bytes);

        // ----------------------------------------------------------------

        Set<Student> peers1 = JSON.Jackson.toSet(json, new TypeReference<Set<Student>>() {});

        Assertions.assertNotNull(peers1);
        Assertions.assertEquals(1, peers1.size());

        Student peer1 = new ArrayList<>(peers1).get(0);
        Assertions.assertEquals(peer1.getId(), student.getId());
        Assertions.assertEquals(peer1.getName(), student.getName());
        Assertions.assertEquals(peer1.getAge(), student.getAge());

        // ----------------------------------------------------------------

        Set<Student> peers2 = JSON.Jackson.toSet(bytes, new TypeReference<Set<Student>>() {});
        Assertions.assertNotNull(peers2);
        Assertions.assertEquals(1, peers2.size());

        Student peer2 = new ArrayList<>(peers2).get(0);
        Assertions.assertEquals(peer2.getId(), student.getId());
        Assertions.assertEquals(peer2.getName(), student.getName());
        Assertions.assertEquals(peer2.getAge(), student.getAge());

        // ----------------------------------------------------------------

        Set<Student> peers3 = JSON.Jackson.toSet(input, new TypeReference<Set<Student>>() {});
        Assertions.assertNotNull(peers3);
        Assertions.assertEquals(1, peers3.size());

        Student peer3 = new ArrayList<>(peers3).get(0);
        Assertions.assertEquals(peer3.getId(), student.getId());
        Assertions.assertEquals(peer3.getName(), student.getName());
        Assertions.assertEquals(peer3.getAge(), student.getAge());
    }

    @Test
    void testToCollection() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        List<Student> students = new ArrayList<>();
        students.add(student);

        String json = JSON.Jackson.toJSONString(students);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        InputStream input = new ByteArrayInputStream(bytes);

        // ----------------------------------------------------------------

        Collection<Student> peers1 = JSON.Jackson.toCollection(json, new TypeReference<Collection<Student>>() {});

        Assertions.assertNotNull(peers1);
        Assertions.assertEquals(1, peers1.size());

        Student peer1 = new ArrayList<>(peers1).get(0);
        Assertions.assertEquals(peer1.getId(), student.getId());
        Assertions.assertEquals(peer1.getName(), student.getName());
        Assertions.assertEquals(peer1.getAge(), student.getAge());

        // ----------------------------------------------------------------

        Collection<Student> peers2 = JSON.Jackson.toCollection(bytes, new TypeReference<Collection<Student>>() {});
        Assertions.assertNotNull(peers2);
        Assertions.assertEquals(1, peers2.size());

        Student peer2 = new ArrayList<>(peers2).get(0);
        Assertions.assertEquals(peer2.getId(), student.getId());
        Assertions.assertEquals(peer2.getName(), student.getName());
        Assertions.assertEquals(peer2.getAge(), student.getAge());

        // ----------------------------------------------------------------

        Collection<Student> peers3 = JSON.Jackson.toCollection(input, new TypeReference<Collection<Student>>() {});
        Assertions.assertNotNull(peers3);
        Assertions.assertEquals(1, peers3.size());

        Student peer3 = new ArrayList<>(peers3).get(0);
        Assertions.assertEquals(peer3.getId(), student.getId());
        Assertions.assertEquals(peer3.getName(), student.getName());
        Assertions.assertEquals(peer3.getAge(), student.getAge());
    }

    @Test
    void testToMap() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        String json = JSON.Jackson.toJSONString(student);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        InputStream input = new ByteArrayInputStream(bytes);

        // ----------------------------------------------------------------

        Map<String, Object> peers1 = JSON.Jackson.toMap(json, new TypeReference<Map<String, Object>>() {});

        Assertions.assertNotNull(peers1);
        Assertions.assertEquals(3, peers1.size());

        Assertions.assertEquals(peers1.get("id"), student.getId());
        Assertions.assertEquals(peers1.get("name"), student.getName());
        Assertions.assertEquals(peers1.get("age"), student.getAge());

        // ----------------------------------------------------------------

        Map<String, Object> peers2 = JSON.Jackson.toMap(bytes, new TypeReference<Map<String, Object>>() {});

        Assertions.assertNotNull(peers2);
        Assertions.assertEquals(3, peers2.size());

        Assertions.assertEquals(peers2.get("id"), student.getId());
        Assertions.assertEquals(peers2.get("name"), student.getName());
        Assertions.assertEquals(peers2.get("age"), student.getAge());

        // ----------------------------------------------------------------

        Map<String, Object> peers3 = JSON.Jackson.toMap(input, new TypeReference<Map<String, Object>>() {});

        Assertions.assertNotNull(peers3);
        Assertions.assertEquals(3, peers3.size());

        Assertions.assertEquals(peers3.get("id"), student.getId());
        Assertions.assertEquals(peers3.get("name"), student.getName());
        Assertions.assertEquals(peers3.get("age"), student.getAge());
    }
}