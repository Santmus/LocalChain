package com.student_projetct.diplom_project.BlockChain.Maining;

import com.student_projetct.diplom_project.BlockChain.Block.iBlock;

public interface iMaining {

    String mineBlock(int difficultPrefix, iBlock iblock);
    String calculateBlockHash(iBlock iblock);

}
