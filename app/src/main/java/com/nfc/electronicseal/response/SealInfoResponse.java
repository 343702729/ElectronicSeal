package com.nfc.electronicseal.response;

import com.nfc.electronicseal.node.SealItemNode;

import java.util.List;

public class SealInfoResponse extends Response<List<SealItemNode>> {
    private SealItemNode electronic;

    public SealItemNode getElectronic() {
        return electronic;
    }

    public void setElectronic(SealItemNode electronic) {
        this.electronic = electronic;
    }
}
