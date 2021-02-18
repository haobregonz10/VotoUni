package com.example.votouni.paillier;

import com.example.votouni.interfaces.PublicKey;

import java.math.BigInteger;

/**
 * The public key of the Paillier cryptosystem.
 *
 * @author Anirban Basu
 *
 */
public class PaillierPublicKey implements PublicKey {
    public PaillierPublicKey() {}

    public PaillierPublicKey(BigInteger n, BigInteger halfN,
                             BigInteger nSquared, int bitSize) {
        this.n = n;
        this.halfN = halfN;
        this.nSquared = nSquared;
        this.bitSize = bitSize;
    }

    private static final long serialVersionUID = 1L;
    protected BigInteger n = null, halfN = null, nSquared = null;
    protected int bitSize;
    public BigInteger getN() {
        return n;
    }
    public void setN(BigInteger n) {
        this.n = n;
    }
    public int getBitSize() {
        return bitSize;
    }
    public void setBitSize(int bitSize) {
        this.bitSize = bitSize;
    }

}
