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
import com.fasterxml.jackson.databind.JsonNode;
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

    // ----------------------------------------------------------------

    @Test
    void testToObject() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        String json = JSON.Jackson.toJSONString(student);

        // ----------------------------------------------------------------

        Map<String, Object> ctx = JSON.Jackson.toMap(json, new TypeReference<Map<String, Object>>() {});
        Assertions.assertNotNull(ctx);

        Student peer = JSON.Jackson.toObject(ctx, Student.class);

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

        byte[] json = JSON.Jackson.toBytes(student);

        // ----------------------------------------------------------------

        Map<String, Object> ctx = JSON.Jackson.toMap(json, new TypeReference<Map<String, Object>>() {});
        Assertions.assertNotNull(ctx);

        Student peer = JSON.Jackson.toObject(ctx, Student.class);

        Assertions.assertNotNull(peer);
        Assertions.assertEquals(peer.getId(), student.getId());
        Assertions.assertEquals(peer.getName(), student.getName());
        Assertions.assertEquals(peer.getAge(), student.getAge());

        // ----------------------------------------------------------------

        InputStream input = new ByteArrayInputStream(json);
        Map<String, Object> ctx2 = JSON.Jackson.toMap(input, new TypeReference<Map<String, Object>>() {});
        Assertions.assertNotNull(ctx2);

        Student peer2 = JSON.Jackson.toObject(ctx2, Student.class);

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

        String json = JSON.Jackson.toJSONString(student);

        JsonNode ctx = JSON.Jackson.toJsonNode(json);
        Assertions.assertNotNull(ctx);

        Assertions.assertEquals(ctx.get("id").asLong(), student.getId());
        Assertions.assertEquals(ctx.get("name").asText(), student.getName());
        Assertions.assertEquals(ctx.get("age").asInt(), student.getAge());

        // ----------------------------------------------------------------

        byte[] bytes = JSON.Jackson.toBytes(student);
        JsonNode ctx2 = JSON.Jackson.toJsonNode(bytes);
        Assertions.assertNotNull(ctx2);

        Assertions.assertEquals(ctx2.get("id").asLong(), student.getId());
        Assertions.assertEquals(ctx2.get("name").asText(), student.getName());
        Assertions.assertEquals(ctx2.get("age").asInt(), student.getAge());

        // ----------------------------------------------------------------

        InputStream input = new ByteArrayInputStream(bytes);
        JsonNode ctx3 = JSON.Jackson.toJsonNode(input);
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
        String jsonArray = JSON.Jackson.toJSONString(students);
        byte[] bytes = jsonArray.getBytes(StandardCharsets.UTF_8);

        List<Student> simpleList1 = JSON.Jackson.parseArray(jsonArray, Student.class);
        Assertions.assertNotNull(simpleList1);
        Assertions.assertEquals(1, simpleList1.size());
        Assertions.assertEquals(now, simpleList1.get(0).getId());

        List<Student> simpleList2 = JSON.Jackson.parseArray(bytes, Student.class);
        Assertions.assertNotNull(simpleList2);
        Assertions.assertEquals(1, simpleList2.size());
        Assertions.assertEquals(now, simpleList2.get(0).getId());

        List<Student> simpleList3 = JSON.Jackson.parseArray(new ByteArrayInputStream(bytes), Student.class);
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
        String jsonArray = JSON.Jackson.toJSONString(students);
        byte[] bytes = jsonArray.getBytes(StandardCharsets.UTF_8);

        List<Student> simpleList1 = JSON.Jackson.toList(jsonArray, Student.class);
        Assertions.assertNotNull(simpleList1);
        Assertions.assertEquals(1, simpleList1.size());
        Assertions.assertEquals(now, simpleList1.get(0).getId());

        List<Student> simpleList2 = JSON.Jackson.toList(bytes, Student.class);
        Assertions.assertNotNull(simpleList2);
        Assertions.assertEquals(1, simpleList2.size());
        Assertions.assertEquals(now, simpleList2.get(0).getId());

        List<Student> simpleList3 = JSON.Jackson.toList(new ByteArrayInputStream(bytes), Student.class);
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
        String jsonArray = JSON.Jackson.toJSONString(students);
        byte[] bytes = jsonArray.getBytes(StandardCharsets.UTF_8);

        Set<Student> simpleSet1 = JSON.Jackson.toSet(jsonArray, Student.class);
        Assertions.assertNotNull(simpleSet1);
        Assertions.assertEquals(1, simpleSet1.size());
        Assertions.assertEquals(now, new ArrayList<>(simpleSet1).get(0).getId());

        Set<Student> simpleSet2 = JSON.Jackson.toSet(bytes, Student.class);
        Assertions.assertNotNull(simpleSet2);
        Assertions.assertEquals(1, simpleSet2.size());
        Assertions.assertEquals(now, new ArrayList<>(simpleSet2).get(0).getId());

        Set<Student> simpleSet3 = JSON.Jackson.toSet(new ByteArrayInputStream(bytes), Student.class);
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
        String jsonArray = JSON.Jackson.toJSONString(students);
        byte[] bytes = jsonArray.getBytes(StandardCharsets.UTF_8);

        Collection<Student> simpleCollection1 = JSON.Jackson.toCollection(jsonArray, Student.class);
        Assertions.assertNotNull(simpleCollection1);
        Assertions.assertEquals(1, simpleCollection1.size());
        Assertions.assertEquals(now, new ArrayList<>(simpleCollection1).get(0).getId());

        Collection<Student> simpleCollection2 = JSON.Jackson.toCollection(bytes, Student.class);
        Assertions.assertNotNull(simpleCollection2);
        Assertions.assertEquals(1, simpleCollection2.size());
        Assertions.assertEquals(now, new ArrayList<>(simpleCollection2).get(0).getId());

        Collection<Student> simpleCollection3 = JSON.Jackson.toCollection(new ByteArrayInputStream(bytes), Student.class);
        Assertions.assertNotNull(simpleCollection3);
        Assertions.assertEquals(1, simpleCollection3.size());
        Assertions.assertEquals(now, new ArrayList<>(simpleCollection3).get(0).getId());
    }
}