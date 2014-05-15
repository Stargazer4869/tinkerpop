package com.tinkerpop.gremlin.structure.util.micro;

import com.tinkerpop.gremlin.structure.Direction;
import com.tinkerpop.gremlin.structure.Edge;
import com.tinkerpop.gremlin.structure.Vertex;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Stephen Mallette (http://stephen.genoprime.com)
 */
public class MicroEdgeTest {

    private MicroEdge me;

    @Before
    public void setup() {
        final Vertex v1 = mock(Vertex.class);
        when(v1.id()).thenReturn("1");
        when(v1.label()).thenReturn("l");
        final Vertex v2 = mock(Vertex.class);
        when(v2.id()).thenReturn("2");
        when(v2.label()).thenReturn("l");

        final Edge e = mock(Edge.class);
        when(e.id()).thenReturn("3");
        when(e.label()).thenReturn("knows");
        when(e.getVertex(Direction.OUT)).thenReturn(v1);
        when(e.getVertex(Direction.IN)).thenReturn(v2);

        this.me = MicroEdge.deflate(e);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotConstructWithNullElement() {
        MicroEdge.deflate(null);
    }

    @Test
    public void shouldConstructMicroEdge() {
        assertEquals("3", this.me.id());
        assertEquals("knows", this.me.label());
        assertEquals(MicroVertex.class, this.me.getVertex(Direction.OUT).getClass());
        assertEquals("1", this.me.getVertex(Direction.OUT).id());
        assertEquals(MicroVertex.class, this.me.getVertex(Direction.IN).getClass());
        assertEquals("2", this.me.getVertex(Direction.IN).id());
    }

    @Test
    public void shouldEvaluateToEqual() {
        final Vertex v1 = mock(Vertex.class);
        when(v1.id()).thenReturn("1");
        when(v1.label()).thenReturn("l");
        final Vertex v2 = mock(Vertex.class);
        when(v2.id()).thenReturn("2");
        when(v2.label()).thenReturn("l");

        final Edge e = mock(Edge.class);
        when(e.id()).thenReturn("3");
        when(e.label()).thenReturn("knows");
        when(e.getVertex(Direction.OUT)).thenReturn(v1);
        when(e.getVertex(Direction.IN)).thenReturn(v2);

        final MicroEdge me1 = MicroEdge.deflate(e);
        assertTrue(me1.equals(this.me));
    }

    @Test
    public void shouldNotEvaluateToEqualDifferentId() {
        final Vertex v1 = mock(Vertex.class);
        when(v1.id()).thenReturn("1");
        when(v1.label()).thenReturn("l");
        final Vertex v2 = mock(Vertex.class);
        when(v2.id()).thenReturn("2");
        when(v2.label()).thenReturn("l");

        final Edge e = mock(Edge.class);
        when(e.id()).thenReturn("4");
        when(e.label()).thenReturn("knows");
        when(e.getVertex(Direction.OUT)).thenReturn(v1);
        when(e.getVertex(Direction.IN)).thenReturn(v2);

        final MicroEdge me1 = MicroEdge.deflate(e);
        assertFalse(me1.equals(this.me));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotAllowSetProperty() {
        this.me.property("test", "test");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotAllowGetProperty() {
        this.me.property("test");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotAllowRemove() {
        this.me.remove();
    }
}
