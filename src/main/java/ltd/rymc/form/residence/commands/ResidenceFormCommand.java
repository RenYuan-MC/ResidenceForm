package ltd.rymc.form.residence.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import ltd.rymc.form.residence.ResidenceForm;
import ltd.rymc.form.residence.forms.MainResidenceForm;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("rform|residenceform")
@Description("领地Form菜单")
public class ResidenceFormCommand extends BaseCommand {

    @Default
    public void form(CommandSender sender){
        if (!(sender instanceof Player)) {
            return;
        }

        new MainResidenceForm((Player) sender, null).send();
    }

    @Subcommand("reload")
    @CommandPermission("tpaform.reload")
    public void reload(CommandSender sender){
        ResidenceForm.getMainConfigManager().reloadConfig();
        sender.sendMessage(ResidenceForm.getLanguage().reload());
    }
}
