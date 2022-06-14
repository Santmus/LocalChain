package com.student_projetct.diplom_project.BlockChain.Maining;

import com.student_projetct.diplom_project.BlockChain.Block.iBlock;
import com.student_projetct.diplom_project.BlockChain.CalculateHash.StringCalculateHash;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class PoW implements iMaining{

    private int nonce;

    @Override
    public String mineBlock(int difficultPrefix, iBlock iblock) {
        String hash = null;
        String prefixString = new String(new char[difficultPrefix]).replace('\0', '0');
        while (hash == null || !hash.substring(0, difficultPrefix).equals(prefixString)) {
            nonce++;
            hash = calculateBlockHash(iblock);
        }
        log.debug("Block hash found: " + hash);
        return hash;
    }

    @Override
    public String calculateBlockHash(iBlock iblock){
        return StringCalculateHash.applySha256(iblock.getPreviousHash()
                    + Arrays.toString(iblock.getData())
                    + nonce
                    + iblock.getIndex()
                    + iblock.getTimeStamp());
    }
}
