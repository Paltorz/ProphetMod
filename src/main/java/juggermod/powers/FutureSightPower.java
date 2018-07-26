package juggermod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import juggermod.JuggerMod;

import java.util.ArrayList;

public class FutureSightPower extends AbstractPower{
    public static final String POWER_ID = "Future Sight";
    public static final String NAME = "Future Sight";
    public static final String ONETURN = " turn, draw ";
    public static final String[] DESCRIPTIONS = new String[]{
            "After ",
            " turns, draw ",
            " cards. NL \n"
    };

    ArrayList<Integer> array = new ArrayList<>();
    private static final int COUNTDOWN = 2;

    public FutureSightPower(AbstractCreature owner, int countdown, int draw) {
        this.name = NAME;
        this.ID = "Future Sight";
        this.owner = owner;
        this.amount = 1;
        this.array.add(countdown);
        this.array.add(draw);
        this.description = DESCRIPTIONS[0];
        this.updateDescription();
        this.img = JuggerMod.getHeavierBodyPowerTexture();
    }

    @Override
    public void atStartOfTurn() {
        for (int i = 0; i < array.size(); i+=2) {
            if (array.get(i) == 1){
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, array.get(i+1)));
                array.remove(i+1);
                array.remove(i);
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "Future Sight", 1));
                i -= 2;
            }else{
                array.set(i, array.get(i)-1);
            }
        }
        this.updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += 1;
        this.array.add(COUNTDOWN);
        this.array.add(stackAmount);
    }

    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.size(); i += 2) {
            sb.append(DESCRIPTIONS[0]);
            sb.append(array.get(i));
            if(array.get(i) == 1){
                sb.append(ONETURN);
            }else{
                sb.append(DESCRIPTIONS[1]);
            }
            sb.append(array.get(i + 1));
            sb.append(DESCRIPTIONS[2]);
        }
        this.description = sb.toString();
    }
}
