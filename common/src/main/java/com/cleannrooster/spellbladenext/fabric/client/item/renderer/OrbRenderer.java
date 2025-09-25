package com.cleannrooster.spellbladenext.fabric.client.item.renderer;

import com.cleannrooster.spellbladenext.fabric.client.item.model.OrbModel;
import com.cleannrooster.spellbladenext.fabric.items.Orb;
import mod.azure.azurelib.common.api.client.renderer.GeoItemRenderer;
public class OrbRenderer extends GeoItemRenderer<Orb> {


    public OrbRenderer() {
        super(new OrbModel());

    }
}
