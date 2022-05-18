package com.student_projetct.diplom_project.BlockChain.Maining;

import com.student_projetct.diplom_project.BlockChain.Block.Block;
import com.student_projetct.diplom_project.BlockChain.Block.iBlock;
import com.student_projetct.diplom_project.BlockChain.CalculateHash.StringCalculateHash;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class PoW {

    private int nonce;


    public String mineBlock(int difficultPrefix, iBlock iblock) {
        String hash = null;
        String prefixString = new String(new char[difficultPrefix]).replace('\0', '0');
        while (hash == null || !hash.substring(0, difficultPrefix).equals(prefixString)) {
            nonce++;
            hash = calculateBlockHash(iblock);
        }
        //log.debug("Block hash found: " + hash);
        return hash;
    }

    public String calculateBlockHash(iBlock iblock){
        String calculateHash = "0";
        if (iblock.getPreviousHash()!= null){
            calculateHash = StringCalculateHash.applySha256(iblock.getPreviousHash()
                    + Arrays.toString(iblock.getData())
                    + nonce
                    + Long.toString(iblock.getIndex())
                    + Long.toString(iblock.getTimeStamp()));
        } else {
            calculateHash = StringCalculateHash.applySha256(
                    Arrays.toString(iblock.getData())
                    + nonce
                    + Long.toString(iblock.getIndex())
                    + Long.toString(iblock.getTimeStamp()));
        }
        return calculateHash;
    }
}
