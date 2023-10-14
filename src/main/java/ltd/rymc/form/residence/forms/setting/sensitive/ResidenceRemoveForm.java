package ltd.rymc.form.residence.forms.setting.sensitive;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.forms.setting.ResidenceNoPermissionForm;
import ltd.rymc.form.residence.utils.InputUtils;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;

public class ResidenceRemoveForm extends RCustomForm {

    private final ClaimedResidence claimedResidence;
    private static final Residence residence = Residence.getInstance();

    public ResidenceRemoveForm(Player player, RForm previousForm, ClaimedResidence claimedResidence) {
        super(player, previousForm);
        this.claimedResidence = claimedResidence;

        if (!claimedResidence.isOwner(player) && !player.isOp()) {
            new ResidenceNoPermissionForm(player,previousForm).send();
            return;
        }

        title("§8领地 §l" + claimedResidence.getName() + " §r§8转移");
        input("请输入玩家名", "完整玩家名(含大小写)");
        input("请再次输入玩家名以确认", "请再次输入玩家名");
    }


    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {
        String input = response.asInput(0);

        if (InputUtils.checkInput(input,claimedResidence.getName())) {
            sendPrevious();
            return;
        }

        new ResidenceConfirmForm(
                bukkitPlayer,
                previousForm,
                "§8领地 §l" + claimedResidence.getName() + " §r§8删除",
                () -> {

                    if (!claimedResidence.isOwner(bukkitPlayer) && !bukkitPlayer.isOp()) {
                        new ResidenceNoPermissionForm(bukkitPlayer,previousForm).send();
                        return;
                    }

                    residence.getResidenceManager().removeResidence(claimedResidence);
                }
        );

    }
}
