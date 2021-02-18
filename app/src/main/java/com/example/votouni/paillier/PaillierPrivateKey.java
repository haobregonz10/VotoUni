package com.example.votouni.paillier;

import com.example.votouni.interfaces.PrivateKey;

import java.math.BigInteger;



/**
 * A private key of the Paillier cryptosystem. Note that the private key is a
 * subclass of the public key.
 *
 * @author Anirban Basu
 *
 */
public class PaillierPrivateKey extends PaillierPublicKey implements PrivateKey {
    public PaillierPrivateKey() {}

    public PaillierPrivateKey(BigInteger n, BigInteger halfN,
                              BigInteger nSquared, int bitSize, BigInteger lambda, BigInteger mu) {
        super(n, halfN, nSquared, bitSize);
        this.lambda = lambda;
        this.mu = mu;
    }

    private static final long serialVersionUID = 1L;
    protected BigInteger lambda = null, mu = null;
    public BigInteger getLambda() {
        return lambda;
    }
    public void setLambda(BigInteger lambda) {
        this.lambda = lambda;
    }
    public BigInteger getMu() {
        return mu;
    }
    public void setMu(BigInteger mu) {
        this.mu = mu;
    }
}
