package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import juggermod.JuggerMod;
import juggermod.characters.TheJuggernaut;
import juggermod.patches.AbstractCardEnum;
import juggermod.patches.MiracleCard;

public class Terminus extends MiracleCard {
    public static final String ID = "Terminus";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final int ATTACK_DMG = 20;
    private static final int UPGRADE_PLUS_DMG = 6;
    private static final int ENERGY_AMT = 1;
    private static final int UPGRADE_PLUS_ENERGY = 1;
    private static final int POOL = 1;

    public Terminus() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.TERMINUS), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ALL_ENEMY, POOL);
        this.isMultiDamage = true;
        this.magicNumber = this.baseMagicNumber = ENERGY_AMT;
        this.damage = this.baseDamage = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage,
                this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        if (miracleActive == true) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.magicNumber));
            miracleActive = false;
        }
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        miracleActive = false;
    }

    @Override
    public void triggerOnManualDiscard(){
        miracleActive = false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Terminus();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.upgradeMagicNumber(UPGRADE_PLUS_ENERGY);
        }
    }
}
