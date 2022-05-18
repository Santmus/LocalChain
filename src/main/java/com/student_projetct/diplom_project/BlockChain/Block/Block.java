package com.student_projetct.diplom_project.BlockChain.Block;

import com.student_projetct.diplom_project.BlockChain.CalculateHash.StringCalculateHash;
import com.student_projetct.diplom_project.BlockChain.Maining.PoW;
import com.student_projetct.diplom_project.BlockChain.Maining.iMaining;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Date;

@ToString(exclude = {"difficultPrefix", "iMaining"})
@Getter()
public class Block implements iBlock {

    private final String hash;
    private final String[] data;

    private final long timeStamp;

    private final Long index;
    private final iBlock previousBlock;

    private final int difficultPrefix = 5;

    // временное поле
    private final iMaining iMaining = new PoW();

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
        this.hash = getIMaining().mineBlock(difficultPrefix, previousBlock);
    }

    @Override
    public iBlock getPreviousHash() {
        return previousBlock;
    }

    @Override
    public String getHash() { return StringCalculateHash.applySha256(
                Arrays.toString(this.getData())
                        + this.getIndex()
                        + this.getTimeStamp());
    }


}