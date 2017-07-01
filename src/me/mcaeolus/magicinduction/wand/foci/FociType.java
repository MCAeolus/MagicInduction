package me.mcaeolus.magicinduction.wand.foci;

/**
 * Created by mcaeo on 7/1/2017.
 */
public enum FociType {
    NONE(new NoneFoci()),
    FIRE(new FireFoci()),
    ICE(new IceFoci()),
    ENDER(new EnderFoci());

    public Foci FOCUS;

    FociType(Foci focus){
        this.FOCUS = focus;
    }

}
