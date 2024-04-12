package ltd.rymc.form.residence.utils.facing;

import ltd.rymc.form.residence.ResidenceForm;
import ltd.rymc.form.residence.language.Language;
import org.bukkit.Location;

public enum Facing {
    East(0, -90.0f, true),
    South(1, 0.0f, true),
    West(2, 90.0f, true),
    North(3, 180.0f, true),
    Up(4, -90.0f, false),
    Down(5, 90.0f, false),
    Unknown(-1, 0.0f, false);

    private final int ID;
    private final float yawOrPitch;
    private final boolean isYaw;

    Facing(int ID, float yawOrPitch, boolean isYaw) {
        this.ID = ID;
        this.yawOrPitch = yawOrPitch;
        this.isYaw = isYaw;
    }

    public static Facing facing(int ID) {
        for (Facing facing : values()) {
            if (facing.getID() == ID) return facing;
        }
        return Facing.Unknown;
    }

    public static Facing facing(float yaw) {
        if (45.0 < yaw && yaw <= 135.0) return Facing.West;
        if (-45.0 < yaw && yaw <= 45.0) return Facing.South;
        if (-135.0 < yaw && yaw <= -45.0) return Facing.East;
        if (135.0 < yaw && yaw <= -135.0) return Facing.North;
        return Facing.Unknown;
    }

    public static String[] facingList() {
        Facing[] facings = values();
        String[] list = new String[facings.length - 1];
        for (int i = 0, j = facings.length; i < j; i++) {
            if (facings[i].getID() == -1) continue;
            list[i] = facings[i].getName();
        }
        return list;
    }

    public static Location translateLocation(Location location, Facing facing) {
        if (facing.getID() == -1) return location;
        Location newLocation = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        if (facing.isYaw()) newLocation.setYaw(facing.getYawOrPitch());
        else newLocation.setPitch(facing.getYawOrPitch());
        return newLocation;
    }

    public int getID() {
        return ID;
    }

    public float getYawOrPitch() {
        return yawOrPitch;
    }

    public String getName() {
        Language language = ResidenceForm.getLanguage();
        switch (this) {
            case North:
                return language.text("forms.facing.north");
            case South:
                return language.text("forms.facing.south");
            case West:
                return language.text("forms.facing.west");
            case East:
                return language.text("forms.facing.east");
            case Up:
                return language.text("forms.facing.up");
            case Down:
                return language.text("forms.facing.down");
            default:
                return language.text("forms.facing.unknown");
        }

    }

    public boolean isYaw() {
        return isYaw;
    }
}
