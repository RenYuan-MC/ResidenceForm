package ltd.rymc.form.residence.configs;

import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfKey;

public interface Language {

    @ConfKey("reload")
    String reload();

    @ConfComments({""})
    @ConfKey("form-dropdown")
    String formDropdown();

    @ConfComments({""})
    @ConfKey("form-input")
    String formInput();

    @ConfComments({""})
    @ConfKey("form-full-res-name")
    String formFullResName();

    @ConfComments({""})
    @ConfKey("form-choose-res")
    String formChooseRes();

    @ConfComments({""})
    @ConfKey("main-title")
    String mainTitle();

    @ConfKey("main-content")
    String mainContent();

    @ConfKey("main-teleport")
    String mainTeleport();

    @ConfKey("main-manage")
    String mainManage();

    @ConfKey("main-create")
    String mainCreate();

    @ConfKey("main-tool")
    String mainTool();

    @ConfKey("main-info")
    String mainInfo();

    @ConfComments({""})
    @ConfKey("teleport-title")
    String teleportTitle();

    @ConfComments({""})
    @ConfKey("setting-select-title")
    String settingSelectTitle();

    @ConfKey("setting-select-dropdown")
    String settingSelectDropdown();

    @ConfKey("setting-select-choose")
    String settingSelectChoose();

    @ConfKey("setting-title")
    String settingTitle();

    @ConfKey("setting-permission-set")
    String settingPermissionSet();

    @ConfKey("setting-player-permission-set")
    String settingPlayerPermissionSet();

    @ConfKey("setting-trusted-player-manage")
    String settingTrustedPlayerManage();

}
