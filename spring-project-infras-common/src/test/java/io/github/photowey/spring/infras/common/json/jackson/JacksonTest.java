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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import io.github.photowey.spring.infras.common.LocalTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * {@code JacksonTest}
 *
 * @author photowey
 * @version 1.3.0
 * @since 2024/04/28
 */
class JacksonTest extends LocalTest {

    @Test
    void testToJSONString() {
        Long now = 1714314630000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        String json = Jackson.toJSONString(student);
        DocumentContext ctx = JsonPath.parse(json);

        Assertions.assertEquals(student.getId(), ctx.read("$.id"));
        Assertions.assertEquals(student.getName(), ctx.read("$.name"));
        Assertions.assertEquals(student.getAge(), ctx.read("$.age"));
    }

    @Test
    void testToJSONString_with_view() {
        Long now = 1714314630000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        String json = Jackson.toJSONString(student, View.Public.class);
        DocumentContext ctx = JsonPath.parse(json);

        Assertions.assertEquals(student.getId(), ctx.read("$.id"));
        Assertions.assertEquals(student.getName(), ctx.read("$.name"));

        Assertions.assertThrows(PathNotFoundException.class, () -> ctx.read("$.age"));
    }

    @Test
    void testToPrettyString() {
        Long now = 1714314630000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        String json = Jackson.toJSONString(student);
        Assertions.assertFalse(json.contains("\n"));

        String prettyJson = Jackson.toPrettyString(json);
        Assertions.assertTrue(prettyJson.contains("\n"));
    }

    // ----------------------------------------------------------------

    @Test
    void testParseObject() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        String json = Jackson.toJSONString(student);
        Student peer = Jackson.parseObject(json, Student.class);

        Assertions.assertNotNull(peer);
        Assertions.assertEquals(peer.getId(), student.getId());
        Assertions.assertEquals(peer.getName(), student.getName());
        Assertions.assertEquals(peer.getAge(), student.getAge());
    }

    @Test
    void testParseObject_bytes() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        String json = Jackson.toJSONString(student);
        Student peer = Jackson.parseObject(json.getBytes(StandardCharsets.UTF_8), Student.class);

        Assertions.assertNotNull(peer);
        Assertions.assertEquals(peer.getId(), student.getId());
        Assertions.assertEquals(peer.getName(), student.getName());
        Assertions.assertEquals(peer.getAge(), student.getAge());
    }

    @Test
    void testParseObject_input_stream() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        String json = Jackson.toJSONString(student);
        InputStream input = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        Student peer = Jackson.parseObject(input, Student.class);

        Assertions.assertNotNull(peer);
        Assertions.assertEquals(peer.getId(), student.getId());
        Assertions.assertEquals(peer.getName(), student.getName());
        Assertions.assertEquals(peer.getAge(), student.getAge());
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

        String json = Jackson.toJSONString(students);
        List<Student> peers = Jackson.parseArray(json, new TypeReference<List<Student>>() {});

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

        String json = Jackson.toJSONString(students);
        List<Student> peers = Jackson.parseArray(json.getBytes(StandardCharsets.UTF_8), new TypeReference<List<Student>>() {});

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

        String json = Jackson.toJSONString(students);
        InputStream input = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        List<Student> peers = Jackson.parseArray(input, new TypeReference<List<Student>>() {});

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

        String json = Jackson.toJSONString(students);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        InputStream input = new ByteArrayInputStream(bytes);

        // ----------------------------------------------------------------

        List<Student> peers1 = Jackson.toList(json, new TypeReference<List<Student>>() {});

        Assertions.assertNotNull(peers1);
        Assertions.assertEquals(1, peers1.size());

        Student peer1 = peers1.get(0);
        Assertions.assertEquals(peer1.getId(), student.getId());
        Assertions.assertEquals(peer1.getName(), student.getName());
        Assertions.assertEquals(peer1.getAge(), student.getAge());

        // ----------------------------------------------------------------

        List<Student> peers2 = Jackson.toList(bytes, new TypeReference<List<Student>>() {});
        Assertions.assertNotNull(peers2);
        Assertions.assertEquals(1, peers2.size());

        Student peer2 = peers2.get(0);
        Assertions.assertEquals(peer2.getId(), student.getId());
        Assertions.assertEquals(peer2.getName(), student.getName());
        Assertions.assertEquals(peer2.getAge(), student.getAge());

        // ----------------------------------------------------------------

        List<Student> peers3 = Jackson.toList(input, new TypeReference<List<Student>>() {});
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

        String json = Jackson.toJSONString(students);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        InputStream input = new ByteArrayInputStream(bytes);

        // ----------------------------------------------------------------

        Set<Student> peers1 = Jackson.toSet(json, new TypeReference<Set<Student>>() {});

        Assertions.assertNotNull(peers1);
        Assertions.assertEquals(1, peers1.size());

        Student peer1 = new ArrayList<>(peers1).get(0);
        Assertions.assertEquals(peer1.getId(), student.getId());
        Assertions.assertEquals(peer1.getName(), student.getName());
        Assertions.assertEquals(peer1.getAge(), student.getAge());

        // ----------------------------------------------------------------

        Set<Student> peers2 = Jackson.toSet(bytes, new TypeReference<Set<Student>>() {});
        Assertions.assertNotNull(peers2);
        Assertions.assertEquals(1, peers2.size());

        Student peer2 = new ArrayList<>(peers2).get(0);
        Assertions.assertEquals(peer2.getId(), student.getId());
        Assertions.assertEquals(peer2.getName(), student.getName());
        Assertions.assertEquals(peer2.getAge(), student.getAge());

        // ----------------------------------------------------------------

        Set<Student> peers3 = Jackson.toSet(input, new TypeReference<Set<Student>>() {});
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

        String json = Jackson.toJSONString(students);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        InputStream input = new ByteArrayInputStream(bytes);

        // ----------------------------------------------------------------

        Collection<Student> peers1 = Jackson.toCollection(json, new TypeReference<Collection<Student>>() {});

        Assertions.assertNotNull(peers1);
        Assertions.assertEquals(1, peers1.size());

        Student peer1 = new ArrayList<>(peers1).get(0);
        Assertions.assertEquals(peer1.getId(), student.getId());
        Assertions.assertEquals(peer1.getName(), student.getName());
        Assertions.assertEquals(peer1.getAge(), student.getAge());

        // ----------------------------------------------------------------

        Collection<Student> peers2 = Jackson.toCollection(bytes, new TypeReference<Collection<Student>>() {});
        Assertions.assertNotNull(peers2);
        Assertions.assertEquals(1, peers2.size());

        Student peer2 = new ArrayList<>(peers2).get(0);
        Assertions.assertEquals(peer2.getId(), student.getId());
        Assertions.assertEquals(peer2.getName(), student.getName());
        Assertions.assertEquals(peer2.getAge(), student.getAge());

        // ----------------------------------------------------------------

        Collection<Student> peers3 = Jackson.toCollection(input, new TypeReference<Collection<Student>>() {});
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

        String json = Jackson.toJSONString(student);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        InputStream input = new ByteArrayInputStream(bytes);

        // ----------------------------------------------------------------

        Map<String, Object> peers1 = Jackson.toMap(json, new TypeReference<Map<String, Object>>() {});

        Assertions.assertNotNull(peers1);
        Assertions.assertEquals(3, peers1.size());

        Assertions.assertEquals(peers1.get("id"), student.getId());
        Assertions.assertEquals(peers1.get("name"), student.getName());
        Assertions.assertEquals(peers1.get("age"), student.getAge());

        // ----------------------------------------------------------------

        Map<String, Object> peers2 = Jackson.toMap(bytes, new TypeReference<Map<String, Object>>() {});

        Assertions.assertNotNull(peers2);
        Assertions.assertEquals(3, peers2.size());

        Assertions.assertEquals(peers2.get("id"), student.getId());
        Assertions.assertEquals(peers2.get("name"), student.getName());
        Assertions.assertEquals(peers2.get("age"), student.getAge());

        // ----------------------------------------------------------------

        Map<String, Object> peers3 = Jackson.toMap(input, new TypeReference<Map<String, Object>>() {});

        Assertions.assertNotNull(peers3);
        Assertions.assertEquals(3, peers3.size());

        Assertions.assertEquals(peers3.get("id"), student.getId());
        Assertions.assertEquals(peers3.get("name"), student.getName());
        Assertions.assertEquals(peers3.get("age"), student.getAge());
    }

    // ----------------------------------------------------------------

    @Test
    void testToObject() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        String json = Jackson.toJSONString(student);

        // ----------------------------------------------------------------

        Map<String, Object> ctx = Jackson.toMap(json, new TypeReference<Map<String, Object>>() {});
        Assertions.assertNotNull(ctx);

        Student peer = Jackson.toObject(ctx, Student.class);

        Assertions.assertNotNull(peer);
        Assertions.assertEquals(peer.getId(), student.getId());
        Assertions.assertEquals(peer.getName(), student.getName());
        Assertions.assertEquals(peer.getAge(), student.getAge());
    }

    @Test
    void testToBytes() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        byte[] json = Jackson.toBytes(student);

        // ----------------------------------------------------------------

        Map<String, Object> ctx = Jackson.toMap(json, new TypeReference<Map<String, Object>>() {});
        Assertions.assertNotNull(ctx);

        Student peer = Jackson.toObject(ctx, Student.class);

        Assertions.assertNotNull(peer);
        Assertions.assertEquals(peer.getId(), student.getId());
        Assertions.assertEquals(peer.getName(), student.getName());
        Assertions.assertEquals(peer.getAge(), student.getAge());

        // ----------------------------------------------------------------

        InputStream input = new ByteArrayInputStream(json);
        Map<String, Object> ctx2 = Jackson.toMap(input, new TypeReference<Map<String, Object>>() {});
        Assertions.assertNotNull(ctx2);

        Student peer2 = Jackson.toObject(ctx2, Student.class);

        Assertions.assertNotNull(peer2);
        Assertions.assertEquals(peer2.getId(), student.getId());
        Assertions.assertEquals(peer2.getName(), student.getName());
        Assertions.assertEquals(peer2.getAge(), student.getAge());
    }

    @Test
    void testToJsonNode() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        String json = Jackson.toJSONString(student);

        JsonNode ctx = Jackson.toJsonNode(json);
        Assertions.assertNotNull(ctx);

        Assertions.assertEquals(ctx.get("id").asLong(), student.getId());
        Assertions.assertEquals(ctx.get("name").asText(), student.getName());
        Assertions.assertEquals(ctx.get("age").asInt(), student.getAge());

        // ----------------------------------------------------------------

        byte[] bytes = Jackson.toBytes(student);
        JsonNode ctx2 = Jackson.toJsonNode(bytes);
        Assertions.assertNotNull(ctx2);

        Assertions.assertEquals(ctx2.get("id").asLong(), student.getId());
        Assertions.assertEquals(ctx2.get("name").asText(), student.getName());
        Assertions.assertEquals(ctx2.get("age").asInt(), student.getAge());

        // ----------------------------------------------------------------

        InputStream input = new ByteArrayInputStream(bytes);
        JsonNode ctx3 = Jackson.toJsonNode(input);
        Assertions.assertNotNull(ctx3);

        Assertions.assertEquals(ctx3.get("id").asLong(), student.getId());
        Assertions.assertEquals(ctx3.get("name").asText(), student.getName());
        Assertions.assertEquals(ctx3.get("age").asInt(), student.getAge());
    }

    // ----------------------------------------------------------------

    @Test
    void testParseArray_simple() {
        long now = System.currentTimeMillis();
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        List<Student> students = new ArrayList<>();
        students.add(student);
        String jsonArray = Jackson.toJSONString(students);
        byte[] bytes = jsonArray.getBytes(StandardCharsets.UTF_8);

        List<Student> simpleList1 = Jackson.parseArray(jsonArray, Student.class);
        Assertions.assertNotNull(simpleList1);
        Assertions.assertEquals(1, simpleList1.size());
        Assertions.assertEquals(now, simpleList1.get(0).getId());

        List<Student> simpleList2 = Jackson.parseArray(bytes, Student.class);
        Assertions.assertNotNull(simpleList2);
        Assertions.assertEquals(1, simpleList2.size());
        Assertions.assertEquals(now, simpleList2.get(0).getId());

        List<Student> simpleList3 = Jackson.parseArray(new ByteArrayInputStream(bytes), Student.class);
        Assertions.assertNotNull(simpleList3);
        Assertions.assertEquals(1, simpleList3.size());
        Assertions.assertEquals(now, simpleList3.get(0).getId());
    }

    @Test
    void testToList_simple() {
        long now = System.currentTimeMillis();
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        List<Student> students = new ArrayList<>();
        students.add(student);
        String jsonArray = Jackson.toJSONString(students);
        byte[] bytes = jsonArray.getBytes(StandardCharsets.UTF_8);

        List<Student> simpleList1 = Jackson.toList(jsonArray, Student.class);
        Assertions.assertNotNull(simpleList1);
        Assertions.assertEquals(1, simpleList1.size());
        Assertions.assertEquals(now, simpleList1.get(0).getId());

        List<Student> simpleList2 = Jackson.toList(bytes, Student.class);
        Assertions.assertNotNull(simpleList2);
        Assertions.assertEquals(1, simpleList2.size());
        Assertions.assertEquals(now, simpleList2.get(0).getId());

        List<Student> simpleList3 = Jackson.toList(new ByteArrayInputStream(bytes), Student.class);
        Assertions.assertNotNull(simpleList3);
        Assertions.assertEquals(1, simpleList3.size());
        Assertions.assertEquals(now, simpleList3.get(0).getId());
    }

    @Test
    void testToSet_simple() {
        long now = System.currentTimeMillis();
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        List<Student> students = new ArrayList<>();
        students.add(student);
        String jsonArray = Jackson.toJSONString(students);
        byte[] bytes = jsonArray.getBytes(StandardCharsets.UTF_8);

        Set<Student> simpleSet1 = Jackson.toSet(jsonArray, Student.class);
        Assertions.assertNotNull(simpleSet1);
        Assertions.assertEquals(1, simpleSet1.size());
        Assertions.assertEquals(now, new ArrayList<>(simpleSet1).get(0).getId());

        Set<Student> simpleSet2 = Jackson.toSet(bytes, Student.class);
        Assertions.assertNotNull(simpleSet2);
        Assertions.assertEquals(1, simpleSet2.size());
        Assertions.assertEquals(now, new ArrayList<>(simpleSet2).get(0).getId());

        Set<Student> simpleSet3 = Jackson.toSet(new ByteArrayInputStream(bytes), Student.class);
        Assertions.assertNotNull(simpleSet3);
        Assertions.assertEquals(1, simpleSet3.size());
        Assertions.assertEquals(now, new ArrayList<>(simpleSet3).get(0).getId());
    }

    @Test
    void testToCollection_simple() {
        long now = System.currentTimeMillis();
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        List<Student> students = new ArrayList<>();
        students.add(student);
        String jsonArray = Jackson.toJSONString(students);
        byte[] bytes = jsonArray.getBytes(StandardCharsets.UTF_8);

        Collection<Student> simpleCollection1 = Jackson.toCollection(jsonArray, Student.class);
        Assertions.assertNotNull(simpleCollection1);
        Assertions.assertEquals(1, simpleCollection1.size());
        Assertions.assertEquals(now, new ArrayList<>(simpleCollection1).get(0).getId());

        Collection<Student> simpleCollection2 = Jackson.toCollection(bytes, Student.class);
        Assertions.assertNotNull(simpleCollection2);
        Assertions.assertEquals(1, simpleCollection2.size());
        Assertions.assertEquals(now, new ArrayList<>(simpleCollection2).get(0).getId());

        Collection<Student> simpleCollection3 = Jackson.toCollection(new ByteArrayInputStream(bytes), Student.class);
        Assertions.assertNotNull(simpleCollection3);
        Assertions.assertEquals(1, simpleCollection3.size());
        Assertions.assertEquals(now, new ArrayList<>(simpleCollection3).get(0).getId());
    }
}