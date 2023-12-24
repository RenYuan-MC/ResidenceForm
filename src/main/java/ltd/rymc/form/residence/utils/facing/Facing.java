package ltd.rymc.form.residence.utils.facing;

import org.bukkit.Location;

public enum Facing {
    North(0, -90.0f, "东", true),
    South(1, 0.0f, "南", true),
    West(2, 90.0f, "西", true),
    East(3, 180.0f, "北", true),
    Up(4, -90.0f, "上", false),
    Down(5, 90.0f, "下", false),
    Unknown(-1, 0.0f, "未知", false);

    private final int ID;
    private final float yawOrPitch;
    private final String name;
    private final boolean isYaw;

    Facing(int ID, float yawOrPitch, String name, boolean isYaw) {
        this.ID = ID;
        this.yawOrPitch = yawOrPitch;
        this.name = name;
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
        if (-135.0 < yaw && yaw <= -45.0) return Facing.North;
        if (135.0 < yaw && yaw <= -135.0) return Facing.East;
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
        return name;
    }

    public boolean isYaw() {
        return isYaw;
    }
}
