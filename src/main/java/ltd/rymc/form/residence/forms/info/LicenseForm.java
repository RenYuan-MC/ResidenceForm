package ltd.rymc.form.residence.forms.info;

import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class LicenseForm extends RSimpleForm {
    private static final String LICENSE = "MIT License\n" +
                                          "\n" +
                                          "Copyright (c) 2022 RenYuan-MC\n" +
                                          "\n" +
                                          "Permission is hereby granted, free of charge, to any person obtaining a copy " +
                                          "of this software and associated documentation files (the \"Software\"), to deal " +
                                          "in the Software without restriction, including without limitation the rights " +
                                          "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell " +
                                          "copies of the Software, and to permit persons to whom the Software is " +
                                          "furnished to do so, subject to the following conditions:\n" +
                                          "\n" +
                                          "The above copyright notice and this permission notice shall be included in all " +
                                          "copies or substantial portions of the Software.\n" +
                                          "\n" +
                                          "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR " +
                                          "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, " +
                                          "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE " +
                                          "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER " +
                                          "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, " +
                                          "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE " +
                                          "SOFTWARE.\n";

    public LicenseForm(Player player, RForm previousForm) {
        super(player, previousForm);
        title("ResidenceForm 开源协议");
        content("§7" + LICENSE);
        button("返回上一级菜单");
    }

    @Override
    public void onValidResult(SimpleForm form, SimpleFormResponse response) {
        sendPrevious();
    }

    @Override
    public void onClosedOrInvalidResult(SimpleForm form, FormResponseResult<SimpleFormResponse> response) {
        sendPrevious();
    }
}
