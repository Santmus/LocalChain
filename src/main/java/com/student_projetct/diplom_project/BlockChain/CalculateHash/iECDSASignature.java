package com.student_projetct.diplom_project.BlockChain.CalculateHash;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface iECDSASignature {

    public byte[] applyECDSASig(PrivateKey privateKey, String input);

    public boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature);
}
