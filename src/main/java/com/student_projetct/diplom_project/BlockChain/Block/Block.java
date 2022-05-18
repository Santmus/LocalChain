package com.student_projetct.diplom_project.BlockChain.Block;

import com.student_projetct.diplom_project.BlockChain.CalculateHash.StringCalculateHash;
import com.student_projetct.diplom_project.BlockChain.Maining.PoW;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Date;

@ToString(exclude = {"difficultPrefix", "poW"})
@Getter()
public class Block implements iBlock {

    private String hash;
    private final String[] data;

    private final long timeStamp;

    private final Long index;
    private final iBlock previousBlock;

    private final int difficultPrefix = 2;

    // временное значение
    private final PoW poW = new PoW();

    public Block(String[] data) {
        this.index = 0L;
        this.data = data;
        this.timeStamp = new Date().getTime();
        this.previousBlock = null;
        this.hash = getHash();
    }

    public Block(String[] data, iBlock previousBlock) {
        this.index = previousBlock.getIndex() + 1L;
        this.data = data;
        this.timeStamp = new Date().getTime();
        this.previousBlock = previousBlock;
        this.hash = previousBlock.getHash();
    }

    @Override
    public iBlock getPreviousHash() {
        return previousBlock;
    }

    @Override
    public String getHash() {
        this.hash = poW.mineBlock(difficultPrefix, this);
        return hash;
    }


}