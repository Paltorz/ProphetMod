package juggermod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import juggermod.JuggerMod;
import juggermod.patches.MiracleCard;

public class StrengthenedFaithPower extends AbstractPower {
    public static final String POWER_ID = "Strengthened Faith";
    public static final String NAME = "Strengthened Faith";
    public static final String[] DESCRIPTIONS = new String[]{
            "Whenever you play a card with its Miracle active, gain ",
            " Strength."
    };


    public StrengthenedFaithPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "Strengthened Faith";
        this.owner = owner;
        this.amount = 1;
        this.updateDescription();
        this.img = JuggerMod.getCombatTrainingPowerTexture();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card instanceof MiracleCard) {
            if (((MiracleCard) card).miracleActive == true) {
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(AbstractDungeon.player, this.amount), this.amount));
            }
        }
    }


    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
