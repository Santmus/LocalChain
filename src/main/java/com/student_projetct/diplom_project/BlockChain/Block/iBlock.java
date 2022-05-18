package com.student_projetct.diplom_project.BlockChain.Block;

public interface iBlock {

    iBlock getPreviousHash();
    Long getIndex();
    String getHash();
    String[] getData();
    long getTimeStamp();
}
