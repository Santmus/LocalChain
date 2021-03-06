package com.student_projetct.diplom_project.BlockChain.CalculateHash;

import com.google.gson.GsonBuilder;
import com.student_projetct.diplom_project.BlockChain.Block.iBlock;
import lombok.NonNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;

public class StringCalculateHash implements iSha256 {

    // переместить в Chaining
    public static ArrayList<iBlock> blockchain = new ArrayList<iBlock>();

    public static String getJson(Object o) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(o);
    }

    @NonNull
    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


