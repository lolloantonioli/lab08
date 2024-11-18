package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static it.unibo.deathnote.api.DeathNote.RULES;
import static java.lang.Thread.sleep;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImplementation;

class TestDeathNote {

    private DeathNote note;
    public static final String NAME = "Lorenzo";
    public static final String OTHER_NAME = "Martina";
    public static final int TIME_1 = 100;
    public static final int TIME_2 = 6100;

    @BeforeEach
    void setUp() {
        note = new DeathNoteImplementation();
    }

    @Test
    void testIndexRules() {
        for (final var element : List.of(0, -1, RULES.size() + 1)) {
            try {
                note.getRule(element);
                fail();
            } catch (IllegalArgumentException e) {
                assertTrue(e.getMessage() != null);
                assertFalse(e.getMessage().isBlank());
                assertFalse(e.getMessage().isEmpty());
            }
        }
    }

    @Test
    void testEmptyRules() {
        for (int i = 1; i < RULES.size(); i++) {
            assertFalse(note.getRule(i).isBlank());
            assertFalse(note.getRule(i) == null);
        }
    }

    @Test
    void testDeath() {
        assertFalse(note.isNameWritten(NAME));
        note.writeName(NAME);
        assertTrue(note.isNameWritten(NAME));
        assertFalse(note.isNameWritten(OTHER_NAME));
        assertFalse(note.isNameWritten(""));
    }

    @Test
    void testDeathCauses() throws InterruptedException {
        try {
            note.writeDeathCause("heart attack");
            note.writeName(NAME);
            fail();
        } catch (IllegalStateException e) {
            assertTrue(e.getMessage() != null);
            assertFalse(e.getMessage().isBlank());
            assertFalse(e.getMessage().isEmpty());
        }

        note.writeName(NAME);
        assertNotEquals("heart attack", note.getDeathCause(NAME));
        note.writeName(OTHER_NAME);
        note.writeDeathCause("karting accident");
        assertEquals("karting accident", note.getDeathCause(OTHER_NAME));
        sleep(TIME_1);
        note.writeDeathCause("hemorrhage");
        assertNotEquals(note.getDeathCause(OTHER_NAME), "hemorrage");
    }

    @Test
    void testDeathDetails() throws InterruptedException {
        try {
            note.writeDetails("generic details");
            note.writeName(NAME);
            fail();
        } catch (IllegalStateException e) {
            assertTrue(e.getMessage() != null);
            assertFalse(e.getMessage().isBlank());
            assertFalse(e.getMessage().isEmpty());
        }

        note.writeName(NAME);
        assertTrue(note.getDeathDetails(NAME).isEmpty());
        note.writeDetails("ran for too long");
        assertEquals("ran for too long", note.getDeathDetails(NAME));
        note.writeName(OTHER_NAME);
        sleep(TIME_2);
        note.writeDetails("other details");
        assertNotEquals("other details", note.getDeathDetails(OTHER_NAME));
    }

}