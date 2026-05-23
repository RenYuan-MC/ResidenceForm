package ltd.rymc.form.residence.utils.facing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FacingTests {

    @Test
    void facing_byID_known() {
        assertEquals(Facing.East, Facing.facing(0));
        assertEquals(Facing.South, Facing.facing(1));
        assertEquals(Facing.West, Facing.facing(2));
        assertEquals(Facing.North, Facing.facing(3));
        assertEquals(Facing.Up, Facing.facing(4));
        assertEquals(Facing.Down, Facing.facing(5));
    }

    @Test
    void facing_byID_unknown() {
        assertEquals(Facing.Unknown, Facing.facing(-1));
        assertEquals(Facing.Unknown, Facing.facing(99));
    }

    @Test
    void facing_byYaw_south() {
        assertEquals(Facing.South, Facing.facing(0.0f));
        assertEquals(Facing.South, Facing.facing(45.0f));
        assertEquals(Facing.South, Facing.facing(44.9f));
    }

    @Test
    void facing_byYaw_east() {
        assertEquals(Facing.East, Facing.facing(-45.0f));
        assertEquals(Facing.East, Facing.facing(-90.0f));
        assertEquals(Facing.East, Facing.facing(-134.9f));
    }

    @Test
    void facing_byYaw_west() {
        assertEquals(Facing.West, Facing.facing(90.0f));
        assertEquals(Facing.West, Facing.facing(135.0f));
        assertEquals(Facing.West, Facing.facing(45.1f));
    }

    @Test
    void facing_byYaw_north() {
        assertEquals(Facing.North, Facing.facing(180.0f));
        assertEquals(Facing.North, Facing.facing(-180.0f));
        assertEquals(Facing.North, Facing.facing(136.0f));
        assertEquals(Facing.North, Facing.facing(-136.0f));
    }

    @Test
    void getID() {
        assertEquals(0, Facing.East.getID());
        assertEquals(1, Facing.South.getID());
        assertEquals(2, Facing.West.getID());
        assertEquals(3, Facing.North.getID());
        assertEquals(-1, Facing.Unknown.getID());
    }

    @Test
    void getYawOrPitch() {
        assertEquals(-90.0f, Facing.East.getYawOrPitch(), 0.001f);
        assertEquals(0.0f, Facing.South.getYawOrPitch(), 0.001f);
        assertEquals(90.0f, Facing.West.getYawOrPitch(), 0.001f);
        assertEquals(180.0f, Facing.North.getYawOrPitch(), 0.001f);
    }

    @Test
    void isYaw() {
        assertEquals(true, Facing.East.isYaw());
        assertEquals(true, Facing.South.isYaw());
        assertEquals(true, Facing.West.isYaw());
        assertEquals(true, Facing.North.isYaw());
        assertEquals(false, Facing.Up.isYaw());
        assertEquals(false, Facing.Down.isYaw());
        assertEquals(false, Facing.Unknown.isYaw());
    }
}
