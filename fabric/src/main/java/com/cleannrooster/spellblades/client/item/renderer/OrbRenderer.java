package com.cleannrooster.spellblades.client.item.renderer;

import com.cleannrooster.spellblades.client.item.model.OrbModel;
import com.cleannrooster.spellblades.items.Orb;
import mod.azure.azurelib.common.api.client.renderer.GeoItemRenderer;
public class OrbRenderer extends GeoItemRenderer<Orb> {


    public OrbRenderer() {
        super(new OrbModel());

    }
}
