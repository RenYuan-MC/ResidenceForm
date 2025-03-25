package ltd.rymc.form.residence.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings({"JavaReflectionMemberAccess", "unchecked"})
public class TeleportUtil {

    private static final Instance instance = initialize();

    public static Instance getInstance() {
        return instance;
    }

    private static boolean isFolia() {
        try {
            Bukkit.class.getMethod("getRegionScheduler");
            return true;
        } catch (NoSuchMethodException exception) {
            return false;
        }
    }

    private static Instance initialize() {
        Instance bukkitInstance = (entity, location) -> CompletableFuture.completedFuture(entity.teleport(location));

        if (!isFolia()) {
            return bukkitInstance;
        }

        try {
            Method teleportAsync = Entity.class.getMethod("teleportAsync", Location.class);
            teleportAsync.setAccessible(true);

            return (entity, location) -> {
                try {
                    return (CompletableFuture<Boolean>) teleportAsync.invoke(entity, location);
                } catch (IllegalAccessException | InvocationTargetException exception) {
                    throw new RuntimeException(exception);
                }
            };

        } catch (Exception exception) {
            return bukkitInstance;
        }

    }


    public interface Instance {
        CompletableFuture<Boolean> teleport(Entity entity, Location location);
    }
}
