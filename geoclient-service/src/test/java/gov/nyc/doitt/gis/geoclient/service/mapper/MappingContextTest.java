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
