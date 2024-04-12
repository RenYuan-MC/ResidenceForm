package ltd.rymc.form.residence.form;

import ltd.rymc.form.residence.ResidenceForm;
import ltd.rymc.form.residence.language.Language;
import ltd.rymc.form.residence.utils.ArraysUtils;
import ltd.rymc.form.residence.utils.StringUtils;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

public abstract class RCustomForm implements RForm {

    protected CustomForm.Builder builder;
    protected final Player bukkitPlayer;
    protected final RForm previousForm;
    protected final FloodgatePlayer player;

    public RCustomForm(Player player, RForm previousForm){
        this.bukkitPlayer = player;
        this.player = FloodgateApi.getInstance().getPlayer(bukkitPlayer.getUniqueId());
        this.builder = CustomForm.builder();
        this.previousForm = previousForm;
        if (this.player == null) return;
        init();
    }

    private void init(){
        builder.validResultHandler(this::onValidResult);
        builder.closedOrInvalidResultHandler(this::onClosedOrInvalidResult);
    }

    public void input(String s1, String s2, String s3){
        builder.input(StringUtils.handleNewLineChar(s1), StringUtils.handleNewLineChar(s2), StringUtils.handleNewLineChar(s3));
    }

    public void input(String s1, String s2){
       input(s1, s2, "");
    }

    public void input(String s1){
        input(s1, "", "");
    }

    public void title(String title){
        builder.title(title);
    }

    public void toggle(String text){
        toggle(text, false);
    }

    public void toggle(String text,boolean state){
        builder.toggle(StringUtils.handleNewLineChar(text), state);
    }

    public void dropdown(String text, String... dropdown){
        builder.dropdown(StringUtils.handleNewLineChar(text),StringUtils.handleNewLineChar(dropdown));
    }

    public void stepSlider(String text, int state, String... states){
        builder.stepSlider(StringUtils.handleNewLineChar(text), state, StringUtils.handleNewLineChar(states));
    }

    public void dropdown(String text,String empty, String... dropdown){
        String[] tmp = ArraysUtils.rotate(dropdown, 1);
        tmp[0] = empty;
        dropdown(text,tmp);
    }

    public void onValidResult(CustomForm form, CustomFormResponse response){
    }

    public void onClosedOrInvalidResult(CustomForm form, FormResponseResult<CustomFormResponse> response){
    }

    public void refresh(){
    }

    @Override
    public void send() {
        if (player == null) return;
        player.sendForm(builder);
    }

    @Override
    public void sendPrevious() {
        if (player == null || previousForm == null) return;
        previousForm.send();
    }

    public String text(String key) {
        return ResidenceForm.getLanguage().text(key);
    }

    public Language.Section section(String key) {
        return ResidenceForm.getLanguage().section(key);
    }
}
