package juggermod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import juggermod.JuggerMod;
import juggermod.actions.common.ModifyMagicNumberAction;
import juggermod.patches.AbstractCardEnum;
import juggermod.patches.MiracleCard;

public class Harvest extends MiracleCard {
    private static final String ID = "Harvest";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final int MIRACLE_UP = 1;
    private static final int DRAW = 1;
    private static final int MIRACLE_AMT = 2;
    private static final int POOL = 1;

    public Harvest() {
        super (ID, NAME, JuggerMod.makePath(JuggerMod.HARVEST), COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.COPPER,
                AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
        this.magicNumber = this.baseMagicNumber = MIRACLE_AMT;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
                    miracleActive = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (miracleActive == true)
        {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
            miracleActive = false;
        }else{
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, DRAW));
        }
    }

    @Override
    public void triggerOnManualDiscard(){
        miracleActive = false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Harvest();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(MIRACLE_UP);
        }
    }
}
