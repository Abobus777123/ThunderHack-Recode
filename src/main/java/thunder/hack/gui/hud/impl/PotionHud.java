package thunder.hack.gui.hud.impl;

import com.google.common.eventbus.Subscribe;
import thunder.hack.events.impl.Render2DEvent;
import thunder.hack.events.impl.RenderBlurEvent;
import thunder.hack.gui.font.FontRenderers;
import thunder.hack.gui.hud.HudElement;
import thunder.hack.modules.client.HudEditor;
import thunder.hack.utility.render.Render2DEngine;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.ArrayList;

public class PotionHud extends HudElement {
    public PotionHud() {
        super("Potions", "Potions", 100,100);
    }


    public static String getDuration(StatusEffectInstance pe) {
        if (pe.isInfinite()) {
            return "*:*";
        } else {
            int var1 = pe.getDuration();
            int mins = var1 / 1200;
            int sec = (var1 % 1200) / 20;

            return mins + ":" + sec;
        }
    }

    @Subscribe
    public void onRenderShader(RenderBlurEvent e){
        int y_offset1 = 0;
        float max_width = 40;

        ArrayList<StatusEffectInstance> effects = new ArrayList<>();

        for (StatusEffectInstance potionEffect : mc.player.getStatusEffects()) {
            if (potionEffect.getDuration() != 0) {
                effects.add(potionEffect);

                y_offset1 += 10;
                StatusEffect potion = potionEffect.getEffectType();
                String power = "";
                if (potionEffect.getAmplifier() == 0) {
                    power = "I";
                } else if (potionEffect.getAmplifier() == 1) {
                    power = "II";
                } else if (potionEffect.getAmplifier() == 2) {
                    power = "III";
                } else if (potionEffect.getAmplifier() == 3) {
                    power = "IV";
                } else if (potionEffect.getAmplifier() == 4) {
                    power = "V";
                }
                String s = potion.getName().getString() + " " + power;
                String s2 = getDuration(potionEffect) + "";

                float a = FontRenderers.sf_bold_mini.getStringWidth(s + "  " + s2) * 1.2f;
                if (a > max_width) {
                    max_width = a;
                }
            }
        }

      //  Render2DEngine.drawGradientBlurredShadow(e.getMatrixStack(), getPosX() + 1, getPosY() + 1, max_width - 2, 18 + y_offset1, 10, HudEditor.getColor(270), HudEditor.getColor(0), HudEditor.getColor(180), HudEditor.getColor(90));

        Render2DEngine.drawGradientGlow(e.getMatrixStack(), HudEditor.getColor(270), HudEditor.getColor(0), HudEditor.getColor(180), HudEditor.getColor(90),getPosX(), getPosY(), max_width, 20 + y_offset1,HudEditor.hudRound.getValue(),10);
        Render2DEngine.drawGradientRoundShader(e.getMatrixStack(), HudEditor.getColor(270), HudEditor.getColor(0), HudEditor.getColor(180), HudEditor.getColor(90), getPosX() - 0.5f, getPosY() - 0.5f, max_width + 1, 21 + y_offset1, HudEditor.hudRound.getValue());
        Render2DEngine.drawRoundShader(e.getMatrixStack(), getPosX(), getPosY(), max_width, 20 + y_offset1, HudEditor.hudRound.getValue(), HudEditor.plateColor.getValue().getColorObject());


        Render2DEngine.horizontalGradient(e.getMatrixStack(), getPosX() + 2, getPosY() + 13.7, getPosX() + 2 + max_width / 2, getPosY() + 14, Render2DEngine.injectAlpha(HudEditor.textColor.getValue().getColorObject(), 0).getRGB(), HudEditor.textColor.getValue().getColorObject().getRGB());
        Render2DEngine.horizontalGradient(e.getMatrixStack(), getPosX() + 2 + max_width / 2, getPosY() + 13.7, getPosX() + max_width - 2, getPosY() + 14, HudEditor.textColor.getValue().getColorObject().getRGB(), Render2DEngine.injectAlpha(HudEditor.textColor.getValue().getColorObject(), 0).getRGB());
    }



    @Subscribe
    public void onRender2D(Render2DEvent e) {
        super.onRender2D(e);
        int y_offset = 0;
        float max_width = 40;

        ArrayList<StatusEffectInstance> effects = new ArrayList<>();


        for (StatusEffectInstance potionEffect : mc.player.getStatusEffects()) {
            if (potionEffect.getDuration() != 0) {
                effects.add(potionEffect);
                StatusEffect potion = potionEffect.getEffectType();
                String power = "";
                if (potionEffect.getAmplifier() == 0) {
                    power = "I";
                } else if (potionEffect.getAmplifier() == 1) {
                    power = "II";
                } else if (potionEffect.getAmplifier() == 2) {
                    power = "III";
                } else if (potionEffect.getAmplifier() == 3) {
                    power = "IV";
                } else if (potionEffect.getAmplifier() == 4) {
                    power = "V";
                }
                String s = potion.getName().getString() + " " + power;
                String s2 = getDuration(potionEffect) + "";

                float a = FontRenderers.sf_bold_mini.getStringWidth(s + "  " + s2) * 1.2f;
                if (a > max_width) {
                    max_width = a;
                }
            }
        }

        FontRenderers.sf_bold.drawCenteredString(e.getMatrixStack(), "Potions", getPosX() + max_width / 2, getPosY() + 3, HudEditor.textColor.getValue().getColor());

        for (StatusEffectInstance potionEffect : effects) {
            StatusEffect potion = potionEffect.getEffectType();
            String power = "";
            if (potionEffect.getAmplifier() == 0) {
                power = "I";
            } else if (potionEffect.getAmplifier() == 1) {
                power = "II";
            } else if (potionEffect.getAmplifier() == 2) {
                power = "III";
            } else if (potionEffect.getAmplifier() == 3) {
                power = "IV";
            } else if (potionEffect.getAmplifier() == 4) {
                power = "V";
            }
            String s = potion.getName().getString() + " " + power;
            String s2 = getDuration(potionEffect) + "";

            FontRenderers.sf_bold_mini.drawString(e.getMatrixStack(),s + "  " + s2, getPosX() + 5, getPosY() + 20 + y_offset, HudEditor.textColor.getValue().getColor(), false);
            y_offset += 10;
        }
    }


}
