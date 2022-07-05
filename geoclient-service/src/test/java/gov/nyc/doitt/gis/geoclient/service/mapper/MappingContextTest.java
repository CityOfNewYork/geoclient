/*
 * Copyright 2013-2022 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.service.mapper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MappingContextTest {

    private CatMapper catMapper;
    private DogMapper dogMapper;
    private List<Mapper<?>> listOfMappers;
    private MappingContext ctx;

    @BeforeEach
    void setUp() throws Exception {
        this.catMapper = new CatMapper();
        this.dogMapper = new DogMapper();
        this.listOfMappers = new ArrayList<>();
        this.listOfMappers.add(dogMapper);
        this.listOfMappers.add(catMapper);
        this.ctx = new MappingContext(listOfMappers);
    }

    @AfterEach
    void tearDown() throws Exception {
        this.listOfMappers.clear();
    }

    @Test
    void testConstructor_listOfMappers() {
        assertSame(catMapper, ctx.getMapper(CatMapper.class));
        assertSame(dogMapper, ctx.getMapper(DogMapper.class));
    }

    @Test
    void testAdd() {
        this.ctx = new MappingContext();
        assertFalse(ctx.containsMapper(CatMapper.class));
        ctx.add(catMapper);
        assertSame(catMapper, ctx.getMapper(CatMapper.class));
    }

}
