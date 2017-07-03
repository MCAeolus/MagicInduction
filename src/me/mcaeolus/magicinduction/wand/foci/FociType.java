package me.mcaeolus.magicinduction.wand.foci;

/**
 * Created by mcaeo on 7/1/2017.
 */
public enum FociType {
    NO_FOCUS(new NoneFoci()),
    FIRE_FOCUS(new FireFoci()),
    ICE_FOCUS(new IceFoci()),
    ENDER_FOCUS(new EnderFoci());

    public Foci FOCUS;

    FociType(Foci focus){
        this.FOCUS = focus;
    }

}
